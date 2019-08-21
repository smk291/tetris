package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;
import java.util.stream.IntStream;

/**
 * **
 *
 * <p>These are basically unit tests for various behaviors
 */
abstract class InputTests {
  private static void createCell(double x, double y, RowList rowList) {
    Block block = new Block(x, Color.LIGHT_GRAY);
    rowList.addBlock(y, block);
  }

  private static int lastRow() {
    return RowList.getHeight() - 1;
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

        for (int y = RowList.getHeight() - 4; y < RowList.getHeight(); y++)
          for (int x = 0, l = RowList.getWidth(); x < l; x++)
            if (x != 4) createCell(x, y, rowList);

        break;
      case "s": // sink test
        rowList.clear();

        for (int x = 0, boardWidth = RowList.getWidth(); x < boardWidth; x++) {
          if (x > 4 && x < 8) createCell(x, lastRow() - 2, rowList);
          if (x == 6) createCell(x, lastRow() - 1, rowList);
          if (x != 2) createCell(x, lastRow(), rowList);
        }

        break;
      case "d": // multiple sinking pieces, test that each behaves correctly
        rowList.clear();

        for (int x = 0, boardWidth = RowList.getWidth(); x < boardWidth; x++) {
          if (x != 2) createCell(x, lastRow() - 8, rowList); // 15

          for (int y = lastRow() - 7; y < RowList.getHeight(); y++) if (x == 2) createCell(x, y, rowList); // 16

          if (x == 8 || x == 9) createCell(x, lastRow() - 7, rowList); // 16

          if (x == 8) createCell(x, lastRow() - 6, rowList); // 17

          if (x == 0 || x == 4 || x == 6) {
            createCell(x, lastRow() - 9, rowList); // 14
            createCell(x, lastRow() - 10, rowList); // 13
            createCell(x, lastRow(), rowList); // 23
          }

          if (x == 4) createCell(x, lastRow() - 2, rowList); // 21
          if (x == 3 || x == 4 || x == 6) createCell(x, lastRow() - 1, rowList); // 22
        }

        piece.setTetromino(TetrominoEnum.values()[0].get());
        piece.setCenter(1, lastRow() - 14); // 9

        break;
      case "f": // test kick
        rowList.clear();

        for (int x = 0, boardWidth = RowList.getWidth(); x < boardWidth; x++) {
          if (x != 0 && !(x > 2 && x < 8)) createCell(x, lastRow() - 5, rowList); // 18

          if (x != 5) {
            createCell(x, lastRow() - 4, rowList); // 19
            createCell(x, lastRow() - 3, rowList); // 20
            createCell(x, lastRow() - 2, rowList); // 21
          }

          if (x != 0 && x != 5) {
            createCell(x, lastRow() - 1, rowList); // 22
            createCell(x, lastRow(), rowList); // 23
          }
        }

        piece.setCenter(4, lastRow() - 14); // 9

        break;
      case "z": // test row deletion
        rowList.clear();

        for (int i = 0; i < RowList.getWidth(); i++) {
          if (i != 5) createCell(i, lastRow(), rowList); // 23
        }

        break;
      case "x": // test row delete after sink -- on bottom row to test bounds checker
        rowList.clear();

        for (int i = 0; i < RowList.getWidth(); i++) {
          if (i == 4) createCell(i, lastRow() - 2, rowList); // 21
          if (i > 2) createCell(i, lastRow() -1 , rowList);
          if (i > 0 && i < 4 || i > 4) createCell(i, lastRow(), rowList);
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, lastRow() - 4); // 19

        break;
      case "c": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
        // row and row deletion creates
        // another sinking piece
        rowList.clear();

        for (int x = 0; x < RowList.getWidth(); x++) {
          if (x == 4) createCell(x, lastRow() - 8, rowList); // 15
          if (x > 2) createCell(x, lastRow() - 7, rowList); // 16
          if (x > 0 && x < 4 || x > 4) createCell(x, lastRow() - 6, rowList); // 17
          if (x > 7) createCell(x, lastRow() - 5, rowList); // 18

          if (x == 2 || x == 0 || x == 4 || x == 6)
            for (int y = lastRow() - 5; y < lastRow() + 1; y++) createCell(x, y, rowList); // 18, 24

          if (x < 8) {
            createCell(x, lastRow() - 4, rowList); // 19
            createCell(x, lastRow() - 2, rowList); // 21
          }

          if (x >= 8) {
            createCell(x, lastRow() - 3, rowList); // 20
            createCell(x, lastRow() - 1, rowList); // 22
          }
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, lastRow() - 11); // 12

        break;
      case "v":
        // Test t-spin
        rowList.clear();
        piece.setTetromino(TetrominoEnum.T.get());
        piece.setCenter(5, lastRow() - 10); // 17

        for (int x = 0; x < RowList.getWidth(); x++) {
          if (x < 4 || x > 5) {
            final int finalX = x;

            IntStream.range(lastRow() - 4, lastRow() - 2).forEach(y -> createCell(finalX, y, rowList)); // 19, 21
          }

          if (x < 3 || x > 5) {
            createCell(x, lastRow() - 2, rowList); // 21
          }

          if (x != 4) createCell(x, lastRow() - 1, rowList); // 22

          createCell(x, lastRow(), rowList); // 23
        }

        break;
    }
  }
}
