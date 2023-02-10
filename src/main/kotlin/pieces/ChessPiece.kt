package pieces

import game_helpers.ChessBoard
import game_helpers.ChessMove
import game_helpers.Location
import game_helpers.Turn
import run.Logger
import kotlin.math.log

/**
 * The class all chess pieces must be created from
 *
 * @param color Who "owns" this piece
 * @param pieceType What kind of piece this is
 * @param directions all the possible directions this piece can move
 */
abstract class ChessPiece(color: Color, pieceType: PieceType, directions: List<Location>) {
    private val logger = Logger(this.javaClass.name)
    private var directions: List<Location>
    var color: Color
    var pieceType: PieceType
    private var hasMoved: Boolean

    init {
        this.directions = directions
        this.color = color
        this.pieceType = pieceType
        this.hasMoved = false
    }

    /**
     * The method to force every child class to implement a way to print its piece
     *
     * @param nothing
     * @return unicode string of the piece
     */
    abstract fun print(): String

    /**
     * Public method to invoke the targeted piece to make a move, pawns move differently than other pieces, this is the
     * initial differentiation
     *
     * @param chessMove The move the player intends on making
     * @param chessBoard The board
     * @param turn information on whose turn it is
     *
     * @return Boolean Whether the piece can move
     */
    fun move(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        return when (this.pieceType) {
            PieceType.PAWN -> pawnMove(chessMove, chessBoard, turn)
            PieceType.EMPTY -> {
                println("ERROR: Trying to move a empty spot...")
                false
            }
            else -> notPawnMove(chessMove, chessBoard, turn)
        }
    }

    /**
     * Helper method for move() finds all the possible moves a piece that is not a pawn can make
     *
     * @param chessMove The move the player intends on making
     * @param chessBoard The board
     * @param turn information on whose turn it is
     *
     * @return Boolean Whether the piece can move
     */
    private fun notPawnMove(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        val possibleMoves = possibleMoves(chessMove.startLocation(), chessBoard, turn)
        for (move in possibleMoves) {
            if (chessMove.endLocation() == move) {
                this.hasMoved = true
                chessBoard.setPiece(chessMove.endLocation(), this)
                chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                return true
            }
        }
        return false
    }

    /**
     * Public function to see if a piece has been moved yet
     *
     * @param nothing
     * @return Boolean representing if the piece has moved or not
     */
    fun hasMoved(): Boolean {
        return this.hasMoved
    }

    /**
     * Helper method for move() finds all the possible moves a piece that is a pawn can make
     *
     * @param chessMove The move the player intends on making
     * @param chessBoard The board
     * @param turn information on whose turn it is
     *
     * @return Boolean Whether the piece can move
     */
    private fun pawnMove(chessMove: ChessMove, chessBoard: ChessBoard, turn: Turn): Boolean {
        val possibleMoves = possibleMoves(chessMove.startLocation(), chessBoard, turn)
        for (move in possibleMoves) {
            if (move.value() == chessMove.endLocation().value()) {
                val movement = if (turn.getColor() == Color.W) 1 else -1
                val behindEndLocation =
                    Location(chessMove.endLocation().row() - movement, chessMove.endLocation().column())
                val behindEndPiece = chessBoard.getPiece(behindEndLocation)
                this.hasMoved = true
                return when {
                    // promote pawn if it reaches last column for its color
                    (canPromote(chessMove,turn)) -> {
                        var newPieceString: String
                        do {
                            newPieceString = getInput()
                        } while (!verifyInput(newPieceString))
                        chessBoard.setPiece(chessMove.endLocation(), convertToPiece(newPieceString, turn))
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        true
                    }
                    // en passant
                    ((behindEndPiece is Pawn) && (chessMove.startLocation()
                        .column() != chessMove.endLocation().column())
                            && (behindEndPiece.canBeEnPassant) && (behindEndPiece.color == turn.enemyColor())) -> {

                        chessBoard.setPiece(chessMove.endLocation(), this)
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        chessBoard.setPiece(behindEndLocation, EmptySpot())
                        true
                    }
                    // if moving two spaces, flag for en passant
                    (kotlin.math.abs(chessMove.startLocation().row() - chessMove.endLocation().row()) == 2
                            && chessBoard.getPiece(chessMove.startLocation()).pieceType == PieceType.PAWN) -> {
                        if (this is Pawn) {
                            this.canBeEnPassant = true
                        }
                        chessBoard.setPiece(chessMove.endLocation(), this)
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        true
                    }
                    // normal move
                    else -> {
                        chessBoard.setPiece(chessMove.endLocation(), this)
                        chessBoard.setPiece(chessMove.startLocation(), EmptySpot())
                        true
                    }
                }
            }
        }
        return false
    }

    /**
     * Helper method for pawnMove() checks if the pawn hit the final row, and can promote
     *
     * @param chessMove The move the player intends on making
     * @param turn information on whose turn it is
     *
     * @return Boolean Whether the pawn can promote
     */
    private fun canPromote(chessMove: ChessMove, turn: Turn): Boolean {
        val endRow = if (turn.getColor() == Color.W) ChessBoard.ROW_END else ChessBoard.ROW_START
        if (chessMove.endLocation().row() == endRow) {
            logger.debug("${turn.colorToString()} pawn can promote!")
            return true
        }
        return false
    }

    /**
     * Helper method for promote() gets user input for what piece type to make the promoted pawn
     *
     * @param nothing
     * @return String the input from the user
     */
    private fun getInput(): String {
        print("Promote Pawn (B, R, K, Q):\n> ")
        return readln().uppercase()
    }

