package com.tetrisrevision;

import java.util.ArrayList;
import java.util.Arrays;

interface TriConsumer<T> {
  void accept(Integer x, Integer y, T t);
}

interface TriConsumerString<T> {
  String accept(Integer x, Integer y, T t);
}

public class PrintToConsole {
  private static int height;
  private static int width;
  private String[][][] board;

  PrintToConsole(int height, int width) {
    PrintToConsole.height = height;
    PrintToConsole.width = width;
  }

  String pad(int i) {
    if (i > 9) {
      return Integer.toString(i);
    }

    return " " + i;
  }

  String printCoords(int x, int y) {
    return pad(x) + ", " + pad(y);
  }

  String printCoords(Cell c) {
    return pad((int) c.getX()) + ", " + pad((int) c.getY());
  }

  private void paintAndPrint(Blocks2d blocks2d, SinkingPieces sinkingPieces, TetrisPiece piece) {
    printBoardCustom(
        blocks2d,
        (Integer x, Integer y, Cell cell) -> {
          if ((int) cell.getX() != x || (int) cell.getY() != y) {
            System.out.print("(" + printCoords(x, y) + ") -> (" + printCoords(cell) + ") :: ");
          } else {
            System.out.print("(      ) -> (      ) :: ");
          }
        });

    getBoard(blocks2d, sinkingPieces, piece);

    printStringBoardCustom(
        (Integer x, Integer y, String[] cell) -> {
          String s = "";

          for (String element : cell) {
            s = s.concat(element);
          }

          return s;
        });

    printStringBoardCustom(
        (Integer x, Integer y, String[] cell) -> {
          if (cell[2].equals("s") && cell[3].equals("!")) return cell[2] + cell[3];
          else return "  ";
        });

    Cell cell1 = blocks2d.getBlocksByRow()[22][0];
    Cell cell2 = blocks2d.getBlocksByRow()[22][4];

    if (cell1 != null)
      System.out.println(
          "22, 0"
              + cell1.printCell()
              + " "
              + (cell1.getColor() != null ? cell1.getColor().toString() : "NC"));
    if (cell2 != null)
      System.out.println(
          "22, 4"
              + cell2.printCell()
              + " "
              + (cell2.getColor() != null ? cell2.getColor().toString() : "NC"));
  }

  private void getBoard(Blocks2d blocks2d, SinkingPieces sinkingPieces, TetrisPiece piece) {
    String[][][] board = new String[height][width][5];

    for (String[][] row : board) {
      for (int x = 0; x < row.length; x++) {
        row[x] = new String[] {" ", " ", " ", " ", " "};
      }
    }

    for (int y = 0; y < board.length; y++) {
      String[][] row = board[y];
      Cell[] fieldRow = blocks2d.getBlocksByRow()[y];

      for (int x = 0; x < row.length; x++) {
        String[] space = row[x];
        Cell cell = fieldRow[x];

        if (cell.isFull()) {
          space[0] = "x";
        }

        if (cell.getColor() != null) {
          space[1] = "c";
        }

        if (sinkingPiecesContainCell(x, y, sinkingPieces)) {
          space[2] = "s";
        }

        if ((int) cell.getX() != x || (int) cell.getY() != y) {
          space[3] = "!";
        }

        if (fallingPieceContainsCell(x, y, piece)) {
          space[4] = "f";
        }
      }
    }

    this.board = board;
  }

  private boolean sinkingPiecesContainCell(int x, int y, SinkingPieces sinkingPieces) {
    for (ArrayList<Cell> piece : sinkingPieces.getPieces()) {
      for (Cell cell : piece) {
        if ((int) cell.getX() == x && (int) cell.getY() == y) return true;
      }
    }

    return false;
  }

  private boolean sinkingPiecesContainCell(Cell c, SinkingPieces sinkingPieces) {
    for (ArrayList<Cell> piece : sinkingPieces.getPieces()) {
      for (Cell cell : piece) {
        if (cell.getX() == c.getX() && cell.getY() == c.getY()) return true;
      }
    }

    return false;
  }

  private boolean fallingPieceContainsCell(int x, int y, TetrisPiece currentPiece2d) {
    return Arrays.stream(currentPiece2d.getCells())
        .anyMatch(c -> (int) c.getX() == x && (int) c.getY() == y);
  }

  private <T> void printBoardCustom(Blocks2d blocks2d, TriConsumer<Cell> print) {
    for (int y = 0; y < blocks2d.getBlocksByRow().length; y++) {
      Cell[] row = blocks2d.getBlocksByRow()[y];

      for (int x = 0; x < row.length; x++) {
        print.accept(x, y, row[x]);

        System.out.print(" ");
      }

      System.out.println();
    }

    System.out.println();
  }

  private void printStringBoardCustom(TriConsumerString<String[]> print) {
    for (int y = 0; y < board.length; y++) {
      String[][] row = board[y];

      for (int x = 0; x < row.length; x++) {
        String[] cell = row[x];

        System.out.print("[ " + print.accept(x, y, cell) + " ]");
      }

      System.out.println();
    }

    System.out.println();
  }
}
