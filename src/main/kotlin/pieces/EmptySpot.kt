package pieces

import game_helpers.Location

class EmptySpot : ChessPiece(Color.E, PieceType.EMPTY, directions()) {
    override fun print(): String {
        return " "
    }
    companion object {
        fun directions(): List<Location> {
            return listOf(
                Location(-100, -100)
            )
        }
    }
}
