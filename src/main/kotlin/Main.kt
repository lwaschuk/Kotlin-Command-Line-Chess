import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import mu.KotlinLogging
import org.slf4j.LoggerFactory
import run.Logger
import run.Run
import kotlin.system.exitProcess



/**
 * Entry point for the game, sets debug if we want to see log messages
 *
 * @param args the optional flags for help or debug
 */
fun main(args: Array<String>) {
    var debug = false
    var help = false



    for (arg in args) {
        when (arg) {
            "-d", "--debug" -> debug = true
            "-h", "--help" -> help = true
        }
    }

    if (help) {
        println("\"Usage: java -jar Chess-1.0-SNAPSHOT-standalone.jar [-h|--help] [-d|--debug]\"")
        exitProcess(0)
    } else if (debug) {
        println("Debug mode enabled.")
    }


    // Get the logback logging context
    val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext


    when (debug) {
        true -> loggerContext.getLogger("root").level = Level.DEBUG
        false -> loggerContext.getLogger("root").level = Level.OFF
    }

    // Log a message
    val logger = Logger("MAIN")
    logger.debug("DEBUG MESSAHE")

    val game = Run()
    game.runGame()
}
