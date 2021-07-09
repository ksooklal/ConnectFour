import java.util.GregorianCalendar;

public class ConnectFourGameDriver {
    /**
     * Set debugKrisBot = TRUE to debug whenever KrisBot loses.
     * Set debugIndividualMoves = TRUE to debug each individual move.
     */
    public static boolean debugKrisBot = true;
    public static boolean debugIndividualMoves = false;

    /**
     * TODO
     * SET PLAYER1 equal to whatever implementation you'd like to be as player 1. The default is to play against Kris's Bot.
     *
     * SET PLAYER2 equal to whatever implementation you'd like to be as player 2.
     */
    public static final ConnectFourBot PLAYER1 = new KrisBot();
    public static final ConnectFourBot PLAYER2 = new RandomBot();

    public static void main (String [] args) {
        playMultipleGames(PLAYER1, PLAYER2, 113);
    }


    public static final void playMultipleGames(ConnectFourBot bot1, ConnectFourBot bot2, double numberOfTimes) {
        double numberOfBot1Wins = 0;
        double numberOfBot2Wins = 0;
        double numberOfDraws = 0;

        for (int i = 0; i < numberOfTimes; i++) {
            // Use random choice to get the player to go first
            boolean bot1First = new GregorianCalendar().getTimeInMillis() % 2L == 0L;
            ConnectFourBot winner = null;
            if (bot1First) {
                winner = playSingleGame(bot1, bot2);
            } else {
                winner = playSingleGame(bot2, bot1);
            }
            if (winner == bot1) {
                numberOfBot1Wins++;
            } else if (winner == bot2){
                numberOfBot2Wins++;
            } else {
                numberOfDraws++;
            }
        }
        System.out.println("\n Number of Bot1 (" + bot1.getClass().getName() + ") Wins: " + numberOfBot1Wins + ", " + (numberOfBot1Wins/numberOfTimes) + "%");
        System.out.println("\n Number of Bot2 (" + bot2.getClass().getName() +  ") Wins: " + numberOfBot2Wins + ", " + (numberOfBot2Wins/numberOfTimes) + "%");
        System.out.println("\n Number of Draws: " + numberOfDraws + ", " + (numberOfDraws/numberOfTimes) + "%");
    }

    /**
     *
     * @param bot1 The player who has the first move
     * @param bot2 The player who has the second move
     *
     * bot1 is always assigned the color BLACK
     * bot2 is always assigned the color RED
     *
     * If the move returned by one of the bots is invalid, then the game will skip that player. BE SURE to implement the
     * bot with the use of the {@link ConnectFourBoard#validateMove(int)} method.
     *
     * @return The player who won the game
     */
    public static ConnectFourBot playSingleGame(ConnectFourBot bot1, ConnectFourBot bot2) {
        ConnectFourBoard connectFourBoard = new ConnectFourBoard();

        while (connectFourBoard.getWinner() == null && connectFourBoard.getMovesMade() < ConnectFourBoard.TOTAL_MOVES) {
            ConnectFourPiece winner = connectFourBoard.getWinner();
            if (winner != null) {
                if (winner == ConnectFourPiece.BLACK) {
                    if (bot2 instanceof KrisBot && debugKrisBot) {
                        System.out.println(connectFourBoard);
                    }
                    return bot1;
                }
                if (winner == ConnectFourPiece.RED) {
                    if (bot1 instanceof KrisBot && debugKrisBot) {
                        System.out.println(connectFourBoard);
                    }
                    return bot2;
                }
            }

            if (debugIndividualMoves) {
                System.out.println("Current state of move: " + connectFourBoard);
            }

            int bot1DesiredMove = -1;
            try {
                bot1DesiredMove = bot1.makeMove(connectFourBoard, ConnectFourPiece.BLACK);
                connectFourBoard.applyMove(bot1DesiredMove, ConnectFourPiece.BLACK);
                if (debugIndividualMoves) {
                    System.out.println("After bot1 move: " + bot1DesiredMove + connectFourBoard);
                }
            } catch (Exception e) {
                System.out.println("Invalid move by " + bot1.getClass().getName() + " bot1: " + bot1DesiredMove + ". Skipping to bot2");
            }

            winner = connectFourBoard.getWinner();
            if (winner != null) {
                if (winner == ConnectFourPiece.BLACK) {
                    if (bot2 instanceof KrisBot && debugKrisBot) {
                        System.out.println(connectFourBoard);
                    }
                    return bot1;
                }
                if (winner == ConnectFourPiece.RED) {
                    if (bot1 instanceof KrisBot && debugKrisBot) {
                        System.out.println(connectFourBoard);
                    }
                    return bot2;
                }
            }


            int bot2DesiredMove = -1;
            try {
                bot2DesiredMove = bot1.makeMove(connectFourBoard, ConnectFourPiece.RED);
                connectFourBoard.applyMove(bot2DesiredMove, ConnectFourPiece.RED);
                if (debugIndividualMoves) {
                    System.out.println("After bot1 move: " + bot1DesiredMove + connectFourBoard);
                }
            } catch (Exception e) {
                System.out.println("Invalid move by " + bot2.getClass().getName() + " bot2: " + bot2DesiredMove + ". Skipping to bot1");
            }

            winner = connectFourBoard.getWinner();
            if (winner != null) {
                if (winner == ConnectFourPiece.BLACK) {
                    if (bot2 instanceof KrisBot && debugKrisBot) {
                        System.out.println(connectFourBoard);
                    }
                    return bot1;
                }
                if (winner == ConnectFourPiece.RED) {
                    if (bot1 instanceof KrisBot && debugKrisBot) {
                        System.out.println(connectFourBoard);
                    }
                    return bot2;
                }
            }
        }

        // No winner, so return null to indicate a draw
        return null;
    }
}
