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
  private static void createCell(double x, double y, PlayField playField) {
    Block block = new Block(x, y);
    block.setColor(Color.LIGHT_GRAY);
    playField.copy(block);
  }

  private static int lastRow() {
    return PlayField.getHeight() - 1;
  }

  static void accept(String e, TetrisPiece piece, PlayField playField) {
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
        playField.empty();
        piece.setTetromino(TetrominoEnum.I.get());

        for (int y = PlayField.getHeight() - 4; y < PlayField.getHeight(); y++)
          for (int x = 0, l = PlayField.getWidth(); x < l; x++)
            if (x != 4) createCell(x, y, playField);

        break;
      case "s": // sink test
        playField.empty();

        for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
          if (x > 4 && x < 8) createCell(x, lastRow() - 2, playField);
          if (x == 6) createCell(x, lastRow() - 1, playField);
          if (x != 2) createCell(x, lastRow(), playField);
        }

        break;
      case "d": // multiple sinking pieces, test that each behaves correctly
        playField.empty();

        for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
          if (x != 2) createCell(x, lastRow() - 8, playField); // 15

          for (int y = lastRow() - 7; y < PlayField.getHeight(); y++) if (x == 2) createCell(x, y, playField); // 16

          if (x == 8 || x == 9) createCell(x, lastRow() - 7, playField); // 16

          if (x == 8) createCell(x, lastRow() - 6, playField); // 17

          if (x == 0 || x == 4 || x == 6) {
            createCell(x, lastRow() - 9, playField); // 14
            createCell(x, lastRow() - 10, playField); // 13
            createCell(x, lastRow(), playField); // 23
          }

          if (x == 4) createCell(x, lastRow() - 2, playField); // 21
          if (x == 3 || x == 4 || x == 6) createCell(x, lastRow() - 1, playField); // 22
        }

        piece.setTetromino(TetrominoEnum.values()[0].get());
        piece.setCenter(1, lastRow() - 14); // 9

        break;
      case "f": // test kick
        playField.empty();

        for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
          if (x != 0 && !(x > 2 && x < 8)) createCell(x, lastRow() - 5, playField); // 18

          if (x != 5) {
            createCell(x, lastRow() - 4, playField); // 19
            createCell(x, lastRow() - 3, playField); // 20
            createCell(x, lastRow() - 2, playField); // 21
          }

          if (x != 0 && x != 5) {
            createCell(x, lastRow() - 1, playField); // 22
            createCell(x, lastRow(), playField); // 23
          }
        }

        piece.setCenter(4, lastRow() - 14); // 9

        break;
      case "z": // test row deletion
        playField.empty();

        for (int i = 0; i < PlayField.getWidth(); i++) {
          if (i != 5) createCell(i, lastRow(), playField); // 23
        }

        break;
      case "x": // test row delete after sink -- on bottom row to test bounds checker
        playField.empty();

        for (int i = 0; i < PlayField.getWidth(); i++) {
          if (i == 4) createCell(i, lastRow() - 2, playField); // 21
          if (i > 2) createCell(i, lastRow() -1 , playField);
          if (i > 0 && i < 4 || i > 4) createCell(i, lastRow(), playField);
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, lastRow() - 4); // 19

        break;
      case "c": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
        // row and row deletion creates
        // another sinking piece
        playField.empty();

        for (int x = 0; x < PlayField.getWidth(); x++) {
          if (x == 4) createCell(x, lastRow() - 8, playField); // 15
          if (x > 2) createCell(x, lastRow() - 7, playField); // 16
          if (x > 0 && x < 4 || x > 4) createCell(x, lastRow() - 6, playField); // 17
          if (x > 7) createCell(x, lastRow() - 5, playField); // 18

          if (x == 2 || x == 0 || x == 4 || x == 6)
            for (int y = lastRow() - 5; y < lastRow() + 1; y++) createCell(x, y, playField); // 18, 24

          if (x < 8) {
            createCell(x, lastRow() - 4, playField); // 19
            createCell(x, lastRow() - 2, playField); // 21
          }

          if (x >= 8) {
            createCell(x, lastRow() - 3, playField); // 20
            createCell(x, lastRow() - 1, playField); // 22
          }
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, lastRow() - 11); // 12

        break;
      case "v":
        // Test t-spin
        playField.empty();
        piece.setTetromino(TetrominoEnum.T.get());
        piece.setCenter(5, lastRow() - 10); // 17

        for (int x = 0; x < PlayField.getWidth(); x++) {
          if (x < 4 || x > 5) {
            final int finalX = x;

            IntStream.range(lastRow() - 4, lastRow() - 2).forEach(y -> createCell(finalX, y, playField)); // 19, 21
          }

          if (x < 3 || x > 5) {
            createCell(x, lastRow() - 2, playField); // 21
          }

          if (x != 4) createCell(x, lastRow() - 1, playField); // 22

          createCell(x, lastRow(), playField); // 23
        }

        break;
    }
  }
}
