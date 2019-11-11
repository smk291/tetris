package com.tetrisrevision.unittests.actions;

import com.tetrisrevision.actions.RowDeleter;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.recordkeeping.GameRecordKeeping;
import com.tetrisrevision.things.Center;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.tetrominoes.OPiece;
import com.tetrisrevision.unittests.UnitTestHelper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RowDeleterTest {
  private RowList rl = new RowList();
  private RowList inserted = new RowList();
  private TetrisPiece t = new TetrisPiece(new OPiece());
  private Center c = new Center(5,5);
  private GameRecordKeeping g = new GameRecordKeeping();

  void fillPlayField() {
    rl.clear();

    rl.add(UnitTestHelper.getRowWithBlocks(16,new int[] {                     7, 8, 9}));
    rl.add(UnitTestHelper.getRowWithBlocks(15,new int[] {                        8, 9}));
    rl.add(UnitTestHelper.getRowWithBlocks(14,new int[] {                  6, 7      }));
    // ...
    rl.add(UnitTestHelper.getRowWithBlocks(11,new int[] {            4,    6         }));
    rl.add(UnitTestHelper.getRowWithBlocks(10,new int[] {0,          4,    6         }));
    rl.add(UnitTestHelper.getRowWithBlocks(9, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    rl.add(UnitTestHelper.getRowWithBlocks(8, new int[] {   1, 2, 3,    5, 6, 7      }));
    rl.add(UnitTestHelper.getRowWithBlocks(7, new int[] {0,    2,    4,    6,    8, 9}));
    rl.add(UnitTestHelper.getRowWithBlocks(6, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    rl.add(UnitTestHelper.getRowWithBlocks(5, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    rl.add(UnitTestHelper.getRowWithBlocks(4, new int[] {0, 1, 2, 3, 4, 5, 6, 7      }));
    rl.add(UnitTestHelper.getRowWithBlocks(3, new int[] {0,    2,    4,    6,    8, 9}));
    rl.add(UnitTestHelper.getRowWithBlocks(2, new int[] {0, 1, 2, 3, 4, 5, 6, 7      }));
    rl.add(UnitTestHelper.getRowWithBlocks(1, new int[] {0,    2,    4,    6,    8, 9}));
    rl.add(UnitTestHelper.getRowWithBlocks(0, new int[] {0,    2,    4,    6         }));
  }

  void fillInserted() {
    inserted.clear();
    inserted.add(UnitTestHelper.getRowWithBlocks(6, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    inserted.add(UnitTestHelper.getRowWithBlocks(5, new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));
    t.setCenter(c);
  }

  @Test
  void testRowDeleter() {
    fillPlayField();
    fillInserted();

    ArrayList<Integer> r = RowDeleter.apply(t.getBlocks(), t, rl, g);

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
