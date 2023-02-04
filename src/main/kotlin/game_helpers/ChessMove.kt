package game_helpers

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
        fun convertSetOfLocations(input: Set<Location>): Set<Pair<Char, Char>> {
            val newSet = mutableSetOf<Pair<Char, Char>>()
            for (location in input) {
                val letter = location.column() + 'a'.code
                val number = location.row() + '1'.code
                newSet.add(Pair(letter.toChar(), number.toChar()))
            }
            return newSet
        }
    }
}