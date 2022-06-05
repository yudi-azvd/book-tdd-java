package com.packtpublishing.tddjava.ch04.connect4tdd;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Connect4TDD {
  private int numOfDiscs = 0;
  private final int COLUMNS = 7;
  private final int ROWS = 6;
  private final int DISCS_TO_WIN = 4;
  private final String EMPTY = " ";
  private final String RED = "R";
  private final String GREEN = "G";
  private final String DELIMETER = "|";
  private String winner = "";
  private String[][] board = new String[ROWS][COLUMNS];
  private String currentPlayer = RED;
  private PrintStream printStream;

  Connect4TDD(PrintStream printStream) {
    this.printStream = printStream;
    for (String[] row : board) {
      Arrays.fill(row, EMPTY);
    }
  }

  public int getNumberOfDiscs() {
    return numOfDiscs;
  }

  // Implementação do livro
  public int _getNumberOfDiscs() {
    return IntStream.range(0, COLUMNS)
        .map(this::_getNumberOfDiscsInColumn).sum();
  }

  public int putDiscInColumn(int column) {
    checkColumn(column);
    int row = getNumberOfDiscsInColumn(column);
    checkIfThereIsRoomForDisc(row, column);
    board[row][column] = currentPlayer;
    checkWinner(row, column);
    switchPlayer();
    printBoard();
    numOfDiscs++;
    return row;
  }

  private void printBoard() {
    for (int row = ROWS - 1; row >= 0; row--) {
      StringJoiner joiner = new StringJoiner(DELIMETER, DELIMETER, DELIMETER);
      Stream.of(board[row])
          .forEachOrdered(joiner::add);
      printStream.println(joiner.toString());
    }
  }

  private void checkColumn(int column) {
    if (column < 0 || COLUMNS <= column)
      throw new RuntimeException("Invalid column " + column);
  }

  private int getNumberOfDiscsInColumn(int column) {
    int row = 0;
    for (; row < ROWS; row++) {
      if (board[row][column] == EMPTY)
        break;
    }
    return row;
  }

  private int _getNumberOfDiscsInColumn(int column) {
    return (int) IntStream.range(0, ROWS)
        .filter(row -> !EMPTY
            .equals(board[row][column]))
        .count();
  }

  private void checkIfThereIsRoomForDisc(int row, int column) {
    if (row >= ROWS)
      throw new RuntimeException("No more room in column " + column);
  }

  private void switchPlayer() {
    if (currentPlayer == RED)
      currentPlayer = GREEN;
    else
      currentPlayer = RED;
  }

  public String getCurrentPlayer() {
    printStream.printf("Player %s turn%n", currentPlayer);
    return currentPlayer;
  }

  public boolean isFinished() {
    return getNumberOfDiscs() == ROWS * COLUMNS;
  }

  private void checkWinner(int row, int column) {
    if (!winner.isEmpty())
      return;

    String playerColor = board[row][column];
    Pattern winPattern = Pattern.compile(".*" + playerColor +
        "{" + DISCS_TO_WIN + "}.*");

    // Vertical check
    String vertical = IntStream.range(0, ROWS)
        .mapToObj(r -> board[r][column])
        .reduce(String::concat).get();
    if (winPattern.matcher(vertical).matches())
      winner = playerColor;

    // Horizontal check
    String horizontal = Stream.of(board[row]).reduce(String::concat).get();
    if (winPattern.matcher(horizontal).matches())
      winner = playerColor;

    // Diagonal 1 check
    int startOffset = Math.min(column, row);
    int myColumn = column - startOffset,
        myRow = row - startOffset;
    StringJoiner joiner = new StringJoiner("");
    do {
      joiner.add(board[myRow++][myColumn++]);
    } while (myColumn < COLUMNS && myRow < ROWS);
    if (winPattern.matcher(joiner.toString()).matches())
      winner = currentPlayer;

    // Diagonal 2 check
    startOffset = Math.min(column, ROWS - 1 - row);
    myColumn = column - startOffset;
    myRow = row + startOffset;
    joiner = new StringJoiner("");
    do {
      joiner.add(board[myRow--][myColumn++]);
    } while (myColumn < COLUMNS && myRow >= 0);
    if (winPattern.matcher(joiner.toString()).matches())
      winner = currentPlayer;
  }

  public String getWinner() {
    return winner;
  }

  public static void main(String[] args) {
    OutputStream output = new ByteArrayOutputStream();
    Connect4TDD connect4 = new Connect4TDD(new PrintStream(output));
    System.out.println("oi");
  }
}
