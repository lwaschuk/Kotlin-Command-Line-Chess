package pieces

import game_helpers.ChessBoard
import game_helpers.ChessMove
import game_helpers.Location
import game_helpers.Turn

interface IChessPiece {
    val color: Color
    val type: PieceType
    fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location>
    fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean
    fun print(): String
}