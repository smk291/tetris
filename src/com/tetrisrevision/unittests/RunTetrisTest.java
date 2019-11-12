package com.tetrisrevision.unittests;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.*;
import com.tetrisrevision.things.tetrominoes.IPiece;
import com.tetrisrevision.things.tetrominoes.TPiece;
import com.tetrisrevision.things.tetrominoes.Tetromino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RunTetrisTest {
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
    assertNotNull(rt.getPlayField());
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

    rt.rotate(1);

    assertEquals(1, rt.getCurrentPiece().getRotation());
    assertEquals(0, rt.getCurrentPiece().getPrevRotation());

    rt.rotate(-1);

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

  // Could add more tests, e.g. to ensure proper deletion, but that shouldn't be necessary, as all
  // methods used are tested elsewhere
  @Test
  void addPieceToBoard() {
    TetrisPiece p = rt.getCurrentPiece();
    Tetromino t = p.getTetromino();
    p.setCenter(c);

    UnitTestHelper.printNonFullLines(p.getBlocks());

    RowList rl = p.getBlocks();

    rt.addPieceToBoard(p);

    for (Row r : rl.get()) {
      for (Block b : r.get()) {
        rt.getPlayField().cellIsNotEmpty(b.getX(), r.getY());
      }
    }

    UnitTestHelper.printNonFullLines(p.getBlocks());

    assertNotSame(t, p.getTetromino());
  }

  @Test
  void dropCurrentPiece() {
    TetrisPiece t = rt.getCurrentPiece();

    assertEquals(4, t.getCenter().getX());
    assertEquals(Constants.topRow, t.getCenter().getY());

    rt.dropCurrentPiece();

    assertEquals(4, t.getCenter().getX());
    assertEquals(Constants.topRow - 1, t.getCenter().getY());
  }

  @Test
  void translatePiece() {
    TetrisPiece t = rt.getCurrentPiece();

    t.setCenter(5,0);

    RowList pieceLocation = t.getBlocks();

    rt.translatePiece(0, Constants.down);

    for (Row r : pieceLocation.get()) {
      for (Block b : r.get()) {
        assertTrue(rt.getPlayField().cellIsNotEmpty(b.getX(), r.getY()));
      }
    }
  }
}
