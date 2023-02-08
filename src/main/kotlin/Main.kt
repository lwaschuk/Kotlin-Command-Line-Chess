import game_helpers.ChessBoard
import game_helpers.ChessMove
import game_helpers.GameLogic
import game_helpers.Turn
import kotlin.system.exitProcess

fun main() {
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
            var validMove: Boolean
            var inCheck: Boolean
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

fun verifyInput(s: String): Boolean {
    if (s == "exit") return true

    val validLetters = 'a'..'h'
    val validNumbers = '1'..'8'

    if (s.length != 4) return false
    if (s[0] !in validLetters || s[2] !in validLetters) return false
    if (s[1] !in validNumbers || s[3] !in validNumbers) return false

    return true
}


fun bye() {
    println("Bye!")
    exitProcess(0)
}

fun getInput(p1Turn: Boolean): String {
    if (p1Turn) {
        print("White's turn:\n> ")
    }
    if (!p1Turn) {
        print("Black's turn:\n> ")
    }
    return readln()
}

fun exit(s: String?): Boolean {
    return s == "exit"
}