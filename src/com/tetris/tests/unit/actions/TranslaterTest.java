package com.tetris.tests.unit.actions;

import com.tetris.game.actions.Translater;
import com.tetris.game.constants.Constants;
import com.tetris.game.things.Center;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;
import com.tetris.game.things.tetrominoes.IBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslaterTest {
  private final ActiveBlock t = new ActiveBlock(new IBlock());
  private final Center c = new Center(5,10);
  private final RowList rl = new RowList();
  private final int[] translateValue = {-1, -1};
  private final Center[] cannotTranslatePosition = {
      new Center(1,10), // left
      new Center(7,10), // right
      new Center(c.getX(), Constants.bottomRow) // down
  };
  private int[][] cannotTranslateMovement = {
      {-1, 0},
      {1, 0},
      {0, -1}
  };

  @BeforeEach
  void before() {
    t.reset();
    t.setCenter(c.getX(), c.getY());
  }

  @Test
  void testTranslate() {
    assertEquals(c.getX(), t.getCenter().getX());
    assertEquals(c.getY(), t.getCenter().getY());

    boolean translateResult = Translater.translate(t, rl, translateValue[0], translateValue[1], false);

    assertTrue(translateResult);
    assertEquals(c.getX() + translateValue[0], t.getCenter().getX());
    assertEquals(c.getY() + translateValue[1], t.getCenter().getY());
  }

  @Test
  void testCannotTranslate() {
    assertEquals(c.getX(), t.getCenter().getX());
    assertEquals(c.getY(), t.getCenter().getY());

    for (int i = 0; i < cannotTranslateMovement.length; i++) {
      int x = cannotTranslateMovement[i][0];
      int y = cannotTranslateMovement[i][1];
      Center newCenter = cannotTranslatePosition[i];

      t.setCenter(newCenter);

      boolean translateResult = Translater.translate(t, rl, x, y, false);

      assertFalse(translateResult);
      assertEquals(newCenter.getX(), t.getCenter().getX());
      assertEquals(newCenter.getY(), t.getCenter().getY());
    }
  }

  @Test
  void hardDrop() {
    assertEquals(c.getX(), t.getCenter().getX());
    assertEquals(c.getY(), t.getCenter().getY());

    Translater.hardDrop(t, rl);

    assertEquals(0, t.getCenter().getY());
  }
}
