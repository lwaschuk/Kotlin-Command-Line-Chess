package chess

import pieces.*

class ChessBoard {
    private var chessBoard = Array(8) { Array<IChessPiece>(8) { EmptySpot() } }

    fun render() {
        println("  +---+---+---+---+---+---+---+---+")
        for (row in 7 downTo 0) {
            print("${row + 1} ")
            for (col in 0 until 8) {
                if (this.chessBoard[row][col].type != PieceType.EMPTY) {
                    print("| ${this.chessBoard[row][col].print()} ")
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

    companion object {
        const val ROW_START = 0
        const val COL_START = 0
        const val ROW_END = 7
        const val COL_END = 7
    }
}