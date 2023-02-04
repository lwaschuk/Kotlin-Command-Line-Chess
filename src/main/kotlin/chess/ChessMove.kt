package chess

class ChessMove {
    private val chessMove: Pair<Location, Location>

    constructor(input: String) {
        val startRow = input[1] - '1'
        val startCol = input[0] - 'a'
        val endRow = input[3] - '1'
        val endCol = input[2] - 'a'

        val startCoordinates = Location(startRow, startCol)
        val endCoordinates = Location(endRow, endCol)

        this.chessMove = Pair(startCoordinates, endCoordinates)
    }

    constructor(start: Location, end: Location) {
        this.chessMove = Pair(start, end)
    }


    fun startLocation(): Location {
        return this.chessMove.first
    }

    fun endLocation(): Location {
        return this.chessMove.second
    }

    companion object {
        fun convertSetLocation(input: Set<Location>): Set<Pair<Char, Char>> {
            val newSet = mutableSetOf<Pair<Char, Char>>()
            for (location in input) {
                val letter = location.column() + 'a'.code
                val number = location.row() + '1'.code
                newSet.add(Pair(letter.toChar(), number.toChar()))
            }
            return newSet
        }

        fun convertLocation(input: Location): Pair<Char, Char> {
            val letter = input.column() + 'a'.code
            val number = input.row() + '1'.code
            return Pair(letter.toChar(), number.toChar())
        }

        fun convertChessMove(chessMove: ChessMove): Pair<Pair<Char, Char>, Pair<Char, Char>> {
            val startLetter = chessMove.startLocation().column() + 'a'.code
            val startNumber = chessMove.startLocation().row() + '1'.code
            val start = Pair(startLetter.toChar(), startNumber.toChar())
            val endLetter = chessMove.endLocation().column() + 'a'.code
            val endNumber = chessMove.endLocation().row() + '1'.code
            val end = Pair(endLetter.toChar(), endNumber.toChar())

            return Pair(start, end)
        }
    }
}