package com.packtpublishing.tddjava.ch03tictactoe;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

    @Test
    public void givenLastTurnWasXWhenNextPlayerThenO() {
        game.play(1, 1);
        assertEquals('O', game.nextPlayer());
    }

    @Test
    @Disabled // Teste inútil, continua escrito por aprendizado
    // Nem tive que mudar a implementação ao acrescentar esse teste, mesmo assim
    // os testes continuaram passando. Sinal de que esse último testes adicionado
    // é supérfluo.
    public void givenLastTurnWasOWhenNextPlayerThenX() {
        game.play(1, 1); // x's turn
        game.play(2, 2); // o's turn
        // x's turn again
        assertEquals('X', game.nextPlayer());
    }

    @Test
    public void whenPlayThenNoWinner() {
        String actual = game.play(1, 1);
        assertEquals("No winner", actual);
    }

    @Test
    public void whenPlayAndWholeHorizontalLineThenWinner() {
        game.play(1, 1); // x
        game.play(1, 2); // o
        game.play(2, 1); // x
        game.play(2, 2); // o
        String actual = game.play(3, 1);
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() {
        game.play(2, 1); // x
        game.play(1, 1); // o
        game.play(3, 1); // x
        game.play(1, 2); // o
        game.play(2, 2); // x
        String actual = game.play(1, 3); // o
        assertEquals("O is the winner", actual);
    }

    @Test
    public void whenPlayAndBottomTopDiagonalLineThenWinner() {
        game.play(1, 3); // x
        game.play(1, 1); // o
        game.play(2, 2); // x
        game.play(1, 2); // o
        String actual = game.play(3, 1); // x
        assertEquals("X is the winner", actual);
    }
}
