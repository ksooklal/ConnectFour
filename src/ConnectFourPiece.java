/**
 * Connect Four Piece for use in {@link ConnectFourBoard}
 *
 * RED or BLACK enum
 *
 * @author Kris Sooklal
 */
public enum ConnectFourPiece {
    BLACK,
    RED;

    public String toString() {
        return this.name();
    }

    public ConnectFourPiece getOpponentColor(){
        if (this == BLACK) {
            return RED;
        } else {
            return BLACK;
        }
    }
}
