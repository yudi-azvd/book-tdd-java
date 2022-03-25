package com.packtpublishing.tddjava.ch03tictactoe;

import java.net.UnknownHostException;

import com.packtpublishing.tddjava.ch03tictactoe.mongo.TicTacToeBean;
import com.packtpublishing.tddjava.ch03tictactoe.mongo.TicTacToeCollection;

public class TicTacToe {
    private Character[][] board = {
            { '\0', '\0', '\0' },
            { '\0', '\0', '\0' },
            { '\0', '\0', '\0' },
    };

    private char lastPlayer = '\0';
    private static final int SIZE = 3;

    private static final String RESULT_DRAW = "The result is draw";
    private static final String NO_WINNER = "No winner";

    private TicTacToeCollection ticTacToeCollection;

    private int turn = 0;

    public TicTacToe() throws UnknownHostException {
        this(new TicTacToeCollection());
    }
    
    protected TicTacToe(TicTacToeCollection collection) {
        ticTacToeCollection = collection;
        if (!getTicTacToeCollection().drop()) {
            throw new RuntimeException("DB could not be dropped");
        }
    }

    public String play(int x, int y) {
        checkAxis(x);
        checkAxis(y);
        lastPlayer = nextPlayer();
        setBox(new TicTacToeBean(++turn, x, y, lastPlayer));

        if (isWin(x, y)) {
            return lastPlayer + " is the winner";
        } else if (isDraw()) {
            return RESULT_DRAW;
        }

        return NO_WINNER;
    }

    private boolean isDraw() {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (board[x][y] == '\0')
                    return false;
            }
        }
        return true;
    }

    private boolean isWin(int x, int y) {
        int playerTotal = lastPlayer * 3;
        char diagonal1, diagonal2, horizontal, vertical = '\0';
        diagonal1 = diagonal2 = horizontal = vertical = '\0';
        for (int i = 0; i < SIZE; i++) {
            horizontal += board[i][y - 1];
            vertical += board[x - 1][i];
            diagonal1 += board[i][i];
            diagonal2 += board[i][SIZE - i - 1];
        }

        if (vertical == playerTotal || horizontal == playerTotal
                || diagonal1 == playerTotal
                || diagonal2 == playerTotal)
            return true;
        return false;
    }

    public char nextPlayer() {
        if (lastPlayer == 'X')
            return 'O';
        return 'X';
    }

    private void checkAxis(int axis) {
        if (axis < 1 || axis > 3)
            throw new RuntimeException("Y is outside board");
    }

    private void setBox(TicTacToeBean bean) {
        if (board[bean.getX() - 1][bean.getY() - 1] != '\0')
            throw new RuntimeException("Box is occupied");
        board[bean.getX() - 1][bean.getY() - 1] = lastPlayer;

        if (!getTicTacToeCollection().saveMove(bean))
            throw new RuntimeException("Saving to DB failed");
    }

    public TicTacToeCollection getTicTacToeCollection() {
        return ticTacToeCollection;
    }
}
