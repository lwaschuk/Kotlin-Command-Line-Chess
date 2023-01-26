package chess

class EmptySpot : IChessPiece{
    override val type: PieceType = PieceType.EMPTY
    override var color: Color = Color.E
    override var canBeEnPassant: Boolean = false
}
