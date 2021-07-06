import java.util.ArrayList;

/**
 * Connect Four Board made up of {@link ConnectFourPiece}
 *
 * Values for the two dimensional board array can be either RED, BLACK, or empty.
 *
 * @author Kris Sooklal
 *
 * Board is a two dimensional array as defined by new ConnectFourPiece[rows][columns]
 *
 * For example, if a board looks like
 *          column 0, column 1, column 2, column 3, column 4, column 5, column 6
 * row 0    | - | - | - | - | - | - | - |
 * row 1    | - | - | - | - | - | - | - |
 * row 2    | - | - | - | - | - | - | - |
 * row 3    | - | - | - | - | - | - | - |
 * row 4    | - | - | - | - | - | - | - |
 * row 5    | - | - | - | R | - | - | - |
 *
 * The first piece, which is RED ("R"), is located at board[5][3].
 *
 * The first row is defined as board[0][x]
 *
 * The bottom row is defined as board[5][x]
 *
 * The middle column is board[x][3].
 */
public class ConnectFourBoard {

    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    public static final int TOTAL_MOVES = COLUMNS * ROWS;

    private final ConnectFourPiece [][] board = new ConnectFourPiece[ROWS][COLUMNS];

    public ConnectFourPiece[][] getBoard() {
        return board;
    }

    public boolean applyMove(int desiredMove, ConnectFourPiece connectFourPiece) {
        if (!validateMove(desiredMove)) {
            throw new ArrayIndexOutOfBoundsException("The desired move: " + desiredMove + " is invalid for the board: " + this.toString()
                    + "\nSkipping to the next player's turn");
        }
        try {
            int nextAvailableRowForDesiredMove = getHighestEmptySpotInColumn(desiredMove);
            this.board[nextAvailableRowForDesiredMove][desiredMove] = connectFourPiece;
            return true;
        } catch (Exception e) {
            throw e;
        }
    }

    private int getHighestEmptySpotInColumn(int desiredMove) {
        for (int row = 5; row >= 0; row--) {
            if (this.board[row][desiredMove] == null) {
                return row;
            }
        }
        throw new ArrayIndexOutOfBoundsException("The desired move: " + desiredMove + " is invalid for the board: " + this.toString()
                + "\nSkipping to the next player's turn");
    }

    /**
     * This method checks the board to see if somebody has won. If so, return the color of the piece that has won.
     *
     * @return the color of the player that has won, or NULL if there is no winner. This method will always return null
     * if there is no winner, so be sure to check for null as the returned value.
     */
    public ConnectFourPiece getWinner() {
        return getWinnerForAnyGivenBoard(this);
    }

    /**
     * This method checks the given board to see if somebody has won. If so, return the color of the piece that has won.
     *
     * @param connectFourBoard any valid connect four board
     * @return the color of the player that has won, or NULL if there is no winner. This method will always return null
     * if there is no winner, so be sure to check for null as the returned value.
     */
    public ConnectFourPiece getWinnerForAnyGivenBoard(ConnectFourBoard connectFourBoard) {
        ConnectFourPiece [][] board = connectFourBoard.board;
        validateBoardIsDefinedCorrectly(board);

        ConnectFourPiece winner = null;
        for (int row = ROWS - 1; row >= 0; row--) {
            winner = checkRowForHorizontalWinner(board[row]);
            if (winner != null) {
                return winner;
            }
        }
        for (int column = 0; column < COLUMNS; column++) {
            //TODO checkColumnForVerticalWinners(board, column);
            if (winner != null) {
                return winner;
            }
        }
        for (int middlePosition = 0; middlePosition < (COLUMNS - 4); middlePosition++) {
            //TODO checkForUpwardsDiagonals(board, middlePosition);
            if (winner != null) {
                return winner;
            }
        }
        for (int middlePosition = (COLUMNS - 4); middlePosition < COLUMNS; middlePosition++) {
            //TODO checkForDownwardsDiagonals(connectFourBoard[middlePosition][3]);
            if (winner != null) {
                return winner;
            }
        }
        return winner;
    }

