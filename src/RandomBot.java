/**
 * Random bot implementation for {@link ConnectFourBot}
 *
 * @author Kris Sooklal
 */
public class RandomBot implements ConnectFourBot {
    private ConnectFourPiece playerColor;
    private ConnectFourPiece opponentColor;

    public ConnectFourPiece getPlayerColor() {
        return playerColor;
    }

    @Override
    public void setPlayerColor(ConnectFourPiece playerColor) {
        this.playerColor = playerColor;
    }

    public ConnectFourPiece getOpponentColor() {
        return opponentColor;
    }

    @Override
    public void setOpponentColor(ConnectFourPiece opponentColor) {
        this.opponentColor = opponentColor;
    }
}
