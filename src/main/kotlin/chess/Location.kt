package chess

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

}