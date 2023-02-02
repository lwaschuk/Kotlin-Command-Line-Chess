package pieces

import chess.ChessBoard
import chess.ChessMove
import chess.Location
import chess.Turn

class Pawn(
    override var color: Color,
    var canBeEnPassant: Boolean = false,
) : IChessPiece {
    override val type: PieceType = PieceType.PAWN

    override fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val direction = if (turn.getColor() == Color.W) 1 else -1
        val nextRow = startLocation.row() + direction
        val col = startLocation.column()

        return if (nextRow in 0..7 && col in 0..7) {
            val nextSquare = chessBoard.getPiece(Location(nextRow, col))

            val possibleMoves = mutableSetOf<Location>()

            if (nextSquare is EmptySpot) {
                possibleMoves.add(Location(nextRow, col))
            }

            if (isFirstMove(turn, startLocation.row())) {
                val nextNextSquare = chessBoard.getPiece(Location(nextRow + direction, col))
                if (nextNextSquare is EmptySpot) {
                    possibleMoves.add(Location(nextRow + direction, col))
                }
            }

            val attackMoves = getAttackMoves(chessBoard, turn, direction, nextRow, col)
            possibleMoves.addAll(attackMoves)
            possibleMoves
        } else {
            emptySet()
        }
    }

    private fun isFirstMove(turn: Turn, row: Int) = (turn.getColor() == Color.W && row == 1)
            || (turn.getColor() == Color.B && row == 6)

    private fun getAttackMoves(
        chessBoard: ChessBoard,
        turn: Turn,
        direction: Int,
        nextRow: Int,
        col: Int
    ): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

        for (deltaCol in listOf(-1, 1)) {
            val attackCol = col + deltaCol
            if (attackCol in ChessBoard.COL_START..ChessBoard.COL_END) {
                val attackCoords = Location(nextRow, attackCol)
                val attackPiece = chessBoard.getPiece(attackCoords)

                if (attackPiece.color == turn.enemyColor()) {
                    possibleMoves.add(attackCoords)
                } else if (attackPiece.color == Color.E) {
                    val behindPiece = chessBoard.getPiece(Location(attackCoords.row() - direction, attackCoords.column()))
                    if (behindPiece.type == PieceType.PAWN && behindPiece.color == turn.enemyColor()) {
                        val pawn = behindPiece as Pawn
                        if (pawn.canBeEnPassant) {
                            possibleMoves.add(attackCoords)
                        }
                    }
                }
            }
        }

        return possibleMoves
    }

    override fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
//        if (chessMove.startLocation() in canMove(chessMove.startLocation(), chessBoard, turn)) return false
        val possibleMoves = canMove(chessMove.startLocation(), chessBoard, turn)
        for (move in possibleMoves) {
            if (move.value() == chessMove.endLocation().value()) {
                val direction = if (turn.getColor() == Color.W) 1 else -1
                val behindEndLocation =
                    Location(chessMove.endLocation().row() - direction, chessMove.endLocation().column())
                val behindEndPiece = chessBoard.getPiece(behindEndLocation)

                return when {
                    ((behindEndPiece is Pawn) && (chessMove.startLocation()
                        .column() != chessMove.endLocation().column())
                            && (behindEndPiece.canBeEnPassant) && (behindEndPiece.color == turn.enemyColor())) -> {

                        chessBoard.setPiece(chessMove.endLocation(), chessBoard.getPiece(chessMove.startLocation()))
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        chessBoard.setPiece(behindEndLocation, EmptySpot())
                        true
                    }

                    (kotlin.math.abs(chessMove.startLocation().row() - chessMove.endLocation().row()) == 2
                            && chessBoard.getPiece(chessMove.startLocation()).type == PieceType.PAWN) -> {
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

    override fun print(): String {
        return if (this.color == Color.W){
            "\u2659"
        } else {
            "\u265F"
        }
    }
}
