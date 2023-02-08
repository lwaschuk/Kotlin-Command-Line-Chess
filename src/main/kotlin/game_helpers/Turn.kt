package game_helpers

import pieces.Color

/**
 * Information for whose turn it is
 *
 * @param p1Turn is it player ones turn
 */
class Turn(p1Turn: Boolean = true) {
    private var p1Turn: Boolean
    private var color: Color

    init {
        this.p1Turn = p1Turn
        this.color = if (this.p1Turn) Color.W else Color.B
    }

    /**
     * Public method to see if it is player 1's turn
     *
     * @param nothing
     * @return Boolean representing if it is player 1's turn
     */
    fun getTurn(): Boolean{
        return this.p1Turn
    }

    /**
     * Public method to get the player's color
     *
     * @param nothing
     * @return Color Either W for p1, B for p2
     */
    fun getColor(): Color {
        return this.color
    }

    /**
     * Public method to get the enemy player's color
     *
     * @param nothing
     * @return Color Either W for p1, B for p2
     */
    fun enemyColor(): Color {
        return if (this.p1Turn) Color.B else Color.W
    }

    /**
     * To convert the enum into a string for error messages
     *
     * @param nothing
     * @return The string of the color
     */
    fun colorToString(): String {
        return when (color) {
            Color.W -> "white"
            Color.B -> "black"
            else -> {
                "empty"
            }
        }
    }

    /**
     * Move the turn and all values from p1 -> p2 -> p1 etc...
     *
     * @param nothing
     * @return nothing
     */
    fun nextTurn() {
        this.p1Turn = !p1Turn
        this.color = if (this.p1Turn) Color.W else Color.B
    }
}