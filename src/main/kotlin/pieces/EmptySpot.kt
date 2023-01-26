package pieces

import chess.ChessBoard
import chess.Turn

class EmptySpot : IChessPiece {
    override val type: PieceType = PieceType.EMPTY
    override var color: Color = Color.E
    override fun canMove(coordinates: Pair<Int, Int>, chessBoard: ChessBoard, turn: Turn): Set<Pair<Int, Int>> {
        return setOf(Pair(0, 0))
    }
}
