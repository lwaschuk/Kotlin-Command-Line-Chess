package pieces

import chess.ChessBoard
import chess.ChessMove
import chess.Location
import chess.Turn

class Knight(
    override var color: Color,
) : IChessPiece {
    override val type: PieceType = PieceType.KNIGHT

    //    /*
//    The function takes in the current row and column of the Knight, and the chess board represented as a 2D array of Pieces.
//    It uses two arrays dx and dy that represent the relative positions of the squares the Knight can move.
//    It loops through the dx array, and for each index, it calculates the new row and column by adding the
//    dx[i] and dy[i] to the current row and column respectively.
//    It then checks if the new row and column are within the chess board boundaries (0-7). And it checks if the square is
//     unoccupied or occupied by an opponent piece.
//    If it is, it adds the new row and column to the possible moves list.
//
//    This should give you a good starting point to implement your own Knight movement function, but please note that this is
//    a simplified version of the Knight movement, that only consider the standard Knight movements, the actual rule for
//    Knights may depend on the game you are implementing.
//     */
    override fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()
//        val dx = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
//        val dy = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)
//
//        for (i in dx.indices) {
//            val newRow = row + dx[i]
//            val newCol = col + dy[i]
//
//            if (newRow in 0..7 && newCol in 0..7) {
//                val nextSquare = board[newRow][newCol]
//                if (nextSquare == null || nextSquare.color != color) {
//                    possibleMoves.add(Pair(newRow, newCol))
//                }
//            }
//        }
        return possibleMoves
    }

    override fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {

        return false
    }
}