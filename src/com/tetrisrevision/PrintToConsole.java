package com.tetrisrevision;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.IntStream;

interface QuadConsumer<T> {
  void accept(Double x, Double y, Double rowY, T t);
}

interface QuadConsumerString<T> {
  String accept(Double x, Double y, Double rowY,  T t);
}

class PrintToConsole {
  private static int height;
  private static int width;
  private String[][][] board;

  PrintToConsole(int height, int width) {
    PrintToConsole.height = height;
    PrintToConsole.width = width;
  }


  public String printCell(double y, Block cell) {
    return "{ " + cell.getX() + ", " + y + " }";
  }

  private String pad(double i) {
    if (i > 9) {
      return Double.toString(i);
    }

    return " " + i;
  }

  private String printCoords(double x, double y) {
    return pad(x) + ", " + pad(y);
  }

  private String printCoords(double y, Block c) {
    return pad((int) c.getX()) + ", " + pad((int) y);
  }

  private void paintAndPrint(RowList rowList, ArrayList<RowList> sinkingPieces, TetrisPiece piece) {
    printBoardCustom(
            rowList,
        (Double x, Double y, Double rowY, Optional<Block> block) -> {
          if (block.isEmpty())
            return;

          if (block.get().getX() != x || !rowY.equals(y)) {
            System.out.print("(" + printCoords(x, y) + ") -> (" + printCoords(block.get().getX(), rowY) + ") :: ");
          } else {
            System.out.print("(      ) -> (      ) :: ");
          }
        });

    getBoard(rowList, sinkingPieces, piece);

    printStringBoardCustom(
        (Double x, Double y, Double rowY, String[] cell) -> {
          String s = "";

          for (String element : cell) {
            s = s.concat(element);
          }

          return s;
        });

    printStringBoardCustom(
        (Double x, Double y, Double rowY, String[] cell) -> {
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
    String[][][] board = new String[height][width][5];

    for (String[][] row : board) {
      IntStream.range(0, row.length).forEach(x -> row[x] = new String[]{" ", " ", " ", " ", " "});
    }

    for (int y = 0; y < board.length; y++) {
      String[][] row = board[y];
      Optional<Row> fieldRow = rowList.getSingleRow(y);

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

          if ((int) cell.get().getX() != x) {
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

  private boolean sinkingPiecesContainCell(double x, double y, ArrayList<RowList> sinkingPieces) {
    for (RowList piece : sinkingPieces) {
      for (Row r : piece) {
        for (Block block : r) {
          if (block.getX() == x && r.getY() == y) return true;
        }
      }
    }

    return false;
  }

  private boolean sinkingPiecesContainCell(Block c, ArrayList<RowList> sinkingPieces) {
    for (RowList piece : sinkingPieces) {
      for (Row r : piece) {
        for (Block block : r) {
          if (block.getX() == c.getX()) return true;
        }
      }
    }

    return false;
  }

  private boolean fallingPieceContainsCell(double x, double y, TetrisPiece currentPiece2d) {
    for (Row r : currentPiece2d.getBlocks())
    {
      if (r.getY() == y)
      {
        for (Block b : r)
        {
          if (b.getX() == x)
            return true;
        }
      }
    }

    return false;
  }

  private <T> void printBoardCustom(RowList rowList, QuadConsumer<Optional<Block>> print) {
    for (int y = 0; y < rowList.size(); y++) {
      Optional<Row> row = rowList.getSingleRow(y);

      if (row.isEmpty())
        continue;

      for (Block b : row.get()) {
        print.accept(b.getX(), row.get().getY(), row.get().getY(), row.get().get(b.getX()));

        System.out.print(" ");
      }

      System.out.println();
    }

    System.out.println();
  }

  private void printStringBoardCustom(QuadConsumerString<String[]> print) {
    for (double y = 0; y < board.length; y++) {
      String[][] row = board[(int) y];

      for (double x = 0; x < row.length; x++) {
        String[] cell = row[(int) x];

        System.out.print("[ " + print.accept(x, y, y, cell) + " ]");
      }

      System.out.println();
    }

    System.out.println();
  }
}
