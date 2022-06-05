package com.packtpublishing.tddjava.ch04.connect4tdd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Connect4TDDSpec {
  private Connect4TDD sut;
  private OutputStream output;

  @BeforeEach
  public void beforeEach() {
    output = new ByteArrayOutputStream();
    sut = new Connect4TDD(new PrintStream(output));
  }

  @Test
  public void whenTheGameIsStartedTheBoardIsEmpty() {
    assertThat(sut.getNumberOfDiscs(), is(0));
  }

  @Test
  public void whenDiscOutsideBoardThenRuntimeException() {
    int column = -1;
    Exception exception = assertThrows(RuntimeException.class, () -> {
      sut.putDiscInColumn(column);
    });

    assertEquals("Invalid column " + column, exception.getMessage());
  }

  @Test
  public void whenFirstDiscInsertedInColumnThenPositionIsZero() {
    int column = 1;
    assertThat(sut.putDiscInColumn(column), is(0));
  }

  @Test
  public void whenSecondDiscInsertedInColumnThenPositionIsOne() {
    int column = 1;
    sut.putDiscInColumn(column);
    assertThat(sut.putDiscInColumn(column), is(1));
  }

  @Test
  public void whenDiscInsertedThenNumberOfDiscsIncreases() {
    int column = 1;
    sut.putDiscInColumn(column);
    assertThat(sut.getNumberOfDiscs(), is(1));
    assertThat(sut._getNumberOfDiscs(), is(1));
  }

  @Test
  public void whenNoMoreRoomIncolumnThenRuntimeException() {
    int column = 1;
    int maxDiscsInColumn = 6;
    fillColumn(column, maxDiscsInColumn);
    Exception exception = assertThrows(RuntimeException.class, () -> {
      sut.putDiscInColumn(column);
    });

    assertEquals("No more room in column " + column, exception.getMessage());
  }

  @Test
  public void whenFirstPlayerPlaysThenColorIsRed() {
    assertThat(sut.getCurrentPlayer(), is("R"));
  }

  @Test
  public void whenSecondPlayerPlaysThenColorIsGreen() {
    sut.putDiscInColumn(1);
    assertThat(sut.getCurrentPlayer(), is("G"));
  }

  @Test
  public void whenAskedForCurrentPlayerTheOutputNotice() {
    sut.getCurrentPlayer();
    assertThat(output.toString(), containsString("Player R turn"));
  }

  @Test
  public void whenADiscIsIntroducedTheBoardIsPrinted() {
    sut.putDiscInColumn(1);
    assertThat(output.toString(),
        containsString("| |R| | | | | |"));
  }

  @Test
  public void whenTheGameStartsItIsNotFinished() {
    assertFalse(sut.isFinished(), "The game must not be finished");
  }

  @Test
  public void whenNoDiscCanBeInsertedTheGameIsFinished() {
    fillBoard();
    assertTrue(sut.isFinished(), "the game must be finished");
  }

  @Test
  public void when4VerticalDiscsAreConnectedThenPlayerWins() {
    for (int row = 0; row < 3; row++) {
      sut.putDiscInColumn(1); // R
      sut.putDiscInColumn(2); // G
    }

    assertThat(sut.getWinner(), is(emptyString()));
    sut.putDiscInColumn(1); // R
    assertThat(sut.getWinner(), is("R"));
  }

  @Test
  public void when4HorizontalDiscsAreConnectedThenPlayerWins() {
    int column = 0;
    for (; column < 3; column++) {
      sut.putDiscInColumn(column); // R
      sut.putDiscInColumn(column); // G
    }

    assertThat(sut.getWinner(), is(emptyString()));
    sut.putDiscInColumn(column); // R
    assertThat(sut.getWinner(), is("R"));
  }

  @Test
  public void when4Diagonal1DiscsAreConnectedThenPlayerWins() {
    int[] gameplay = new int[] { 1, 2, 2, 3, 4, 3, 3, 4, 4, 5, 4 };
    for (int column : gameplay) {
      sut.putDiscInColumn(column);
    }

    assertThat(sut.getWinner(), is("R"));
  }

  @Test
  public void when4Diagonal2DiscsAreConnectedThenPlayerWins() {
    int[] gameplay = new int[] { 3, 4, 2, 3, 2, 2, 1, 1, 1, 1 };
    for (int column : gameplay) {
      sut.putDiscInColumn(column);
    }

    assertThat(sut.getWinner(), is("G"));
  }

  private void fillBoard() {
    for (int row = 0; row < 6; row++) {
      for (int column = 0; column < 7; column++) {
        sut.putDiscInColumn(column);
      }
    }
  }

  /**
   * Usar quando não faz diferença a cor dos discos.
   * 
   * @param column
   * @param maxDiscsInColumn
   */
  private void fillColumn(int column, int maxDiscsInColumn) {
    for (int times = 0; times < maxDiscsInColumn; times++) {
      sut.putDiscInColumn(column);
    }
  }
}
