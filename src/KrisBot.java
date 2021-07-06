import java.util.ArrayList;

/**
 * Random bot implementation for {@link ConnectFourBot}
 *
 * @author Kris Sooklal
 */
public class KrisBot implements ConnectFourBot {
    private ConnectFourPiece myColor;
    private ConnectFourPiece opponentColor;

    @Override
    public int makeMove(ConnectFourBoard connectFourBoard, ConnectFourPiece playerColor) {
        if (myColor == null) {
            this.setPlayerColor(playerColor);
            this.setOpponentColor(playerColor.getOpponentColor());
        }

        int desiredMove = connectFourBoard.getAllValidMoves().get(0);

        // Rule #1: Win Game
        Integer possibleMove = checkForWinningPosition(connectFourBoard, myColor);
        if (possibleMove != null && connectFourBoard.validateMove(possibleMove)) {
            return possibleMove;
        }

        // Rule #2: Block Win of Game

        // Rule #7: Take Middle
        if (connectFourBoard.validateMove(3)) {
            return 3;
        }

        // Use the default when there is no further logic to be made.
        return ConnectFourBot.super.makeMove(connectFourBoard, playerColor);
    }

    private Integer checkForWinningPosition(ConnectFourBoard connectFourBoard, ConnectFourPiece piece) {
        ArrayList<Integer> validMoves = connectFourBoard.getAllValidMoves();
        Integer verticalWin = validMoves.contains(3) ? 3 : validMoves.get(0); //checkForWinningVerticalWin();
        if (verticalWin != null) {
            return verticalWin;
        }
        Integer horizontalWin = validMoves.size() >= 2 ? validMoves.get(1) : null;//checkForWinningHorizontalWin();
        if (horizontalWin != null) {
            return horizontalWin;
        }
        Integer diagonalWin = validMoves.size() >= 3 ? validMoves.get(2) : null;//checkForWinningDiagonalWin();
        return diagonalWin;
    }

    @Override
    public void setPlayerColor(ConnectFourPiece playerColor) {
        this.myColor = playerColor;
    }

    @Override
    public void setOpponentColor(ConnectFourPiece opponentColor) {
        this.opponentColor = opponentColor;
    }
}
