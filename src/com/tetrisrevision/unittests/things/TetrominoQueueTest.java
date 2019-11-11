package com.tetrisrevision.unittests.things;

import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.TetrominoQueue;
import com.tetrisrevision.things.tetrominoes.Tetromino;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TetrominoQueueTest {
  @Test
  void resetCurrentPiece() {
    TetrisPiece t = new TetrisPiece();
    assertNull(t.getTetromino());

    var q = new TetrominoQueue(t);

    assertNotNull(t.getTetromino());
  }


  @Test
  void getQueue() {
    assertEquals(7, new TetrominoQueue(new TetrisPiece()).getQueue().size());
  }

  @Test
  void distributionTest() {
    HashMap<Tetromino, Integer> lastOccurrences = new HashMap<>();
    int maxLoopIterations = 84;
    int maxSeparation = 13;

    TetrisPiece t = new TetrisPiece();
    TetrominoQueue q = new TetrominoQueue(t);

    for (int i = 0; i < maxLoopIterations; i++) {
      var tr = t.getTetromino();
      if (lastOccurrences.get(tr) != null) {
        System.out.println(i - lastOccurrences.get(tr));
        assertTrue(maxSeparation >= i - lastOccurrences.get(tr));
      }

      lastOccurrences.put(tr, i);
      q.resetCurrentPiece(t);
    }
  }
}
