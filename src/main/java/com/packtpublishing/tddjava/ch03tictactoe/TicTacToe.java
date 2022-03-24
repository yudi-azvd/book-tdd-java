package com.packtpublishing.tddjava.ch03tictactoe;

public class TicTacToe {
    private Character[][] board = {
            { '\0', '\0', '\0' },
            { '\0', '\0', '\0' },
            { '\0', '\0', '\0' },
    };

    private char lastPlayer = '\0';
    private static final int SIZE = 3;

    public String play(int x, int y) {
        checkAxis(x);
        checkAxis(y);
        lastPlayer = nextPlayer();
        setBox(x, y, lastPlayer);

        if (isWin(x, y)) {
            return lastPlayer + " is the winner";
        } else if (isDraw()) {
            return "The result is draw";
        }

        return "No winner";
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

    private void setBox(int x, int y, char lastPlayer) {
        if (board[x - 1][y - 1] != '\0')
            throw new RuntimeException("Box is occupied");
        board[x - 1][y - 1] = lastPlayer;
    }
}
