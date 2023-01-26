package pieces

import chess.ChessBoard
import chess.Turn

class Rook(
    override var color: Color,
) : IChessPiece {
    override val type: PieceType = PieceType.ROOK

    //    /*
//    In this version of the function, I added an additional parameter called direction which is a string that indicates
//    in which direction the function should check for possible moves, it could be "left" or "right".
//    You can call this function twice, once with direction "left" and once with direction "right" to get all the possible
//    moves for the Rook.
//    Please note that this is a simplified version of the Rook movement, that only consider the standard Rook movements,
//    the actual rule for Rooks may depend on the game you are implementing.
//     */
    override fun canMove(coordinates: Pair<Int, Int>, chessBoard: ChessBoard, turn: Turn): Set<Pair<Int, Int>> {
        val possibleMoves = mutableSetOf<Pair<Int, Int>>()
//        when(direction) {
//            "left" -> {
//                // Check squares to the left
//                for (c in col - 1 downTo 0) {
//                    val square = board[row][c]
//                    if (square == null) {
//                        possibleMoves.add(Pair(row, c))
//                    } else if (square.color != color) {
//                        possibleMoves.add(Pair(row, c))
//                        break
//                    } else {
//                        break
//                    }
//                }
//            }
//            "right" -> {
//                // Check squares to the right
//                for (c in col + 1..7) {
//                    val square = board[row][c]
//                    if (square == null) {
//                        possibleMoves.add(Pair(row, c))
//                    } else if (square.color != color) {
//                        possibleMoves.add(Pair(row, c))
//                        break
//                    } else {
//                        break
//                    }
//                }
//            }
//        }
        return possibleMoves
    }
}