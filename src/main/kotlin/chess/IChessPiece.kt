package chess

interface IChessPiece {
    val color: Color
    val type: PieceType
    var canBeEnPassant: Boolean
}