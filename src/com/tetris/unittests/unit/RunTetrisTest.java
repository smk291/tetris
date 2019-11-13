package com.tetris.unittests.unit;

import com.tetris.RunTetris;
import com.tetris.constants.Constants;
import com.tetris.things.*;
import com.tetris.things.Tetromino;
import com.tetris.unittests.unit.UnitTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RunTetrisTest {
  JFrame frame = new JFrame();

  private RunTetris rt = new RunTetris();
  private Center c = new Center(5,5);

  private int[][][] insert = {
      {{6,9},{6,8}},
      {{4,3},{3,3},{2,3}}
  };

  @BeforeEach
  void beforeTest() {
    rt = new RunTetris();
  }

  @Test
  void getCurrentPiece() {
    assertNotNull(rt.getCurrentPiece());
  }

  @Test
  void getPlayField() {
    assertNotNull(rt.getPlayfield());
  }

  @Test
  void getSinkingPieces() {
    assertNotNull(rt.getSinkingPieces());
  }

  @Test
  void getRecordKeeping() {
    assertNotNull(rt.getRecordKeeping());
  }

  @Test
  void rotate() {
    assertEquals(0, rt.getCurrentPiece().getRotation());

    rt.rotate(frame, 1);

    assertEquals(1, rt.getCurrentPiece().getRotation());
    assertEquals(0, rt.getCurrentPiece().getPrevRotation());

    rt.rotate(frame, -1);

    assertEquals(0, rt.getCurrentPiece().getRotation());
    assertEquals(1, rt.getCurrentPiece().getPrevRotation());
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

  void setSinkingPieces() {
    RowList[] rls = UnitTestHelper.getRowListArrays(insert);

    rt.setSinkingPieces(new ArrayList<>(Arrays.asList(rls)));
  }

  @Test
  void setSinkingPiecesTest() {
    assertEquals(0, rt.getSinkingPieces().size());

    setSinkingPieces();

    ArrayList<RowList> sps = rt.getSinkingPieces();
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
  void dropSinkingPieces() {
    assertEquals(0, rt.getSinkingPieces().size());

    setSinkingPieces();

    ArrayList<RowList> sps = rt.getSinkingPieces();
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

    rt.dropSinkingPieces();

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
  void addPieceToBoard() {
    ActiveBlock p = rt.getCurrentPiece();
    Tetromino t = p.getTetromino();
    p.setCenter(c);

    UnitTestHelper.printNonFullLines(p.getSquares());

    RowList rl = p.getSquares();

    rt.addPieceToPlayfield(p);

    for (Row r : rl.get()) {
      for (Square b : r.get()) {
        rt.getPlayfield().cellIsNotEmpty(b.getX(), r.getY());
      }
    }

    UnitTestHelper.printNonFullLines(p.getSquares());

    assertNotSame(t, p.getTetromino());
  }

  @Test
  void dropCurrentPiece() {
    ActiveBlock t = rt.getCurrentPiece();

    assertEquals(4, t.getCenter().getX());
    assertEquals(Constants.topRow, t.getCenter().getY());

    rt.dropCurrentPiece(frame);

    assertEquals(4, t.getCenter().getX());
    assertEquals(Constants.topRow - 1, t.getCenter().getY());
  }

  @Test
  void translatePiece() {
    ActiveBlock t = rt.getCurrentPiece();

    t.setCenter(5,0);

    RowList blockLocation = t.getSquares();

    rt.translatePiece(frame,0, Constants.down);

    for (Row r : blockLocation.get()) {
      for (Square b : r.get()) {
        assertTrue(rt.getPlayfield().cellIsNotEmpty(b.getX(), r.getY()));
      }
    }
  }
}
