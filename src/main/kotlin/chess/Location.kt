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

}