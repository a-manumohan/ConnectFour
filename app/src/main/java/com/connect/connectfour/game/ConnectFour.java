package com.connect.connectfour.game;

/**
 * Created by manuMohan on 15/01/27.
 */
public class ConnectFour {
    private static ConnectFour mSharedConnectFour;
    public static final int ROW_COUNT = 6;
    public static final int COLUMN_COUNT = 7;

    private ConnectFourListener mConnectFourListener;

    public enum Player {
        FIRST_PLAYER,
        SECOND_PLAYER
    }

    private Player mCurrentPlayer = Player.FIRST_PLAYER;


    public Node[][] mGameState;

    private ConnectFour() {
        mGameState = new Node[ROW_COUNT][COLUMN_COUNT];
        for (int i = 0; i < ROW_COUNT; ++i)
            for (int j = 0; j < COLUMN_COUNT; ++j)
                mGameState[i][j] = new Node();
        mGameState[3][4].isEmpty();
    }

    public static ConnectFour getInstance(ConnectFourListener connectFourListener) {
        if (mSharedConnectFour == null) {
            mSharedConnectFour = new ConnectFour();
        }
        mSharedConnectFour.mConnectFourListener = connectFourListener;
        return mSharedConnectFour;
    }

    /**
     * @param player
     * @return true if won
     */
    public boolean checkWhetherWon(Player player, int row, int column) {
        return checkVerticalPattern(player, column) || checkHorizontalPattern(player, row) || checkDiagonalPattern(player, row, column);
    }

    private boolean checkVerticalPattern(Player player, int column) {
        int count = 0;
        for (int i = 0; i < ROW_COUNT; ++i) {
            if (mGameState[i][column].isEmpty() || mGameState[i][column].getPlayer() != player) {
                count = 0;
                continue;
            }
            count++;
            if (count == 4) return true;
        }
        return false;
    }

    private boolean checkHorizontalPattern(Player player, int row) {
        int count = 0;
        for (int i = 0; i < COLUMN_COUNT; ++i) {
            if (mGameState[row][i].isEmpty() || mGameState[row][i].getPlayer() != player) {
                count = 0;
                continue;
            }
            count++;
            if (count == 4) return true;
        }
        return false;
    }

    private boolean checkDiagonalPattern(Player player, int row, int column) {
        int count = 1;
        int i = 1;
        if (row - 1 >= 0 && column + 1 < COLUMN_COUNT)
            if (!mGameState[row - 1][column + 1].isEmpty() && mGameState[row - 1][column + 1].getPlayer() == player)
                count++;//check immediate right down
        while (column - i >= 0 && row + i < ROW_COUNT) { // left up diagonal
            if (mGameState[row + i][column - i].isEmpty() ||
                    mGameState[row + i][column - i].getPlayer() != player) {
                break;
            }
            count++;
            ++i;
            if (count == 4) return true;
        }

        i = 1;
        count = 1;
        if (row - 1 >= 0 && column - 1 >= 0)
            if (!mGameState[row - 1][column - 1].isEmpty() && mGameState[row - 1][column - 1].getPlayer() == player)
                count++;//check immediate left down
        while (column + i < COLUMN_COUNT && row + i < ROW_COUNT) { // right up diagonal
            if (mGameState[row + i][column + i].isEmpty() ||
                    mGameState[row + i][column + i].getPlayer() != player) {
                break;
            }
            count++;
            ++i;
            if (count == 4) return true;
        }

        i = 1;
        count = 1;
        if (row + 1 < ROW_COUNT && column + 1 < COLUMN_COUNT)
            if (!mGameState[row + 1][column + 1].isEmpty() && mGameState[row + 1][column + 1].getPlayer() == player)
                count++;//check immediate right up
        while (column - i >= 0 && row - i >= 0) { // left down diagonal
            if (mGameState[row - i][column - i].isEmpty() ||
                    mGameState[row - i][column - i].getPlayer() != player) {
                break;
            }
            count++;
            ++i;
            if (count == 4) return true;
        }

        i = 1;
        count = 1;
        if (row + 1 < ROW_COUNT && column - 1 >= 0)
            if (!mGameState[row + 1][column - 1].isEmpty() && mGameState[row + 1][column - 1].getPlayer() == player)
                count++;//check immediate left up
        while (column + i < COLUMN_COUNT && row - i >= 0) { // right down diagonal
            if (mGameState[row - i][column + i].isEmpty() ||
                    mGameState[row - i][column + i].getPlayer() != player) {
                break;
            }
            count++;
            ++i;
            if (count == 4) return true;
        }
        return false;
    }

    /**
     * @return true if over
     */
    public boolean checkGameOver() {
        boolean gameOver = true;
        for (int i = 0; i < COLUMN_COUNT; ++i)
            if (mGameState[ROW_COUNT - 1][i].isEmpty()) {
                gameOver = false;
                break;
            }
        return gameOver;
    }

    public void play(int column) {
        int i = 0;
        while (i < ROW_COUNT && !mGameState[i][column].isEmpty()) ++i;
        if (i < ROW_COUNT) {
            mGameState[i][column].setPlayer(mCurrentPlayer);
            if (checkWhetherWon(mCurrentPlayer, i, column)) {
                mConnectFourListener.won(mCurrentPlayer);
            } else if (checkGameOver()) {
                mConnectFourListener.gameOver();
            } else {
                mConnectFourListener.ready();
            }
        } else {
            mConnectFourListener.ready();
        }
        mCurrentPlayer = mCurrentPlayer == Player.FIRST_PLAYER ? Player.SECOND_PLAYER : Player.FIRST_PLAYER;
    }

    public void reset() {
        for (int i = 0; i < ROW_COUNT; ++i)
            for (int j = 0; j < COLUMN_COUNT; ++j)
                mGameState[i][j].setEmpty(true);
        mCurrentPlayer = Player.FIRST_PLAYER;
    }

    public static class Node {
        private Player player;
        private boolean empty;

        public Node() {
            empty = true;
        }

        public Node(Player player) {
            this.player = player;
            empty = false;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
            this.empty = false;
        }

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }
    }

    public static interface ConnectFourListener {
        public void gameOver();

        public void won(Player player);

        public void ready();
    }
}
