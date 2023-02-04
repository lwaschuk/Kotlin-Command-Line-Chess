package pieces

import chess.ChessBoard
import chess.ChessMove
import chess.Location
import chess.Turn

class Queen(
    override var color: Color,
) : IChessPiece {
    override val type: PieceType = PieceType.QUEEN

    override fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

        val directions = listOf(
            Location(1, 1), Location(1, -1),
            Location(-1, 1), Location(-1, -1),
            Location(1, 0), Location(0, 1),
            Location(-1, 0), Location(0, -1)
        )

        for (direction in directions) {
            var nextLocation = startLocation + direction
            while (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if (nextPiece.color == turn.getColor()) {
                    break
                }
                else if (nextPiece.color == turn.enemyColor()) {
                    possibleMoves.add(nextLocation)
                    break
                }
                else if (nextPiece.color == Color.E) {
                    possibleMoves.add(nextLocation)
                    nextLocation += direction
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
            "\u2655"
        } else {
            "\u265B"
        }
    }
}