package chess

class Pawn(
    override var color: Color,
    override var canBeEnPassant: Boolean = false
): IChessPiece{
    override val type: PieceType = PieceType.PAWN

}
