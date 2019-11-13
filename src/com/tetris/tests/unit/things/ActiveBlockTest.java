package com.tetris.tests.unit.things;

import com.tetris.game.things.Square;
import com.tetris.game.things.Center;
import com.tetris.game.things.Row;
import com.tetris.game.things.ActiveBlock;
import com.tetris.game.things.tetrominoes.IBlock;
import com.tetris.game.things.tetrominoes.SBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ActiveBlockTest {
  private ActiveBlock t = new ActiveBlock(new IBlock());
  private int[] newCenter = {7, 5};
  private int newRotation = 3;
  private int[] resetCenter = {4, 19};
  private int resetRotation = 0;

  @BeforeEach
  void setUp() {
    t.setCenter(3, 4);
    t.reset(new IBlock());
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
    assertEquals(0, t.getPrevRotation());
    t.incrementRotation(-2);
    assertEquals(3, t.getRotation());
    assertEquals(1, t.getPrevRotation());
    t.incrementRotation(1);
    assertEquals(0, t.getRotation());
    assertEquals(3, t.getPrevRotation());
    t.incrementRotation(-1);
    assertEquals(3, t.getRotation());
    assertEquals(0, t.getPrevRotation());

  }

  @Test
  void getPrevRotation() {
    t.incrementRotation(1);
    assertEquals(resetRotation, t.getPrevRotation());
  }

  @Test
  void getSquares() {
    int squareCount = 0;
    int[][] squares = new int[4][2];
    int[][] shape = t.getTetromino().getOffsets()[resetRotation];

    for (Row r : t.getSquares().get()) {
      for (Square b : r.get()) {
        squares[squareCount][0] = b.getX();
        squares[squareCount][1] = r.getY();

        squareCount++;
      }
    }

    assertEquals(shape.length, squareCount);

    for (int[] b : squares) {
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
    assertTrue(t.getTetromino() instanceof IBlock);
    t.setTetromino(new SBlock());
    assertTrue(t.getTetromino() instanceof SBlock);
  }

  @Test
  void gettSpinTracker() {
    assertNotNull(t.gettSpinTracker());
  }

  @Test
  void setTSpinTracker() {}
}
