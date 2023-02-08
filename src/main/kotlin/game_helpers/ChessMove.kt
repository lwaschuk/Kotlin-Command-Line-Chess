package game_helpers

/**
 * A class that contains the Start Location and the End Location
 *
 * @param nothing
 */
class ChessMove {
    private val chessMove: Pair<Location, Location>

    /**
     * Constructs the chessMove from the user input ex. a2a4
     *
     * @param input the string the user input
     */
    constructor(input: String) {
        val startRow = input[1] - '1'
        val startCol = input[0] - 'a'
        val endRow = input[3] - '1'
        val endCol = input[2] - 'a'

        val startCoordinates = Location(startRow, startCol)
        val endCoordinates = Location(endRow, endCol)

        this.chessMove = Pair(startCoordinates, endCoordinates)
    }

    /**
     * Constructs the chessMove from a Start Location and End Location
     *
     * @param start The starting Location
     * @param end The ending Location
     */
    constructor(start: Location, end: Location) {
        this.chessMove = Pair(start, end)
    }

    /**
     * Method to return the starting location of a chess move
     *
     * @return the starting location
     */
    fun startLocation(): Location {
        return this.chessMove.first
    }

    /**
     * Method to return the ending location of a chess move
     *
     * @return the ending location
     */
    fun endLocation(): Location {
        return this.chessMove.second
    }

    companion object {
        /**
         * A method to convert a set of chess moves from how it is stored ((0,1),(0,3))
         * to a human-readable representation ((a,2),(a,4))
         *
         * @param input the set of chess moves to convert
         *
         * @return the set of moves in a human-readable representation
         */
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