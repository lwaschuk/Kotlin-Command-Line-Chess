package chess

import kotlin.math.abs

class ChessBoard {
    private var board = Array(8) { Array<IChessPiece>(8)  { EmptySpot() } }
    private var verifier = Verifier()

    fun startGame() {
        initPawns()
        render()
    }

    fun render() {
        println("  +---+---+---+---+---+---+---+---+")
        for (row in 7 downTo 0){
            print("${row+1} ")
            for (col in 0 until  8){
                if (this.board[row][col].type != PieceType.EMPTY){
                    print("| ${this.board[row][col].color} ")
                }
                else {
                    print("|   ")
                }
            }
            println("|")
            println("  +---+---+---+---+---+---+---+---+")
        }
        println("    a   b   c   d   e   f   g   h")
    }

    private fun initPawns() {
        for (col in 0 until 8) {
            setPiece(Pair(1, col), Pawn(Color.W))
            setPiece(Pair(6, col), Pawn(Color.B))
        }

    }

    fun gameOver(turn: Turn): Boolean {
        return reachEnd() || elimAll() || stalemate(turn)
    }

    private fun elimAll(): Boolean {
        var whiteFound = false
        var blackFound = false

        for (row in 0 until 8){
            for (col in 0 until 8){
                when (this.board[row][col].color) {
                    Color.B -> blackFound = true
                    Color.W -> whiteFound = true
                    else -> {}
                }
            }
        }
        if (whiteFound && !blackFound)  {
            println("\nWhite Wins!")
            return true
        }
        if (blackFound && !whiteFound)  {
            println("\nBlack Wins!")
            return true
        }
        return false
    }

    private fun reachEnd(): Boolean {
        for (col in 0 until this.board[0].size) {
            if (this.board[0][col].color == Color.B) {
                println("\nBlack Wins!")
                return true
            }
            if (this.board[7][col].color == Color.W) {
                println("\nWhite Wins!")
                return true
            }
        }
        return false
    }

    private fun stalemate(turn: Turn): Boolean {
        for (row in 0 until 7 ){
            for (col in 0 until 8 ){
                val piece = getPiece(Pair(row, col))
                if (piece.color == turn.getColor() && pawnCanMove(row, col, turn)) {
                    return false
                }
            }
        }
        println("\nStalemate!")
        return true
    }

    private fun pawnCanMove(row: Int, col: Int, turn: Turn): Boolean {
        val direction = if (turn.getColor() == Color.W) 1 else -1
        val nextRow = row + direction
        val nextSquare = getPiece(Pair(nextRow, col))

        return when (nextSquare.color) {
            Color.E -> true
            turn.getColor() -> false
            else -> col in 1..6
        }
    }

//    private fun pawnCanMove(row: Int, col: Int, turn: Turn): Boolean {
//        val direction = if (turn.getColor() == Color.W) 1 else -1
//        val nextRow = row + direction
//        val nextSquare = getPiece(Pair(nextRow, col))
//
//        if (nextSquare.color == Color.E) {
//            return true
//        }
//        else {
//            return nextSquare.color != turn.getColor() && col > 0 && col < 7
//        }
//    }

    fun move(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        val (startCoordinates, endCoordinates) = chessMove

        if (verifier.verify(chessMove, turn) && checkOrigin(chessMove, PieceType.PAWN, turn) && canMove(chessMove, turn)) {

            setPiece(startCoordinates, EmptySpot())

            if (pawnCanEnPassant(chessMove, turn)){
                val behindEnd = if (turn.getTurn()) {
                    endCoordinates.copy(first = endCoordinates.first - 1)
                } else {
                    endCoordinates.copy(first = endCoordinates.first + 1)
                }
                setPiece(behindEnd, EmptySpot())
            }

            resetEnPassant()

            if (abs(startCoordinates.first - endCoordinates.first) == 2) {
                setPiece(endCoordinates, Pawn(turn.getColor(), true))
            }
            else {
                setPiece(endCoordinates, Pawn(turn.getColor()))
            }
            return true
        }
        return false
    }

