package com.connect.connectfour.game;

/**
 * Created by manuMohan on 15/01/27.
 */
public class ConnectFour {
    private static ConnectFour mSharedConnectFour;
    private static final int ROW_COUNT = 6;
    private static final int COLUMN_COUNT = 7;

    public enum Player {
        FIRST_PLAYER,
        SECOND_PLAYER
    }


    public Node[][] mGameState;

    private ConnectFour() {
        mGameState = new Node[ROW_COUNT][COLUMN_COUNT];
        for (int i = 0; i < ROW_COUNT; ++i)
            for (int j = 0; j < COLUMN_COUNT; ++j)
                mGameState[i][j] = new Node();
        mGameState[3][4].isEmpty();
    }

    public static ConnectFour getInstance() {
        if (mSharedConnectFour == null) {
            mSharedConnectFour = new ConnectFour();
        }
        return mSharedConnectFour;
    }

    /**
     * @param player
     * @return true if won
     */
    public boolean checkWhetherWon(Player player) {
        return false;
    }

    private boolean checkVerticalPattern(Player player){
        int count = 0;
        for (int j = 0; j < COLUMN_COUNT; ++j)
        for(int i = 0; i < ROW_COUNT - 1; ++ i){
            if(mGameState[i][j].isEmpty())continue;
            if(mGameState[i][j].getPlayer() != player){ count = 0; continue;}
            count++;
            if(count == 4)return true;
        }
        return false;
    }
    private boolean checkHorizontalPattern(Player player){
        int count = 0;
        for (int j = 0; j < ROW_COUNT; ++j)
            for(int i = 0; i < COLUMN_COUNT - 1; ++ i){
                if(mGameState[i][j].isEmpty())continue;
                if(mGameState[i][j].getPlayer() != player){ count = 0; continue;}
                count++;
                if(count == 4)return true;
            }
        return false;
    }
    private boolean checkDiagonalPattern(Player player){
        int count = 0;
        for (int j = 0; j < ROW_COUNT; ++j)
            for(int i = 0; i < COLUMN_COUNT - 1; ++ i){
                if(mGameState[i][j].isEmpty())continue;
                if(mGameState[i][j].getPlayer() != player){ count = 0; continue;}
                count++;
                if(count == 4)return true;
            }
        return false;
    }

    /**
     * @return true if over
     */
    public boolean checkGameOver() {
        boolean gameOver = true;
        for (int i = 0; i < COLUMN_COUNT; ++i)
            if (mGameState[0][i].isEmpty()) {
                gameOver = false;
                break;
            }
        return gameOver;
    }

    public void play(Player player, int column) {
        int i = 0;
        while (i < ROW_COUNT && !mGameState[i][column].isEmpty()) ++i;
        if (i < ROW_COUNT) mGameState[i][column].setPlayer(player);
    }

    private static class Node {
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
            empty = false;
        }

        public boolean isEmpty() {
            return empty;
        }
    }
}
