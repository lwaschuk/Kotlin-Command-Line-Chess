package game_helpers

import pieces.*

class GameLogic(chessBoard: ChessBoard) {
    private var chessBoard: ChessBoard

    init {
        this.chessBoard = chessBoard
    }

    fun startGame() {
        initChessPieces()
//        testCheckStale()
//        pawnPromotion()
        chessBoard.render()
    }

    private fun testCheckStale() {
        chessBoard.setPiece(Location(5,6), Rook(Color.W))
        chessBoard.setPiece(Location(6,3), Queen(Color.W))
        chessBoard.setPiece(Location(0,2), King(Color.W))
        chessBoard.setPiece(Location(7,6), King(Color.B))
    }

    private fun pawnPromotion() {
        chessBoard.setPiece(Location(6,5), Pawn(Color.W))
        chessBoard.setPiece(Location(1,3), Pawn(Color.B))
        chessBoard.setPiece(Location(0,2), King(Color.W))
        chessBoard.setPiece(Location(7,6), King(Color.B))
    }

    /**
     * Finds every piece that the player has then finds every move of every piece and temporarily moves it to its
     * end location to run check() and if no moves exist that can take the players king out of check the game ends
     *
     * @param turn Information on whose turn it is
     * @return Boolean for if any legal moves can be made
     */
    fun legalMoves(turn: Turn): Boolean {
        val pieceLocations = allLocations(turn)
        for (location in pieceLocations) {
            val allMoves = findMoves(location, chessBoard, turn)
            for (move in allMoves) {
                val inCheck = tmpMove(move, chessBoard, turn)
                if (!inCheck) {
                    return false
                }
            }
        }
        return true
    }

    fun check(turn: Turn): Boolean {
        val enemyMoves = mutableSetOf<ChessMove>()
        val enemyTurn = Turn(!turn.getTurn()) // FIX THIS LATER

        val kingLocation = kingsLocation(turn)

        // if king sees a piece of opposite color, get all the moves that piece can make
        enemyMoves.addAll(checkLineOfSight(kingLocation, turn, enemyTurn))

        // if king location in enemy moves return true
        for (move in enemyMoves) {
            if (kingLocation == move.endLocation()) {
                return true
            }
        }
        return false
    }

    private fun checkLineOfSight(kingLocation: Location, turn: Turn, enemyTurn: Turn): Set<ChessMove> {
        val enemyMoves = mutableSetOf<ChessMove>()
        // general queen movement for LOS
        val queenDirections = listOf(
            Location(1, 1), Location(1, -1),
            Location(-1, 1), Location(-1, -1),
            Location(1, 0), Location(-1, 0),
            Location(0, 1),Location(0, -1)
        )
        // if the king sees a pc of opposite color, get the possibleMoves
        for (direction in queenDirections) {
            var nextLocation = kingLocation + direction
            while (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if (nextPiece.color == turn.getColor()) {
                    break
                }
                else if (nextPiece.color == turn.enemyColor()) {
                    enemyMoves.addAll(findMoves(nextLocation, chessBoard, enemyTurn))
                    break
                }
                else if (nextPiece.color == Color.E) {
                    nextLocation += direction
                }
            }
        }
        enemyMoves.addAll(checkKnightMovement(kingLocation, turn, enemyTurn))
        return enemyMoves
    }

    private fun checkKnightMovement (kingLocation: Location, turn: Turn, enemyTurn: Turn): Set<ChessMove> {
        val enemyMoves = mutableSetOf<ChessMove>()
        // knight movement
        val knightDirections = listOf(
            Location(2, -1), Location(2, 1),
            Location(-2, -1), Location(-2, 1),
            Location(1, 2), Location(-1, 2),
            Location(1, -2), Location(-1, -2)
        )
        // if the king sees a pc of opposite color, get the possibleMoves
        for (direction in knightDirections) {
            val nextLocation = kingLocation + direction
            if (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if (nextPiece.color == turn.enemyColor()) {
                    enemyMoves.addAll(findMoves(nextLocation, chessBoard, enemyTurn))
                }
            }
        }
        return enemyMoves
    }

    private fun findMoves(location: Location, chessBoard: ChessBoard, turn: Turn): Set<ChessMove> {
        val possibleMoves = mutableSetOf<Location>()
        val piece = chessBoard.getPiece(location)
        possibleMoves.addAll(piece.canMove(location, chessBoard, turn))

        val chessMoves = mutableSetOf<ChessMove>()
        for (end in possibleMoves) {
            chessMoves.add(ChessMove(location, end))
        }
        return chessMoves
    }

    /**
     * Find the kings location of the current turn
     *
     * @param turn Information on whose turn it is
     * @return Location(row,col) The location of the king
     */
    private fun kingsLocation(turn: Turn): Location {
        for (row in ChessBoard.ROW_START..ChessBoard.ROW_END) {
            for (col in ChessBoard.COL_START..ChessBoard.COL_END) {
                val currentPiece = chessBoard.getPiece(Location(row, col))
                if ((currentPiece is King) && currentPiece.color == turn.getColor()) {
                    return Location(row, col)
                }
            }
        }
        return Location(0,0)
    }

    fun tmpMove(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        val startLocation = chessMove.startLocation()
        val endLocation = chessMove.endLocation()
        val startPiece = chessBoard.getPiece(startLocation)
        val tmpPiece = chessBoard.getPiece(endLocation)

        chessBoard.setPiece(endLocation, startPiece)
        chessBoard.setPiece(startLocation, EmptySpot())

        val inCheck = check(turn)

        chessBoard.setPiece(startLocation, startPiece)
        chessBoard.setPiece(endLocation, tmpPiece)

        return inCheck
    }

    private fun allLocations(turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()
        for (row in ChessBoard.ROW_START..ChessBoard.ROW_END) {
            for (col in ChessBoard.COL_START..ChessBoard.COL_END) {
                val currentPiece = chessBoard.getPiece(Location(row, col))
                if ((currentPiece !is EmptySpot) && currentPiece.color == turn.getColor()) {
                    val piece = Location(row, col)
                    possibleMoves.add(piece)
                }
            }
        }
        return possibleMoves
    }

    fun sourceCoordinateVerifier(chessMove: ChessMove, turn: Turn): Boolean {
        val startCoordinates = chessMove.startLocation()
        val piece = chessBoard.getPiece(startCoordinates)
        return piece.type != PieceType.EMPTY && piece.color == turn.getColor()
    }

    private fun initChessPieces() {
        initPawns()
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