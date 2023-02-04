package game_helpers

import pieces.Color

class Turn(p1Turn: Boolean = true) {
    private var p1Turn: Boolean
    private var color: Color

    init {
        this.p1Turn = p1Turn
        this.color = if (this.p1Turn) Color.W else Color.B
    }

    fun getTurn(): Boolean{
        return this.p1Turn
    }

    fun getColor(): Color {
        return this.color
    }

    fun enemyColor(): Color {
        return if (this.p1Turn) Color.B else Color.W
    }

    fun colorToString(): String {
        return when (color) {
            Color.W -> "white"
            Color.B -> "black"
            else -> {
                "empty"
            }
        }
    }

    fun nextTurn() {
        this.p1Turn = !p1Turn
        this.color = if (this.p1Turn) Color.W else Color.B
    }
}