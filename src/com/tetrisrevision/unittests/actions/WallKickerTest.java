package com.tetrisrevision.unittests.actions;

import com.tetrisrevision.actions.Rotator;
import com.tetrisrevision.constants.Constants;
import com.tetrisrevision.things.*;
import com.tetrisrevision.things.tetrominoes.IBlock;
import com.tetrisrevision.things.tetrominoes.OBlock;
import com.tetrisrevision.things.Tetromino;
import com.tetrisrevision.things.tetrominoes.TetrominoEnum;
import com.tetrisrevision.unittests.UnitTestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WallKickerTest {
  private ActiveBlock t = new ActiveBlock(new IBlock());
  private RowList rl = new RowList();
  private static Center c;

  public static Center getCenter() {
    return c;
  }

  WallKickerTest() {
  }

  void test(int incr, Tetromino p) {
    t.setTetromino(p);
    // System.out.println(t.getTetromino().getClass().getName());
    t.reset();

    for (int i = 0; i < t.getRotationMax(); i++) {
      t.incrementRotation(incr);
      int rotation = t.getRotation();
      int prevRotation = t.getPrevRotation();
      int[][] kickData = t.getKickData().get(prevRotation).get(rotation);

      // System.out.println("====" + i);
      // System.out.print("Rotation: " + prevRotation + ", " + rotation);
      // for (int[] kick : kickData) System.out.print("{" + kick[0] + ", " + kick[1] + "}");
      //  System.out.println();

      for (int j = 0; j < kickData.length; j++) {
        int[] kick = kickData[j];

        // System.out.println("\t--- {" + kick[0] + ", " + kick[1] + "}");

        t.setCenter(c);
        t.setRotation(rotation);
        t.setPrevRotation(prevRotation);
        t.setCenter(t.getCenter().getX() + kick[0], t.getCenter().getY() + kick[1]);

        RowList kickedPosition = t.getSquares();

        UnitTestHelper.fillPlayField(rl);
        rl.removeSquares(t.getSquares());

        for (Row r : t.getSquares().get())
          for (Square b : r.get()) {
            assertFalse(rl.cellIsNotEmpty(b.getX(), r.getY()));
          }

        // UnitTestHelper.printNonFullLines(rl, "\t\t\t");

        t.incrementRotation(-incr);
        t.incrementRotation(-incr);
        t.incrementRotation(incr);
        t.setCenter(c);

        int rotateResult = Rotator.apply(incr, t, rl);

        assertEquals(j, rotateResult);

        // UnitTestHelper.printNonFullLines(t.getSquares(), "\t\t\t");

        for (Row r : kickedPosition.get())
          for (Square b : r.get()) {
            assertFalse(rl.cellIsNotEmpty(b.getX(), r.getY()));
            assertTrue(t.getSquares().cellIsNotEmpty(b.getX(), r.getY()));
          }
      }
    }
  }

  @Test
  void runTestsByPiece() {
    Constants.setWidth(20);

    c = new Center(Constants.width / 2, 10);

    t.setCenter(c);
    Tetromino[] tetrominoes =
        Arrays.stream(TetrominoEnum.values())
            .filter(te -> !(te.get() instanceof OBlock))
            .map(TetrominoEnum::get)
            .toArray(Tetromino[]::new);

    for (var t : tetrominoes) test(1, t);
    for (var t : tetrominoes) test(-1, t);

    Constants.setWidth(10);
  }
}
