package com.tetrisrevision.unittests.actions;

import com.tetrisrevision.actions.Rotator;
import com.tetrisrevision.actions.Translater;
import com.tetrisrevision.things.*;
import com.tetrisrevision.things.tetrominoes.*;
import com.tetrisrevision.unittests.UnitTestHelper;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class WallKickerTest {
  private TetrisPiece t = new TetrisPiece(new IPiece());
  private RowList rl = new RowList();
  private Center c = new Center(5,10);
  private RowList emptyRL = new RowList();

  void test(int incr, Tetromino p) {
    t.setTetromino(p);
    t.reset();

    Rotator.apply(1, t, emptyRL);

    t.setCenter(c);

    System.out.println("==");
    System.out.println(t.getTetromino().getClass().getName());

    for (int i = 0; i < 4; i++) {
      int[][] kickData = getKickData();
      int rotation = t.getRotation(); //System.out.println("rotation: " + rotation); System.out.println("prevRotation: " + t.getPrevRotation());System.out.println("--");

      for (int[] kick : kickData) { // System.out.println("kick: " + kick[0] + ", " + kick[1]);RowList removeBlocks = getInsertCellsFromKickData(kick); // UnitTestHelper.buildAndPrintBoard(removeBlocks);UnitTestHelper.buildAndPrintBoard(rl);UnitTestHelper.buildAndPrintBoard(t.getBlocks());
        removeBlocksForKick(incr, kick);

        System.out.println("Before attempted rotation: ");
        UnitTestHelper.printLines(t.getBlocks());

        boolean rotateResult = Rotator.apply(incr, t, rl);

        System.out.println("After attempted rotation: ");
        UnitTestHelper.printLines(t.getBlocks());

        assertTrue(rotateResult); // System.out.println("Is: " + UnitTestHelper.printRowList(t.getBlocks()));
        assertEquals(rotation, t.getPrevRotation());
        assertEquals(rotation + incr, t.getRotation());

        for (Row r : t.getBlocks().get())
          for (Block b : r.get())
            assertTrue(rl.cellIsNotEmpty(b.getX(), r.getY()));
      }

      rl.clear();
    }
  }

  void removeBlocksForKick(int incr, int[] kick) {                                // System.out.println("===="); System.out.println("Kick: " + kick[0] + ", " + kick[1]);System.out.println("Piece currrent:");UnitTestHelper.printLines(t.getBlocks());
    UnitTestHelper.fillPlayField(rl);
    t.incrementRotation(incr);                                                    // System.out.println("Piece after rotation: "); UnitTestHelper.printLines(t.getBlocks());
    Translater.translate(t, emptyRL, kick[0], kick[1], false);

    RowList removeBlocks = t.getBlocks();                                         //    System.out.println("Remove:"); UnitTestHelper.printLines(removeBlocks);

    t.incrementRotation(-incr);
    Translater.translate(t, emptyRL, -kick[0], -kick[1], false);
    rl.removeBlocks(removeBlocks);

    System.out.println("Board after removal: ");
    UnitTestHelper.printLines(rl);

    // Cells that need to be empty are empty
    for (Row r : t.getBlocks().get()) {
      for (Block b : r.get()) {
        assertFalse(removeBlocks.cellIsNotEmpty(b.getX(), r.getY()));
      }
    } // System.out.println("Was: " + UnitTestHelper.printRowList(t.getBlocks()));

     System.out.println(kick[0] + ", " + kick[1]);
    //UnitTestHelper.buildAndPrintBoard(removeBlocks);
  }

  @Test
  void runTestsByPiece() {
    t.setCenter(c);


    test(1, new IPiece());
    test(-1, new IPiece());
    test(1, new JPiece());
    test(-1, new JPiece());
    test(1, new LPiece());
    test(-1, new LPiece());
    test(1, new SPiece());
    test(-1, new SPiece());
    test(1, new TPiece());
    test(-1, new TPiece());
    test(1, new ZPiece());
    test(-1, new ZPiece());
  }

  int[][] getKickData() {
    return t.getKickData().get(t.getPrevRotation()).get(t.getRotation());
  }

  RowList getInsertCellsFromKickData(int[] kickData) {
    int[][] cells = new int[4][2];
    int cx = c.getX();
    int cy = c.getY();
    for (int i = 0; i < cells.length; i++) {
      cells[i][0] = cx + kickData[0];
      cells[i][1] = cy + kickData[1];
    }

    return UnitTestHelper.getRowsWithBlocks(cells);
  }
}
