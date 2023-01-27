package chess

class ChessMove(input: String) {
    private val chessMove: Pair<Location, Location>

    init {
        val startRow = input[1] - '1'
        val startCol = input[0] - 'a'
        val endRow = input[3] - '1'
        val endCol = input[2] - 'a'

        val startCoordinates = Location(startRow, startCol)
        val endCoordinates = Location(endRow, endCol)

        this.chessMove = Pair(startCoordinates, endCoordinates)
    }

    fun startLocation(): Location {
        return this.chessMove.first
    }

    fun endLocation(): Location {
        return this.chessMove.second
    }
}