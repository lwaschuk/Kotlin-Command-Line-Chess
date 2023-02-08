package pieces

import game_helpers.Location

class Pawn(color: Color, var canBeEnPassant: Boolean = false) : ChessPiece(color, PieceType.PAWN, directions()) {
    override fun print(): String {
        return if (this.color == Color.W){
            "\u2659"
        } else {
            "\u265F"
        }
    }
    companion object {
        fun directions(): List<Location> {
            return listOf(
                Location(1,0), Location(-1,0), // Up / down
                Location(0,1), Location(0,-1), // Left / Right
                Location(1,1), Location(-1,-1), // Right Diagonal
                Location(1,-1), Location(-1,1) // Left Diagonal
            )
        }
    }
}
