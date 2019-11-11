package com.tetrisrevision.unittests.actions;

import com.tetrisrevision.actions.SinkingPieceFinder;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.unittests.UnitTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SinkingPieceFinderTest {
  private RowList rl = new RowList();
  private ArrayList<RowList> sinkingPieces = new ArrayList<>();
  private final int[][] skip = new int[Constants.height][Constants.width];
  private final int[] startIdx = {7, 15};
  private final int[][][][] expectedSinkingPieces = {
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
  // If a block is connected both to the bottom of the playfield
  // and to a block that appears in one of the two lines examined
  // for sinking pieces, it will be in the table of values to skip
  private final int[][][] expectedSkipCells = {
    {{1,6},{0,7},{6,0}},
    {{9,15},{7,16}}
  };
  private final int[][][] expectedDoNotSkipCells = {
      {{1,14},{2,16},{5,8}},
      {{0,6},{6,16}}
  };

  SinkingPieceFinderTest() {
    rl = fillPlayField();
  }

  @BeforeEach
  void reset() {
    sinkingPieces.clear();
  }

  public RowList fillPlayField() {
    RowList rl2 = new RowList();

    rl2.add(UnitTestHelper.getRowWithBlocks(16,new int[]{              7,8,9}));
    rl2.add(UnitTestHelper.getRowWithBlocks(15,new int[]{                8,9}));    // <-- findPiecesOnBothLines
    rl2.add(UnitTestHelper.getRowWithBlocks(14,new int[]{            6,7    }));
    // ...
    rl2.add(UnitTestHelper.getRowWithBlocks(8, new int[]{        4,  6      }));
    rl2.add(UnitTestHelper.getRowWithBlocks(7, new int[]{0,      4,  6      }));    // <-- findPiecesonDeletedLine
    rl2.add(UnitTestHelper.getRowWithBlocks(6, new int[]{  1,2,3,  5,6,7    }));
    rl2.add(UnitTestHelper.getRowWithBlocks(5, new int[]{0,  2,  4,  6,  8,9}));
    rl2.add(UnitTestHelper.getRowWithBlocks(4, new int[]{0,1,2,3,4,5,6,7    }));
    rl2.add(UnitTestHelper.getRowWithBlocks(3, new int[]{0,  2,  4,  6,  8,9}));
    rl2.add(UnitTestHelper.getRowWithBlocks(2, new int[]{0,1,2,3,4,5,6,7    }));
    rl2.add(UnitTestHelper.getRowWithBlocks(1, new int[]{0,  2,  4,  6,  8,9}));
    rl2.add(UnitTestHelper.getRowWithBlocks(0, new int[]{0,  2,  4,  6      }));

    return rl2;
  }

  void findSinkingPieces(int idx) {
    new SinkingPieceFinder().findSinkingPieces(startIdx[idx], rl, sinkingPieces);

    assertEquals(expectedSinkingPieces[idx].length, sinkingPieces.size());

    for (int[][] coordsArray : expectedSinkingPieces[idx]) {
      RowList p = null;

      for (RowList t : sinkingPieces) {
        int i = 0;

        for (Row r : t.get())
          for (Block b : r.get())
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
  void findPiecesOnDeletedLine() {
    findSinkingPieces(0);
  }

  @Test
  void findPiecesOnBothLines() {
    findSinkingPieces(1);
  }

  @Test
  void getAdjacentBlocks() {}

  void doSkipCell(int idx) {
    SinkingPieceFinder spf = new SinkingPieceFinder();

    spf.findSinkingPieces(startIdx[idx], rl, sinkingPieces);

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
