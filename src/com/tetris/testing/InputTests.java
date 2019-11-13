package com.tetris.testing;

import com.tetris.constants.Constants;
import com.tetris.things.Square;
import com.tetris.things.Row;
import com.tetris.things.RowList;
import com.tetris.things.ActiveBlock;
import com.tetris.things.tetrominoes.IBlock;
import com.tetris.things.tetrominoes.TetrominoEnum;
import com.tetris.unittests.actions.WallKickerTest;

import java.awt.*;

/**
 * These are integration tests. Each test should be appropriately labeled/explained.
 *
 * */
public abstract class InputTests {
  static void put(RowList rl, int y, int[] squares) {
    Row row = new Row(y);

    for (int i : squares) {
      row.add(new Square(i, Color.lightGray));
    }

    rl.add(row);
  }

  public static void accept(String e, ActiveBlock block, RowList board) {
    switch (e) {
      case "1":
      case "2":
      case "3":
      case "4":
      case "5":
      case "6":
      case "7":
        block.setTetromino(TetrominoEnum.values()[Integer.parseInt(e) - 1].get());
        block.setCenter(4, 13);

        break;
      case "a": // tetris-deletion test
        board.clear();

        block.setTetromino(TetrominoEnum.I.get());

        put(board, 3,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(board, 2,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(board, 1,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(board, 0,  new int[] {0,1,2,3,  5,6,7,8,9});

        break;
      case "A": // tetris-deletion test
        board.clear();
        block.setTetromino(TetrominoEnum.I.get());

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
        block.setCenter(6, 15);

        put(board, 2,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(board, 1,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(board, 0,  new int[] {0,1,2,3,4,  6,7,8,9});

        break;
      case "S":
        board.clear();

        block.setTetromino(TetrominoEnum.S.get());
        block.setRotation(3);
        block.setCenter(5, 5);

        put(board, 3,  new int[] {0,1,2,3,    6,7,8,9});
        put(board, 2,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(board, 1,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(board, 0,  new int[] {0,1,2,3,4,  6,7,8,9});

        break;
      case "d": // multiple sinking blocks, test that each behaves correctly
        board.clear();

        block.setTetromino(TetrominoEnum.values()[0].get());
        block.setCenter(2, 15);

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

        block.setCenter(Constants.fromLeft(4), Constants.fromBottom(15));

        break;
      case "z": // test row deletion
        board.clear();

        put(board, 0,  new int[] {0,1,2,  4,5,6,7,8,9});

        break;
      case "x": // test row delete after sink -- on bottom row to test bounds checker
        board.clear();

        block.setTetromino(TetrominoEnum.J.get());
        block.setRotation(0);
        block.setCenter(1, 3);

        put(board, 2,  new int[] {      3            });
        put(board, 1,  new int[] {      3,4,5,6,7,8,9});
        put(board, 0,  new int[] {  1,2,  4,5,6,7,8,9});


        break;
      case "c": // test recursive sinking blocks -- sinking blocks created when sinking block fills
        // row and row deletion creates
        // another sinking block
        board.clear();

        block.setTetromino(TetrominoEnum.J.get());
        block.resetRotation();
        block.setCenter(1, 12);

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
        block.setTetromino(TetrominoEnum.T.get());
        block.setCenter(6, 5);
        block.setRotation(1);

        put(board, 3,  new int[] {0,1,2,3,4,    7,8,9});
        put(board, 2,  new int[] {0,1,2,3,4,    7,8,9});
        put(board, 1,  new int[] {0,1,2,3,      7,8,9});
        put(board, 0,  new int[] {0,1,2,3,4,  6,7,8,9});

        break;
      case "o":
        board.clear();
        block.reset(new IBlock());
        block.setCenter(WallKickerTest.getCenter());

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