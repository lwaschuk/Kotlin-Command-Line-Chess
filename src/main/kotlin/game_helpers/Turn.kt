package game_helpers

import pieces.Color
import run.Logger

/**
 * Information for whose turn it is
 *
 * @param p1Turn is it player ones turn (optional)
 * @author Lukas Waschuk
 */
class Turn(p1Turn: Boolean = true) {
    private val logger = Logger(this.javaClass.name)
    private var p1Turn: Boolean
    private var color: Color

    init {
        logger.debug("Creating Turn")
        this.p1Turn = p1Turn
        this.color = if (this.p1Turn) Color.W else Color.B
        logger.debug("Turn created starting on ${this.color}")
    }

    /**
     * Public method to see if it is player 1's turn
     *
     * @return Boolean representing if it is player 1's turn
     * @author Lukas Waschuk
     */
    fun getTurn(): Boolean {
        return this.p1Turn
    }

    /**
     * Public method to get the player's color
     *
     * @return Color Either W for p1, B for p2
     * @author Lukas Waschuk
     */
    fun getColor(): Color {
        return this.color
    }

    /**
     * Public method to get the enemy player's color
     *
     * @return Color Either W for p1, B for p2
     * @author Lukas Waschuk
     */
    fun enemyColor(): Color {
        return if (this.p1Turn) Color.B else Color.W
    }

    /**
     * To convert the enum into a string for error messages
     *
     * @return The string of the color
     * @author Lukas Waschuk
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
     * @author Lukas Waschuk
     */
    fun nextTurn() {
        logger.debug("Changing Turn")
        this.p1Turn = !p1Turn
        this.color = if (this.p1Turn) Color.W else Color.B
    }
}