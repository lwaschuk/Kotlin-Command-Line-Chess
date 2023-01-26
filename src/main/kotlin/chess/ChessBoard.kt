package chess

import pieces.*
import kotlin.math.abs

class ChessBoard {
    private var board = Array(8) { Array<IChessPiece>(8) { EmptySpot() } }
    private var verifier = Verifier()

    fun startGame() {
        initPawns()
        render()
    }

    fun render() {
        println("  +---+---+---+---+---+---+---+---+")
        for (row in 7 downTo 0) {
            print("${row + 1} ")
            for (col in 0 until 8) {
                if (this.board[row][col].type != PieceType.EMPTY) {
                    print("| ${this.board[row][col].color} ")
                } else {
                    print("|   ")
                }
            }
            println("|")
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h")
    }

    private fun initPawns() {
        for (col in 0 until 8) {
            setPiece(Pair(1, col), Pawn(Color.W))
            setPiece(Pair(6, col), Pawn(Color.B))
        }

    }

    fun gameOver(turn: Turn): Boolean {
        return reachEnd() || elimAll() || stalemate(turn)
    }

    private fun elimAll(): Boolean {
        var whiteFound = false
        var blackFound = false

        for (row in 0 until 8) {
            for (col in 0 until 8) {
                when (this.board[row][col].color) {
                    Color.B -> blackFound = true
                    Color.W -> whiteFound = true
                    else -> {}
                }
            }
        }
        if (whiteFound && !blackFound) {
            println("\nWhite Wins!")
            return true
        }
        if (blackFound && !whiteFound) {
            println("\nBlack Wins!")
            return true
        }
        return false
    }

    private fun reachEnd(): Boolean {
        for (col in 0 until this.board[0].size) {
            if (this.board[0][col].color == Color.B) {
                println("\nBlack Wins!")
                return true
            }
            if (this.board[7][col].color == Color.W) {
                println("\nWhite Wins!")
                return true
            }
        }
        return false
    }

    private fun stalemate(turn: Turn): Boolean {
        for (row in 0 until 7) {
            for (col in 0 until 8) {
                val piece = getPiece(Pair(row, col))
                if (piece.color == turn.getColor() && pawnCanMove(row, col, turn)) {
                    return false
                }
            }
        }
        println("\nStalemate!")
        return true
    }

    private fun pawnCanMove(row: Int, col: Int, turn: Turn): Boolean {
        val direction = if (turn.getColor() == Color.W) 1 else -1
        val nextRow = row + direction
        val nextSquare = getPiece(Pair(nextRow, col))

        return when (nextSquare.color) {
            Color.E -> true
            turn.getColor() -> false
            else -> col in 1..6
        }
    }

    fun move(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        val (startCoordinates, endCoordinates) = chessMove

        if (verifier.verify(chessMove, turn) && checkOrigin(chessMove, PieceType.PAWN, turn) && canMove(
                chessMove,
                turn
            )
        ) {

            setPiece(startCoordinates, EmptySpot())
            if (pawnCanEnPassant(chessMove, turn)) {
                val behindEnd = if (turn.getTurn()) {
                    endCoordinates.copy(first = endCoordinates.first - 1)
                } else {
                    endCoordinates.copy(first = endCoordinates.first + 1)
                }
                setPiece(behindEnd, EmptySpot())
            }

            resetEnPassant()

            if (abs(startCoordinates.first - endCoordinates.first) == 2) {
                setPiece(endCoordinates, Pawn(turn.getColor(), true))
            } else {
                setPiece(endCoordinates, Pawn(turn.getColor()))
            }
            return true
        }
        return false
    }

    private fun resetEnPassant() {
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                this.board[row][col].let {
                    if (it is Pawn) {
                        it.canBeEnPassant = false
                    }
                }
            }
        }
    }

    private fun checkOrigin(
        chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>,
        pieceType: PieceType,
        turn: Turn,
    ): Boolean {
        val startCoordinates = chessMove.first

        // check starting coordinates for a pawn
        return getPiece(startCoordinates).type == pieceType
                && getPiece(startCoordinates).color == turn.getColor()
    }

    private fun canMove(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        return pawnCanMoveStraight(chessMove) || pawnCanTake(chessMove, turn) || pawnCanEnPassant(chessMove, turn)
    }

    private fun pawnCanMoveStraight(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>): Boolean {
        val startCoordinates = chessMove.first
        val endCoordinates = chessMove.second

        return getPiece(endCoordinates).type == PieceType.EMPTY && startCoordinates.second == endCoordinates.second
    }

    private fun pawnCanTake(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        val startCoordinates = chessMove.first
        val endCoordinates = chessMove.second

        return getPiece(endCoordinates).type == PieceType.PAWN && getPiece(endCoordinates).color == turn.enemyColor()
                && startCoordinates.second != endCoordinates.second
    }

    private fun pawnCanEnPassant(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        val startCoordinates = chessMove.first
        val endCoordinates = chessMove.second
        var firstStatement = false
        var secondStatement = false
        val behindEnd: Pair<Int, Int> = if (turn.getTurn()) {
            Pair(endCoordinates.first - 1, endCoordinates.second)
        } else {
            Pair(endCoordinates.first + 1, endCoordinates.second)
        }

        var pieceBehindEnd = getPiece(behindEnd)
        val pieceAtEnd = getPiece(endCoordinates)

        if (pieceBehindEnd.type == PieceType.PAWN) {
            pieceBehindEnd = pieceBehindEnd as Pawn

            firstStatement =
                pieceAtEnd.type == PieceType.EMPTY && startCoordinates.second != endCoordinates.second
            secondStatement =
                pieceBehindEnd.color == turn.enemyColor() && pieceBehindEnd.canBeEnPassant
        }


        return firstStatement && secondStatement
    }

    fun sourceCoordinateVerifier(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        val startCoordinates = chessMove.first

        return getPiece(startCoordinates).type == PieceType.PAWN && getPiece(startCoordinates).color == turn.getColor()
    }

    fun getPiece(coordinates: Pair<Int, Int>): IChessPiece {
        return this.board[coordinates.first][coordinates.second]
    }

    fun getPieceClass(coordinates: Pair<Int, Int>): Class<out IChessPiece> {
        return when (this.board[coordinates.first][coordinates.second]) {
            is Pawn -> Pawn::class.java
            is Rook -> Rook::class.java
            is Knight -> Knight::class.java
            is Bishop -> Bishop::class.java
            is Queen -> Queen::class.java
            is King -> King::class.java
            is EmptySpot -> EmptySpot::class.java
            else -> throw IllegalArgumentException("Error casting")
        }
    }

    private fun setPiece(coordinates: Pair<Int, Int>, piece: IChessPiece) {
        this.board[coordinates.first][coordinates.second] = piece
    }
}