package pieces

import game_helpers.Location

/**
 * Empty Spot Chess Piece
 *
 * @author Lukas Waschuk
 */
class EmptySpot : ChessPiece(Color.E, PieceType.EMPTY, directions()) {

    /**
     * Prints the unicode representation of the corresponding piece
     *
     * @author Lukas Waschuk
     */
    override fun print(): String {
        return " "
    }
    companion object {
        /**
         * The possible directions the piece can move in
         *
         * @author Lukas Waschuk
         */
        fun directions(): List<Location> {
            return listOf(
                Location(-100, -100)
            )
        }
    }
}
