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
        Integer possibleWin = checkForWinningPosition(connectFourBoard, myColor);
        if (possibleWin != null && connectFourBoard.validateMove(possibleWin)) {
            return possibleWin;
        }

        // Rule #2: Block Win of Game
        Integer possibleBlockWin = checkForWinningPosition(connectFourBoard, opponentColor);
        if (possibleBlockWin != null && connectFourBoard.validateMove(possibleBlockWin)) {
            return possibleBlockWin;
        }

        // Rule #7: Take Middle
        if (connectFourBoard.getAllValidMoves().contains(3)) {
            return 3;
        }
        if (connectFourBoard.getAllValidMoves().contains(4)) {
            return 4;
        }
        if (connectFourBoard.getAllValidMoves().contains(2)) {
            return 2;
        }
        if (connectFourBoard.getAllValidMoves().contains(1)) {
            return 1;
        }
        if (connectFourBoard.getAllValidMoves().contains(5)) {
            return 5;
        }
        if (connectFourBoard.getAllValidMoves().contains(0)) {
            return 0;
        }
        return desiredMove;
    }

    private Integer checkForWinningPosition(ConnectFourBoard connectFourBoard, ConnectFourPiece piece) {
        ArrayList<Integer> validMoves = connectFourBoard.getAllValidMoves();
        Integer verticalWin = checkForWinningVerticalWin(connectFourBoard.getBoard(), piece);
        if (verticalWin != null && validMoves.contains(verticalWin)) {
            return verticalWin;
        }
        Integer horizontalWin = checkForWinningHorizontalWin(connectFourBoard.getBoard(), piece);
        if (horizontalWin != null && validMoves.contains(horizontalWin)) {
            return horizontalWin;
        }

        //Integer diagonalWin = //checkForWinningDiagonalWin();
        return validMoves.contains(3) ? 3 : null;
    }

    private Integer checkForWinningHorizontalWin(ConnectFourPiece [][] board, ConnectFourPiece piece) {
        Integer winningPosition = null;
        for (int row = ConnectFourBoard.ROWS - 1; row >= 0; row--) {
            winningPosition = checkIndividualRowForHorizontalWin(board, row, piece);
            if (winningPosition != null) {
                return winningPosition;
            }
        }
        return winningPosition;
    }

    private Integer checkIndividualRowForHorizontalWin(ConnectFourPiece [][] board, int currentRow, ConnectFourPiece piece) {
        int consecutiveMatches = 0;
        for (int position = 0; position < ConnectFourBoard.COLUMNS; position++) {
            if (board[currentRow][position] == piece) {
                consecutiveMatches++;
            } else {
                consecutiveMatches = 0;
            }
            if (consecutiveMatches == 3) {
                if (position < (ConnectFourBoard.COLUMNS - 1)) {
                    Integer highestPositionInAdjacentPosition = getHighestEmptySpotInColumn(board, position + 1);
                    if (highestPositionInAdjacentPosition != null && highestPositionInAdjacentPosition == currentRow) {
                        return position + 1;
                    }
                }
                if (position >= 3) {
                    Integer highestPositionInAdjacentPosition = getHighestEmptySpotInColumn(board, position - 3);
                    if (highestPositionInAdjacentPosition != null && highestPositionInAdjacentPosition == currentRow) {
                        return position - 3;
                    }
                }
                consecutiveMatches = 0;
            }
        }
        return null;
    }

    private Integer getHighestEmptySpotInColumn(ConnectFourPiece [][] board, int desiredMove) {
        for (int row = ConnectFourBoard.ROWS - 1; row >= 0; row--) {
            if (board[row][desiredMove] == null) {
                return row;
            }
        }
        return null;
    }

    private Integer checkForWinningVerticalWin(ConnectFourPiece [][] board, ConnectFourPiece piece) {
        for (int column = 0; column < ConnectFourBoard.COLUMNS; column++) {
            int consecutiveMatchingPieces = 0;
            for (int position = ConnectFourBoard.ROWS - 1; position >= 1; position--) {
                if (board[position][column] == piece) {
                    consecutiveMatchingPieces++;
                } else {
                    consecutiveMatchingPieces = 0;
                }
                if (consecutiveMatchingPieces == 3 && board[position - 1][column] == null) {
                    return column;
                }
            }
        }
        return null;
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
