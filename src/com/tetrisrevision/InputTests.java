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
  private static void createCell(int x, int y, Blocks2d blocks2d) {
    Cell cell = new Cell(x, y, Color.blue);
    cell.setEmpty(true);
    blocks2d.setCell(cell);
  }

  static void accept(String command, TetrisPiece piece, Blocks2d blocks2d) {
    switch (command) {
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
      case "6":
      case "7":
        piece.setTetromino(TetrominoEnum.values()[Integer.parseInt(command) - 1].get());
        piece.setCenter(new Point(4, 3));

        break;
      case "itest": // tetris-deletion test
        blocks2d.createEmpty();
        piece.setTetromino(TetrominoEnum.I.get());

        for (int y = Blocks2d.getHeight() - 4; y < Blocks2d.getHeight(); y++)
          for (int x = 0, l = Blocks2d.getWidth(); x < l; x++)
            if (x != 4) createCell(x, y, blocks2d);

        break;
      case "floattest": // sink test
        blocks2d.createEmpty();

        for (int x = 0, boardWidth = Blocks2d.getWidth(); x < boardWidth; x++) {
          if (x > 4 && x < 8) createCell(x, 20, blocks2d);
          if (x == 6) createCell(x, 21, blocks2d);
          if (x != 2) createCell(x, 22, blocks2d);
        }

        break;
      case "q": // multiple sinking pieces, test that each behaves correctly
        blocks2d.createEmpty();

        for (int x = 0, boardWidth = Blocks2d.getWidth(); x < boardWidth; x++) {
          if (x != 2) createCell(x, 15, blocks2d);

          for (int y = 16; y < Blocks2d.getHeight(); y++) if (x == 2) createCell(x, y, blocks2d);

          if (x == 8 || x == 9) createCell(x, 16, blocks2d);

          if (x == 8) createCell(x, 17, blocks2d);

          if (x == 0 || x == 4 || x == 6) {
            createCell(x, 14, blocks2d);
            createCell(x, 13, blocks2d);
            createCell(x, 23, blocks2d);
          }

          if (x == 4) createCell(x, 21, blocks2d);
          if (x == 3 || x == 4 || x == 6) createCell(x, 22, blocks2d);
        }

        piece.setTetromino(TetrominoEnum.values()[0].get());
        piece.setCenter(1, 9);

        break;
      case "bounds": // test kick
        blocks2d.createEmpty();

        for (int x = 0, boardWidth = Blocks2d.getWidth(); x < boardWidth; x++) {
          if (x != 0 && !(x > 2 && x < 8)) createCell(x, 18, blocks2d);

          if (x != 5) {
            createCell(x, 19, blocks2d);
            createCell(x, 20, blocks2d);
            createCell(x, 21, blocks2d);
          }

          if (x != 0 && x != 5) {
            createCell(x, 22, blocks2d);
            createCell(x, 23, blocks2d);
          }
        }

        piece.setCenter(4, 9);

        break;
      case "w": // test row deletion
        blocks2d.createEmpty();

        for (int i = 0; i < Blocks2d.getWidth(); i++) {
          if (i != 5) createCell(i, 23, blocks2d);
        }

        break;
      case "e": // test row delete after sink -- on bottom row to test bounds checker
        blocks2d.createEmpty();

        for (int i = 0; i < Blocks2d.getWidth(); i++) {
          if (i == 4) createCell(i, 21, blocks2d);
          if (i > 2) createCell(i, 22, blocks2d);
          if (i > 0 && i < 4 || i > 4) createCell(i, 23, blocks2d);
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, 19);

        break;
      case "r": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
        // row and row deletion creates
        // another sinking piece
        blocks2d.createEmpty();

        for (int x = 0; x < Blocks2d.getWidth(); x++) {
          if (x == 4) createCell(x, 15, blocks2d);
          if (x > 2) createCell(x, 16, blocks2d);
          if (x > 0 && x < 4 || x > 4) createCell(x, 17, blocks2d);
          if (x > 7) createCell(x, 18, blocks2d);

          if (x == 2 || x == 0 || x == 4 || x == 6)
            for (int y = 18; y < 24; y++) createCell(x, y, blocks2d);

          if (x < 8) {
            createCell(x, 19, blocks2d);
            createCell(x, 21, blocks2d);
          }

          if (x >= 8) {
            createCell(x, 20, blocks2d);
            createCell(x, 22, blocks2d);
          }
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, 12);

        break;
      case "a":
        // Test t-spin
        blocks2d.createEmpty();
        piece.setTetromino(TetrominoEnum.T.get());
        piece.setCenter(5, 17);

        for (int x = 0; x < Blocks2d.getWidth(); x++) {
          if (x < 4 || x > 5) {
            final int finalX = x;

            IntStream.range(19, 21).forEach(y -> createCell(finalX, y, blocks2d));
          }

          if (x < 3 || x > 5) {
            createCell(x, 21, blocks2d);
          }

          if (x != 4)
            createCell(x, 22, blocks2d);

          createCell(x, 23, blocks2d);
        }

        break;
    }
  }
}
