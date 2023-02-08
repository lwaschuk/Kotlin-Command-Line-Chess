package pieces

import game_helpers.Location


class Bishop(color: Color) : ChessPiece(color, PieceType.BISHOP, directions()) {
      override fun print(): String {
        return if (this.color == Color.W){
            "\u2657"
        } else {
            "\u265D"
        }
    }
    companion object {
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