    private ConnectFourPiece checkRowForHorizontalWinner(ConnectFourPiece [] row) {
        int countRed = 0;
        int countBlack = 0;
        int countEmpty = 0;

        /*
         * This block of code is a "quick-check" to simply count all of the pieces in a row. If there are less than
         * four of each color, then there is no winner for sure. If there are four empty slots, then there is no winner
         * for sure.
         */
        for (int i = 0; i < COLUMNS; i++) {
            if (row[i] == ConnectFourPiece.BLACK) {
                countBlack++;
            } else if (row[i] == ConnectFourPiece.RED) {
                countRed++;
            } else if (row[i] == null) {
                countEmpty++;
            }
            if (countEmpty >= 4) {
                return null;
            }
        }

        if (countBlack >= 4) {
            ConnectFourPiece winner = countConsecutivePiecesInARow(row, ConnectFourPiece.BLACK);
            if (winner != null) {
                return winner;
            } else {
                System.out.println("Not enough pieces in row: " + toString(row) + " to declare BLACK as the horizontal winner");
            }
        }
        if (countRed >= 4) {
            ConnectFourPiece winner = countConsecutivePiecesInARow(row, ConnectFourPiece.RED);
            if (winner != null) {
                return winner;
            } else {
                System.out.println("Not enough pieces in row: " + toString(row) + " to declare RED as the horizontal winner");
            }
        }

        return null;
    }

    private ConnectFourPiece countConsecutivePiecesInARow(ConnectFourPiece [] row, ConnectFourPiece color) {
        int consecutiveMatches = 0;
        for (int i = 0; i < COLUMNS; i++) {
            if (row[i] == color) {
                consecutiveMatches++;
                if (consecutiveMatches >= 4) {
                    return color;
                }
            } else {
                consecutiveMatches = 0;
            }
        }
        return null;
    }

    public static boolean validateMoveForAnyGivenBoard(ConnectFourBoard connectFourBoard, int column) {
        ConnectFourPiece [][] board = connectFourBoard.getBoard();
        validateBoardIsDefinedCorrectly(board);
        if (column < 0 || column >= COLUMNS) {
            return false;
        }
        if (board[0][column] == null) {
            return true;
        }
        if (board[0][column] == ConnectFourPiece.BLACK) {
            System.out.println("This move is invalid because the column " + column + " is taken by a BLACK piece" + connectFourBoard);
            return false;
        }
        if (board[0][column] == ConnectFourPiece.RED) {
            System.out.println("This move is invalid because the column " + column + " is taken by a RED piece" + connectFourBoard);
            return false;
        }
        return true;
    }

    /**
     * Use this method to check your move before you make it.
     *
     * @param column desired move
     * @return true if the move is valid
     */
    public boolean validateMove(int column) {
        return validateMoveForAnyGivenBoard(this, column);
    }

    public static ArrayList<Integer> getValidMovesForAnyGivenBoard(ConnectFourBoard connectFourBoard) {
        ArrayList<Integer> validMoves = new ArrayList<>();
        ConnectFourPiece [][] board = connectFourBoard.getBoard();
        validateBoardIsDefinedCorrectly(board);
        for (int desiredColumn = 0; desiredColumn < COLUMNS; desiredColumn++) {
            if (board[0][desiredColumn] == null){
                validMoves.add(desiredColumn);
            }
        }
        return validMoves;
    }

    /**
     * Use this method to get all valid moves you can make for your board, as is.
     *
     * @return an arraylist of all valid moves
     */
    public ArrayList<Integer> getAllValidMoves() {
        return getValidMovesForAnyGivenBoard(this);
    }

    public static boolean validateBoardIsDefinedCorrectly(ConnectFourPiece [][] board) {
        if (board == null || board[0] == null) {
            throw new NullPointerException("The board is null");
        }
        if (board.length != ROWS || board[0].length != COLUMNS) {
            throw new ArrayIndexOutOfBoundsException("The board is not defined with the correct amount of rows: " + ROWS
                    + " or columns: " + COLUMNS);
        }

        return true;
    }

    public String toString(ConnectFourPiece[] connectFourBoardRow) {
        String row = "| ";
        for (int j = 0; j < COLUMNS; j++) {
            String piece = " - ";
            if (connectFourBoardRow[j] == ConnectFourPiece.BLACK) {
                piece = " B ";
            }
            if (connectFourBoardRow[j] == ConnectFourPiece.RED) {
                piece = " R ";
            }
            row += (" " + piece + " |");
        }
        return row;
    }

    public String toString() {
        String bottomOfBoard = "__________________________";
        String board = "";
        for (int i = 0; i < ROWS; i++) {
            String row = "| ";
            for (int j = 0; j < COLUMNS; j++) {
                String piece = " - ";
                if (this.board[i][j] == ConnectFourPiece.BLACK) {
                    piece = " B ";
                }
                if (this.board[i][j] == ConnectFourPiece.RED) {
                    piece = " R ";
                }
                row += (" " + piece + " |");
            }
            board += (row + "\n");
        }
        ConnectFourPiece winner = getWinnerForAnyGivenBoard(this);
        String win = "";
        if (winner != null) {
            win = "\n The winner is: " + winner.name();
        }
        return "\n" + board + bottomOfBoard + win;
    }
}
