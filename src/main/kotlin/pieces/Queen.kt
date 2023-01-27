package pieces

import chess.ChessBoard
import chess.ChessMove
import chess.Location
import chess.Turn

class Queen(
    override var color: Color,
) : IChessPiece {
    override val type: PieceType = PieceType.QUEEN

    //
//
//    /*
//    This function checks all the horizontal, vertical, and diagonal squares for the queen. It uses nested for loops,
//    similar to the bishop, but it also check for horizontal and vertical squares.
//    It checks if the square is unoccupied or occupied by an opponent piece. If it is, it adds the new row and column to the
//    possible moves list.
//    Please note that this is a simplified version of the Queen movement, that only consider the standard Queen movements,
//    the actual rule for Queen may depend on the game you are implementing.
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
//
//        // Check squares horizontally to the left
//        for (c in col - 1 downTo 0) {
//            val square = board[row][c]
//            if (square == null) {
//                possibleMoves.add(Pair(row, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(row, c))
//                break
//            } else {
//                break
//            }
//        }
//        // Check squares horizontally to the right
//        for (c in col + 1..7) {
//            val square = board[row][c]
//            if (square == null) {
//                possibleMoves.add(Pair(row, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(row, c))
//                break
//            } else {
//                break
//            }
//        }
//
//        // Check squares vertically up
//        for (r in row - 1 downTo 0) {
//            val square = board[r][col]
//            if (square == null) {
//                possibleMoves.add(Pair(r, col))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, col))
//                break
//            } else {
//                break
//            }
//        }
//
//        // Check squares vertically down
//        for (r in row + 1..7) {
//            val square = board[r][col]
//            if (square == null) {
//                possibleMoves.add(Pair(r, col))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, col))
//                break
//            } else {
//                break
//            }
//        }

        return possibleMoves
    }

    override fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {

        return false
    }
}