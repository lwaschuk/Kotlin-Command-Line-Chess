package chess

import pieces.*

class GameLogic(chessBoard: ChessBoard) {
    private var chessBoard: ChessBoard

    init {
        this.chessBoard = chessBoard
    }

    fun startGame() {
        initChessPieces()
        chessBoard.render()
    }

    fun gameOver(turn: Turn): Boolean {
        return kingsEliminated() || stalemate(turn)
    }

    private fun kingsEliminated(): Boolean {
        var whiteFound = false
        var blackFound = false

        for (row in ChessBoard.ROW_START..ChessBoard.ROW_END) {
            for (col in ChessBoard.COL_START..ChessBoard.COL_END) {
                val currentPiece = chessBoard.getPiece(Location(row, col))
                if (currentPiece is King) {
                    when (currentPiece.color) {
                        Color.B -> blackFound = true
                        Color.W -> whiteFound = true
                        else -> {}
                    }
                }
            }
        }
        if (whiteFound && !blackFound) {
            println("\nWhite Wins!")
            return true
        }
        if (blackFound && !whiteFound) {
            println("\nBlack Wins!")
            return true
        }
        return false
    }

    private fun stalemate(turn: Turn): Boolean {
        for (row in ChessBoard.ROW_START .. ChessBoard.ROW_END) {
            for (col in ChessBoard.COL_START .. ChessBoard.COL_END) {
                val location = Location(row, col)
                val piece = chessBoard.getPiece(location)
                // only check pc's for the current turn (black or white)
                if (piece.color == turn.getColor() && piece.canMove(location, this.chessBoard, turn).isNotEmpty()) {
                    return false
                }
            }
        }
        println("\nStalemate!")
        return true
    }

    fun sourceCoordinateVerifier(chessMove: ChessMove, turn: Turn): Boolean {
        val startCoordinates = chessMove.startLocation()
        val piece = chessBoard.getPiece(startCoordinates)
        return piece.type != PieceType.EMPTY && piece.color == turn.getColor()
    }

    private fun initChessPieces() {
//        initPawns()
        initKnights()
        initRooks()
        initBishops()
        initQueens()
        initKings()
    }
    private fun initKings() {
        chessBoard.setPiece(Location(0,4), King(Color.W))
        chessBoard.setPiece(Location(7,4), King(Color.B))
    }
    private fun initQueens() {
        chessBoard.setPiece(Location(0,3), Queen(Color.W))
        chessBoard.setPiece(Location(7,3), Queen(Color.B))
    }

    private fun initBishops() {
        chessBoard.setPiece(Location(0,2), Bishop(Color.W))
        chessBoard.setPiece(Location(0,5), Bishop(Color.W))
        chessBoard.setPiece(Location(7,2), Bishop(Color.B))
        chessBoard.setPiece(Location(7,5), Bishop(Color.B))
    }
    private fun initKnights(){
        chessBoard.setPiece(Location(0,1), Knight(Color.W))
        chessBoard.setPiece(Location(0,6), Knight(Color.W))
        chessBoard.setPiece(Location(7,1), Knight(Color.B))
        chessBoard.setPiece(Location(7,6), Knight(Color.B))
    }

    private fun initRooks(){
        chessBoard.setPiece(Location(0, 0), Rook(Color.W))
        chessBoard.setPiece(Location(0, 7), Rook(Color.W))
        chessBoard.setPiece(Location(7, 0), Rook(Color.B))
        chessBoard.setPiece(Location(7, 7), Rook(Color.B))
    }

    private fun initPawns() {
        for (col in 0 until 8) {
            chessBoard.setPiece(Location(1, col), Pawn(Color.W))
            chessBoard.setPiece(Location(6, col), Pawn(Color.B))
        }
    }

}