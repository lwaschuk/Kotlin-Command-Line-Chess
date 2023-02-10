package game_helpers

import pieces.*
import run.Logger

/**
 * A chessboard class that contains public methods to get/set a piece on the board, as well as render the board
 *
 * @param nothing
 */
class ChessBoard {
    private val logger = Logger(this.javaClass.name)
    private var chessBoard = Array(8) { Array<ChessPiece>(8) { EmptySpot() } }

    /**
     * Print the board to the console
     *
     * @param nothing
     * @return nothing
     */
    fun render() {
        println("  +---+---+---+---+---+---+---+---+")
        for (row in ROW_END downTo ROW_START) {
            print("${row + 1} ")
            for (col in COL_START .. COL_END) {
                if (this.chessBoard[row][col].pieceType != PieceType.EMPTY) {
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

    /**
     * Gets a chess piece that was set on the board
     *
     * @param coordinates The location of the piece we are interested in
     * @return The piece
     */
    fun getPiece(coordinates: Location): ChessPiece {
        return this.chessBoard[coordinates.row()][coordinates.column()]
    }

    /**
     * Sets a chess piece on the board
     *
     * @param coordinates The location of the piece we are interested in setting
     * @return nothing
     */
    fun setPiece(location: Location, piece: ChessPiece) {
        this.chessBoard[location.row()][location.column()] = piece
    }

    companion object {
        const val ROW_START = 0
        const val COL_START = 0
        const val ROW_END = 7
        const val COL_END = 7
    }
}