import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectFourBoardTest {

    @Test
    void testHorizontalFourInARow(){
        ConnectFourBoard connectFourBoard = new ConnectFourBoard();
        connectFourBoard.applyMove(3, ConnectFourPiece.BLACK);
        connectFourBoard.applyMove(2, ConnectFourPiece.BLACK);
        connectFourBoard.applyMove(1, ConnectFourPiece.BLACK);
        connectFourBoard.applyMove(5, ConnectFourPiece.BLACK);

        assertTrue(connectFourBoard.getWinner());
    }
}