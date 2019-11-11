package com.tetrisrevision.testing;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.tetrominoes.IPiece;
import com.tetrisrevision.things.tetrominoes.TetrominoEnum;
import com.tetrisrevision.unittests.actions.WallKickerTest;

import java.awt.*;

/** These are basically unit tests for various behaviors */
public abstract class InputTests {
  static void put(RowList rl, int y, int[] blocks) {
    Row row = new Row(y);

    for (int i : blocks) {
      row.add(new Block(i, Color.lightGray));
    }

    rl.add(row);
  }

  public static void accept(String e, TetrisPiece piece, RowList board) {
    switch (e) {
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
      case "6":
      case "7":
        piece.setTetromino(TetrominoEnum.values()[Integer.parseInt(e) - 1].get());
        piece.setCenter(4, 13);

        break;
      case "a": // tetris-deletion test
        board.clear();

        piece.setTetromino(TetrominoEnum.I.get());

        put(board, 3,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(board, 2,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(board, 1,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(board, 0,  new int[] {0,1,2,3,  5,6,7,8,9});

        break;
      case "A": // tetris-deletion test
        board.clear();
        piece.setTetromino(TetrominoEnum.I.get());

        put(board, 3,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(board, 2,  new int[] {  1,2,3,  5,6,7,8,9});
        put(board, 1,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(board, 0,  new int[] {0,1,2,3,  5,6,7,8,9});

        board
            .getRowByY(2)
            .ifPresent(r -> r.get().remove(0));

        break;
      case "s": // sink test
        board.clear();
        piece.setCenter(6, 15);

        put(board, 2,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(board, 1,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(board, 0,  new int[] {0,1,2,3,4,  6,7,8,9});

        break;
      case "S":
        board.clear();

        piece.setTetromino(TetrominoEnum.S.get());
        piece.setRotation(3);
        piece.setCenter(5, 5);

        put(board, 3,  new int[] {0,1,2,3,    6,7,8,9});
        put(board, 2,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(board, 1,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(board, 0,  new int[] {0,1,2,3,4,  6,7,8,9});

        break;
      case "d": // multiple sinking pieces, test that each behaves correctly
        board.clear();

        piece.setTetromino(TetrominoEnum.values()[0].get());
        piece.setCenter(2, 15);

        put(board, 11, new int[] {0,      4,  6      });
        put(board, 10, new int[] {0,      4,  6      });
        put(board, 9,  new int[] {0,1,  3,4,5,6,7,8,9});
        put(board, 8,  new int[] {    2,        7,8  });
        put(board, 7,  new int[] {    2,          8  });
        put(board, 6,  new int[] {    2,             });
        put(board, 5,  new int[] {    2              });
        put(board, 4,  new int[] {    2,             });
        put(board, 3,  new int[] {    2,  4          });
        put(board, 2,  new int[] {    2,3,4,  6      });
        put(board, 1,  new int[] {0,  2,  4,  6,     });
        put(board, 0,  new int[] {0,  2,  4,  6      });


        break;
      case "f": // test kick
        board.clear();

        piece.setCenter(Constants.fromLeft(4), Constants.fromBottom(15));

        break;
      case "z": // test row deletion
        board.clear();

        put(board, 0,  new int[] {0,1,2,  4,5,6,7,8,9});

        break;
      case "x": // test row delete after sink -- on bottom row to test bounds checker
        board.clear();

        piece.setTetromino(TetrominoEnum.J.get());
        piece.setRotation(0);
        piece.setCenter(1, 3);

        put(board, 2,  new int[] {      3            });
        put(board, 1,  new int[] {      3,4,5,6,7,8,9});
        put(board, 0,  new int[] {  1,2,  4,5,6,7,8,9});


        break;
      case "c": // test recursive sinking pieces -- sinking pieces created when sinking piece fills
        // row and row deletion creates
        // another sinking piece
        board.clear();

        piece.setTetromino(TetrominoEnum.J.get());
        piece.resetRotation();
        piece.setCenter(1, 12);

        put(board, 10, new int[] {        4          });
        put(board, 9,  new int[] {      3,4,5,6,7,8,9});
        put(board, 8,  new int[] {  1,2,3,  5,6,7,8,9});
        put(board, 7,  new int[] {0,  2,  4,  6,  8,9});
        put(board, 6,  new int[] {0,  2,  4,  6      });
        put(board, 5,  new int[] {0,1,2,3,4,5,6,7    });
        put(board, 4,  new int[] {0,  2,  4,  6,  8,9});
        put(board, 3,  new int[] {0,  2,  4,  6      });
        put(board, 2,  new int[] {0,1,2,3,4,5,6,7    });
        put(board, 1,  new int[] {0,  2,  4,  6,  8,9});
        put(board, 0,  new int[] {0,  2,  4,  6      });


        break;
      case "v":
        // Test t-spin
        board.clear();
        piece.setTetromino(TetrominoEnum.T.get());
        piece.setCenter(6, 5);
        piece.setRotation(1);

        put(board, 3,  new int[] {0,1,2,3,4,    7,8,9});
        put(board, 2,  new int[] {0,1,2,3,4,    7,8,9});
        put(board, 1,  new int[] {0,1,2,3,      7,8,9});
        put(board, 0,  new int[] {0,1,2,3,4,  6,7,8,9});

        break;
      case "o":
        board.clear();
        piece.reset(new IPiece());
        piece.setCenter(WallKickerTest.getCenter());

        put(board, 19, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 18, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 17, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 16, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 15, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 14, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 13, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 12, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 11, new int[]{0,1,2,3,  5,6,7,8,9});
        put(board, 10, new int[]{0,1,2,3,  5,6,7,8,9});
        put(board, 9,  new int[]{0,1,2,3,  5,6,7,8,9});
        put(board, 8,  new int[]{0,1,2,3,  5,6,7,8,9});
        put(board, 7,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 6,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 5,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 4,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 3,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 2,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 1,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(board, 0,  new int[]{0,1,2,3,4,5,6,7,8,9});

    }
  }
}