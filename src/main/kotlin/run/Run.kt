package run

import game_helpers.ChessBoard
import game_helpers.ChessMove
import game_helpers.GameLogic
import game_helpers.Turn
import kotlin.system.exitProcess

/**
 * All methods to run the game loop
 *
 * @param nothing
 */
class Run {

    /**
     * The Game Loop
     *
     * @param nothing
     * @return nothing
     */
    fun runGame(){
        // init
        val chessBoard = ChessBoard()
        val gameLogic = GameLogic(chessBoard)
        val turn = Turn()

        // get names
        println("Kotlin Command Line Chess")

        // start game
        gameLogic.startGame()

        while (true) {
            do {
                var validMove = false
                var inCheck = false
                var validInput: Boolean
                var s: String

                // get move
                do {
                    s = getInput(turn.getTurn())
                    validInput = verifyInput(s).also {
                        if (!it) {
                            println("Invalid Input")
                        }
                    }
                } while (!validInput)

                if (s == "h") {
                    continue
                }

                if (s == "00") {
                    if (gameLogic.kingsCastle(turn, true)) {
                        gameLogic.castle(turn, true)
                        break
                    }
                    else {
                        println("Cannot Castle King-side")
                        continue
                    }
                }
                else if (s == "000") {
                    if (gameLogic.queensCastle(turn, false)) {
                        gameLogic.castle(turn, false)
                        break
                    }
                    else {
                        println("Cannot Castle Queen-side")
                        continue
                    }
                }

                val chessMove = ChessMove(s)

                if (exit(s)) {
                    bye()
                }

                validMove = gameLogic.sourceCoordinateVerifier(chessMove, turn).also {
                    if (!it) {
                        println(
                            "No ${turn.colorToString()} " +
                                    "piece at ${s[0]}${s[1]}"
                        )
                    }
                }

                inCheck = gameLogic.tmpMove(chessMove, chessBoard, turn).also {
                    if (it) {
                        println("You are in check, move your king...")
                    }
                }
                if ((validMove) && (!inCheck)) {
                    validMove = chessBoard.getPiece(chessMove.startLocation()).move(chessMove, chessBoard, turn).also {
                        if (!it) {
                            println("Not a possible chess move")
                        }
                    }
                }

            } while ((!validMove) || (inCheck))

            chessBoard.render()
            turn.nextTurn()
            if (gameLogic.check(turn)) {
                if (gameLogic.legalMoves(turn)) {
                    println("CHECKMATE!")
                    bye()
                }
                else {
                    println("CHECK!")
                }
            }
            if (gameLogic.legalMoves(turn)) {
                println("STALEMATE!")
                bye()
            }
        }
    }

    /**
     * Helper method for runGame(), verifies user input
     *
     * @param s the input string
     *
     * @return true After it is validated
     */
    private fun verifyInput(s: String): Boolean {
        if (s == "exit") return true
        if (s == "00") return true
        if (s == "000") return true
        if (s == "h") {help(); return true}
        val validLetters = 'a'..'h'
        val validNumbers = '1'..'8'

        if (s.length != 4) return false

        if (s[0] !in validLetters || s[2] !in validLetters) return false
        if (s[1] !in validNumbers || s[3] !in validNumbers) return false

        return true
    }

    private fun help() {
        println("\nKotlin Command Line Help:")
        println(">> Pawns: \n\t Can move forward one square at a time, or two squares if they are on their starting square. They capture pieces by \n\t moving one square diagonally.")
        println("----->> Pawn En passant Capture: \n\t\t  A pawn has the option to capture an opponent's pawn that has moved two squares forward from its \n\t\t  starting position, as if it had only moved one square forward.")
        println(">> Rooks: \n\t Can move horizontally or vertically, any number of squares.")
        println(">> Knights: \n\t Move in an L-shape: two squares in one direction and then one square perpendicular to \n\tthat direction.")
        println(">> Bishops: \n\t Move diagonally, any number of squares.")
        println(">> Queen: \n\t Can move horizontally, vertically, or diagonally, any number of squares.")
        println("----->> Queenside castle (move= '000'): \n\t\t  The King and Rook switch places, but only if neither piece has moved and the\n\t\t  squares between them are unoccupied.")
        println(">> King: \n\t Can move one square in any direction.")
        println("----->> Kingside castle (move= '00'): \n\t\t  The King and Rook switch places, but only if neither piece has moved and the\n\t\t  squares between them are unoccupied.\n")
    }

    /**
     * Helper function for runGame(), exits the game
     *
     * @param nothing
     * @return nothing
     */
    private fun bye() {
        println("Bye!")
        exitProcess(0)
    }

    /**
     * Helper method for runGame(), gets user input
     *
     * @param p1Turn Boolean representing player ones turn
     *
     * @return String Readline()
     */
    private fun getInput(p1Turn: Boolean): String {
        if (p1Turn) {
            print("White's turn ('h' for help):\n> ")
        }
        if (!p1Turn) {
            print("Black's turn ('h' for help):\n> ")
        }
        return readln()
    }

    /**
     * Helper method for runGame(), checks the input and sees if we want to exit game
     *
     * @param s The input string
     *
     * @return Boolean Representing if the user input == 'exit'
     */
    private fun exit(s: String?): Boolean {
        return s == "exit"
    }
}