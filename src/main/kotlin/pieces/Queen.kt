package pieces

import game_helpers.Location

/**
 * Queen Chess Piece
 *
 * @param color Who the piece belongs to
 * @author Lukas Waschuk
 */
class Queen(color: Color) : ChessPiece(color, PieceType.QUEEN, directions()) {

    /**
     * Prints the unicode representation of the corresponding piece
     *
     * @author Lukas Waschuk
     */
    override fun print(): String {
        return if (this.color == Color.W){
            "\u2655"
        } else {
            "\u265B"
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
                Location(1, 1), Location(1, -1),
                Location(-1, 1), Location(-1, -1),
                Location(1, 0), Location(0, 1),
                Location(-1, 0), Location(0, -1)
            )
        }
    }
}