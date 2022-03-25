package com.packtpublishing.tddjava.ch03tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.UnknownHostException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TicTacToeInteg {
  @Disabled
  @Test
  public void givenMongoDbIsRunningWhenPlayThenNoException() throws UnknownHostException {
    TicTacToe sut = new TicTacToe();
    assertEquals(TicTacToe.NO_WINNER, sut.play(1, 1));
  }
}
