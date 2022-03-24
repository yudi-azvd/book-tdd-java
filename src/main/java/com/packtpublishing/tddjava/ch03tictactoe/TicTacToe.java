package com.packtpublishing.tddjava.ch03tictactoe;

public class TicTacToe {
    private Character[][] board = {
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'},
    };

    public void play(int x, int y) {
        checkAxis(x);
        checkAxis(y);
        setBox(x, y);
    }

    public char nextPlayer() {
        return 'X';
    }

    private void checkAxis(int axis) {
        if (axis < 1 || axis > 3)
            throw new RuntimeException("Y is outside board");
    }

    private void setBox(int x, int y) {
        if (board[x - 1][y - 1] != '\0')
            throw new RuntimeException("Box is occupied");
        board[x - 1][y - 1] = 'X';
    }
}
