package pieces

import game_helpers.Location

/**
 * Knight Chess Piece
 *
 * @param color Who the piece belongs to
 */
class Knight(color: Color) : ChessPiece(color, PieceType.KNIGHT, directions()) {

    /**
     * Prints the unicode representation of the corresponding piece
     *
     * @param nothing
     * @return nothing
     */
    override fun print(): String {
        return if (this.color == Color.W){
            "\u2658"
        } else {
            "\u265E"
        }
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
                Location(2, -1), Location(2, 1),
                Location(-2, -1), Location(-2, 1),
                Location(1, 2), Location(-1, 2),
                Location(1, -2), Location(-1, -2)
            )
        }
    }
}