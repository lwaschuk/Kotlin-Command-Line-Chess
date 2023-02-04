package pieces

import game_helpers.ChessBoard
import game_helpers.ChessMove
import game_helpers.Location
import game_helpers.Turn

class King(
    override var color: Color,
) : IChessPiece {
    override val type: PieceType = PieceType.KING

    override fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

        val directions = listOf(
            Location(1,0), Location(-1,0), // Up / down
            Location(0,1), Location(0,-1), // Left / Right
            Location(1,1), Location(-1,-1), // Right Diagonal
            Location(1,-1), Location(-1,1) // Left Diagonal
        )

        for (direction in directions) {
            val nextLocation = startLocation + direction
            if (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if (nextPiece.color == turn.enemyColor()) {
                    possibleMoves.add(nextLocation)
                }
                else if (nextPiece.color == Color.E) {
                    possibleMoves.add(nextLocation)
                }
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
            "\u2654"
        } else {
            "\u265A"
        }
    }
}