    /*
    <EN PASSANT>
    In this version of the function, I added an additional parameter called enPassant which is a Pair of (row, col)
    coordinates representing the location of a pawn that can be captured "en passant".

    I then check if the enPassant parameter is not null and if the row and col in the enPassant pair match the
    coordinates of the pawn two squares forward and one square to the left or right of the pawn.
    If it does match, the pawn can capture the pawn en passant and I add the square to the possible moves list.

    Please note that this is a simplified version of the "en passant" rule, that only considers the standard pawn
    movements, the actual rule for en passant is slightly more complex and depends on different conditions such as the
    pawn double moved and the turn.

    <PROMOTION>
    In this version of the function, I added an additional parameter called promote which is a boolean that indicates if
     the pawn can promote or not.
    I then check if the promote parameter is true and if the pawn reached the last row of the board, then I add 4 new
    possible moves to the possible moves list, one for each of the promotion pieces (queen, rook, bishop, knight) .

    You should also consider the case of pawns that reach the last row and can be promoted, you should handle the
    promotion of pawns when they reach the last row.

    It's important to keep in mind that this is a simplified version of the "promotion" rule, that only considers the
    standard pawn movements, the actual rule for promotion may depend on the game you are implementing, but it should
    give you a good starting point to implement your own pawn promotion function.
     */
    fun Pawn.canMove(coordinates: Pair<Int, Int>, board: Array<Array<IChessPiece>>, turn: Turn): List<Pair<Int, Int>> {
        val direction = if (turn.getColor() == Color.W) 1 else -1
        val nextRow = coordinates.first + direction
        val nextCol = coordinates.second
        val possibleMoves = mutableListOf<Pair<Int, Int>>()

        if (nextRow in 0..7 && nextCol in 0..7) {
            val nextSquare = getPiece(Pair(nextRow, nextCol))
            if (nextSquare.color == Color.E) {
                // pawn can move one square forward if it is unoccupied
                possibleMoves.add(Pair(nextRow,nextCol))
//                if(promote && (nextRow == 0 || nextRow == 7)) {
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.QUEEN))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.ROOK))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.BISHOP))
//                    possibleMoves.add(Pair(nextRow,nextCol, PromotionType.KNIGHT))
//                }
            }
            if (nextCol in 1..6) {
                if (nextRow > 0) {
                    val leftDiagonal = getPiece(Pair(nextRow-1, nextCol-1))
                    if (leftDiagonal?.color != color) {
                        possibleMoves.add(Pair(nextRow-1,nextCol-1))
                    }
                }
                if (nextRow < 7) {
                    val rightDiagonal = board[nextRow+1][nextCol-1]
                    if (rightDiagonal?.color != color) {
                        possibleMoves.add(Pair(nextRow+1,nextCol-1))
                    }
                }
            }
//            if(enPassant != null) {
//                if (enPassant.first == nextRow && enPassant.second == nextCol-1) {
//                    possibleMoves.add(Pair(nextRow,nextCol-1))
//                }
//            }
        }
        return possibleMoves
    }

