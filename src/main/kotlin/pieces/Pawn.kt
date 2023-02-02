package pieces

import chess.ChessBoard
import chess.ChessMove
import chess.Location
import chess.Turn

class Pawn(
    override var color: Color,
    var canBeEnPassant: Boolean = false,
) : IChessPiece {
    override val type: PieceType = PieceType.PAWN
    override fun canMove(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val direction = if (turn.getColor() == Color.W) 1 else -1
        val nextRow = startLocation.row() + direction
        val col = startLocation.column()
        val possibleMoves = mutableSetOf<Location>()

        if (nextRow in 0..7 && col in 0..7) {
            val nextSquare = chessBoard.getPiece(Location(nextRow, col))
            if (nextSquare is EmptySpot) {
                possibleMoves.add(Location(nextRow, col))
                // find promote later
//                if(promote && (nextRow == 0 || nextRow == 7)) {
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.QUEEN))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.ROOK))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.BISHOP))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.KNIGHT))
//                }
            }
            if ((turn.getTurn() && startLocation.row() == 1) || (!turn.getTurn() && startLocation.row() == 6)) {
                val nextnextSquare = chessBoard.getPiece(Location(nextRow + direction, col))
                if (nextnextSquare is EmptySpot) {
                    possibleMoves.add(Location(nextRow + direction, col))
                }
            }

            if (nextRow in ChessBoard.ROW_START..ChessBoard.ROW_END) {
                val leftCol = col - direction
                if (leftCol in ChessBoard.COL_START..ChessBoard.COL_END){
                    val leftDCoords = Location(nextRow, leftCol)
                    val leftDPiece = chessBoard.getPiece(leftDCoords)
                    if (leftDPiece.color == turn.enemyColor()) {
                        possibleMoves.add(leftDCoords)
                    }
                    if (leftDPiece.color == Color.E) {
                        var behindDPiece =
                            chessBoard.getPiece(Location(leftDCoords.row() - direction, leftDCoords.column()))
                        if (behindDPiece.type == PieceType.PAWN) {
                            behindDPiece = behindDPiece as Pawn
                            if (behindDPiece.canBeEnPassant && behindDPiece.color == turn.enemyColor()) {
                                possibleMoves.add(leftDCoords)
                            }
                        }
                    }
                }

                val rightCol = col + direction
                if (rightCol in ChessBoard.COL_START..ChessBoard.COL_END) {
                    val rightDCoords = Location(nextRow, rightCol)
                    val rightDPiece = chessBoard.getPiece(rightDCoords)
                    if (rightDPiece.color == turn.enemyColor()) {
                        possibleMoves.add(rightDCoords)
                    }

                    if (rightDPiece.color == Color.E) {
                        var behindRPiece =
                            chessBoard.getPiece(Location(rightDCoords.row() - direction, rightDCoords.column()))
                        if (behindRPiece.type == PieceType.PAWN) {
                            behindRPiece = behindRPiece as Pawn
                            if (behindRPiece.canBeEnPassant && behindRPiece.color == turn.enemyColor()) {
                                possibleMoves.add(rightDCoords)
                            }
                        }
                    }
                }
            }
        }
        return possibleMoves
    }

    override fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        val possibleMoves = canMove(chessMove.startLocation(), chessBoard, turn)

        for (move in possibleMoves) {
            if (chessMove.endLocation() == move) {
                val direction = if (turn.getColor() == Color.W) 1 else -1
                val behindEndLocation = Location(chessMove.endLocation().row() - direction, chessMove.endLocation().column())
                var behindEndPiece =
                    chessBoard.getPiece(behindEndLocation)

                // if nothing is at the end location and a pawn behind it en passant
                if ((behindEndPiece.type == PieceType.PAWN) && (chessMove.startLocation().column() != chessMove.endLocation().column())) {
                    behindEndPiece = behindEndPiece as Pawn
                    if (behindEndPiece.canBeEnPassant && behindEndPiece.color == turn.enemyColor()) {
                        chessBoard.setPiece(chessMove.endLocation(), chessBoard.getPiece(chessMove.startLocation()))
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        chessBoard.setPiece(behindEndLocation, EmptySpot())
                    }
                }
                else if ((kotlin.math.abs(chessMove.startLocation().row() - chessMove.endLocation().row()) == 2)
                    && (chessBoard.getPiece(chessMove.startLocation()).type == PieceType.PAWN)) {
                    val enPassant = Pawn(turn.getColor(), true)
                    chessBoard.setPiece(chessMove.endLocation(), enPassant)
                    chessBoard.setPiece(chessMove.startLocation(), EmptySpot())

                }
                else {
                    chessBoard.setPiece(chessMove.endLocation(), chessBoard.getPiece(chessMove.startLocation()))
                    chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                }

                return true
            }
        }
        return false
    }

    override fun print(): String {
        return if (this.color == Color.W){
            "\u265F"
        } else {
            "\u2659"
        }
    }
}
