package com.tetris.tests.unit.things;

import com.tetris.game.things.ActiveBlock;
import com.tetris.game.things.TetrominoQueue;
import com.tetris.game.things.Tetromino;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TetrominoQueueTest {
  @Test
  void resetCurrentPiece() {
    ActiveBlock t = new ActiveBlock();
    assertNull(t.getTetromino());

    var q = new TetrominoQueue(t);

    assertNotNull(t.getTetromino());
  }


  @Test
  void getQueue() {
    assertEquals(7, new TetrominoQueue(new ActiveBlock()).getQueue().size());
  }

  @Test
  void distributionTest() {
    HashMap<Tetromino, Integer> lastOccurrences = new HashMap<>();
    int maxLoopIterations = 84;
    int maxSeparation = 13;

    ActiveBlock t = new ActiveBlock();
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
