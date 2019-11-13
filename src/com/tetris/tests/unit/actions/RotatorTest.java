package com.tetris.tests.unit.actions;

import com.tetris.game.actions.Rotator;
import com.tetris.game.things.Row;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;
import com.tetris.game.things.tetrominoes.IBlock;
import com.tetris.tests.unit.UnitTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotatorTest {
  RowList rl = new RowList();
  ActiveBlock t = new ActiveBlock(new IBlock());

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
    UnitTestHelper.printLines(t.getSquares());

    for (int r : new int[]{-1,1}) {
      Rotator.apply(r, t, rl);
      assertEquals(3, t.getRotation());
    }

    assertEquals(rotation, t.getRotation());
    assertEquals(prevRotation, t.getPrevRotation());
  }

}

