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
 * These are integration tests. Each test should be appropriately labeled/explained below.
 * The code is formatted so that the user should be able to visualize easily what the playfield will look like.
 *
 * */
public abstract class IntegrationTests {
  static void put(RowList rl, int y, int[] squares) {
    Row row = new Row(y);

    for (int i : squares) {
      row.add(new Square(i, Color.lightGray));
    }

    rl.add(row);
  }

  public static void accept(String e, ActiveBlock block, RowList playfield) {
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
        playfield.clear();

        block.setTetromino(TetrominoEnum.I.get());

        put(playfield, 3,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(playfield, 2,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(playfield, 1,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(playfield, 0,  new int[] {0,1,2,3,  5,6,7,8,9});

        break;
      case "A": // deletion test
        playfield.clear();
        block.setTetromino(TetrominoEnum.I.get());

        put(playfield, 3,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(playfield, 2,  new int[] {  1,2,3,  5,6,7,8,9});
        put(playfield, 1,  new int[] {0,1,2,3,  5,6,7,8,9});
        put(playfield, 0,  new int[] {0,1,2,3,  5,6,7,8,9});

        playfield
            .getRowByY(2)
            .ifPresent(r -> r.get().remove(0));

        break;
      case "s": // sink test
        playfield.clear();
        block.setCenter(6, 15);

        put(playfield, 2,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(playfield, 1,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(playfield, 0,  new int[] {0,1,2,3,4,  6,7,8,9});

        break;
      case "S":
        playfield.clear();

        block.setTetromino(TetrominoEnum.S.get());
        block.setRotation(3);
        block.setCenter(5, 5);

        put(playfield, 3,  new int[] {0,1,2,3,    6,7,8,9});
        put(playfield, 2,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(playfield, 1,  new int[] {0,1,2,3,4,  6,7,8,9});
        put(playfield, 0,  new int[] {0,1,2,3,4,  6,7,8,9});

        break;
      case "d": // multiple sinking blocks, test that each behaves correctly
        playfield.clear();

        block.setTetromino(TetrominoEnum.values()[0].get());
        block.setCenter(2, 15);

        put(playfield, 11, new int[] {0,      4,  6      });
        put(playfield, 10, new int[] {0,      4,  6      });
        put(playfield, 9,  new int[] {0,1,  3,4,5,6,7,8,9});
        put(playfield, 8,  new int[] {    2,        7,8  });
        put(playfield, 7,  new int[] {    2,          8  });
        put(playfield, 6,  new int[] {    2,             });
        put(playfield, 5,  new int[] {    2              });
        put(playfield, 4,  new int[] {    2,             });
        put(playfield, 3,  new int[] {    2,  4          });
        put(playfield, 2,  new int[] {    2,3,4,  6      });
        put(playfield, 1,  new int[] {0,  2,  4,  6,     });
        put(playfield, 0,  new int[] {0,  2,  4,  6      });


        break;
      case "f": // test kick
        playfield.clear();

        block.setCenter(Constants.fromLeft(4), Constants.fromBottom(15));

        break;
      case "z": // test row deletion
        playfield.clear();

        put(playfield, 0,  new int[] {0,1,2,  4,5,6,7,8,9});

        break;
      case "x": // test row delete after sink -- on bottom row to test bounds checker
        playfield.clear();

        block.setTetromino(TetrominoEnum.J.get());
        block.setRotation(0);
        block.setCenter(1, 3);

        put(playfield, 2,  new int[] {      3            });
        put(playfield, 1,  new int[] {      3,4,5,6,7,8,9});
        put(playfield, 0,  new int[] {  1,2,  4,5,6,7,8,9});


        break;
      case "c": // test recursive sinking blocks -- sinking blocks created when sinking block fills
        // row and row deletion creates
        // another sinking block
        playfield.clear();

        block.setTetromino(TetrominoEnum.J.get());
        block.resetRotation();
        block.setCenter(1, 12);

        put(playfield, 10, new int[] {        4          });
        put(playfield, 9,  new int[] {      3,4,5,6,7,8,9});
        put(playfield, 8,  new int[] {  1,2,3,  5,6,7,8,9});
        put(playfield, 7,  new int[] {0,  2,  4,  6,  8,9});
        put(playfield, 6,  new int[] {0,  2,  4,  6      });
        put(playfield, 5,  new int[] {0,1,2,3,4,5,6,7    });
        put(playfield, 4,  new int[] {0,  2,  4,  6,  8,9});
        put(playfield, 3,  new int[] {0,  2,  4,  6      });
        put(playfield, 2,  new int[] {0,1,2,3,4,5,6,7    });
        put(playfield, 1,  new int[] {0,  2,  4,  6,  8,9});
        put(playfield, 0,  new int[] {0,  2,  4,  6      });


        break;
      case "v":
        // Test t-spin
        playfield.clear();
        block.setTetromino(TetrominoEnum.T.get());
        block.setCenter(6, 5);
        block.setRotation(1);

        put(playfield, 3,  new int[] {0,1,2,3,4,    7,8,9});
        put(playfield, 2,  new int[] {0,1,2,3,4,    7,8,9});
        put(playfield, 1,  new int[] {0,1,2,3,      7,8,9});
        put(playfield, 0,  new int[] {0,1,2,3,4,  6,7,8,9});

        break;
      case "o":
        playfield.clear();
        block.reset(new IBlock());
        block.setCenter(WallKickerTest.getCenter());

        put(playfield, 19, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 18, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 17, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 16, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 15, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 14, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 13, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 12, new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 11, new int[]{0,1,2,3,  5,6,7,8,9});
        put(playfield, 10, new int[]{0,1,2,3,  5,6,7,8,9});
        put(playfield, 9,  new int[]{0,1,2,3,  5,6,7,8,9});
        put(playfield, 8,  new int[]{0,1,2,3,  5,6,7,8,9});
        put(playfield, 7,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 6,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 5,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 4,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 3,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 2,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 1,  new int[]{0,1,2,3,4,5,6,7,8,9});
        put(playfield, 0,  new int[]{0,1,2,3,4,5,6,7,8,9});

    }
  }
}