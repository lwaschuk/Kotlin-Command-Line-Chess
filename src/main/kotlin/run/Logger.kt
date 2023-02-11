package run

import game_helpers.Location
import mu.KotlinLogging

/**
 * The `Logger` class provides a simple wrapper around KotlinLogging to log messages at different levels.
 *
 * @param name the name of the logger
 * @author Lukas Waschuk
 */
class Logger(name: String) {
    private val logger = KotlinLogging.logger(name)

    /**
     * Log a trace message
     *
     * @param message the message to log
     * @author Lukas Waschuk
     */
    fun trace(message: String) = logger.trace(message)

    /**
     * Log a debug message
     *
     * @param message the message to log
     * @author Lukas Waschuk
     */
    fun debug(message: String) = logger.debug(message)

    /**
     * Log a warn message
     *
     * @param message the message to log
     * @author Lukas Waschuk
     */
    fun warn(message: String) = logger.warn(message)

    companion object {
        /**
         * A method to convert a set of chess moves from how it is stored ((0,1),(0,3))
         * to a human-readable representation ((a,2),(a,4))
         *
         * @param input the set of chess moves to convert
         *
         * @return the set of moves in a human-readable representation
         * @author Lukas Waschuk
         */
        fun convertSetOfLocations(input: Set<Location>): Set<Pair<Char, Char>> {
            val newSet = mutableSetOf<Pair<Char, Char>>()
            for (location in input) {
                val letter = location.column() + 'a'.code
                val number = location.row() + '1'.code
                newSet.add(Pair(letter.toChar(), number.toChar()))
            }
            return newSet
        }

        /**
         * A method to convert a Location from how it is stored (0,1)
         * to a human-readable representation (a,2)
         *
         * @param input the location to convert
         *
         * @return the location in a human-readable representation
         * @author Lukas Waschuk
         */
        fun convertLocation(input: Location): Pair<Char, Char> {
            val letter = input.column() + 'a'.code
            val number = input.row() + '1'.code

            return Pair(letter.toChar(), number.toChar())
        }
    }
}
