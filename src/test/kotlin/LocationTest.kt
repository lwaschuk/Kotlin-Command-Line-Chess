import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import game_helpers.Location
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class LocationTest {
    @Test
    fun `check row return value`() {
        assertEquals(1, locationOne.row())
        assertEquals(2, locationTwo.row())
        assertEquals(10, invalidLocation.row())
    }

    @Test
    fun `check column return value`() {
        assertEquals(1, locationOne.column())
        assertEquals(2, locationTwo.column())
        assertEquals(10, invalidLocation.column())
    }

    @Test
    fun `check value return`() {
        assertEquals(locationOne.value(), Pair(1,1))
        assertEquals(locationTwo.value(), Pair(2,2))
        assertEquals(invalidLocation.value(), Pair(10,10))
    }

    @Test
    fun `check invalid locations`() {
        assertTrue(locationOne.isValid())
        assertTrue(locationTwo.isValid())
        assertFalse(invalidLocation.isValid())
    }

    @Test
    fun `check equals`() {
        assertEquals(locationOne, locationOne)
        assertNotEquals(locationTwo, invalidLocation)
    }

    @Test
    fun `check if they can be added` () {
        val result = locationOne + locationTwo
        assertEquals(result.value(), Pair(3,3))
    }

    companion object {
        // Get the logback logging context
        private val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
        val locationOne = Location(1,1)
        val locationTwo = Location(2,2)
        val invalidLocation = Location(10,10)

        @JvmStatic
        @BeforeAll
        fun setup() {
            loggerContext.getLogger("root").level = Level.OFF
        }

    }

}