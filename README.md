# Kotlin Chess Game (Two Player)

Welcome to Kotlin Chess Game, a fully-featured command-line chess game that is implemented in Kotlin language. This 
chess game features a wide range of features such as Check, Checkmate, Stalemate, complete piece movement logic, and 
pawn en passant logic. It is a perfect choice for anyone who wants to play chess on their terminal or anyone who wants 
to learn more about the game of chess.


[![Documentation](https://img.shields.io/static/v1?label=Documentation&message=Latest&color=<blue>.svg?style=for-the-badge&logo=appveyor)](https://napkinzz.github.io/Kotlin-Command-Line-Chess/)

## Releases
[![Release](https://img.shields.io/github/release/Napkinzz/Kotlin-Command-Line-Chess.svg?style=for-the-badge&logo=appveyor)](https://github.com/Napkinzz/Kotlin-Command-Line-Chess/releases)

[![Software License](https://img.shields.io/github/license/Napkinzz/Kotlin-Command-Line-Chess?style=for-the-badge&logo=appveyor)](LICENSE.md)


## Features

* Check logic
* Checkmate logic
* Stalemate logic
* All piece movement logic (King, Queen, Rook, Bishop, Knight, and Pawn)
* Pawn en passant logic

## Getting Started

To run this chess game on your system, you need to install Java 11 or higher and Kotlin 1.5 or higher. You can download 
the latest version of these tools from the official websites.

Run the game using the following command:

```bash
./gradlew run --quiet --console=plain
```

You can build and run the .jar locally:

```bash
./gradlew fatJar
java -jar build/libs/KCL-Chess.jar
```
Or, you can download the pre-compiled .jar from the [releases page][releases] page.

[releases]:  https://github.com/Napkinzz/Kotlin-Command-Line-Chess/releases

## How to Play

The game is played in the terminal, and you will see the chess board on your screen. You can make moves by entering the 
starting and ending coordinates of the piece you want to move. For example, if you want to move the piece at the "a2" 
square to the "a3" square, you would enter `a2a3`.

The game will prompt you to make moves, and you can enter your move as described above. The game will continue until 
either a player wins by putting the other player in checkmate, or the game ends in a stalemate. See [Chess Piece Movement](https://github.com/Napkinzz/Kotlin-Command-Line-Chess/wiki/Chess-Piece-Movements)





## Testing

I did basic movement tests, there is no value to make more for a command line game of chess... when chess.com exists! 
