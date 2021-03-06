package com.tetris.tests.unit.actions;

import com.tetris.game.actions.SinkingBlockFinder;
import com.tetris.game.constants.Constants;
import com.tetris.game.things.Square;
import com.tetris.game.things.Row;
import com.tetris.game.things.RowList;
import com.tetris.tests.unit.UnitTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SinkingBlockFinderTest {
  private RowList rl = new RowList();
  private ArrayList<RowList> sinkingBlocks = new ArrayList<>();
  private final int[][] skip = new int[Constants.height][Constants.width];
  private final int[] startIdx = {7, 15};
  private final int[][][][] expectedSinkingBlocks = {
      {
          {{0,7}},
          {{4,7},{4,8}}
      },
      {
          {{8,15},{9,15},{7,16},{8,16},{9,16}},
          {{6,14},{7,14}}
      }
  };
  // Empty cells are never added to the skip-cell 2d array.
  // If a square is connected both to the bottom of the playfield
  // and to a square that appears in one of the two lines examined
  // for sinking blocks, it will be in the table of values to skip
  private final int[][][] expectedSkipCells = {
    {{1,6},{0,7},{6,0}},
    {{9,15},{7,16}}
  };
  private final int[][][] expectedDoNotSkipCells = {
      {{1,14},{2,16},{5,8}},
      {{0,6},{6,16}}
  };

  SinkingBlockFinderTest() {
    rl = fillPlayfield();
  }

  @BeforeEach
  void reset() {
    sinkingBlocks.clear();
  }

  public RowList fillPlayfield() {
    RowList rl2 = new RowList();

    rl2.add(UnitTestHelper.getRowWithSquares(16,new int[]{              7,8,9}));
    rl2.add(UnitTestHelper.getRowWithSquares(15,new int[]{                8,9}));    // <-- findBlocksOnBothLines
    rl2.add(UnitTestHelper.getRowWithSquares(14,new int[]{            6,7    }));
    // ...
    rl2.add(UnitTestHelper.getRowWithSquares(8, new int[]{        4,  6      }));
    rl2.add(UnitTestHelper.getRowWithSquares(7, new int[]{0,      4,  6      }));    // <-- findBlocksonDeletedLine
    rl2.add(UnitTestHelper.getRowWithSquares(6, new int[]{  1,2,3,  5,6,7    }));
    rl2.add(UnitTestHelper.getRowWithSquares(5, new int[]{0,  2,  4,  6,  8,9}));
    rl2.add(UnitTestHelper.getRowWithSquares(4, new int[]{0,1,2,3,4,5,6,7    }));
    rl2.add(UnitTestHelper.getRowWithSquares(3, new int[]{0,  2,  4,  6,  8,9}));
    rl2.add(UnitTestHelper.getRowWithSquares(2, new int[]{0,1,2,3,4,5,6,7    }));
    rl2.add(UnitTestHelper.getRowWithSquares(1, new int[]{0,  2,  4,  6,  8,9}));
    rl2.add(UnitTestHelper.getRowWithSquares(0, new int[]{0,  2,  4,  6      }));

    return rl2;
  }

  void findSinkingBlocks(int idx) {
    new SinkingBlockFinder().findSinkingBlocks(startIdx[idx], rl, sinkingBlocks);

    assertEquals(expectedSinkingBlocks[idx].length, sinkingBlocks.size());

    for (int[][] coordsArray : expectedSinkingBlocks[idx]) {
      RowList p = null;

      for (RowList t : sinkingBlocks) {
        int i = 0;

        for (Row r : t.get())
          for (Square b : r.get())
            i++;

        if (i == coordsArray.length)
          p = t;
      }

      assertNotNull(p);

      for (int[] coord : coordsArray)
        assertTrue(p.cellIsNotEmpty(coord[0], coord[1]));
    }
  }

  @Test
  void findBlocksOnDeletedLine() {
    findSinkingBlocks(0);
  }

  @Test
  void findBlocksOnBothLines() {
    findSinkingBlocks(1);
  }

  @Test
  void getAdjacentSquares() {}

  void doSkipCell(int idx) {
    SinkingBlockFinder spf = new SinkingBlockFinder();

    spf.findSinkingBlocks(startIdx[idx], rl, sinkingBlocks);

    for (int[] coords : expectedSkipCells[idx]) {
      assertTrue(spf.doSkipCell(coords[0], coords[1]));
    }

    for (int[] coords : expectedDoNotSkipCells[idx]) {
      assertFalse(spf.doSkipCell(coords[0], coords[1]));
    }

    // Running 'doSkipCell' adds that cell to the list of cells to skip
    for (int[] coords : expectedDoNotSkipCells[idx]) {
      assertTrue(spf.doSkipCell(coords[0], coords[1]));
    }
  }

  @Test
  void testSkip(){
    doSkipCell(0);
    doSkipCell(1);
  }
}
