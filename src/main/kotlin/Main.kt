import chess.ChessBoard
import chess.ChessMove
import chess.GameLogic
import chess.Turn
import kotlin.system.exitProcess

fun main() {
    // init
    val chessBoard = ChessBoard()
    val gameLogic = GameLogic(chessBoard)
    val turn = Turn()

    // get names
    println("Pawns-Only Chess")

    // start game
    gameLogic.startGame()

    while (true) {
        do {
            var validMove = true
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

            // check the "start location" to (1) make sure a pc is there (2) make sure it's the appropriate turn

            validMove = gameLogic.sourceCoordinateVerifier(chessMove, turn).also {
                if (!it) {
                    println(
                        "No ${turn.colorToString()} " +
                                "piece at ${s[0]}${s[1]}"
                    )
                }
            }


            val startLocation = chessMove.startLocation()
            val possibleMoves = chessBoard.getPiece(startLocation).canMove(startLocation, chessBoard, turn)
            println(ChessMove.convertLocation(possibleMoves))


            if (validMove) {
                validMove = chessBoard.getPiece(startLocation).move(chessMove, chessBoard, turn).also {
                    if (!it) {
                        println("Not a possible chess move")
                    }
                }
            }

        } while (!validMove)

        chessBoard.render()
        turn.nextTurn()
        if (gameLogic.gameOver(turn)) {
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