package com.tetris.tests.unit;

import com.tetris.game.RunTetris;
import com.tetris.game.constants.Constants;
import com.tetris.game.things.*;
import com.tetris.game.things.Tetromino;
import com.tetris.gui.GUIMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RunTetrisTest {
  JFrame frame = new JFrame();
  GUIMain gui = new GUIMain();

  private RunTetris rt = new RunTetris(gui);
  private Center c = new Center(5,5);

  private int[][][] insert = {
      {{6,9},{6,8}},
      {{4,3},{3,3},{2,3}}
  };

  @BeforeEach
  void beforeTest() {
    rt = new RunTetris(gui);
  }

  @Test
  void getCurrentBlock() {
    assertNotNull(rt.getActiveBlock());
  }

  @Test
  void getPlayfield() {
    assertNotNull(rt.getPlayfield());
  }

  @Test
  void getSinkingBlocks() {
    assertNotNull(rt.getSinkingBlocks());
  }

  @Test
  void getRecordKeeping() {
    assertNotNull(rt.getRecordKeeping());
  }

  @Test
  void rotate() {
    assertEquals(0, rt.getActiveBlock().getRotation());

    rt.rotate(1);

    assertEquals(1, rt.getActiveBlock().getRotation());
    assertEquals(0, rt.getActiveBlock().getPrevRotation());

    rt.rotate(-1);

    assertEquals(0, rt.getActiveBlock().getRotation());
    assertEquals(1, rt.getActiveBlock().getPrevRotation());
  }

  @Test
  void getTetrominoQueue() {
    assertNotNull(rt.getTetrominoQueue());
  }

  @Test
  void getHoldPiece() {
    rt.setHoldPiece();
    assertNotNull(rt.getHoldPiece());
  }

  @Test
  void setHoldPiece() {
    Tetromino t = rt.getHoldPiece();

    assertNull(t);

    rt.setHoldPiece();

    t = rt.getHoldPiece();

    rt.setHoldPiece();

    assertNotNull(t);
  }

  void setSinkingBlocks() {
    RowList[] rls = UnitTestHelper.getRowListArrays(insert);

    rt.setSinkingBlocks(new ArrayList<>(Arrays.asList(rls)));
  }

  @Test
  void setSinkingBlocksTest() {
    assertEquals(0, rt.getSinkingBlocks().size());

    setSinkingBlocks();

    ArrayList<RowList> sps = rt.getSinkingBlocks();
    assertEquals(2, sps.size());

    for (int[][] coordsArray : insert) {
      RowList sp = sps.stream().filter(rl -> {
        int size = 0;

        for (Row r : rl.get())
          size += r.get().size();

        return size == coordsArray.length;
      }).toArray(RowList[]::new)[0];

      assertNotNull(sp);

      for (int[] coords : coordsArray) {
        assertTrue(sp.cellIsNotEmpty(coords[0], coords[1]));
      }
    }
  }

  @Test
  void dropSinkingBlocks() {
    assertEquals(0, rt.getSinkingBlocks().size());

    setSinkingBlocks();

    ArrayList<RowList> sps = rt.getSinkingBlocks();
    assertEquals(2, sps.size());

    for (int[][] coordsArray : insert) {
      RowList sp = sps.stream().filter(rl -> {
        int size = 0;

        for (Row r : rl.get())
          size += r.get().size();

        return size == coordsArray.length;
      }).toArray(RowList[]::new)[0];

      assertNotNull(sp);

      for (int[] coords : coordsArray) {
        assertTrue(sp.cellIsNotEmpty(coords[0], coords[1]));
      }
    }

    rt.dropSinkingBlocks();

    for (int[][] coordsArray : insert) {
      RowList sp = sps.stream().filter(rl -> {
        int size = 0;

        for (Row r : rl.get())
          size += r.get().size();

        return size == coordsArray.length;
      }).toArray(RowList[]::new)[0];

      assertNotNull(sp);

      for (int[] coords : coordsArray) {
        assertTrue(sp.cellIsNotEmpty(coords[0], coords[1] - 1));
      }
    }
  }

  // Could add more tests for this method, e.g. to ensure proper deletion, but that shouldn't be necessary,
  // as all methods this method uses are tested elsewhere.
  @Test
  void addBlockToBoard() {
    ActiveBlock p = rt.getActiveBlock();
    Tetromino t = p.getTetromino();
    p.setCenter(c);

    UnitTestHelper.printNonFullLines(p.getSquares());

    RowList rl = p.getSquares();

    rt.addBlockToPlayfield(p);

    for (Row r : rl.get()) {
      for (Square b : r.get()) {
        rt.getPlayfield().cellIsNotEmpty(b.getX(), r.getY());
      }
    }

    UnitTestHelper.printNonFullLines(p.getSquares());

    assertNotSame(t, p.getTetromino());
  }

  @Test
  void dropCurrentBlock() {
    ActiveBlock t = rt.getActiveBlock();

    assertEquals(4, t.getCenter().getX());
    assertEquals(Constants.topRow, t.getCenter().getY());

    rt.dropActiveBlock();

    assertEquals(4, t.getCenter().getX());
    assertEquals(Constants.topRow - 1, t.getCenter().getY());
  }

  @Test
  void translateBlock() {
    ActiveBlock t = rt.getActiveBlock();

    t.setCenter(5,0);

    RowList blockLocation = t.getSquares();

    rt.translate(0, Constants.down);

    for (Row r : blockLocation.get()) {
      for (Square b : r.get()) {
        assertTrue(rt.getPlayfield().cellIsNotEmpty(b.getX(), r.getY()));
      }
    }
  }
}
