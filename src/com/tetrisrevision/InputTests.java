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
    rowList.addBlock(Constants.topRow - y, block);
  }

  private static int lastRow() {
    return Constants.height - 1;
  }

  static void accept(String e, TetrisPiece piece, RowList rowList) {
    switch (e) {
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
      case "6":
      case "7":
        piece.setTetromino(TetrominoEnum.values()[Integer.parseInt(e) - 1].get());
        piece.setCenter(new Point(4, 3)); // 3

        break;
      case "a": // tetris-deletion test
        rowList.clear();
        piece.setTetromino(TetrominoEnum.I.get());

        for (int y = Constants.height - 4; y < Constants.height; y++)
          for (int x = 0, l = Constants.width; x < l; x++) if (x != 4) createCell(x, y, rowList);

        break;
      case "s": // sink test
        rowList.clear();

        for (int x = 0, boardWidth = Constants.width; x < boardWidth; x++) {
          if (x > 4 && x < 8) createCell(x, lastRow() - 2, rowList);
          if (x == 6) createCell(x, lastRow() - 1, rowList);
          if (x != 2) createCell(x, lastRow(), rowList);
        }

        break;
      case "d": // multiple sinking pieces, test that each behaves correctly
        rowList.clear();

        for (int x = 0, boardWidth = Constants.width; x < boardWidth; x++) {
          if (x != 2) createCell(x, lastRow() - 8, rowList);

          for (int y = lastRow() - 7; y < Constants.height; y++)
            if (x == 2) createCell(x, y, rowList);

          if (x == 8 || x == 9) createCell(x, lastRow() - 7, rowList);

          if (x == 8) createCell(x, lastRow() - 6, rowList);

          if (x == 0 || x == 4 || x == 6) {
            createCell(x, lastRow() - 9, rowList);
            createCell(x, lastRow() - 10, rowList);
            createCell(x, lastRow(), rowList);
          }

          if (x == 4) createCell(x, lastRow() - 2, rowList);
          if (x == 3 || x == 4 || x == 6) createCell(x, lastRow() - 1, rowList);
        }

        piece.setTetromino(TetrominoEnum.values()[0].get());
        piece.setCenter(1, lastRow() - 14);

        break;
      case "f": // test kick
        rowList.clear();

        for (int x = 0, boardWidth = Constants.width; x < boardWidth; x++) {
          if (x != 0 && !(x > 2 && x < 8)) createCell(x, lastRow() - 5, rowList);

          if (x != 5) {
            createCell(x, lastRow() - 4, rowList);
            createCell(x, lastRow() - 3, rowList);
            createCell(x, lastRow() - 2, rowList);
          }

          if (x != 0 && x != 5) {
            createCell(x, lastRow() - 1, rowList);
            createCell(x, lastRow(), rowList);
          }
        }

        piece.setCenter(4, lastRow() - 14);

        break;
      case "z": // test row deletion
        rowList.clear();

        for (int i = 0; i < Constants.width; i++) {
          if (i != 5) createCell(i, lastRow(), rowList);
        }

        break;
      case "x": // test row delete after sink -- on bottom row to test bounds checker
        rowList.clear();

        for (int i = 0; i < Constants.width; i++) {
          if (i == 4) createCell(i, lastRow() - 2, rowList);
          if (i > 2) createCell(i, lastRow() - 1, rowList);
          if (i > 0 && i < 4 || i > 4) createCell(i, lastRow(), rowList);
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.resetRotation();
        piece.setCenter(1, lastRow() - 4);

        break;
      case "c": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
        // row and row deletion creates
        // another sinking piece
        rowList.clear();

        for (int x = 0; x < Constants.width; x++) {
          if (x == 4) createCell(x, lastRow() - 8, rowList);
          if (x > 2) createCell(x, lastRow() - 7, rowList);
          if (x > 0 && x < 4 || x > 4) createCell(x, lastRow() - 6, rowList);
          if (x > 7) createCell(x, lastRow() - 5, rowList);

          if (x == 2 || x == 0 || x == 4 || x == 6)
            for (int y = lastRow() - 5; y < lastRow() + 1; y++) createCell(x, y, rowList);

          if (x < 8) {
            createCell(x, lastRow() - 4, rowList);
            createCell(x, lastRow() - 2, rowList);
          }

          if (x >= 8) {
            createCell(x, lastRow() - 3, rowList);
            createCell(x, lastRow() - 1, rowList);
          }
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.resetRotation();
        piece.setCenter(1, lastRow() - 11);

        break;
      case "v":
        // Test t-spin
        rowList.clear();
        piece.setTetromino(TetrominoEnum.T.get());
        piece.setCenter(5, lastRow() - 10);

        for (int x = 0; x < Constants.width; x++) {
          if (x < 4 || x > 5) {
            final int finalX = x;

            IntStream.range(lastRow() - 4, lastRow() - 2)
                .forEach(y -> createCell(finalX, y, rowList));
          }

          if (x < 3 || x > 5) {
            createCell(x, lastRow() - 2, rowList);
          }

          if (x != 4) createCell(x, lastRow() - 1, rowList);

          createCell(x, lastRow(), rowList);
        }

        break;
    }
  }
}
