package com.tetrisrevision.unittests.things;

import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Center;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.tetrominoes.IPiece;
import com.tetrisrevision.things.tetrominoes.SPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TetrisPieceTest {
  private TetrisPiece t = new TetrisPiece(new IPiece());
  private int[] newCenter = {7, 5};
  private int newRotation = 3;
  private int[] resetCenter = {4, 19};
  private int resetRotation = 0;

  @BeforeEach
  void setUp() {
    t.setCenter(3, 4);
    t.reset(new IPiece());
  }

  @Test
  void reset() {
    t.setCenter(newCenter[0], newCenter[1]);
    t.setRotation(newRotation);

    assertEquals(newCenter[0], t.getCenter().getX());
    assertEquals(newCenter[1], t.getCenter().getY());
    assertEquals(newRotation, t.getRotation());

    t.reset();

    assertEquals(resetCenter[0], t.getCenter().getX());
    assertEquals(resetCenter[1], t.getCenter().getY());
    assertEquals(resetRotation, t.getRotation());
  }

  @Test
  void getRotation() {
    assertEquals(resetRotation, t.getRotation());
  }

  @Test
  void setRotation() {
    t.setRotation(newRotation);
    assertEquals(newRotation, t.getRotation());
  }

  @Test
  void resetRotation() {
    t.setRotation(newRotation);
    assertEquals(newRotation, t.getRotation());

    t.resetRotation();

    assertEquals(resetRotation, t.getRotation());
  }

  @Test
  void incrementRotation() {
    t.incrementRotation(1);
    assertEquals(1, t.getRotation());
    t.incrementRotation(-2);
    assertEquals(3, t.getRotation());
    t.incrementRotation(1);
    assertEquals(0, t.getRotation());
  }

  @Test
  void getPrevRotation() {
    t.incrementRotation(1);
    assertEquals(resetRotation, t.getPrevRotation());
  }

  @Test
  void getBlocks() {
    int blockCount = 0;
    int[][] blocks = new int[4][2];
    int[][] shape = t.getTetromino().getOffsets()[resetRotation];

    for (Row r : t.getBlocks().get()) {
      for (Block b : r.get()) {
        blocks[blockCount][0] = b.getX();
        blocks[blockCount][1] = r.getY();

        blockCount++;
      }
    }

    assertEquals(shape.length, blockCount);

    for (int[] b : blocks) {
      assertTrue(
          Arrays.stream(shape)
              .anyMatch(
                  relCoords ->
                      relCoords[0] + resetCenter[0] == b[0]
                          && relCoords[1] + resetCenter[1] == b[1]));
    }
  }

  @Test
  void getKickData() {
    assertNotNull(t.getKickData());
  }

  @Test
  void setCenter() {
    t.setCenter(newCenter[0], newCenter[1]);
    assertEquals(newCenter[0], t.getCenter().getX());
    assertEquals(newCenter[1], t.getCenter().getY());
  }

  @Test
  void getCenter() {
    assertNotNull(t.getCenter());
    assertEquals(resetCenter[0], t.getCenter().getX());
    assertEquals(resetCenter[1], t.getCenter().getY());
  }

  @Test
  void testSetCenter() {
    t.setCenter(new Center(newCenter[0], newCenter[1]));
    assertEquals(newCenter[0], t.getCenter().getX());
    assertEquals(newCenter[1], t.getCenter().getY());
  }

  @Test
  void getTetromino() {
    assertNotNull(t.getTetromino());
  }

  @Test
  void setTetromino() {
    assertTrue(t.getTetromino() instanceof IPiece);
    t.setTetromino(new SPiece());
    assertTrue(t.getTetromino() instanceof SPiece);
  }

  @Test
  void gettSpinTracker() {
    assertNotNull(t.gettSpinTracker());
  }

  @Test
  void setTSpinTracker() {}
}
