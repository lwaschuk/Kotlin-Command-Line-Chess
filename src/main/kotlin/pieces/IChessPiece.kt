package pieces

import chess.ChessBoard
import chess.Turn

interface IChessPiece {
    val color: Color
    val type: PieceType
    fun canMove(coordinates: Pair<Int, Int>, chessBoard: ChessBoard, turn: Turn): Set<Pair<Int, Int>>
}