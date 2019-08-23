package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;
import java.util.stream.IntStream;

/**
 * These are basically unit tests for various behaviors
 */
abstract class InputTests {
  private static void createCell(double x, double y, RowList rowList) {
    Block block = new Block(x, Color.LIGHT_GRAY);
    rowList.addBlock(y, block);
  }

  private static void createCellAdjust(double x, double y, RowList rowList) {
    createCell(x, 19 - y, rowList);
  }

  private static int lastRow() {
    return Constants.topRow - 1;
  }

  static void accept(String e, TetrisPiece piece, RowList board) {
    switch (e) {
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
      case "6":
      case "7":
        piece.setTetromino(TetrominoEnum.values()[Integer.parseInt(e) - 1].get());
        piece.setCenter(new Point(Constants.fromLeft(4), Constants.fromBottom(13)));

        break;
      case "a": // tetris-deletion test
        board.clear();
        piece.setTetromino(TetrominoEnum.I.get());

        for (int y = Constants.bottomRow; y != Constants.fromBottom(4); y += Constants.up) {
          for (int x = Constants.leftBound, l = Constants.rightBound + Constants.right; x != l; x += Constants.right) {
            if (x != Constants.fromLeft(4)) {
              createCell(x, y, board);
            }
          }
        }
        break;
      case "A": // tetris-deletion test
        board.clear();
        piece.setTetromino(TetrominoEnum.I.get());

        for (int y = Constants.bottomRow; y != Constants.fromBottom(4); y += Constants.up) {
          for (int x = Constants.leftBound, l = Constants.rightBound + Constants.right; x != l; x += Constants.right){
            if (x != Constants.fromLeft(4)) {
              createCell(x, y, board);
            }
          }
        }

        board.getRowByY((double) Constants.fromBottom(2)).ifPresent(r -> r.get().remove(Constants.leftBound));

        break;
      case "s": // sink test
        board.clear();
        piece.setCenter(Constants.fromLeft(6), Constants.fromBottom(15));

        for (int x = Constants.leftBound; x != Constants.rightBound + Constants.right; x += Constants.right) {
          if (x > Constants.fromLeft(4) && x < Constants.rightBound + Constants.left) {
            createCell(x, Constants.fromBottom(2), board);
          }

          if (x == Constants.fromLeft(6)) {
            createCell(x, Constants.fromBottom(1), board);
          }

          if (x != Constants.fromLeft(2)) {
            createCell(x, Constants.bottomRow, board);
          }
        }

        break;
      case "S":
        board.clear();

        piece.setTetromino(TetrominoEnum.S.get());
        piece.setRotation(3);
        piece.setCenter(6, Constants.bottomRow + 15);

        for (int x = Constants.leftBound; x != Constants.rightBound + Constants.right; x += Constants.right) {
          if (x != Constants.fromLeft(6)) {
            createCell(x, Constants.bottomRow, board);
            createCell(x, Constants.fromBottom(1), board);
            createCell(x, Constants.fromBottom(2), board);

            if (x != Constants.fromLeft(5)) {
              createCell(x, Constants.fromBottom(3), board);
            }
          }
        }

        break;
      case "d": // multiple sinking pieces, test that each behaves correctly
        board.clear();

        for (int x = Constants.leftBound, boardWidth = Constants.rightBound + Constants.right; x != boardWidth; x += Constants.right) {
          if (x != Constants.fromLeft(3)) {
            createCell(x, Constants.fromBottom(9), board);
          }

          if (x == Constants.fromLeft(3)) {
            for (int y = Constants.fromBottom(8); y != Constants.bottomRow + Constants.down; y += Constants.down) {
              createCell(x, y, board);
            }
          }

          if (x == 8 || x == 9) {
            createCellAdjust(x, lastRow() - 7, board);
          }

          if (x == 8) {
            createCellAdjust(x, lastRow() - 6, board);
          }

          if (x == 0 || x == 4 || x == 6) {
            createCellAdjust(x, lastRow() - 9, board);
            createCellAdjust(x, lastRow() - 10, board);
            createCellAdjust(x, lastRow(), board);
            createCellAdjust(x, 19, board);
          }

          if (x == 4) {
            createCellAdjust(x, lastRow() - 2, board);
          }

          if (x == 3 || x == 4 || x == 6) {
            createCellAdjust(x, lastRow() - 1, board);
          }
        }

        piece.setTetromino(TetrominoEnum.values()[0].get());
        piece.setCenter(1, 14);

        break;
      case "f": // test kick
        board.clear();

        for (int x = 0, boardWidth = Constants.width; x < boardWidth; x++) {
          if (x != 0 && !(x > 2 && x < 8)) createCell(x, lastRow() - 5, board);

          if (x != 5) {
            createCell(x, lastRow() - 4, board);
            createCell(x, lastRow() - 3, board);
            createCell(x, lastRow() - 2, board);
          }

          if (x != 0 && x != 5) {
            createCell(x, lastRow() - 1, board);
            createCell(x, lastRow(), board);
          }
        }

        piece.setCenter(4, lastRow() - 14);

        break;
      case "z": // test row deletion
        board.clear();

        for (int i = 0; i < Constants.width; i++) {
          if (i != 5) createCell(i, lastRow(), board);
        }

        break;
      case "x": // test row delete after sink -- on bottom row to test bounds checker
        board.clear();

        for (int i = 0; i < Constants.width; i++) {
          if (i == 4) createCell(i, lastRow() - 2, board);
          if (i > 2) createCell(i, lastRow() - 1, board);
          if (i > 0 && i < 4 || i > 4) createCell(i, lastRow(), board);
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.resetRotation();
        piece.setCenter(1, lastRow() - 4);

        break;
      case "c": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
        // row and row deletion creates
        // another sinking piece
        board.clear();

        for (int x = 0; x < Constants.width; x++) {
          if (x == 4) createCell(x, lastRow() - 8, board);
          if (x > 2) createCell(x, lastRow() - 7, board);
          if (x > 0 && x < 4 || x > 4) createCell(x, lastRow() - 6, board);
          if (x > 7) createCell(x, lastRow() - 5, board);

          if (x == 2 || x == 0 || x == 4 || x == 6)
            for (int y = lastRow() - 5; y < lastRow() + 1; y++) createCell(x, y, board);

          if (x < 8) {
            createCell(x, lastRow() - 4, board);
            createCell(x, lastRow() - 2, board);
          }

          if (x >= 8) {
            createCell(x, lastRow() - 3, board);
            createCell(x, lastRow() - 1, board);
          }
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.resetRotation();
        piece.setCenter(1, lastRow() - 11);

        break;
      case "v":
        // Test t-spin
        board.clear();
        piece.setTetromino(TetrominoEnum.T.get());
        piece.setCenter(5, lastRow() - 10);

        for (int x = 0; x < Constants.width; x++) {
          if (x < 4 || x > 5) {
            final int finalX = x;

            IntStream.range(lastRow() - 4, lastRow() - 2)
                .forEach(y -> createCell(finalX, y, board));
          }

          if (x < 3 || x > 5) {
            createCell(x, lastRow() - 2, board);
          }

          if (x != 4) createCell(x, lastRow() - 1, board);

          createCell(x, lastRow(), board);
        }

        break;
    }
  }
}
