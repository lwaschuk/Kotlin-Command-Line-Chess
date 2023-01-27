package chess

import pieces.EmptySpot
import pieces.IChessPiece
import pieces.Pawn
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

    fun getPieceClass(coordinates: Pair<Int, Int>): IChessPiece {
        return when (this.chessBoard[coordinates.first][coordinates.second]) {
            is Pawn -> this.chessBoard[coordinates.first][coordinates.second] as Pawn
//                is Rook -> Rook::class.java
//                is Knight -> Knight::class.java
//                is Bishop -> Bishop::class.java
//                is Queen -> Queen::class.java
//                is King -> King::class.java
//                is EmptySpot -> EmptySpot::class.java
            else -> throw IllegalArgumentException("Error casting")
        }
    }

    fun setPiece(location: Location, piece: IChessPiece) {
        this.chessBoard[location.row()][location.column()] = piece
    }
}