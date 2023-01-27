package pieces

import chess.ChessBoard
import chess.ChessMove
import chess.Location
import chess.Turn

class Bishop(
    override var color: Color,
) : IChessPiece {
    override val type: PieceType = PieceType.BISHOP

    //    /*
//    This function takes in the current row and column of the bishop and the chess board represented as a 2D array of Pieces.
//    It then uses four nested while loops, one for each diagonal direction and it checks all the squares in that diagonal
//    direction until it reaches the end of the board or finds an occupied square of the same color.
//    If it finds an unoccupied square or a square occupied by an opponent, it adds it to the possible moves list and continues
//    checking the next square in that diagonal direction
//     */
    override fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

//        // Check squares diagonally to the left and up
//        var r = row - 1
//        var c = col - 1
//        while (r >= 0 && c >= 0) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r--
//            c--
//        }
//
//        // Check squares diagonally to the right and up
//        r = row - 1
//        c = col + 1
//        while (r >= 0 && c <= 7) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r--
//            c++
//        }
//
//        // Check squares diagonally to the left and down
//        r = row + 1
//        c = col - 1
//        while (r <= 7 && c >= 0) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r++
//            c--
//        }
//
//        // Check squares diagonally to the right and down
//        r = row + 1
//        c = col + 1
//        while (r <= 7 && c <= 7) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r++
//            c++
//        }
        return possibleMoves
    }

    override fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {

        return false
    }
}