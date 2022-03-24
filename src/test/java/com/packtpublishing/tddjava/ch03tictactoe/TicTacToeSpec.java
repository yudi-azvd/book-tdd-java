package com.packtpublishing.tddjava.ch03tictactoe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicTacToeSpec {
    private TicTacToe game;

    @BeforeEach
    public void setup() {
        game = new TicTacToe();
    }

    @Test
    public void whenXOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            game.play(5, 2);
        });
    }

    @Test
    public void whenYOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            game.play(2, 5);
        });
    }

    @Test
    public void whenOccupiedThenRunTimeException() {
        game.play(2, 2);

        assertThrows(RuntimeException.class, () -> {
            game.play(2, 2);
        });
    }

    @Test
    public void givenFirstTurnWhenNextPlayerThenX() {
        assertEquals('X', game.nextPlayer());
    }

    
}
