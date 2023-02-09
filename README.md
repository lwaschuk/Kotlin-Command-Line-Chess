# Kotlin Chess Game (Two Player)

Welcome to Kotlin Chess Game, a fully-featured command-line chess game that is implemented in Kotlin language. This 
chess game features a wide range of features such as Check, Checkmate, Stalemate, complete piece movement logic, and 
pawn en passant logic. It is a perfect choice for anyone who wants to play chess on their terminal or anyone who wants 
to learn more about the game of chess.

## Features

* Check logic
* Checkmate logic
* Stalemate logic
* All piece movement logic (King, Queen, Rook, Bishop, Knight, and Pawn)
* Pawn en passant logic

## Requirements

* Java 11 or higher 
* Kotlin 1.5 or higher


## Getting Started

To run this chess game on your system, you need to install Java 11 or higher and Kotlin 1.5 or higher. You can download 
the latest version of these tools from the official websites.

Once you have installed Java and Kotlin, clone this repository to your system using the following command:

```bash
git clone https://github.com/<username>/kotlin-chess-game.git
```

Next, navigate to the cloned repository directory:

```bash
cd kotlin-chess-game
```

Finally, run the game using the following command:

```bash
chmod +x gradlew
./gradlew run
```

## How to Play

The game is played in the terminal, and you will see the chess board on your screen. You can make moves by entering the 
starting and ending coordinates of the piece you want to move. For example, if you want to move the piece at the "a2" 
square to the "a3" square, you would enter `a2a3`.

The game will prompt you to make moves, and you can enter your move as described above. The game will continue until 
either a player wins by putting the other player in checkmate, or the game ends in a stalemate.

## Chess Piece Movements

### Pawn

The pawn is the most basic piece in chess, and it moves forward one square at a time, with the option to move two squares on its first move. If an opposing piece is on a square directly in front of the pawn, the pawn can only move one square forward, and can capture the opposing piece by moving diagonally.

#### First Move
On its first move, a pawn has the option to move forward two squares instead of just one.

#### En Passant
If a pawn moves two squares forward from its starting position and lands directly next to an opposing pawn, that opposing pawn has the option to capture the first pawn "en passant" as if it had only moved one square forward. This capture can only be made on the next move, and only by the pawn that was adjacent to the pawn that moved two squares forward.

### Knight

The knight is the only piece that can "jump" over other pieces. It moves in an L-shape, either two squares forward and one square to the side, or two squares to the side and one square forward.

### Bishop

The bishop moves diagonally any number of squares. It can capture an opposing piece by landing on the square occupied by that piece.

### Rook

The rook moves horizontally or vertically any number of squares. It can capture an opposing piece by landing on the square occupied by that piece.

### Queen

The queen is a combination of a bishop and a rook. It can move diagonally, horizontally, or vertically any number of squares. It can capture an opposing piece by landing on the square occupied by that piece.

### King

The king is the most important piece, and it moves one square in any direction. The king cannot move into check (a square threatened by an opposing piece), and cannot move to a square that would put it in check.

#### Castling
The king and rook can perform a special move called castling. In castling, the king moves two squares towards a rook on its own side of the board, and then that rook moves to the square over which the king crossed. Castling is only allowed under certain conditions:

* Neither the king nor the rook can have moved previously in the game
* There cannot be any pieces between the king and rook
* The king cannot be in check, nor can the squares the king moves over or to be under attack by opposing pieces.

## Conclusion

Kotlin Chess Game is a fun and challenging way to play chess on your terminal with a friend. Whether you are an experienced player or 
a beginner, you will find this game to be a great way to improve your chess skills. So, go ahead and give it a try!