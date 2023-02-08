package pieces

import game_helpers.Location

class Rook(color: Color) : ChessPiece(color, PieceType.ROOK, directions()) {
    override fun print(): String {
        return if (this.color == Color.W){
            "\u2656"
        } else {
            "\u265C"
        }
    }
    companion object {
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