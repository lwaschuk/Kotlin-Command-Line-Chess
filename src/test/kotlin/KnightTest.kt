import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import game_helpers.ChessBoard
import game_helpers.ChessMove
import game_helpers.Location
import game_helpers.Turn
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import pieces.*
import run.Logger
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KnightTest {
    // Get the logback logging context
    private val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
    private lateinit var piece: ChessPiece
    private lateinit var emptyBoard: ChessBoard
    private lateinit var enemiesBoard: ChessBoard
    private lateinit var friendlyBoard: ChessBoard
    private lateinit var knightFriendlyBoard: ChessBoard
    private lateinit var knightEnemyBoard: ChessBoard
    private lateinit var startLocation: Location
    private lateinit var turn: Turn
    private lateinit var emptyMoves: Set<Location>
    private lateinit var enemyMoves: Set<Location>
    private lateinit var friendlyMoves: Set<Location>
    private lateinit var knightFriendlyMoves: Set<Location>
    private lateinit var knightEnemyMoves: Set<Location>
    private var directPieceLocations = setOf(
        Location(4,3), Location(4,4),
        Location(3,4), Location(2,4),
        Location(2,3), Location(2,2),
        Location(3,2), Location(4,2)
    )
    private var knightPossibleLocations = setOf(
        Location(5,2), Location(5,4), // up
        Location(1,2), Location(1,4), // down
        Location(4,1), Location(2,1), // left
        Location(4,5), Location(2,5)  // right
    )

    @BeforeEach
    fun setup() {
        loggerContext.getLogger("root").level = Level.OFF
        startLocation = Location(3,3)
        piece = Knight(Color.W)
        emptyBoard = ChessBoard()
        enemiesBoard = ChessBoard()
        friendlyBoard = ChessBoard()
        knightFriendlyBoard = ChessBoard()
        knightEnemyBoard = ChessBoard()

        turn = Turn(true) // white

        emptyBoard.setPiece(startLocation, piece)

        friendlyBoard.setPiece(startLocation, piece)
        for (location in directPieceLocations) {
            friendlyBoard.setPiece(location, Pawn(Color.W))
        }
        for (location in knightPossibleLocations) {
            friendlyBoard.setPiece(location, Pawn(Color.W))
        }

        enemiesBoard.setPiece(startLocation, piece)
        for (location in directPieceLocations) {
            enemiesBoard.setPiece(location, Pawn(Color.B))
        }
        for (location in knightPossibleLocations) {
            enemiesBoard.setPiece(location, Pawn(Color.B))
        }

        emptyMoves = piece.possibleMoves(startLocation, emptyBoard, turn)
        enemyMoves = piece.possibleMoves(startLocation, enemiesBoard, turn)
        friendlyMoves = piece.possibleMoves(startLocation, friendlyBoard, turn)
    }

    @Test
    fun `move up`() {
        val endLocation = Location(4,3)
        assertFalse(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move down`() {
        val endLocation = Location(2,3)
        assertFalse(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move right`() {
        val endLocation = Location(3,4)
        assertFalse(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move left`() {
        val endLocation = Location(3,2)
        assertFalse(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move top left`() {
        val endLocation = Location(4,2)
        assertFalse(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move top right`() {
        val endLocation = Location(4,4)
        assertFalse(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move bottom left`() {
        val endLocation = Location(2,2)
        assertFalse(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move bottom right`() {
        val endLocation = Location(2,4)
        assertFalse(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move two spaces`() {
        val endLocation = Location(1,5)
        assertFalse(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move like a knight up left`() {
        val endLocation = Location(5,2)
        assertTrue(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move like a knight up right`() {
        val endLocation = Location(5,4)
        assertTrue(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move like a knight down right`() {
        val endLocation = Location(1,4)
        assertTrue(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move like a knight down left`() {
        val endLocation = Location(1,2)
        assertTrue(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move like a knight left up`() {
        val endLocation = Location(4,1)
        assertTrue(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move like a knight left down`() {
        val endLocation = Location(2,5)
        assertTrue(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move like a knight right up`() {
        val endLocation = Location(4,5)
        assertTrue(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }

    @Test
    fun `move like a knight right down`() {
        val endLocation = Location(2,5)
        assertTrue(emptyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), emptyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by EMPTY spots: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(enemyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by ENEMY pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertTrue(piece.move(ChessMove(startLocation, endLocation), enemiesBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by enemy pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(friendlyMoves.contains(endLocation), "${piece.pieceType} did not have a possible move to the expected position when surrounded by FRIENDLY pieces:: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
        assertFalse(piece.move(ChessMove(startLocation, endLocation), friendlyBoard, turn), "${piece.pieceType} failed to move to the expected position when surrounded by friendly pieces: " +
                "START: ${Logger.convertLocation(startLocation)} -> END: ${Logger.convertLocation(endLocation)}")
    }
}