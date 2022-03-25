package com.packtpublishing.tddjava.ch03tictactoe.mongo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.net.UnknownHostException;

import com.mongodb.MongoException;

import org.jongo.MongoCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Capítulo 6
 */
public class TicTacToeCollectionSpec {
  private MongoCollection mongoCollection;
  private TicTacToeCollection sut;
  private TicTacToeBean bean;

  @BeforeEach
  public void beforeEach() throws UnknownHostException {
    sut = spy(new TicTacToeCollection());
    bean = new TicTacToeBean(3, 2, 1, 'Y');
    mongoCollection = mock(MongoCollection.class);
  }

  @Test
  public void whenInstatiatedThenMongoHasDbNameTicTacToe() {
    assertEquals("tic-tac-toe", sut
      .getMongoCollection()
      .getDBCollection().getDB().getName());
  }

  @Test
  public void whenInstatiatedThenMongoCollectionHasNameGame() {
    assertEquals("game", sut
      .getMongoCollection().getName());
  }

  @Test
  public void whenSaveMoveThenInvokeMongoCollectionSave() {
    doReturn(mongoCollection).when(sut).getMongoCollection();

    sut.saveMove(bean);
    
    verify(mongoCollection, times(1)).save(bean);
  }

  @Test
  public void whenSaveMoveThenReturnTrue() {
    doReturn(mongoCollection).when(sut).getMongoCollection();

    sut.saveMove(bean);
    
    assertTrue(sut.saveMove(bean));
  }

  @Test
  public void givenExceptionWhenSaveMoveThenReturnFalse() {
    doThrow(new MongoException("Bla"))
      .when(mongoCollection)
      .save(any(TicTacToeBean.class));
    
    doReturn(mongoCollection).when(sut).getMongoCollection();

    assertFalse(sut.saveMove(bean));
  }

  @Test
  public void whenDropThenInvokeMongoCollectionDrop() {
    doReturn(mongoCollection).when(sut).getMongoCollection();

    sut.drop();

    verify(mongoCollection).drop();
  }

  @Test
  public void whenDropThenReturnTrue() {
    doReturn(mongoCollection).when(sut).getMongoCollection();

    assertTrue(sut.drop());
  }

  @Test
  public void givenExceptionWhenDropThenReturnFalse() {
    doThrow(new MongoException("bla")).when(mongoCollection).drop();
    doReturn(mongoCollection).when(sut).getMongoCollection();

    assertFalse(sut.drop());
  }
}
