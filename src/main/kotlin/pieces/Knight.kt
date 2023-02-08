package pieces

import game_helpers.Location

class Knight(color: Color) : ChessPiece(color, PieceType.KNIGHT, directions()) {
    override fun print(): String {
        return if (this.color == Color.W){
            "\u2658"
        } else {
            "\u265E"
        }
    }
    companion object {
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