package chess

import pieces.EmptySpot
import pieces.IChessPiece
import pieces.PieceType

class ChessBoard {
    private var chessBoard = Array(8) { Array<IChessPiece>(8) { EmptySpot() } }

    fun render() {
        println("  +---+---+---+---+---+---+---+---+")
        for (row in 7 downTo 0) {
            print("${row + 1} ")
            for (col in 0 until 8) {
                if (this.chessBoard[row][col].type != PieceType.EMPTY) {
                    print("| ${this.chessBoard[row][col].color} ")
                } else {
                    print("|   ")
                }
            }
            println("|")
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h")
    }

    fun getPiece(coordinates: Location): IChessPiece {
        return this.chessBoard[coordinates.row()][coordinates.column()]
    }

    fun setPiece(location: Location, piece: IChessPiece) {
        this.chessBoard[location.row()][location.column()] = piece
    }
}