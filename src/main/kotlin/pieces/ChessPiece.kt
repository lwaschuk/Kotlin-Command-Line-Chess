package pieces

import game_helpers.ChessBoard
import game_helpers.ChessMove
import game_helpers.Location
import game_helpers.Turn

abstract class ChessPiece(color: Color, pieceType: PieceType, directions: List<Location>) {
    private var directions: List<Location>
    var color: Color
    var pieceType: PieceType

    init {
        this.directions = directions
        this.color = color
        this.pieceType = pieceType
    }

    abstract fun print(): String

    fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        return when (this.pieceType) {
            PieceType.PAWN -> pawnMove(chessMove, chessBoard, turn)
            PieceType.EMPTY -> {
                println("ERROR: Trying to move a empty spot...")
                false
            }
            else -> notPawnMove(chessMove, chessBoard, turn)
        }
    }

    private fun notPawnMove(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        val possibleMoves = possibleMoves(chessMove.startLocation(), chessBoard, turn)
        for (move in possibleMoves) {
            if (chessMove.endLocation() == move) {
                chessBoard.setPiece(chessMove.endLocation(), chessBoard.getPiece(chessMove.startLocation()))
                chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                return true
            }
        }
        return false
    }

    private fun pawnMove(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        val possibleMoves = possibleMoves(chessMove.startLocation(), chessBoard, turn)
        for (move in possibleMoves) {
            if (move.value() == chessMove.endLocation().value()) {
                val movement = if (turn.getColor() == Color.W) 1 else -1
                val behindEndLocation =
                    Location(chessMove.endLocation().row() - movement, chessMove.endLocation().column())
                val behindEndPiece = chessBoard.getPiece(behindEndLocation)

                return when {
                    (canPromote(chessMove,turn)) -> {
                        var newPieceString: String
                        do {
                            newPieceString = getInput()
                        } while (!verifyInput(newPieceString))

                        chessBoard.setPiece(chessMove.endLocation(), convertToPiece(newPieceString, turn))
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        true
                    }

                    ((behindEndPiece is Pawn) && (chessMove.startLocation()
                        .column() != chessMove.endLocation().column())
                            && (behindEndPiece.canBeEnPassant) && (behindEndPiece.color == turn.enemyColor())) -> {

                        chessBoard.setPiece(chessMove.endLocation(), chessBoard.getPiece(chessMove.startLocation()))
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        chessBoard.setPiece(behindEndLocation, EmptySpot())
                        true
                    }

                    (kotlin.math.abs(chessMove.startLocation().row() - chessMove.endLocation().row()) == 2
                            && chessBoard.getPiece(chessMove.startLocation()).pieceType == PieceType.PAWN) -> {
                        val enPassant = Pawn(turn.getColor(), true)
                        chessBoard.setPiece(chessMove.endLocation(), enPassant)
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        true
                    }

                    else -> {
                        chessBoard.setPiece(chessMove.endLocation(), chessBoard.getPiece(chessMove.startLocation()))
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        true
                    }
                }
            }
        }
        return false
    }

    private fun canPromote(chessMove: ChessMove, turn: Turn): Boolean {
        val endRow = if (turn.getColor() == Color.W) ChessBoard.ROW_END else ChessBoard.ROW_START
        if (chessMove.endLocation().row() == endRow) {
            return true
        }
        return false
    }

    private fun getInput(): String {
        print("Promote Pawn (B, R, K, Q):\n> ")
        return readln().uppercase()
    }

    private fun verifyInput(input: String): Boolean {
        val types = listOf("B", "R", "K", "Q")
        return input in types
    }

    private fun convertToPiece(input: String, turn: Turn): ChessPiece {
        return when (input) {
            "B" -> Bishop(turn.getColor())
            "R" -> Rook(turn.getColor())
            "K" -> Knight(turn.getColor())
            "Q" -> Queen(turn.getColor())
            else -> EmptySpot()
        }
    }

    fun possibleMoves(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

        when (this.pieceType) {
            PieceType.PAWN -> possibleMoves.addAll(pawnMovement(startLocation, chessBoard, turn))
            PieceType.QUEEN -> possibleMoves.addAll(additiveMovement(startLocation, chessBoard, turn))
            PieceType.BISHOP -> possibleMoves.addAll(additiveMovement(startLocation, chessBoard, turn))
            PieceType.ROOK -> possibleMoves.addAll(additiveMovement(startLocation, chessBoard, turn))
            PieceType.KING -> possibleMoves.addAll(staticMovement(startLocation, chessBoard, turn))
            PieceType.KNIGHT -> possibleMoves.addAll(staticMovement(startLocation, chessBoard, turn))
            else -> {
                println("ERROR: Piece type cannot be found...")
            }
        }

        return possibleMoves
    }

    private fun additiveMovement(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()
        for (direction in directions) {
            var nextLocation = startLocation + direction
            while (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if (nextPiece.color == turn.getColor()) {
                    break
                }
                else if (nextPiece.color == turn.enemyColor()) {
                    possibleMoves.add(nextLocation)
                    break
                }
                else if (nextPiece.color == Color.E) {
                    possibleMoves.add(nextLocation)
                    nextLocation += direction
                }
            }
        }
        return possibleMoves
    }

    private fun pawnMovement(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()
        val movement = if (turn.getColor() == Color.W) 1 else -1

        possibleMoves.addAll(getStraightMoves(chessBoard, turn, movement, startLocation))
        possibleMoves.addAll(getAttackMoves(chessBoard, turn, movement, startLocation))

        return possibleMoves
    }

    private fun staticMovement(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()
        for (direction in directions) {
            val nextLocation = startLocation + direction
            if (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if (nextPiece.color == turn.enemyColor()) {
                    possibleMoves.add(nextLocation)
                }
                else if (nextPiece.color == Color.E) {
                    possibleMoves.add(nextLocation)
                }
            }
        }
        return possibleMoves
    }

    private fun getStraightMoves(chessBoard: ChessBoard, turn: Turn, movement: Int, startLocation: Location): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

        val straightDirections = mutableListOf<Location>()

        val startingRow = if (turn.getColor() == Color.W) 1 else 6

        if (startLocation.row() == startingRow) {
            straightDirections.add(Location(2 * movement, 0))
        }
        straightDirections.add(Location(1 * movement, 0))

        for (direction in straightDirections) {
            val nextLocation = startLocation + direction
            if (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if (nextPiece is EmptySpot) {
                    possibleMoves.add(nextLocation)
                }
            }
        }
        return possibleMoves
    }

    private fun getAttackMoves(chessBoard: ChessBoard, turn: Turn, movement: Int, startLocation: Location): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

        val attackDirections = listOf(
            Location(movement, -1),
            Location(movement, 1),
        )

        for (direction in attackDirections) {
            val nextLocation = startLocation + direction
            if (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if ((nextPiece !is EmptySpot) && (nextPiece.color == turn.enemyColor())) {
                    possibleMoves.add(nextLocation)
                }
                if (nextPiece is EmptySpot) {
                    val behindPiece = chessBoard.getPiece(Location(nextLocation.row() - movement, nextLocation.column()))
                    if (behindPiece.pieceType == PieceType.PAWN && behindPiece.color == turn.enemyColor()) {
                        val pawn = behindPiece as Pawn
                        if (pawn.canBeEnPassant) {
                            possibleMoves.add(nextLocation)
                        }
                    }
                }
            }
        }
        return possibleMoves
    }
}