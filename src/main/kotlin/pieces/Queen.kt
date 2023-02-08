package pieces

import game_helpers.Location

class Queen(color: Color) : ChessPiece(color, PieceType.QUEEN, directions()) {
    override fun print(): String {
        return if (this.color == Color.W){
            "\u2655"
        } else {
            "\u265B"
        }
    }
    companion object {
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