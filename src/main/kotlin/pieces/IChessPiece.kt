package pieces

import chess.ChessBoard
import chess.ChessMove
import chess.Location
import chess.Turn

interface IChessPiece {
    val color: Color
    val type: PieceType
    fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location>
    fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean
}