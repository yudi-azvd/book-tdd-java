package com.packtpublishing.tddjava.ch03tictactoe;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.UnknownHostException;

import com.packtpublishing.tddjava.ch03tictactoe.mongo.TicTacToeBean;
import com.packtpublishing.tddjava.ch03tictactoe.mongo.TicTacToeCollection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TicTacToeSpec {
    private TicTacToe sut;
    private TicTacToeCollection collection;

    @BeforeEach
    public void setup() throws UnknownHostException {
        collection = mock(TicTacToeCollection.class);
        // doReturn stubs the mocked collection
        doReturn(true) 
            .when(collection)
            .saveMove(any(TicTacToeBean.class));
        doReturn(true)
            .when(collection)
            .drop();
        sut = new TicTacToe(collection);
    }

    @Test
    public void whenXOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            sut.play(5, 2);
        });
    }

    @Test
    public void whenYOutsideBoardThenRuntimeException() {
        assertThrows(RuntimeException.class, () -> {
            sut.play(2, 5);
        });
    }

    @Test
    public void whenOccupiedThenRunTimeException() {
        sut.play(2, 2);

        assertThrows(RuntimeException.class, () -> {
            sut.play(2, 2);
        });
    }

    @Test
    public void givenFirstTurnWhenNextPlayerThenX() {
        assertEquals('X', sut.nextPlayer());
    }

    @Test
    public void givenLastTurnWasXWhenNextPlayerThenO() {
        sut.play(1, 1);
        assertEquals('O', sut.nextPlayer());
    }

    @Test
    @Disabled // Teste inútil, continua escrito por aprendizado
    // Nem tive que mudar a implementação ao acrescentar esse teste, mesmo assim
    // os testes continuaram passando. Sinal de que esse último testes adicionado
    // é supérfluo.
    public void givenLastTurnWasOWhenNextPlayerThenX() {
        sut.play(1, 1); // x's turn
        sut.play(2, 2); // o's turn
        // x's turn again
        assertEquals('X', sut.nextPlayer());
    }

    @Test
    public void whenPlayThenNoWinner() {
        String actual = sut.play(1, 1);
        assertEquals("No winner", actual);
    }

    @Test
    public void whenPlayAndWholeHorizontalLineThenWinner() {
        sut.play(1, 1); // x
        sut.play(1, 2); // o
        sut.play(2, 1); // x
        sut.play(2, 2); // o
        String actual = sut.play(3, 1);
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() {
        sut.play(2, 1); // x
        sut.play(1, 1); // o
        sut.play(3, 1); // x
        sut.play(1, 2); // o
        sut.play(2, 2); // x
        String actual = sut.play(1, 3); // o
        assertEquals("O is the winner", actual);
    }

    @Test
    public void whenPlayAndBottomTopDiagonalLineThenWinner() {
        sut.play(1, 3); // x
        sut.play(1, 1); // o
        sut.play(2, 2); // x
        sut.play(1, 2); // o
        String actual = sut.play(3, 1); // x
        assertEquals("X is the winner", actual);
    }

    @Test
    public void whenAllBoxesAreFilledThenDraw() {
        sut.play(1, 1);
        sut.play(1, 2);
        sut.play(1, 3);
        sut.play(2, 1);
        sut.play(2, 3);
        sut.play(2, 2);
        sut.play(3, 1);
        sut.play(3, 3);
        String actual = sut.play(3, 2);
        assertEquals("The result is draw", actual);
    }

    @Test
    public void whenInstatiatedThenSetCollection() {
        assertNotNull(sut.getTicTacToeCollection());
    }

    @Test 
    public void whenPlayThenSaveMoveIsinvoked() {
        TicTacToeBean move = new TicTacToeBean(1, 1, 3, 'X');
        sut.play(move.getX(), move.getY());

        verify(collection).saveMove(move);
    }

    @Test
    public void whenPlayAndSaveReturnsFalseThenThrowException() {
        doReturn(false).when(collection).saveMove(any(TicTacToeBean.class));
        TicTacToeBean move = new TicTacToeBean(1, 1, 3, 'X');

        assertThrows(RuntimeException.class, () -> {
            sut.play(move.getX(), move.getY());
        });
    }

    @Test
    public void whenPlayInvokedMultipleTimesThenTurnIncreases() {
        TicTacToeBean move1 = new TicTacToeBean(1, 1, 1, 'X');
        sut.play(move1.getX(), move1.getY());
        verify(collection, times(1)).saveMove(move1);
        TicTacToeBean move2 = new TicTacToeBean(2, 1, 2, 'O');
        
        sut.play(move2.getX(), move2.getY());
        
        verify(collection, times(1)).saveMove(move2);
    }

    @Test
    public void whenInstantiatedThenDropIsInvoked() {
        verify(collection, times(1)).drop();
    }

    /**
     * Ficou como exercício do livro. Eu só implementei
     * a especificação e os testes continuaram rodando.
     * Esse é um teste desnecessário então?
     * 
     * [Pouco tempo depois...] Aparentemente eu tinha
     * implementado mais código que o necessário no teste
     * anterior.
     */
    @Test 
    public void whenDropReturnsFalseThenThrowException() {
        doReturn(false).when(collection).drop();

        assertThrowsExactly(RuntimeException.class, () -> {
            sut = new TicTacToe(collection);
        });
    }
}