//    /*
//    In this version of the function, I added an additional parameter called direction which is a string that indicates
//    in which direction the function should check for possible moves, it could be "left" or "right".
//    You can call this function twice, once with direction "left" and once with direction "right" to get all the possible
//    moves for the Rook.
//    Please note that this is a simplified version of the Rook movement, that only consider the standard Rook movements,
//    the actual rule for Rooks may depend on the game you are implementing.
//     */
//    fun Rook.canMove(row: Int, col: Int, board: Array<Array<Piece?>>, direction: String): List<Pair<Int, Int>> {
//        val possibleMoves = mutableListOf<Pair<Int, Int>>()
//        when(direction) {
//            "left" -> {
//                // Check squares to the left
//                for (c in col - 1 downTo 0) {
//                    val square = board[row][c]
//                    if (square == null) {
//                        possibleMoves.add(Pair(row, c))
//                    } else if (square.color != color) {
//                        possibleMoves.add(Pair(row, c))
//                        break
//                    } else {
//                        break
//                    }
//                }
//            }
//            "right" -> {
//                // Check squares to the right
//                for (c in col + 1..7) {
//                    val square = board[row][c]
//                    if (square == null) {
//                        possibleMoves.add(Pair(row, c))
//                    } else if (square.color != color) {
//                        possibleMoves.add(Pair(row, c))
//                        break
//                    } else {
//                        break
//                    }
//                }
//            }
//        }
//        return possibleMoves
//    }
//
//    /*
//    The function takes in the current row and column of the Knight, and the chess board represented as a 2D array of Pieces.
//    It uses two arrays dx and dy that represent the relative positions of the squares the Knight can move.
//    It loops through the dx array, and for each index, it calculates the new row and column by adding the
//    dx[i] and dy[i] to the current row and column respectively.
//    It then checks if the new row and column are within the chess board boundaries (0-7). And it checks if the square is
//     unoccupied or occupied by an opponent piece.
//    If it is, it adds the new row and column to the possible moves list.
//
//    This should give you a good starting point to implement your own Knight movement function, but please note that this is
//    a simplified version of the Knight movement, that only consider the standard Knight movements, the actual rule for
//    Knights may depend on the game you are implementing.
//     */
//    fun Knight.canMove(row: Int, col: Int, board: Array<Array<Piece?>>): List<Pair<Int, Int>> {
//        val possibleMoves = mutableListOf<Pair<Int, Int>>()
//        val dx = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
//        val dy = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)
//
//        for (i in dx.indices) {
//            val newRow = row + dx[i]
//            val newCol = col + dy[i]
//
//            if (newRow in 0..7 && newCol in 0..7) {
//                val nextSquare = board[newRow][newCol]
//                if (nextSquare == null || nextSquare.color != color) {
//                    possibleMoves.add(Pair(newRow, newCol))
//                }
//            }
//        }
//        return possibleMoves
//    }
//
//    /*
//    This function takes in the current row and column of the bishop and the chess board represented as a 2D array of Pieces.
//    It then uses four nested while loops, one for each diagonal direction and it checks all the squares in that diagonal
//    direction until it reaches the end of the board or finds an occupied square of the same color.
//    If it finds an unoccupied square or a square occupied by an opponent, it adds it to the possible moves list and continues
//    checking the next square in that diagonal direction
//     */
//    fun Bishop.canMove(row: Int, col: Int, board: Array<Array<Piece?>>): List<Pair<Int, Int>> {
//        val possibleMoves = mutableListOf<Pair<Int, Int>>()
//
//        // Check squares diagonally to the left and up
//        var r = row - 1
//        var c = col - 1
//        while (r >= 0 && c >= 0) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r--
//            c--
//        }
//
//        // Check squares diagonally to the right and up
//        r = row - 1
//        c = col + 1
//        while (r >= 0 && c <= 7) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r--
//            c++
//        }
//
//        // Check squares diagonally to the left and down
//        r = row + 1
//        c = col - 1
//        while (r <= 7 && c >= 0) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r++
//            c--
//        }
//
//        // Check squares diagonally to the right and down
//        r = row + 1
//        c = col + 1
//        while (r <= 7 && c <= 7) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r++
//            c++
//        }
//        return possibleMoves
//    }
//
//    /*
//    The function takes in the current row and column of the King and the chess board represented as a 2D array of Pieces.
//    It uses two arrays dx and dy that represent the relative positions of the squares the King can move.
//    It loops through the dx array, and for each index, it calculates the new row and column by adding the dx[i] and dy[i]
//    to the current row and column respectively.
//    It then checks if the new row and column are within the chess board boundaries (0-7). And it checks if the square is
//    unoccupied or occupied by an opponent piece.
//    If it is, it adds the new row and column to the possible moves list.
//    Please note that this is a simplified version of the King movement, that only consider the standard King movements, the
//     actual rule for King may depend on the game you are implementing.
//     */
//    fun King.canMove(row: Int, col: Int, board: Array<Array<Piece?>>): List<Pair<Int, Int>> {
//        val possibleMoves = mutableListOf<Pair<Int, Int>>()
//        val dx = intArrayOf(-1, -1, -1, 0, 1, 1, 1, 0)
//        val dy = intArrayOf(-1, 0, 1, 1, 1, 0, -1, -1)
//
//        for (i in dx.indices) {
//            val newRow = row + dx[i]
//            val newCol = col + dy[i]
//            if (newRow in 0..7 && newCol in 0..7) {
//                val nextSquare = board[newRow][newCol]
//                if (nextSquare == null || nextSquare.color != color) {
//                    possibleMoves.add(Pair(newRow, newCol))
//                }
//            }
//        }
//        return possibleMoves
//    }
//
//
//    /*
//    This function checks all the horizontal, vertical, and diagonal squares for the queen. It uses nested for loops,
//    similar to the bishop, but it also check for horizontal and vertical squares.
//    It checks if the square is unoccupied or occupied by an opponent piece. If it is, it adds the new row and column to the
//    possible moves list.
//    Please note that this is a simplified version of the Queen movement, that only consider the standard Queen movements,
//    the actual rule for Queen may depend on the game you are implementing.
//     */
//    fun Queen.canMove(row: Int, col: Int, board: Array<Array<Piece?>>): List<Pair<Int, Int>> {
//        val possibleMoves = mutableListOf<Pair<Int, Int>>()
//
//        // Check squares diagonally to the left and up
//        var r = row - 1
//        var c = col - 1
//        while (r >= 0 && c >= 0) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r--
//            c--
//        }
//
//        // Check squares diagonally to the right and up
//        r = row - 1
//        c = col + 1
//        while (r >= 0 && c <= 7) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r--
//            c++
//        }
//
//        // Check squares diagonally to the left and down
//        r = row + 1
//        c = col - 1
//        while (r <= 7 && c >= 0) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r++
//            c--
//        }
//
//        // Check squares diagonally to the right and down
//        r = row + 1
//        c = col + 1
//        while (r <= 7 && c <= 7) {
//            val square = board[r][c]
//            if (square == null) {
//                possibleMoves.add(Pair(r, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, c))
//                break
//            } else {
//                break
//            }
//            r++
//            c++
//        }
//
//        // Check squares horizontally to the left
//        for (c in col - 1 downTo 0) {
//            val square = board[row][c]
//            if (square == null) {
//                possibleMoves.add(Pair(row, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(row, c))
//                break
//            } else {
//                break
//            }
//        }
//        // Check squares horizontally to the right
//        for (c in col + 1..7) {
//            val square = board[row][c]
//            if (square == null) {
//                possibleMoves.add(Pair(row, c))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(row, c))
//                break
//            } else {
//                break
//            }
//        }
//
//        // Check squares vertically up
//        for (r in row - 1 downTo 0) {
//            val square = board[r][col]
//            if (square == null) {
//                possibleMoves.add(Pair(r, col))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, col))
//                break
//            } else {
//                break
//            }
//        }
//
//        // Check squares vertically down
//        for (r in row + 1..7) {
//            val square = board[r][col]
//            if (square == null) {
//                possibleMoves.add(Pair(r, col))
//            } else if (square.color != color) {
//                possibleMoves.add(Pair(r, col))
//                break
//            } else {
//                break
//            }
//        }
//
//        return possibleMoves
//    }
//
//
//    /*
//    When a pawn is moved and it has en passant status, it sets the en passant pawn to that pawn and sets the en passant
//     reset turn to the current turn + 1.
//    When the next move is made and the current turn is the same as the en passant reset turn, it checks if the en passant
//    pawn is still there and if it is, it resets its en passant status to false and sets the en passant pawn and turn count
//    to null and 0 respectively.
//
//    This is just one possible way of handling en passant reset, depending on how you are implementing your game and what
//    data structures you are using, you might need to adjust the code accordingly.
//     */
//    private var enPassantPawn: Pawn? = null
//    private var enPassantResetTurn = 0
//
//    fun move(from: Pair<Int, Int>, to: Pair<Int, Int>) {
//        // Code to move the piece
//        if (movedPiece is Pawn && movedPiece.enPassant) {
//            // Set the en passant pawn and turn count
//            enPassantPawn = movedPiece
//            enPassantResetTurn = currentTurn + 1
//        } else if (enPassantPawn != null && currentTurn == enPassantResetTurn) {
//            // If the en passant pawn is not taken by the next move, reset its en passant status
//            enPassantPawn?.enPassant = false
//            enPassantPawn = null
//            enPassantResetTurn = 0
//        }
//    }


