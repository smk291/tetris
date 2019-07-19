package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;
import java.util.stream.IntStream;

/**
 * **
 *
 * <p>These are basically unit tests for various behaviors
 */
class InputTests {
  private TetrisPiece falling;
  private PlayField playField;

  InputTests(PlayField playField, TetrisPiece falling) {
    this.playField = playField;
    this.falling = falling;
  }

  void accept(String command) {
    switch (command) {
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
      case "6":
      case "7":
        falling.setFromTetromino(TetrominoEnum.values()[Integer.parseInt(command) - 1].get());
        falling.setCenter(new Point(4, 3));

        break;
      case "itest": // tetris-deletion test
        playField.createEmpty();
        falling.setFromTetromino(TetrominoEnum.I.get());

        for (int y = PlayField.getHeight() - 4; y < PlayField.getHeight(); y++)
          for (int x = 0, l = PlayField.getWidth(); x < l; x++)
            if (x != 4) playField.fillCell(x, y);

        break;
      case "floattest": // sink test
        playField.createEmpty();

        for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
          if (x > 4 && x < 8) playField.fillCell(x, 20);
          if (x == 6) playField.fillCell(x, 21);
          if (x != 2) playField.fillCell(x, 22);
        }

        break;
      case "q": // multiple sinking pieces, test that each behaves correctly
        playField.createEmpty();

        for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
          if (x != 2) playField.fillCell(x, 15);

          for (int y = 16; y < PlayField.getHeight(); y++) if (x == 2) playField.fillCell(x, y);

          if (x == 8 || x == 9) playField.fillCell(x, 16);

          if (x == 8) playField.fillCell(x, 17);

          if (x == 0 || x == 4 || x == 6) {
            playField.fillCell(x, 14);
            playField.fillCell(x, 13);
            playField.fillCell(x, 23);
          }

          if (x == 4) playField.fillCell(x, 21);
          if (x == 3 || x == 4 || x == 6) playField.fillCell(x, 22);
        }

        falling.setFromTetromino(TetrominoEnum.values()[0].get());
        falling.setCenter(1, 9);

        break;
      case "bounds": // test kick
        playField.createEmpty();

        for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
          if (x != 0 && !(x > 2 && x < 8)) playField.fillCell(new Point(x, 18));

          if (x != 5) {
            playField.fillCell(new Point(x, 19));
            playField.fillCell(new Point(x, 20));
            playField.fillCell(new Point(x, 21));
          }

          if (x != 0 && x != 5) {
            playField.fillCell(new Point(x, 22));
            playField.fillCell(new Point(x, 23));
          }
        }

        falling.setCenter(4, 9);

        break;
      case "w": // test row deletion
        playField.createEmpty();

        for (int i = 0; i < PlayField.getWidth(); i++) {
          if (i != 5) playField.fillCell(i, 23);
        }

        break;
      case "e": // test row delete after sink -- on bottom row to test bounds checker
        playField.createEmpty();

        for (int i = 0; i < PlayField.getWidth(); i++) {
          if (i == 4) playField.fillCell(i, 21);
          if (i > 2) playField.fillCell(i, 22);
          if (i > 0 && i < 4 || i > 4) playField.fillCell(i, 23);
        }

        falling.setFromTetromino(TetrominoEnum.J.get());
        falling.setRotation(0);
        falling.setCenter(1, 19);

        break;
      case "r": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
                // row and row deletion creates
        // another sinking piece
        playField.createEmpty();

        for (int x = 0; x < PlayField.getWidth(); x++) {
          if (x == 4) playField.fillCell(x, 15);
          if (x > 2) playField.fillCell(x, 16);
          if (x > 0 && x < 4 || x > 4) playField.fillCell(x, 17);
          if (x > 7) playField.fillCell(x, 18);

          if (x == 2 || x == 0 || x == 4 || x == 6)
            for (int y = 18; y < 24; y++) playField.fillCell(x, y);

          if (x < 8) {
            playField.fillCell(x, 19);
            playField.fillCell(x, 21);
          }

          if (x >= 8) {
            playField.fillCell(x, 20);
            playField.fillCell(x, 22);
          }
        }

        falling.setFromTetromino(TetrominoEnum.J.get());
        falling.setRotation(0);
        falling.setCenter(1, 12);

        break;
      case "a":
        // Test t-spin
        playField.createEmpty();
        falling.setFromTetromino(TetrominoEnum.T.get());
        falling.setCenter(5, 17);

        for (int x = 0; x < PlayField.getWidth(); x++) {
          if (x < 4 || x > 5) {
            final int finalX = x;

            IntStream.range(19, 21).forEach(y -> playField.fillCell(finalX, y));
          }

          if (x < 3 || x > 5) {
            playField.fillCell(x, 21);
          }

          playField.fillCell(x, 22);
          playField.fillCell(x, 23);
        }

        playField.emptyCell(new Point(4, 22));

        break;
    }
  }
}
