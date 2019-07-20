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
  static void accept(String command, TetrisPiece piece, PlayField field) {
    switch (command) {
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
      case "6":
      case "7":
        piece.setFromTetromino(TetrominoEnum.values()[Integer.parseInt(command) - 1].get());
        piece.setCenter(new Point(4, 3));

        break;
      case "itest": // tetris-deletion test
        field.createEmpty();
        piece.setFromTetromino(TetrominoEnum.I.get());

        for (int y = PlayField.getHeight() - 4; y < PlayField.getHeight(); y++)
          for (int x = 0, l = PlayField.getWidth(); x < l; x++)
            if (x != 4) field.fillCell(x, y);

        break;
      case "floattest": // sink test
        field.createEmpty();

        for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
          if (x > 4 && x < 8) field.fillCell(x, 20);
          if (x == 6) field.fillCell(x, 21);
          if (x != 2) field.fillCell(x, 22);
        }

        break;
      case "q": // multiple sinking pieces, test that each behaves correctly
        field.createEmpty();

        for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
          if (x != 2) field.fillCell(x, 15);

          for (int y = 16; y < PlayField.getHeight(); y++) if (x == 2) field.fillCell(x, y);

          if (x == 8 || x == 9) field.fillCell(x, 16);

          if (x == 8) field.fillCell(x, 17);

          if (x == 0 || x == 4 || x == 6) {
            field.fillCell(x, 14);
            field.fillCell(x, 13);
            field.fillCell(x, 23);
          }

          if (x == 4) field.fillCell(x, 21);
          if (x == 3 || x == 4 || x == 6) field.fillCell(x, 22);
        }

        piece.setFromTetromino(TetrominoEnum.values()[0].get());
        piece.setCenter(1, 9);

        break;
      case "bounds": // test kick
        field.createEmpty();

        for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
          if (x != 0 && !(x > 2 && x < 8)) field.fillCell(new Point(x, 18));

          if (x != 5) {
            field.fillCell(new Point(x, 19));
            field.fillCell(new Point(x, 20));
            field.fillCell(new Point(x, 21));
          }

          if (x != 0 && x != 5) {
            field.fillCell(new Point(x, 22));
            field.fillCell(new Point(x, 23));
          }
        }

        piece.setCenter(4, 9);

        break;
      case "w": // test row deletion
        field.createEmpty();

        for (int i = 0; i < PlayField.getWidth(); i++) {
          if (i != 5) field.fillCell(i, 23);
        }

        break;
      case "e": // test row delete after sink -- on bottom row to test bounds checker
        field.createEmpty();

        for (int i = 0; i < PlayField.getWidth(); i++) {
          if (i == 4) field.fillCell(i, 21);
          if (i > 2) field.fillCell(i, 22);
          if (i > 0 && i < 4 || i > 4) field.fillCell(i, 23);
        }

        piece.setFromTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, 19);

        break;
      case "r": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
        // row and row deletion creates
        // another sinking piece
        field.createEmpty();

        for (int x = 0; x < PlayField.getWidth(); x++) {
          if (x == 4) field.fillCell(x, 15);
          if (x > 2) field.fillCell(x, 16);
          if (x > 0 && x < 4 || x > 4) field.fillCell(x, 17);
          if (x > 7) field.fillCell(x, 18);

          if (x == 2 || x == 0 || x == 4 || x == 6)
            for (int y = 18; y < 24; y++) field.fillCell(x, y);

          if (x < 8) {
            field.fillCell(x, 19);
            field.fillCell(x, 21);
          }

          if (x >= 8) {
            field.fillCell(x, 20);
            field.fillCell(x, 22);
          }
        }

        piece.setFromTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, 12);

        break;
      case "a":
        // Test t-spin
        field.createEmpty();
        piece.setFromTetromino(TetrominoEnum.T.get());
        piece.setCenter(5, 17);

        for (int x = 0; x < PlayField.getWidth(); x++) {
          if (x < 4 || x > 5) {
            final int finalX = x;

            IntStream.range(19, 21).forEach(y -> field.fillCell(finalX, y));
          }

          if (x < 3 || x > 5) {
            field.fillCell(x, 21);
          }

          field.fillCell(x, 22);
          field.fillCell(x, 23);
        }

        field.emptyCell(new Point(4, 22));

        break;
    }
  }
}
