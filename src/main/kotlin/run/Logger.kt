package run

import game_helpers.Location
import mu.KotlinLogging

class Logger(name: String) {
    private val logger = KotlinLogging.logger(name)
    fun trace(message: String) = logger.trace(message)
    fun debug(message: String) = logger.debug(message)
    fun info(message: String) = logger.info(message)
    fun warn(message: String) = logger.warn(message)
    fun error(message: String) = logger.error(message)

    companion object {
        /**
         * A method to convert a set of chess moves from how it is stored ((0,1),(0,3))
         * to a human-readable representation ((a,2),(a,4))
         *
         * @param input the set of chess moves to convert
         *
         * @return the set of moves in a human-readable representation
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
         */
        fun convertLocation(input: Location): Pair<Char, Char> {
            val letter = input.column() + 'a'.code
            val number = input.row() + '1'.code

            return Pair(letter.toChar(), number.toChar())
        }
    }
}
