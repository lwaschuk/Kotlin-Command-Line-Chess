package chess

import pieces.PieceType

class Verifier {
    fun verify(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        return validatePieceRules(chessMove, PieceType.PAWN, turn)
    }

    private fun validatePieceRules(
        chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>,
        pieceType: PieceType,
        turn: Turn,
    ): Boolean {
        return when (pieceType) {
            PieceType.PAWN -> validatePawnMove(chessMove, turn)
            PieceType.KNIGHT -> true
            PieceType.ROOK -> true
            PieceType.BISHOP -> true
            PieceType.QUEEN -> true
            PieceType.KING -> true
            PieceType.EMPTY -> true
        }
    }

    private fun validatePawnMove(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        return pawnStraight(chessMove, turn) || pawnTake(chessMove, turn)
    }


    private fun pawnStraight(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        val startCoordinates = chessMove.first
        val endCoordinates = chessMove.second

        // check column logic (they must match for pawns)
        if (startCoordinates.second != endCoordinates.second) {
            return false
        }
        if (turn.getTurn()) {
            return if (startCoordinates.first == 1) {
                val range = startCoordinates.first + 1..startCoordinates.first + 2
                endCoordinates.first in range
            } else {
                val range = startCoordinates.first + 1..startCoordinates.first + 1
                endCoordinates.first in range
            }
        } else {
            return if (startCoordinates.first == 6) {
                val range = startCoordinates.first - 1 downTo startCoordinates.first - 2
                endCoordinates.first in range
            } else {
                val range = startCoordinates.first - 1 downTo startCoordinates.first - 1
                endCoordinates.first in range
            }
        }
    }

    private fun pawnTake(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        val startCoordinates = chessMove.first
        val endCoordinates = chessMove.second

        return if (turn.getTurn()) {
            val takeLeft = Pair(startCoordinates.first + 1, startCoordinates.second - 1)
            val takeRight = Pair(startCoordinates.first + 1, startCoordinates.second + 1)
            endCoordinates == takeLeft || endCoordinates == takeRight
        } else {
            val takeLeft = Pair(startCoordinates.first - 1, startCoordinates.second - 1)
            val takeRight = Pair(startCoordinates.first - 1, startCoordinates.second + 1)
            endCoordinates == takeLeft || endCoordinates == takeRight
        }
    }

    companion object {
        fun convertInput(input: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
            val startRow = input[1] - '1'
            val startCol = input[0] - 'a'
            val endRow = input[3] - '1'
            val endCol = input[2] - 'a'

            val startCoordinates = Pair(startRow, startCol)
            val endCoordinates = Pair(endRow, endCol)

//            println("Move from S:ROW ${startCoordinates.first} S:COL ${startCoordinates.second} to E:ROW ${endCoordinates.first} E:COL ${endCoordinates.second}")
            return Pair(startCoordinates, endCoordinates)
        }

        fun convertBack(s: Set<Pair<Int, Int>>): Set<Pair<Char, Char>> {
            val newSet = mutableSetOf<Pair<Char, Char>>()
            for (item in s) {
                val number = item.first + '1'.code
                val letter = item.second + 'a'.code
                newSet.add(Pair(letter.toChar(), number.toChar()))
            }
            return newSet
        }
    }
}
