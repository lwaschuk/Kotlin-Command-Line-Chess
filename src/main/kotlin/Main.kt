import chess.ChessBoard
import chess.Player
import chess.Turn
import chess.Verifier
import chess.Verifier.Companion.convertBack
import pieces.Pawn
import pieces.PieceType
import kotlin.system.exitProcess

fun main() {
    val board = ChessBoard()
    val turn = Turn()

    println("Pawns-Only Chess")
    println("First Player's name:")
    val p1 = Player(readLine()!!)
    println("Second Player's name:")
    val p2 = Player(readLine()!!)

    board.startGame()

    while (true) {

        do {
            var validMove = true
            var validInput: Boolean
            var s: String

            do {
                s = getInput(turn.getTurn(), p1, p2)
                validInput = verifyInput(s).also {
                    if (!it) {
                        println("Invalid Input")
                    }
                }
            } while (!validInput)

            val chessMove = Verifier.convertInput(s)

            if (exit(s)) {
                bye()
            }

            if (validMove) {
                validMove = board.sourceCoordinateVerifier(chessMove, turn).also {
                    if (!it) {
                        println(
                            "No ${turn.colorToString()} " +
                                    "pawn at ${s[0]}${s[1]}"
                        )
                    }
                }
            }

            val coordinates = Pair(chessMove.first.first, chessMove.first.second)
            var pc = board.getPiece(coordinates)
            if (pc.type == PieceType.PAWN) {
                pc = pc as Pawn
                println(convertBack(pc.canMove(coordinates, board, turn)))
            }


            if (validMove) {
                validMove = board.move(chessMove, turn).also {
                    if (!it) {
                        println("Invalid Input")
                    }
                }
            }

        } while (!validMove)

        board.render()
        turn.nextTurn()
        if (board.gameOver(turn)) {
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

fun getInput(p1Turn: Boolean, p1: Player, p2: Player): String {
    if (p1Turn) {
        println("${p1.name()}'s turn:")
    }
    if (!p1Turn) {
        println("${p2.name()}'s turn:")
    }
    return readLine()!!
}

fun exit(s: String?): Boolean {
    return s == "exit"
}