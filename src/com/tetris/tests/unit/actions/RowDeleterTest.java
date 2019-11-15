package com.tetris.tests.unit.actions;

import com.tetris.game.actions.RowDeleter;
import com.tetris.game.recordkeeping.GameRecordKeeping;
import com.tetris.game.things.Center;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;
import com.tetris.game.things.tetrominoes.OBlock;
import com.tetris.tests.unit.UnitTestHelper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RowDeleterTest {
  private RowList rl = new RowList();
  private RowList inserted = new RowList();
  private ActiveBlock t = new ActiveBlock(new OBlock());
  private Center c = new Center(5,5);
  private GameRecordKeeping g = new GameRecordKeeping();

  void fillPlayfield() {
    rl.clear();

    rl.add(UnitTestHelper.getRowWithSquares(16,new int[] {                     7, 8, 9}));
    rl.add(UnitTestHelper.getRowWithSquares(15,new int[] {                        8, 9}));
    rl.add(UnitTestHelper.getRowWithSquares(14,new int[] {                  6, 7      }));
    // ...
    rl.add(UnitTestHelper.getRowWithSquares(11,new int[] {            4,    6         }));
    rl.add(UnitTestHelper.getRowWithSquares(10,new int[] {0,          4,    6         }));
    rl.add(UnitTestHelper.getRowWithSquares(9, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    rl.add(UnitTestHelper.getRowWithSquares(8, new int[] {   1, 2, 3,    5, 6, 7      }));
    rl.add(UnitTestHelper.getRowWithSquares(7, new int[] {0,    2,    4,    6,    8, 9}));
    rl.add(UnitTestHelper.getRowWithSquares(6, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    rl.add(UnitTestHelper.getRowWithSquares(5, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    rl.add(UnitTestHelper.getRowWithSquares(4, new int[] {0, 1, 2, 3, 4, 5, 6, 7      }));
    rl.add(UnitTestHelper.getRowWithSquares(3, new int[] {0,    2,    4,    6,    8, 9}));
    rl.add(UnitTestHelper.getRowWithSquares(2, new int[] {0, 1, 2, 3, 4, 5, 6, 7      }));
    rl.add(UnitTestHelper.getRowWithSquares(1, new int[] {0,    2,    4,    6,    8, 9}));
    rl.add(UnitTestHelper.getRowWithSquares(0, new int[] {0,    2,    4,    6         }));
  }

  void fillInserted() {
    inserted.clear();
    inserted.add(UnitTestHelper.getRowWithSquares(6, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    inserted.add(UnitTestHelper.getRowWithSquares(5, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    t.setCenter(c);
  }

  @Test
  void testRowDeleter() {
    fillPlayfield();
    fillInserted();

    ArrayList<Integer> r = RowDeleter.apply(t.getSquares(), t, rl, g);

    assertTrue(r.contains(5));
    assertEquals(1, r.size());

    for (int x : new int[]{0,1,2,3,4,5,6,7})
      assertTrue(rl.cellIsNotEmpty(x, 4));

    for (int x : new int[]{8,9})
      assertFalse(rl.cellIsNotEmpty(x, 4));

    for (int x : new int[]{0,2,4,6,8,9})
      assertTrue(rl.cellIsNotEmpty(x, 5));

    for (int x : new int[]{1,3,5,7})
      assertFalse(rl.cellIsNotEmpty(x, 5));

    for (int x : new int[]{1,2,3,5,6,7})
      assertTrue(rl.cellIsNotEmpty(x, 6));

    for (int x : new int[]{0,4,8,9})
      assertFalse(rl.cellIsNotEmpty(x, 6));


  }
}
