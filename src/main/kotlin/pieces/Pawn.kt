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
        val possibleMoves = mutableSetOf<Location>()

        if (nextRow in 0..7 && col in 0..7) {
            val nextSquare = chessBoard.getPiece(Location(nextRow, col))
            if (nextSquare is EmptySpot) {
                // pawn can move one square forward if it is unoccupied
                possibleMoves.add(Location(nextRow, col))
//                if(promote && (nextRow == 0 || nextRow == 7)) {
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.QUEEN))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.ROOK))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.BISHOP))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.KNIGHT))
//                }
            }
            if ((turn.getTurn() && startLocation.row() == 1) || (!turn.getTurn() && startLocation.row() == 6)) {
                val nextnextSquare = chessBoard.getPiece(Location(nextRow + direction, col))
                if (nextnextSquare is EmptySpot) {
                    possibleMoves.add(Location(nextRow + direction, col))
                }
            }

            if (col in 1..6) {
                if (nextRow in 1..6) {
                    val leftDCoords = Location(nextRow, col - direction)
                    val leftDPiece = chessBoard.getPiece(leftDCoords)
                    if (leftDPiece.color == turn.enemyColor()) {
                        possibleMoves.add(leftDCoords)
                    }
                    if (leftDPiece.color == Color.E) {
                        var behindDPiece =
                            chessBoard.getPiece(Location(leftDCoords.row() - direction, leftDCoords.column()))
                        if (behindDPiece.type == PieceType.PAWN) {
                            behindDPiece = behindDPiece as Pawn
                            if (behindDPiece.canBeEnPassant && behindDPiece.color == turn.enemyColor()) {
                                possibleMoves.add(leftDCoords)
                            }
                        }
                    }

                    val rightDCoords = Location(nextRow, col + direction)
                    val rightDPiece = chessBoard.getPiece(leftDCoords)
                    if (rightDPiece.color == turn.enemyColor()) {
                        possibleMoves.add(rightDCoords)
                    }

                    if (rightDPiece.color == Color.E) {
                        var behindRPiece =
                            chessBoard.getPiece(Location(rightDCoords.row() - direction, rightDCoords.column()))
                        if (behindRPiece.type == PieceType.PAWN) {
                            behindRPiece = behindRPiece as Pawn
                            if (behindRPiece.canBeEnPassant && behindRPiece.color == turn.enemyColor()) {
                                possibleMoves.add(rightDCoords)
                            }
                        }
                    }
                }
            }
        }
        return possibleMoves
    }

    override fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        val possibleMoves = canMove(chessMove.startLocation(), chessBoard, turn)
        if (chessMove.endLocation() in possibleMoves) {
            chessBoard.setPiece(chessMove.endLocation(), chessBoard.getPiece(chessMove.startLocation()))
            chessBoard.setPiece(chessMove.startLocation(), EmptySpot())

        }
        return false
    }
}
