package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;
import java.util.stream.IntStream;

/** These are basically unit tests for various behaviors */
abstract class InputTests {
  private static void createCell(double x, double y, RowList rowList) {
    Block block = new Block(x, Color.LIGHT_GRAY);
    rowList.addBlock(y, block);
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

        for (int x = Constants.leftBound, l = Constants.rightBoundOuter;
            x != l;
            x += Constants.right) {
          if (x != Constants.fromLeft(5)) {
            createCell(x, Constants.bottomRow + Constants.up * 3, board);
            createCell(x, Constants.bottomRow + Constants.up * 2, board);
            createCell(x, Constants.bottomRow + Constants.up, board);
            createCell(x, Constants.bottomRow, board);
          }
        }
        break;
      case "A": // tetris-deletion test
        board.clear();
        piece.setTetromino(TetrominoEnum.I.get());

        for (int x = Constants.leftBound, l = Constants.rightBoundOuter;
            x != l;
            x += Constants.right) {
          if (x != Constants.fromLeft(5)) {
            createCell(x, Constants.bottomRow + Constants.up * 3, board);
            createCell(x, Constants.bottomRow + Constants.up * 2, board);
            createCell(x, Constants.bottomRow + Constants.up, board);
            createCell(x, Constants.bottomRow, board);
            ;
          }
        }

        board
            .getRowByY(Constants.fromBottom(2))
            .ifPresent(r -> r.get().remove(Constants.leftBound));

        break;
      case "s": // sink test
        board.clear();
        piece.setCenter(Constants.fromLeft(6), Constants.fromBottom(15));

        for (int x = Constants.leftBound; x != Constants.rightBoundOuter; x += Constants.right) {
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
        piece.setCenter(5, Constants.bottomRow + 5);

        for (int x = Constants.leftBound; x != Constants.rightBoundOuter; x += Constants.right) {
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

        for (int x = Constants.leftBound, boardWidth = Constants.rightBoundOuter;
            x != boardWidth;
            x += Constants.right) {
          if (x != Constants.fromLeft(3)) {
            createCell(x, Constants.fromBottom(9), board);
          }

          if (x == Constants.fromLeft(3)) {
            for (int y = Constants.fromBottom(8);
                y != Constants.bottomBoundOuter;
                y += Constants.down) {
              createCell(x, y, board);
            }
          }

          if (x == Constants.fromLeft(8) || x == Constants.fromLeft(9)) {
            createCell(x, Constants.fromBottom(8), board);
          }

          if (x == Constants.fromLeft(9)) {
            createCell(x, Constants.fromBottom(7), board);
          }

          if (x == Constants.leftBound
              || x == Constants.fromLeft(5)
              || x == Constants.fromLeft(7)) {
            createCell(x, Constants.fromBottom(10), board);
            createCell(x, Constants.fromBottom(11), board);
            createCell(x, Constants.bottomRow + Constants.up, board);
            createCell(x, Constants.bottomRow, board);
          }

          if (x == Constants.fromLeft(5)) {
            createCell(x, Constants.fromBottom(3), board);
          }

          if (x == Constants.fromLeft(4)
              || x == Constants.fromLeft(5)
              || x == Constants.fromLeft(7)) {
            createCell(x, Constants.fromBottom(2), board);
          }
        }

        piece.setTetromino(TetrominoEnum.values()[0].get());
        piece.setCenter(Constants.fromLeft(2), Constants.fromBottom(15));

        break;
      case "f": // test kick
        board.clear();

        for (int x = Constants.leftBound; x != Constants.rightBoundOuter; x += Constants.right) {
          if (x != Constants.fromLeft(1)
              && !(x > Constants.fromLeft(3) && x < Constants.fromLeft(9))) {
            createCell(x, Constants.fromBottom(6), board);
          }

          if (x != Constants.fromBottom(6)) {
            createCell(x, Constants.fromBottom(5), board);
            createCell(x, Constants.fromBottom(4), board);
            createCell(x, Constants.fromBottom(3), board);
          }

          if (x != Constants.fromLeft(1) && x != Constants.fromLeft(6)) {
            createCell(x, Constants.fromBottom(2), board);
            createCell(x, Constants.bottomRow, board);
          }
        }

        piece.setCenter(Constants.fromLeft(4), Constants.fromBottom(15));

        break;
      case "z": // test row deletion
        board.clear();

        for (int x = Constants.leftBound; x != Constants.rightBoundOuter; x += Constants.right) {
          if (x != Constants.fromLeft(6)) {
            createCell(x, Constants.bottomRow, board);
          }
        }

        break;
      case "x": // test row delete after sink -- on bottom row to test bounds checker
        board.clear();

        for (int x = Constants.leftBound; x != Constants.rightBoundOuter; x += Constants.right) {
          if (x == Constants.fromLeft(5)) {
            createCell(x, Constants.fromBottom(2), board);
          }

          if (x > Constants.fromLeft(3)) {
            createCell(x, Constants.fromBottom(1), board);
          }

          if (x > Constants.leftBound && x < Constants.fromLeft(5) || x > Constants.fromLeft(5)) {
            createCell(x, Constants.bottomRow, board);
          }
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.resetRotation();
        piece.setCenter(Constants.fromLeft(2), Constants.fromBottom(5));

        break;
      case "c": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
        // row and row deletion creates
        // another sinking piece
        board.clear();

        for (int y = Constants.bottomRow; y != Constants.fromBottom(8); y += Constants.up) {
          createCell(0, y, board);
          createCell(2, y, board);
          createCell(4, y, board);
          createCell(6, y, board);
        }

        for (int x = Constants.leftBound; x != Constants.rightBoundOuter; x += Constants.right) {
          if (x == Constants.fromLeft(5)) {
            createCell(x, Constants.fromBottom(10), board);
          }

          if (x > Constants.fromLeft(3)) {
            createCell(x, Constants.fromBottom(9), board);
          }

          if (x == Constants.rightBound || x == Constants.rightBound + Constants.left) {
            createCell(x, Constants.fromBottom(7), board);
            createCell(x, Constants.fromBottom(4), board);
            createCell(x, Constants.fromBottom(1), board);
          }

          if (x != Constants.leftBound && x < Constants.fromLeft(5)) {
            createCell(x, Constants.fromBottom(8), board);
          }

          if (x > Constants.fromLeft(5)) {
            createCell(x, Constants.fromBottom(8), board);
          }

          if (x != Constants.rightBound && x != Constants.rightBound + Constants.left) {
            createCell(x, Constants.fromBottom(2), board);
            createCell(x, Constants.fromBottom(5), board);
          }
        }

        piece.setTetromino(TetrominoEnum.J.get());
        piece.resetRotation();
        piece.setCenter(1, Constants.fromBottom(12));

        break;
      case "v":
        // Test t-spin
        board.clear();
        piece.setTetromino(TetrominoEnum.T.get());
        piece.setCenter(Constants.fromLeft(6), Constants.fromBottom(5));
        piece.setRotation(1);

        for (int x = Constants.leftBound; x < Constants.rightBoundOuter; x += Constants.right) {
          if (x < Constants.fromLeft(5) || x > Constants.fromRight(5)) {
            final int finalX = x;

            IntStream.range(Constants.fromBottom(5), Constants.fromBottom(3))
                .forEach(y -> createCell(finalX, y, board));
          }

          if (x < Constants.fromLeft(5) || x > Constants.fromLeft(7)) {
            createCell(x, Constants.fromBottom(1), board);
          }
          if (x < Constants.fromLeft(6) || x > Constants.fromLeft(7)) {
            createCell(x, Constants.fromBottom(2), board);
            createCell(x, Constants.fromBottom(3), board);
          }

          if (x != Constants.fromLeft(6)) {
            createCell(x, Constants.bottomRow, board);
          }
        }

        break;
    }
  }
}
