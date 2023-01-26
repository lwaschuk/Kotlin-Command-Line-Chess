package chess

class Player(name: String) {
    private val name: String
    init {
        this.name = name
    }

    fun name(): String { return this.name }
}