    /**
     * Helper method for getInput() verifies if the piece can be converted
     *
     * @param input The piece the player intends to promote the pawn to
     * @return String
     */
    private fun verifyInput(input: String): Boolean {
        val types = listOf("B", "R", "K", "Q")
        return input in types
    }

    /**
     * Helper method for promote() converts the string input into a piece class
     *
     * @param input The piece the player intends to promote the pawn to
     * @param turn Information on whose turn it is
     * @return ChessPiece The Piece class of the new piece
     */
    private fun convertToPiece(input: String, turn: Turn): ChessPiece {
        return when (input) {
            "B" -> Bishop(turn.getColor())
            "R" -> Rook(turn.getColor())
            "K" -> Knight(turn.getColor())
            "Q" -> Queen(turn.getColor())
            else -> EmptySpot()
        }
    }

    /**
     * Method to get all the possible moves a targeted piece can make
     *
     * @param startLocation The starting location
     * @param chessBoard The board
     * @param turn Information on whose turn it is
     * @return Set<Location> The set of all locations the piece can move
     */
    fun possibleMoves(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

        when (this.pieceType) {
            PieceType.PAWN -> possibleMoves.addAll(pawnMovement(startLocation, chessBoard, turn))
            PieceType.QUEEN -> possibleMoves.addAll(additiveMovement(startLocation, chessBoard, turn))
            PieceType.BISHOP -> possibleMoves.addAll(additiveMovement(startLocation, chessBoard, turn))
            PieceType.ROOK -> possibleMoves.addAll(additiveMovement(startLocation, chessBoard, turn))
            PieceType.KING -> possibleMoves.addAll(staticMovement(startLocation, chessBoard, turn))
            PieceType.KNIGHT -> possibleMoves.addAll(staticMovement(startLocation, chessBoard, turn))
            else -> {
                println("ERROR: Piece type cannot be found...")
            }
        }
        logger.debug("All Moves for piece @ location ${Logger.convertLocation(startLocation)}: ${Logger.convertSetOfLocations(possibleMoves)}")
        return possibleMoves
    }

    /**
     * Helper method for possibleMoves(), find possible moves for the ROOK, BISHOP, AND QUEEN
     *
     * @param startLocation The starting location
     * @param chessBoard The board
     * @param turn Information on whose turn it is
     * @return Set<Location> The set of all locations the piece can move
     */
    private fun additiveMovement(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()
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

    /**
     * Helper method for possibleMoves(), find possible moves for the KNIGHT AND KING
     *
     * @param startLocation The starting location
     * @param chessBoard The board
     * @param turn Information on whose turn it is
     * @return Set<Location> The set of all locations the piece can move
     */
    private fun staticMovement(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()
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

    /**
     * Helper method for possibleMoves(), find possible moves for the PAWNS
     *
     * @param startLocation The starting location
     * @param chessBoard The board
     * @param turn Information on whose turn it is
     * @return Set<Location> The set of all locations the piece can move
     */
    private fun pawnMovement(startLocation: Location, chessBoard: ChessBoard, turn: Turn): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()
        val movement = if (turn.getColor() == Color.W) 1 else -1

        possibleMoves.addAll(getStraightMoves(chessBoard, turn, movement, startLocation))
        possibleMoves.addAll(getAttackMoves(chessBoard, turn, movement, startLocation))

        return possibleMoves
    }

    /**
     * Helper method for possibleMoves(), find possible straight moves for the PAWN
     *
     * @param startLocation The starting location
     * @param chessBoard The board
     * @param turn Information on whose turn it is
     * @return Set<Location> The set of all locations the piece can move
     */
    private fun getStraightMoves(chessBoard: ChessBoard, turn: Turn, movement: Int, startLocation: Location): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

        val straightDirections = mutableListOf<Location>()

        val startingRow = if (turn.getColor() == Color.W) 1 else 6

        if (startLocation.row() == startingRow) {
            straightDirections.add(Location(2 * movement, 0))
        }
        straightDirections.add(Location(1 * movement, 0))

        for (direction in straightDirections) {
            val nextLocation = startLocation + direction
            if (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if (nextPiece is EmptySpot) {
                    possibleMoves.add(nextLocation)
                }
            }
        }
        return possibleMoves
    }

    /**
     * Helper method for possibleMoves(), find possible attacking moves for the PAWN
     * @param startLocation The starting location
     * @param chessBoard The board
     * @param turn Information on whose turn it is
     * @return Set<Location> The set of all locations the piece can move
     */
    private fun getAttackMoves(chessBoard: ChessBoard, turn: Turn, movement: Int, startLocation: Location): Set<Location> {
        val possibleMoves = mutableSetOf<Location>()

        val attackDirections = listOf(
            Location(movement, -1),
            Location(movement, 1),
        )

        for (direction in attackDirections) {
            val nextLocation = startLocation + direction
            if (nextLocation.isValid(nextLocation)) {
                val nextPiece = chessBoard.getPiece(nextLocation)
                if ((nextPiece !is EmptySpot) && (nextPiece.color == turn.enemyColor())) {
                    possibleMoves.add(nextLocation)
                }
                if (nextPiece is EmptySpot) {
                    val behindPiece = chessBoard.getPiece(Location(nextLocation.row() - movement, nextLocation.column()))
                    if (behindPiece.pieceType == PieceType.PAWN && behindPiece.color == turn.enemyColor()) {
                        val pawn = behindPiece as Pawn
                        if (pawn.canBeEnPassant) {
                            possibleMoves.add(nextLocation)
                        }
                    }
                }
            }
        }
        return possibleMoves
    }
}