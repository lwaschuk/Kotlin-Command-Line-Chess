package game_helpers

import pieces.*
import run.Logger
import kotlin.math.max
import kotlin.math.min

/**
 * The class that is responsible for all the Game Logic. Checks for Check, Checkmate, Stalemate, Legal Moves.
 *
 * @param chessBoard the board the game will be ran on
 */
class GameLogic(chessBoard: ChessBoard) {
    private val logger = Logger(this.javaClass.name)
    private var chessBoard: ChessBoard

    init {
        this.chessBoard = chessBoard
    }

    /**
     * The public method to begin the game
     *
     * @param nothing
     * @return nothing
     */
    fun startGame() {
        logger.debug("Run Game Started")
        logger.debug("Init all pieces")
        initChessPieces()
        chessBoard.render()
    }

    /**
     * Finds every piece that the player has then finds every move of every piece and temporarily moves it to its
     * end location to run check() and if no moves exist that can take the players king out of check the game ends
     *
     * @param turn Information on whose turn it is
     * @return Boolean for if any legal moves can be made
     */
    fun legalMoves(turn: Turn): Boolean {
        logger.debug("Legal Moves Started, Turn is ${turn.colorToString()}")
        val pieceLocations = allLocations(turn)
        logger.debug("All Piece location of ${turn.colorToString()}: ${Logger.convertSetOfLocations(pieceLocations)}")
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

    /**
     * Checks if the king is in check, using turn check whose turn it is
     *
     * @param turn Information on whose turn it is
     *
     * @return Boolean stating if the king is in check
     */
    fun check(turn: Turn): Boolean {
        val enemyMoves = mutableSetOf<ChessMove>()
        val enemyTurn = Turn(!turn.getTurn()) // FIX THIS LATER

        val kingLocation = kingsLocation(turn)

        // if king sees a piece of opposite color, get all the moves that piece can make
        enemyMoves.addAll(checkLineOfSight(kingLocation, turn, enemyTurn))

        // if king location in enemy moves return true
        for (move in enemyMoves) {
            if (kingLocation == move.endLocation()) {
                logger.warn("${turn.colorToString()} King is being attacked on ${Logger.convertLocation(kingLocation)}")
                return true
            }
        }
        return false
    }

    /**
     * Checks what pieces of the opposite color the king can see
     *
     * @param kingLocation The location of the king
     * @param turn Information on whose turn it is
     * @param enemyTurn Information on the enemy (pc color etc..)
     *
     * @return Set<enemyMoves> -> the set of all legal moves the enemy can make that can attack the king
     */
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
            while (nextLocation.isValid()) {
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

    /**
     * Helper function for checkLineOfSight that simulates knight movement
     *
     * @param kingLocation The location of the king
     * @param turn Information on whose turn it is
     * @param enemyTurn Information on the enemy (pc color etc..)
     *
     * @return Set<enemyMoves> -> the set of all legal moves the enemy can make that can attack the king
     */
    private fun checkKnightMovement (kingLocation: Location, turn: Turn, enemyTurn: Turn): Set<ChessMove> {
        val enemyMoves = mutableSetOf<ChessMove>()
        // general knight movement
        val knightDirections = listOf(
            Location(2, -1), Location(2, 1),
            Location(-2, -1), Location(-2, 1),
            Location(1, 2), Location(-1, 2),
            Location(1, -2), Location(-1, -2)
        )
        // if the king sees a pc of opposite color, get the possibleMoves (of the knight)
        for (direction in knightDirections) {
            val nextLocation = kingLocation + direction
            if (nextLocation.isValid()) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if (nextPiece.color == turn.enemyColor()) {
                    enemyMoves.addAll(findMoves(nextLocation, chessBoard, enemyTurn))
                }
            }
        }
        return enemyMoves
    }

    /**
     * Helper function for legalMoves() that finds every move a particular piece on the board can make
     *
     * @param location The location of the piece we are interested in
     * @param chessBoard The board
     * @param turn Information on whose turn it is
     */
    private fun findMoves(location: Location, chessBoard: ChessBoard, turn: Turn): Set<ChessMove> {
        val possibleMoves = mutableSetOf<Location>()
        val piece = chessBoard.getPiece(location)
        possibleMoves.addAll(piece.possibleMoves(location, chessBoard, turn))

        val chessMoves = mutableSetOf<ChessMove>()
        for (end in possibleMoves) {
            chessMoves.add(ChessMove(location, end))
        }
        return chessMoves
    }

    /**
     * Find the kings location for the current player
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

    /**
     * Helper for legalMoves() that moves a chess piece to check if there exists a move that will keep the
     * king out of check
     *
     * @param chessMove Information and the Start & End Location of a move
     * @param chessBoard The board
     * @param turn Information on whose turn it is
     *
     * @return Boolean representing if the king is in check or not
     */
    fun tmpMove(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        val startLocation = chessMove.startLocation()
        val endLocation = chessMove.endLocation()
        val startPiece = chessBoard.getPiece(startLocation)
        val tmpPiece = chessBoard.getPiece(endLocation)
        logger.debug("Temporarily moving piece from ${Logger.convertLocation(startLocation)} to ${Logger.convertLocation(endLocation)}")
        chessBoard.setPiece(endLocation, startPiece)
        chessBoard.setPiece(startLocation, EmptySpot())

        val inCheck = check(turn)
        logger.warn("${turn.colorToString()} in check: $inCheck")
        chessBoard.setPiece(startLocation, startPiece)
        chessBoard.setPiece(endLocation, tmpPiece)

        return inCheck
    }

    /**
     * Gets every location of every piece for <turn>
     *
     * @param turn Information on whose turn it is
     *
     * @return Set<Location> containing the location for all the pieces owned by <turn>
     */
    private fun allLocations(turn: Turn): Set<Location> {
        val locations = mutableSetOf<Location>()
        for (row in ChessBoard.ROW_START..ChessBoard.ROW_END) {
            for (col in ChessBoard.COL_START..ChessBoard.COL_END) {
                val currentPiece = chessBoard.getPiece(Location(row, col))
                if ((currentPiece !is EmptySpot) && currentPiece.color == turn.getColor()) {
                    val piece = Location(row, col)
                    locations.add(piece)
                }
            }
        }
        return locations
    }

    /**
     * Checks if a king-side castle is possible
     *
     * @param turn Information on whose turn it is
     * @param kingside Kingside or Queenside castle
     * @return Boolean representing if a king-side castle is possible
     */
    fun kingsCastle(turn: Turn, kingside: Boolean): Boolean {
        val kingsLocation = Location(if (turn.getTurn()) 0 else 7, 4)
        val rooksLocation = Location(if (turn.getTurn()) 0 else 7, if (kingside) 7 else 0)
        val king = chessBoard.getPiece(kingsLocation)
        val rook = chessBoard.getPiece(rooksLocation)

        if ((!king.hasMoved()) && (!rook.hasMoved()) && (kingside) && (king is King) && (rook is Rook)
            && (!pieceBetween(kingsLocation, rooksLocation))) {
            return true
        }
        return false
    }

    /**
     * Checks if a queen-side castle is possible
     *
     * @param turn Information on whose turn it is
     * @param kingside Kingside or Queenside castle
     * @return Boolean representing if a queen-side castle is possible
     */
    fun queensCastle(turn: Turn, kingside: Boolean): Boolean {
        val kingsLocation = Location(if (turn.getTurn()) 0 else 7, 4)
        val rooksLocation = Location(if (turn.getTurn()) 0 else 7, if (kingside) 7 else 0)
        val king = chessBoard.getPiece(kingsLocation)
        val rook = chessBoard.getPiece(rooksLocation)

        if ((!king.hasMoved()) && (!rook.hasMoved()) && (!kingside) && (king is King) && (rook is Rook)
            && (!pieceBetween(kingsLocation, rooksLocation))) {
            return true
        }
        return false
    }

    /**
     * Checks if there is a piece in between the king and the rook when attemping to castle
     *
     * @param kingLocation The location of the king
     * @param rooksLocation The location of the rook
     */
    private fun pieceBetween(kingLocation: Location, rooksLocation: Location): Boolean {
        for (col in min(kingLocation.column(), rooksLocation.column())+1 until max(kingLocation.column(), rooksLocation.column())) {
            if (chessBoard.getPiece(Location(kingLocation.row(), col)).pieceType != PieceType.EMPTY){
                return true
            }
        }
        return false
    }

    /**
     * After verifying the move expected from the player is a castling move, this function will actually move the pieces
     *
     * @param turn Information on whose turn it is
     * @param kingside Kingside or Queenside castle
     * @return nothing
     */
    fun castle(turn: Turn, kingside: Boolean) {
        val kingsLocation = Location(if (turn.getTurn()) 0 else 7, 4)
        val rooksLocation = Location(if (turn.getTurn()) 0 else 7, if (kingside) 7 else 0)
        val kingsDestination = Location(if (turn.getTurn()) 0 else 7, if (kingside) 6 else 2)
        val rooksDestination = Location(if (turn.getTurn()) 0 else 7, if (kingside) 5 else 3)
        val king = chessBoard.getPiece(kingsLocation)
        val rook = chessBoard.getPiece(rooksLocation)

        // move king
        chessBoard.setPiece(kingsDestination, king)
        chessBoard.setPiece(kingsLocation, EmptySpot())
        // move rook
        chessBoard.setPiece(rooksDestination, rook)
        chessBoard.setPiece(rooksLocation, EmptySpot())
    }


    /**
     * Check if a location on the chessBoard is <turns> color and not empty
     *
     * @param chessMove Information on the start location of the targeted piece
     * @param turn Information on whose turn it is
     */
    fun sourceCoordinateVerifier(chessMove: ChessMove, turn: Turn): Boolean {
        val startCoordinates = chessMove.startLocation()
        val piece = chessBoard.getPiece(startCoordinates)
        return piece.pieceType != PieceType.EMPTY && piece.color == turn.getColor()
    }

    /**
     * Initialize all the chess pieces
     *
     * @param nothing
     * @return nothing
     */
    private fun initChessPieces() {
        initPawns()
        initKnights()
        initRooks()
        initBishops()
        initQueens()
        initKings()
    }

    /**
     * Initialize all the King pieces
     *
     * @param nothing
     * @return nothing
     */
    private fun initKings() {
        chessBoard.setPiece(Location(0,4), King(Color.W))
        chessBoard.setPiece(Location(7,4), King(Color.B))
    }

    /**
     * Initialize all the Queen pieces
     *
     * @param nothing
     * @return nothing
     */
    private fun initQueens() {
        chessBoard.setPiece(Location(0,3), Queen(Color.W))
        chessBoard.setPiece(Location(7,3), Queen(Color.B))
    }

    /**
     * Initialize all the Bishop pieces
     *
     * @param nothing
     * @return nothing
     */
    private fun initBishops() {
        chessBoard.setPiece(Location(0,2), Bishop(Color.W))
        chessBoard.setPiece(Location(0,5), Bishop(Color.W))
        chessBoard.setPiece(Location(7,2), Bishop(Color.B))
        chessBoard.setPiece(Location(7,5), Bishop(Color.B))
    }

    /**
     * Initialize all the Knight pieces
     *
     * @param nothing
     * @return nothing
     */
    private fun initKnights(){
        chessBoard.setPiece(Location(0,1), Knight(Color.W))
        chessBoard.setPiece(Location(0,6), Knight(Color.W))
        chessBoard.setPiece(Location(7,1), Knight(Color.B))
        chessBoard.setPiece(Location(7,6), Knight(Color.B))
    }

    /**
     * Initialize all the Rook pieces
     *
     * @param nothing
     * @return nothing
     */
    private fun initRooks(){
        chessBoard.setPiece(Location(0, 0), Rook(Color.W))
        chessBoard.setPiece(Location(0, 7), Rook(Color.W))
        chessBoard.setPiece(Location(7, 0), Rook(Color.B))
        chessBoard.setPiece(Location(7, 7), Rook(Color.B))
    }

    /**
     * Initialize all the Pawn pieces
     *
     * @param nothing
     * @return nothing
     */
    private fun initPawns() {
        for (col in 0 until 8) {
            chessBoard.setPiece(Location(1, col), Pawn(Color.W))
            chessBoard.setPiece(Location(6, col), Pawn(Color.B))
        }
    }

}