package pieces

import chess.ChessBoard
import chess.ChessMove
import chess.Location
import chess.Turn

class King(
    override var color: Color,
) : IChessPiece {
    override val type: PieceType = PieceType.KING

    //    /*
//    The function takes in the current row and column of the King and the chess board represented as a 2D array of Pieces.
//    It uses two arrays dx and dy that represent the relative positions of the squares the King can move.
//    It loops through the dx array, and for each index, it calculates the new row and column by adding the dx[i] and dy[i]
//    to the current row and column respectively.
//    It then checks if the new row and column are within the chess board boundaries (0-7). And it checks if the square is
//    unoccupied or occupied by an opponent piece.
//    If it is, it adds the new row and column to the possible moves list.
//    Please note that this is a simplified version of the King movement, that only consider the standard King movements, the
//     actual rule for King may depend on the game you are implementing.
//     */
    override fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()
//        val dx = intArrayOf(-1, -1, -1, 0, 1, 1, 1, 0)
//        val dy = intArrayOf(-1, 0, 1, 1, 1, 0, -1, -1)
//
//        for (i in dx.indices) {
//            val newRow = row + dx[i]
//            val newCol = col + dy[i]
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

    override fun print(): String {
        return if (this.color == Color.W){
            "\u265A"
        } else {
            "\u2654"
        }
    }
}