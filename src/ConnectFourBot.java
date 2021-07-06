import java.util.ArrayList;
import java.util.GregorianCalendar;

public interface ConnectFourBot {

    default int makeMove(ConnectFourBoard connectFourBoard, ConnectFourPiece playerColor) {
        ConnectFourBoard.validateBoardIsDefinedCorrectly(connectFourBoard.getBoard());
        ArrayList<Integer> validMoves = connectFourBoard.getAllValidMoves();
        if (validMoves == null || validMoves.size() == 0) {
            throw new ArrayIndexOutOfBoundsException("No valid moves left for the given board: " + connectFourBoard);
        } else if (validMoves.size() == 1) {
            return validMoves.get(0);
        } else {
            int randomMove = 0;
            randomMove += (new GregorianCalendar().getTimeInMillis() % validMoves.size());
            return validMoves.get(randomMove);
        }
    }

    void setPlayerColor(ConnectFourPiece playerColor);

    void setOpponentColor(ConnectFourPiece opponentColor);
}
