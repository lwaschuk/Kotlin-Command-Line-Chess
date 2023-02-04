package pieces

import game_helpers.ChessBoard
import game_helpers.ChessMove
import game_helpers.Location
import game_helpers.Turn

class EmptySpot : IChessPiece {
    override val type: PieceType = PieceType.EMPTY
    override var color: Color = Color.E
    override fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        return setOf(Location(-1, -1))
    }

    override fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {

        return false
    }

    override fun print(): String {
        return " "
    }
}
