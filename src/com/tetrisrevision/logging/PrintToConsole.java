package com.tetrisrevision.logging;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;

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

  public String printCell(int y, Block cell) {
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

  private String printCoords(int y, Block c) {
    return pad(c.getX()) + ", " + pad(y);
  }

  private void paintAndPrint(RowList rowList, ArrayList<RowList> sinkingPieces, TetrisPiece piece) {
    printBoardCustom(
        rowList,
        (int x, int y, Integer rowY, Optional<Block> block) -> {
          if (block.isEmpty()) return;

          if (block.get().getX() != x || !rowY.equals(y)) {
            System.out.print(
                "("
                    + printCoords(x, y)
                    + ") -> ("
                    + printCoords(block.get().getX(), rowY)
                    + ") :: ");
          } else {
            System.out.print("(      ) -> (      ) :: ");
          }
        });

    getBoard(rowList, sinkingPieces, piece);

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

    //    Optional<Block> block1 = playField.getBlock(0, 22);
    //    Optional<Block> block2 = playField.getBlock(4,22);

    //    block1.ifPresent(block -> System.out.println(
    //            "22, 0"
    //                    + printCell(block)
    //                    + " "
    //                    + (block.getColor() != null ? block.getColor().toString() : "NC")));
    //    block2.ifPresent(block -> System.out.println(
    //            "22, 4"
    //                    + printCell(block)
    //                    + " "
    //                    + (block.getColor() != null ? block.getColor().toString() : "NC")));
  }

  private void getBoard(RowList rowList, ArrayList<RowList> sinkingPieces, TetrisPiece piece) {
    String[][][] board = new String[Constants.height][Constants.width][5];

    for (String[][] row : board) {
      IntStream.range(0, row.length).forEach(x -> row[x] = new String[] {" ", " ", " ", " ", " "});
    }

    for (int y = 0; y < board.length; y++) {
      String[][] row = board[y];

      for (int x = 0; x < row.length; x++) {
        String[] space = row[x];
        Optional<Block> cell = rowList.getBlock(x, y);

        if (cell.isPresent()) {
          space[0] = "x";

          if (cell.get().getColor() != null) {
            space[1] = "c";
          }

          if (sinkingPiecesContainCell(x, y, sinkingPieces)) {
            space[2] = "s";
          }

          if (cell.get().getX() != x) {
            space[3] = "!";
          }

          if (fallingPieceContainsCell(x, y, piece)) {
            space[4] = "f";
          }
        }
      }
    }

    this.board = board;
  }

  private boolean sinkingPiecesContainCell(int x, int y, ArrayList<RowList> sinkingPieces) {
    for (RowList piece : sinkingPieces) {
      for (Row r : piece.get()) {
        for (Block block : r.get()) {
          if (block.getX() == x && r.getY() == y) return true;
        }
      }
    }

    return false;
  }

  private boolean sinkingPiecesContainCell(Block c, ArrayList<RowList> sinkingPieces) {
    for (RowList piece : sinkingPieces) {
      for (Row r : piece.get()) {
        for (Block block : r.get()) {
          if (block.getX() == c.getX()) return true;
        }
      }
    }

    return false;
  }

  private boolean fallingPieceContainsCell(int x, int y, TetrisPiece currentPiece2d) {
    for (Row r : currentPiece2d.getBlocks().get()) {
      if (r.getY() == y) {
        for (Block b : r.get()) {
          if (b.getX() == x) return true;
        }
      }
    }

    return false;
  }

  private <T> void printBoardCustom(RowList rowList, QuadConsumer<Optional<Block>> print) {
    for (Row row : rowList.get()) {
      if (row == null || row.isEmpty()) continue;

      for (Block b : row.get()) {
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