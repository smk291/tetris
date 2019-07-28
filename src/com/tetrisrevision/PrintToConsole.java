package com.tetrisrevision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

interface TriConsumer<T> {
  void accept(Integer x, Integer y, T t);
}

interface TriConsumerString<T> {
  String accept(Integer x, Integer y, T t);
}

class PrintToConsole {
  private static int height;
  private static int width;
  private String[][][] board;

  PrintToConsole(int height, int width) {
    PrintToConsole.height = height;
    PrintToConsole.width = width;
  }

  private String pad(int i) {
    if (i > 9) {
      return Integer.toString(i);
    }

    return " " + i;
  }

  private String printCoords(int x, int y) {
    return pad(x) + ", " + pad(y);
  }

  private String printCoords(Block c) {
    return pad((int) c.getX()) + ", " + pad((int) c.getY());
  }

  private void paintAndPrint(Blocks2d blocks2d, SinkingPieces sinkingPieces, TetrisPiece piece) {
    printBoardCustom(
        blocks2d,
        (Integer x, Integer y, Block block) -> {
          if ((int) block.getX() != x || (int) block.getY() != y) {
            System.out.print("(" + printCoords(x, y) + ") -> (" + printCoords(block) + ") :: ");
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

    Block block1 = blocks2d.getBlocksByRow()[22].get(0);
    Block block2 = blocks2d.getBlocksByRow()[22].get(4);

    if (block1 != null)
      System.out.println(
          "22, 0"
              + block1.printCell()
              + " "
              + (block1.getColor() != null ? block1.getColor().toString() : "NC"));
    if (block2 != null)
      System.out.println(
          "22, 4"
              + block2.printCell()
              + " "
              + (block2.getColor() != null ? block2.getColor().toString() : "NC"));
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
      ArrayList<Block> fieldRow = blocks2d.getBlocksByRow()[y];

      for (int x = 0; x < row.length; x++) {
        String[] space = row[x];
        Optional<Block> cell = blocks2d.getCell(x, y);

        if (cell.isPresent()) {
          space[0] = "x";

          if (cell.get().getColor() != null) {
            space[1] = "c";
          }

          if (sinkingPiecesContainCell(x, y, sinkingPieces)) {
            space[2] = "s";
          }

          if ((int) cell.get().getX() != x || (int) cell.get().getY() != y) {
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

  private boolean sinkingPiecesContainCell(int x, int y, SinkingPieces sinkingPieces) {
    for (ArrayList<Block> piece : sinkingPieces.getPieces()) {
      for (Block block : piece) {
        if ((int) block.getX() == x && (int) block.getY() == y) return true;
      }
    }

    return false;
  }

  private boolean sinkingPiecesContainCell(Block c, SinkingPieces sinkingPieces) {
    for (ArrayList<Block> piece : sinkingPieces.getPieces()) {
      for (Block block : piece) {
        if (block.getX() == c.getX() && block.getY() == c.getY()) return true;
      }
    }

    return false;
  }

  private boolean fallingPieceContainsCell(int x, int y, TetrisPiece currentPiece2d) {
    return Arrays.stream(currentPiece2d.getCells())
        .anyMatch(c -> (int) c.getX() == x && (int) c.getY() == y);
  }

  private <T> void printBoardCustom(Blocks2d blocks2d, TriConsumer<Block> print) {
    for (int y = 0; y < blocks2d.getBlocksByRow().length; y++) {
      ArrayList<Block> row = blocks2d.getBlocksByRow()[y];

      for (int x = 0; x < row.size(); x++) {
        print.accept(x, y, row.get(x));

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
