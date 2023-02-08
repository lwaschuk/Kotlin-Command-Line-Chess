package pieces

import game_helpers.Location

/**
 * Empty Spot Chess Piece
 *
 * @param color Will always be EMPTY
 */
class EmptySpot : ChessPiece(Color.E, PieceType.EMPTY, directions()) {

    /**
     * Prints the unicode representation of the corresponding piece
     *
     * @param nothing
     * @return nothing
     */
    override fun print(): String {
        return " "
    }
    companion object {
        /**
         * The possible directions the piece can move in
         *
         * @param nothing
         * @return nothing
         */
        fun directions(): List<Location> {
            return listOf(
                Location(-100, -100)
            )
        }
    }
}
