package com.tetrisrevision.unittests.actions;

import com.tetrisrevision.actions.Rotator;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.tetrominoes.IPiece;
import com.tetrisrevision.things.tetrominoes.Tetromino;
import com.tetrisrevision.unittests.UnitTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class RotatorTest {
  RowList rl = new RowList();
  TetrisPiece t = new TetrisPiece(new IPiece());

  RotatorTest() {

  }

  @BeforeEach
  void setUp() {
    rl.clear();
    t.reset();
  }

  @Test
  void unencumberedRotation() {
    int[] rotation = {0,1,2,3,0};

    for (int r : rotation) {
      assertEquals(r, t.getRotation());
      Rotator.apply(1, t, rl);
    }

    int[] decrRotation = {1,0,3,2,1,0};

    for (int r : decrRotation) {
      assertEquals(r, t.getRotation());
      Rotator.apply(-1, t, rl);
    }
  }

  @Test
  void rotationImpossible() {

    rl = UnitTestHelper.getFullRowList(new int[]{0,1,2,3,4});
    t.setRotation(3);
    t.setPrevRotation(2);
    t.setCenter(4, 2);

    int rotation = t.getRotation();
    int prevRotation = t.getPrevRotation();

    for (Row r : rl.get()) {
      r.remove(4);
      assertFalse(rl.cellIsNotEmpty(4, r.getY()));
    }

    UnitTestHelper.printLines(rl);
    UnitTestHelper.printLines(t.getBlocks());

    for (int r : new int[]{-1,1}) {
      Rotator.apply(r, t, rl);
      assertEquals(3, t.getRotation());
    }

    assertEquals(rotation, t.getRotation());
    assertEquals(prevRotation, t.getPrevRotation());
  }

}

