package com.packtpublishing.tddjava.ch03tictactoe.mongo;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class TicTacToeCollection {
  private MongoCollection collection;

  public TicTacToeCollection() throws UnknownHostException {
    DB db = new MongoClient().getDB("tic-tac-toe");
    collection = new Jongo(db).getCollection("game");
  }

  protected MongoCollection getMongoCollection() {
    return collection;
  }

  public boolean saveMove(TicTacToeBean bean) {
    try {
      getMongoCollection().save(bean);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

	public boolean drop() {
    try {
      getMongoCollection().drop();
      return true;
    } catch (Exception e) {
      return false;
    }
	}
}
