package pieces

import game_helpers.Location

/**
 * Rook Chess Piece
 *
 * @param color Who the piece belongs to
 * @author Lukas Waschuk
 */
class Rook(color: Color) : ChessPiece(color, PieceType.ROOK, directions()) {

    /**
     * Prints the unicode representation of the corresponding piece
     *
     * @author Lukas Waschuk
     */
    override fun print(): String {
        return if (this.color == Color.W){
            "\u2656"
        } else {
            "\u265C"
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
                Location(1, 0),
                Location(0, 1),
                Location(-1, 0),
                Location(0, -1)
            )
        }
    }
}