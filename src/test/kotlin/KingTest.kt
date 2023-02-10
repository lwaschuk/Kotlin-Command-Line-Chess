import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import game_helpers.ChessBoard
import game_helpers.Location
import game_helpers.Turn
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import pieces.ChessPiece
import pieces.Color
import pieces.King
import pieces.Pawn
import run.Logger
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KingTest {
    // Get the logback logging context
    private val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
    private val logger = Logger(this.javaClass.name)
    private lateinit var piece: ChessPiece
    private lateinit var emptyBoard: ChessBoard
    private lateinit var enemiesBoard: ChessBoard
    private lateinit var friendlyBoard: ChessBoard
    private lateinit var startLocation: Location
    private lateinit var turn: Turn
    private lateinit var emptyMoves: Set<Location>
    private lateinit var enemyMoves: Set<Location>
    private lateinit var friendlyMoves: Set<Location>
    private var pieceLocations = setOf(
        Location(4,3), Location(4,4),
        Location(3,4), Location(2,4),
        Location(2,3), Location(2,2),
        Location(3,2), Location(4,2)
    )

    @BeforeEach
    fun setup(){
        loggerContext.getLogger("root").level = Level.OFF
        startLocation = Location(3,3)
        piece = King(Color.W)
        emptyBoard = ChessBoard()
        enemiesBoard = ChessBoard()
        friendlyBoard = ChessBoard()
        turn = Turn(true) // white

        for (location in pieceLocations) {
            enemiesBoard.setPiece(location, Pawn(Color.B))
        }
        for (location in pieceLocations) {
            friendlyBoard.setPiece(location, Pawn(Color.W))
        }
        logger.info("Possible Moves for a King surrounded by empty spots")
        emptyMoves = piece.possibleMoves(startLocation, emptyBoard, turn)
        logger.info("Possible Moves for a King surrounded by enemy pieces")
        enemyMoves = piece.possibleMoves(startLocation, enemiesBoard, turn)
        logger.info("Possible Moves for a King surrounded by friendly pieces")
        friendlyMoves = piece.possibleMoves(startLocation, friendlyBoard, turn)
    }

    @Test
    fun `move up`() {
        val endLocation = Location(4,3)
        assertTrue(emptyMoves.contains(endLocation), "King did not move to the expected position when surrounded by empty spots")
        assertTrue(enemyMoves.contains(endLocation), "King did not move to the expected position when surrounded by enemy pieces")
        assertFalse(friendlyMoves.contains(endLocation), "King did not move to the expected position when surrounded by friendly pieces")
    }

    @Test
    fun `move down`() {
        val endLocation = Location(2,3)
        assertTrue(emptyMoves.contains(endLocation), "King did not move to the expected position when surrounded by empty spots")
        assertTrue(enemyMoves.contains(endLocation), "King did not move to the expected position when surrounded by enemy pieces")
        assertFalse(friendlyMoves.contains(endLocation), "King did not move to the expected position when surrounded by friendly pieces")
    }

    @Test
    fun `move right`() {
        val endLocation = Location(3,4)
        assertTrue(emptyMoves.contains(endLocation), "King did not move to the expected position when surrounded by empty spots")
        assertTrue(enemyMoves.contains(endLocation), "King did not move to the expected position when surrounded by enemy pieces")
        assertFalse(friendlyMoves.contains(endLocation), "King did not move to the expected position when surrounded by friendly pieces")
    }

    @Test
    fun `move left`() {
        val endLocation = Location(3,2)
        assertTrue(emptyMoves.contains(endLocation), "King did not move to the expected position when surrounded by empty spots")
        assertTrue(enemyMoves.contains(endLocation), "King did not move to the expected position when surrounded by enemy pieces")
        assertFalse(friendlyMoves.contains(endLocation), "King did not move to the expected position when surrounded by friendly pieces")
    }

    @Test
    fun `move top left`() {
        val endLocation = Location(4,2)
        assertTrue(emptyMoves.contains(endLocation), "King did not move to the expected position when surrounded by empty spots")
        assertTrue(enemyMoves.contains(endLocation), "King did not move to the expected position when surrounded by enemy pieces")
        assertFalse(friendlyMoves.contains(endLocation), "King did not move to the expected position when surrounded by friendly pieces")
    }

    @Test
    fun `move top right`() {
        val endLocation = Location(4,4)
        assertTrue(emptyMoves.contains(endLocation), "King did not move to the expected position when surrounded by empty spots")
        assertTrue(enemyMoves.contains(endLocation), "King did not move to the expected position when surrounded by enemy pieces")
        assertFalse(friendlyMoves.contains(endLocation), "King did not move to the expected position when surrounded by friendly pieces")
    }

    @Test
    fun `move bottom left`() {
        val endLocation = Location(2,2)
        assertTrue(emptyMoves.contains(endLocation), "King did not move to the expected position when surrounded by empty spots")
        assertTrue(enemyMoves.contains(endLocation), "King did not move to the expected position when surrounded by enemy pieces")
        assertFalse(friendlyMoves.contains(endLocation), "King did not move to the expected position when surrounded by friendly pieces")
    }

    @Test
    fun `move bottom right`() {
        val endLocation = Location(2,4)
        assertTrue(emptyMoves.contains(endLocation), "King did not move to the expected position when surrounded by empty spots")
        assertTrue(enemyMoves.contains(endLocation), "King did not move to the expected position when surrounded by enemy pieces")
        assertFalse(friendlyMoves.contains(endLocation), "King did not move to the expected position when surrounded by friendly pieces")
    }

    @Test
    fun `move two spaces`() {
        val endLocation = Location(3,5)
        assertFalse(emptyMoves.contains(endLocation), "King did not move to the expected position when surrounded by empty spots")
        assertFalse(enemyMoves.contains(endLocation), "King did not move to the expected position when surrounded by enemy pieces")
        assertFalse(friendlyMoves.contains(endLocation), "King did not move to the expected position when surrounded by friendly pieces")
    }
}