package pieces

import chess.ChessBoard
import chess.ChessMove
import chess.Location
import chess.Turn

class Rook(
    override var color: Color,
) : IChessPiece {
    override val type: PieceType = PieceType.ROOK

    override fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()


        for (row in startLocation.row()+1..ChessBoard.ROW_END) {
            val nextLocation = Location(row, startLocation.column())
            val next = chessBoard.getPiece(nextLocation)
            if (next.color == turn.getColor()) {
                break
            }
            else if (next.color == turn.enemyColor()) {
                possibleMoves.add(nextLocation)
                break
            }
            else if (next.color == Color.E) {
                possibleMoves.add(nextLocation)
            }
        }
        for (column in startLocation.column()+1..ChessBoard.COL_END) {
            val nextLocation = Location(startLocation.row(), column)
            val next = chessBoard.getPiece(nextLocation)
            if (next.color == turn.getColor()) {
                break
            }
            else if (next.color == turn.enemyColor()) {
                possibleMoves.add(nextLocation)
                break
            }
            else if (next.color == Color.E) {
                possibleMoves.add(nextLocation)
            }
        }
        for (row in startLocation.row()-1 downTo ChessBoard.ROW_START) {
            val nextLocation = Location(row, startLocation.column())
            val next = chessBoard.getPiece(nextLocation)
            if (next.color == turn.getColor()) {
                break
            }
            else if (next.color == turn.enemyColor()) {
                possibleMoves.add(nextLocation)
                break
            }
            else if (next.color == Color.E) {
                possibleMoves.add(nextLocation)
            }
        }
        for (column in startLocation.column()-1 downTo ChessBoard.COL_START) {
            val nextLocation = Location(startLocation.row(), column)
            val next = chessBoard.getPiece(nextLocation)
            if (next.color == turn.getColor()) {
                break
            }
            else if (next.color == turn.enemyColor()) {
                possibleMoves.add(nextLocation)
                break
            }
            else if (next.color == Color.E) {
                possibleMoves.add(nextLocation)
            }
        }
        return possibleMoves
    }

    override fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        val possibleMoves = canMove(chessMove.startLocation(), chessBoard, turn)

        for (move in possibleMoves) {
            if (chessMove.endLocation() == move) {
                chessBoard.setPiece(chessMove.endLocation(), chessBoard.getPiece(chessMove.startLocation()))
                chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                return true
            }
        }

        return false
    }

    override fun print(): String {
        return if (this.color == Color.W){
            "\u265C"
        } else {
            "\u2656"
        }
    }
}