//    fun move(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
//        val startCoordinates = chessMove.first
//        val endCoordinates = chessMove.second
//
//        if (verifier.verify(chessMove, turn) && checkOrigin(chessMove, PieceType.PAWN, turn) && canMove(chessMove, turn)) {
//
//            setPiece(startCoordinates, EmptySpot())
//
//            if (pawnCanEnPassant(chessMove, turn)){
//                val behindEnd: Pair<Int, Int> = if (turn.getTurn()) {
//                    Pair(endCoordinates.first - 1, endCoordinates.second)
//                } else {
//                    Pair(endCoordinates.first + 1, endCoordinates.second)
//                }
//                setPiece(behindEnd, EmptySpot())
//            }
//
//            resetEnPassant()
//
//            if (abs(startCoordinates.first - endCoordinates.first) == 2) {
//                setPiece(endCoordinates, Pawn(turn.getColor(), true))
//            }
//            else {
//                setPiece(endCoordinates, Pawn(turn.getColor()))
//            }
//
//            return true
//        }
//        return false
//    }

    private fun resetEnPassant(){
        for (row in 0 until 8) {
            for (col in 0 until 8) {
                this.board[row][col].canBeEnPassant = false
            }
        }
    }

    private fun checkOrigin(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, pieceType: PieceType, turn: Turn) : Boolean{
        val startCoordinates = chessMove.first

        // check starting coordinates for a pawn
        return getPiece(startCoordinates).type == pieceType
                && getPiece(startCoordinates).color == turn.getColor()
    }

    private fun canMove(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn): Boolean {
        return pawnCanMoveStraight(chessMove) || pawnCanTake(chessMove, turn) || pawnCanEnPassant(chessMove, turn)
    }

    private fun pawnCanMoveStraight(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>) : Boolean {
        val startCoordinates = chessMove.first
        val endCoordinates = chessMove.second

        return getPiece(endCoordinates).type == PieceType.EMPTY && startCoordinates.second == endCoordinates.second
    }

    private fun pawnCanTake(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn) : Boolean {
        val startCoordinates = chessMove.first
        val endCoordinates = chessMove.second

        return getPiece(endCoordinates).type == PieceType.PAWN && getPiece(endCoordinates).color == turn.enemyColor()
                && startCoordinates.second != endCoordinates.second
    }

    private fun pawnCanEnPassant(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn) : Boolean {
        val startCoordinates = chessMove.first
        val endCoordinates = chessMove.second
        val behindEnd: Pair<Int, Int> = if (turn.getTurn()) {
            Pair(endCoordinates.first - 1, endCoordinates.second)
        } else {
            Pair(endCoordinates.first + 1, endCoordinates.second)
        }
        val firstStatement = getPiece(endCoordinates).type == PieceType.EMPTY && startCoordinates.second != endCoordinates.second
        val secondStatement = getPiece(behindEnd).color == turn.enemyColor() && getPiece(behindEnd).type == PieceType.PAWN
                && getPiece(behindEnd).canBeEnPassant
        return firstStatement && secondStatement
    }

    fun sourceCoordinateVerifier(chessMove: Pair<Pair<Int, Int>, Pair<Int, Int>>, turn: Turn) : Boolean {
        val startCoordinates = chessMove.first

        return getPiece(startCoordinates).type == PieceType.PAWN && getPiece(startCoordinates).color == turn.getColor()
    }

    private fun getPiece(coordinates: Pair<Int, Int>): IChessPiece {
        return this.board[coordinates.first][coordinates.second]
    }

    private fun setPiece(coordinates: Pair<Int, Int>, piece: IChessPiece) {
        this.board[coordinates.first][coordinates.second] = piece
    }
}