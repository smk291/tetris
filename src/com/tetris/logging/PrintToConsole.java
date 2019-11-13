package com.tetris.logging;

import com.tetris.game.constants.Constants;
import com.tetris.game.things.Square;
import com.tetris.game.things.Row;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

interface QuadConsumer<T> {
  void accept(int x, int y, Integer rowY, T t);
}

interface QuadConsumerString<T> {
  String accept(int x, int y, Integer rowY, T t);
}

class PrintToConsole {
  private String[][][] board;

  PrintToConsole() {}

  public String printCell(int y, Square cell) {
    return "{ " + cell.getX() + ", " + y + " }";
  }

  private String pad(double i) {
    if (i > 9) {
      return Double.toString(i);
    }

    return " " + i;
  }

  private String printCoords(int x, int y) {
    return pad(x) + ", " + pad(y);
  }

  private String printCoords(int y, Square c) {
    return pad(c.getX()) + ", " + pad(y);
  }

  private void paintAndPrint(RowList rowList, ArrayList<RowList> sinkingBlocks, ActiveBlock block) {
    printBoardCustom(
        rowList,
        (int x, int y, Integer rowY, Optional<Square> square) -> {
          if (square.isEmpty()) return;

          if (square.get().getX() != x || !rowY.equals(y)) {
            System.out.print(
                "("
                    + printCoords(x, y)
                    + ") -> ("
                    + printCoords(square.get().getX(), rowY)
                    + ") :: ");
          } else {
            System.out.print("(      ) -> (      ) :: ");
          }
        });

    getBoard(rowList, sinkingBlocks, block);

    printStringBoardCustom(
        (int x, int y, Integer rowY, String[] cell) -> {
          String s = "";

          for (String element : cell) {
            s = s.concat(element);
          }

          return s;
        });

    printStringBoardCustom(
        (int x, int y, Integer rowY, String[] cell) -> {
          if (cell[2].equals("s") && cell[3].equals("!")) return cell[2] + cell[3];
          else return "  ";
        });

    //    Optional<Square> square1 = playField.getSquare(0, 22);
    //    Optional<Square> square2 = playField.getSquare(4,22);

    //    square1.ifPresent(square -> System.out.println(
    //            "22, 0"
    //                    + printCell(square)
    //                    + " "
    //                    + (square.getColor() != null ? square.getColor().toString() : "NC")));
    //    square2.ifPresent(square -> System.out.println(
    //            "22, 4"
    //                    + printCell(square)
    //                    + " "
    //                    + (square.getColor() != null ? square.getColor().toString() : "NC")));
  }

  private void getBoard(RowList rowList, ArrayList<RowList> sinkingBlocks, ActiveBlock block) {
    String[][][] board = new String[Constants.height][Constants.width][5];

    for (String[][] row : board) {
      IntStream.range(0, row.length).forEach(x -> row[x] = new String[] {" ", " ", " ", " ", " "});
    }

    for (int y = 0; y < board.length; y++) {
      String[][] row = board[y];

      for (int x = 0; x < row.length; x++) {
        String[] space = row[x];
        Optional<Square> cell = rowList.getSquare(x, y);

        if (cell.isPresent()) {
          space[0] = "x";

          if (cell.get().getColor() != null) {
            space[1] = "c";
          }

          if (sinkingBlocksContainCell(x, y, sinkingBlocks)) {
            space[2] = "s";
          }

          if (cell.get().getX() != x) {
            space[3] = "!";
          }

          if (fallingBlockContainsCell(x, y, block)) {
            space[4] = "f";
          }
        }
      }
    }

    this.board = board;
  }

  private boolean sinkingBlocksContainCell(int x, int y, ArrayList<RowList> sinkingBlocks) {
    for (RowList block : sinkingBlocks) {
      for (Row r : block.get()) {
        for (Square square : r.get()) {
          if (square.getX() == x && r.getY() == y) return true;
        }
      }
    }

    return false;
  }

  private boolean sinkingBlocksContainCell(Square c, ArrayList<RowList> sinkingBlocks) {
    for (RowList block : sinkingBlocks) {
      for (Row r : block.get()) {
        for (Square square : r.get()) {
          if (square.getX() == c.getX()) return true;
        }
      }
    }

    return false;
  }

  private boolean fallingBlockContainsCell(int x, int y, ActiveBlock currentBlock2d) {
    for (Row r : currentBlock2d.getSquares().get()) {
      if (r.getY() == y) {
        for (Square b : r.get()) {
          if (b.getX() == x) return true;
        }
      }
    }

    return false;
  }

  private <T> void printBoardCustom(RowList rowList, QuadConsumer<Optional<Square>> print) {
    for (Row row : rowList.get()) {
      if (row == null || row.isEmpty()) continue;

      for (Square b : row.get()) {
        print.accept(b.getX(), row.getY(), row.getY(), row.get(b.getX()));

        System.out.print(" ");
      }

      System.out.println();
    }

    System.out.println();
  }

  private void printStringBoardCustom(QuadConsumerString<String[]> print) {
    for (int y = 0; y < board.length; y++) {
      String[][] row = board[y];

      for (int x = 0; x < row.length; x++) {
        String[] cell = row[x];

        System.out.print("[ " + print.accept(x, y, y, cell) + " ]");
      }

      System.out.println();
    }

    System.out.println();
  }
}
