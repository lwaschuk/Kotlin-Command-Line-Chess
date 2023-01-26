package pieces

import chess.ChessBoard
import chess.Turn

class Pawn(
    override var color: Color,
    var canBeEnPassant: Boolean = false,
) : IChessPiece {
    override val type: PieceType = PieceType.PAWN
    override fun canMove(coordinates: Pair<Int, Int>, chessBoard: ChessBoard, turn: Turn): Set<Pair<Int, Int>> {
        val direction = if (turn.getColor() == Color.W) 1 else -1
        val nextRow = coordinates.first + direction
        val col = coordinates.second
        val possibleMoves = mutableSetOf<Pair<Int, Int>>()

        if (nextRow in 0..7 && col in 0..7) {
            val nextSquare = chessBoard.getPiece(Pair(nextRow, col))
            if (nextSquare is EmptySpot) {
                // pawn can move one square forward if it is unoccupied
                possibleMoves.add(Pair(nextRow, col))
//                if(promote && (nextRow == 0 || nextRow == 7)) {
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.QUEEN))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.ROOK))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.BISHOP))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.KNIGHT))
//                }
            }
            if ((turn.getTurn() && coordinates.first == 1) || (!turn.getTurn() && coordinates.first == 6)) {
                val nextnextSquare = chessBoard.getPiece(Pair(nextRow + direction, col))
                if (nextnextSquare is EmptySpot) {
                    possibleMoves.add(Pair(nextRow + direction, col))
                }
            }

            if (col in 1..6) {
                if (nextRow in 1..6) {
                    val leftDCoords = Pair(nextRow, col - direction)
                    val leftDPiece = chessBoard.getPiece(leftDCoords)
                    if (leftDPiece.color == turn.enemyColor()) {
                        possibleMoves.add(leftDCoords)
                    }
                    if (leftDPiece.color == Color.E) {
                        var behindDPiece = chessBoard.getPiece(Pair(leftDCoords.first - direction, leftDCoords.second))
                        if (behindDPiece.type == PieceType.PAWN) {
                            behindDPiece = behindDPiece as Pawn
                            if (behindDPiece.canBeEnPassant && behindDPiece.color == turn.enemyColor()) {
                                possibleMoves.add(leftDCoords)
                            }
                        }
                    }

                    val rightDCoords = Pair(nextRow, col + direction)
                    val rightDPiece = chessBoard.getPiece(leftDCoords)
                    if (rightDPiece.color == turn.enemyColor()) {
                        possibleMoves.add(rightDCoords)
                    }

                    if (rightDPiece.color == Color.E) {
                        var behindRPiece =
                            chessBoard.getPiece(Pair(rightDCoords.first - direction, rightDCoords.second))
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
}
