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
  private static void createCell(double x, double y, Blocks2d blocks2d) {
    Block block = new Block(x, y);
    block.setColor(Color.LIGHT_GRAY);
    blocks2d.setCell(block);
  }

  private static int lastRow() {
    return Blocks2d.getHeight() - 1;
  }

  static void accept(String e, TetrisPiece piece, Blocks2d blocks2d) {
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
        blocks2d.createEmpty();
        piece.setTetromino(TetrominoEnum.I.get());

        for (int y = Blocks2d.getHeight() - 4; y < Blocks2d.getHeight(); y++)
          for (int x = 0, l = Blocks2d.getWidth(); x < l; x++)
            if (x != 4) createCell(x, y, blocks2d);

        break;
      case "s": // sink test
        blocks2d.createEmpty();

        for (int x = 0, boardWidth = Blocks2d.getWidth(); x < boardWidth; x++) {
          if (x > 4 && x < 8) createCell(x, lastRow() - 2, blocks2d);
          if (x == 6) createCell(x, lastRow() - 1, blocks2d);
          if (x != 2) createCell(x, lastRow(), blocks2d);
        }

        break;
      case "d": // multiple sinking pieces, test that each behaves correctly
        blocks2d.createEmpty();

        for (int x = 0, boardWidth = Blocks2d.getWidth(); x < boardWidth; x++) {
          if (x != 2) createCell(x, lastRow() - 8, blocks2d); // 15

          for (int y = lastRow() - 7; y < Blocks2d.getHeight(); y++) if (x == 2) createCell(x, y, blocks2d); // 16

          if (x == 8 || x == 9) createCell(x, lastRow() - 7, blocks2d); // 16

          if (x == 8) createCell(x, lastRow() - 6, blocks2d); // 17

          if (x == 0 || x == 4 || x == 6) {
            createCell(x, lastRow() - 9, blocks2d); // 14
            createCell(x, lastRow() - 10, blocks2d); // 13
            createCell(x, lastRow(), blocks2d); // 23
          }

          if (x == 4) createCell(x, lastRow() - 2, blocks2d); // 21
          if (x == 3 || x == 4 || x == 6) createCell(x, lastRow() - 1, blocks2d); // 22
        }

        piece.setTetromino(TetrominoEnum.values()[0].get());
        piece.setCenter(1, lastRow() - 14); // 9

        break;
      case "f": // test kick
        blocks2d.createEmpty();

        for (int x = 0, boardWidth = Blocks2d.getWidth(); x < boardWidth; x++) {
          if (x != 0 && !(x > 2 && x < 8)) createCell(x, lastRow() - 5, blocks2d); // 18

          if (x != 5) {
            createCell(x, lastRow() - 4, blocks2d); // 19
            createCell(x, lastRow() - 3, blocks2d); // 20
            createCell(x, lastRow() - 2, blocks2d); // 21
          }

          if (x != 0 && x != 5) {
            createCell(x, lastRow() - 1, blocks2d); // 22
            createCell(x, lastRow(), blocks2d); // 23
          }
        }

        piece.setCenter(4, lastRow() - 14); // 9

        break;
      case "z": // test row deletion
        blocks2d.createEmpty();

        for (int i = 0; i < Blocks2d.getWidth(); i++) {
          if (i != 5) createCell(i, lastRow(), blocks2d); // 23
        }

        break;
      case "x": // test row delete after sink -- on bottom row to test bounds checker
        blocks2d.createEmpty();

        for (int i = 0; i < Blocks2d.getWidth(); i++) {
          if (i == 4) createCell(i, lastRow() - 2, blocks2d); // 21
          if (i > 2) createCell(i, lastRow() -1 , blocks2d);
          if (i > 0 && i < 4 || i > 4) createCell(i, lastRow(), blocks2d);
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, lastRow() - 4); // 19

        break;
      case "c": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
        // row and row deletion creates
        // another sinking piece
        blocks2d.createEmpty();

        for (int x = 0; x < Blocks2d.getWidth(); x++) {
          if (x == 4) createCell(x, lastRow() - 8, blocks2d); // 15
          if (x > 2) createCell(x, lastRow() - 7, blocks2d); // 16
          if (x > 0 && x < 4 || x > 4) createCell(x, lastRow() - 6, blocks2d); // 17
          if (x > 7) createCell(x, lastRow() - 5, blocks2d); // 18

          if (x == 2 || x == 0 || x == 4 || x == 6)
            for (int y = lastRow() - 5; y < lastRow() + 1; y++) createCell(x, y, blocks2d); // 18, 24

          if (x < 8) {
            createCell(x, lastRow() - 4, blocks2d); // 19
            createCell(x, lastRow() - 2, blocks2d); // 21
          }

          if (x >= 8) {
            createCell(x, lastRow() - 3, blocks2d); // 20
            createCell(x, lastRow() - 1, blocks2d); // 22
          }
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, lastRow() - 11); // 12

        break;
      case "v":
        // Test t-spin
        blocks2d.createEmpty();
        piece.setTetromino(TetrominoEnum.T.get());
        piece.setCenter(5, lastRow() - 6); // 17

        for (int x = 0; x < Blocks2d.getWidth(); x++) {
          if (x < 4 || x > 5) {
            final int finalX = x;

            IntStream.range(lastRow() - 4, lastRow() - 2).forEach(y -> createCell(finalX, y, blocks2d)); // 19, 21
          }

          if (x < 3 || x > 5) {
            createCell(x, lastRow() - 2, blocks2d); // 21
          }

          if (x != 4) createCell(x, lastRow() - 1, blocks2d); // 22

          createCell(x, lastRow(), blocks2d); // 23
        }

        break;
    }
  }
}
