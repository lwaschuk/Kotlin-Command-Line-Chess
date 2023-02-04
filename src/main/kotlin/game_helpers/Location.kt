package game_helpers

class Location(row: Int, col: Int) {
    private val location: Pair<Int, Int>

    init {
        this.location = Pair(row, col)
    }

    fun row(): Int {
        return this.location.first
    }

    fun column(): Int {
        return this.location.second
    }

    fun value(): Pair<Int, Int> {
        return this.location
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Location) {
            return false
        }
        return row() == other.row() && column() == other.column()
    }

    override fun hashCode(): Int {
        return location.hashCode()
    }

    operator fun plus(direction: Location): Location {
        return Location(row() + direction.row(), column() + direction.column())
    }

    fun isValid(location: Location): Boolean {
        return location.row() in ChessBoard.ROW_START..ChessBoard.ROW_END
                && location.column() in ChessBoard.COL_START..ChessBoard.COL_END
    }
}