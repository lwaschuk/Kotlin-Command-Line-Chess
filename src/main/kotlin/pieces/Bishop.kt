package pieces

import game_helpers.Location

/**
 * Bishop Chess Piece
 *
 * @param color Who the piece belongs to
 * @author Lukas Waschuk
 */
class Bishop(color: Color) : ChessPiece(color, PieceType.BISHOP, directions()) {

    /**
     * Prints the unicode representation of the corresponding piece
     *
     * @author Lukas Waschuk
     */
    override fun print(): String {
        return if (this.color == Color.W){
            "\u2657"
        } else {
            "\u265D"
        }
    }
    companion object {
        /**
         * The possible directions the piece can move in
         *
         * @author Lukas Waschuk
         */
        fun directions(): List<Location> {
            return listOf(
                Location(1, 1),
                Location(1, -1),
                Location(-1, 1),
                Location(-1, -1)
            )
        }
    }
}