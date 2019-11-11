package com.tetrisrevision.unittests.actions;

import com.tetrisrevision.actions.Rotator;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.*;
import com.tetrisrevision.things.tetrominoes.IPiece;
import com.tetrisrevision.things.tetrominoes.OPiece;
import com.tetrisrevision.things.tetrominoes.Tetromino;
import com.tetrisrevision.things.tetrominoes.TetrominoEnum;
import com.tetrisrevision.unittests.UnitTestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class WallKickerTest {
  private TetrisPiece t = new TetrisPiece(new IPiece());
  private RowList rl = new RowList();
  private static Center c = new Center(3, 10);

  public static Center getCenter() {
    return c;
  }

  WallKickerTest() {
    Constants.setWidth(20);
    c = new Center(Constants.width / 2, 10);
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

        RowList kickedPosition = t.getBlocks();

        UnitTestHelper.fillPlayField(rl);
        rl.removeBlocks(t.getBlocks());

        for (Row r : t.getBlocks().get())
          for (Block b : r.get()) {
            assertFalse(rl.cellIsNotEmpty(b.getX(), r.getY()));
          }

        // UnitTestHelper.printNonFullLines(rl, "\t\t\t");

        t.incrementRotation(-incr);
        t.incrementRotation(-incr);
        t.incrementRotation(incr);
        t.setCenter(c);

        int rotateResult = Rotator.apply(incr, t, rl);

        assertEquals(j, rotateResult);

        // UnitTestHelper.printNonFullLines(t.getBlocks(), "\t\t\t");

        for (Row r : kickedPosition.get())
          for (Block b : r.get()) {
            assertFalse(rl.cellIsNotEmpty(b.getX(), r.getY()));
            assertTrue(t.getBlocks().cellIsNotEmpty(b.getX(), r.getY()));
          }
      }
    }
  }

  @Test
  void runTestsByPiece() {
    t.setCenter(c);
    Tetromino[] tetrominoes =
        Arrays.stream(TetrominoEnum.values())
            .filter(te -> !(te.get() instanceof OPiece))
            .map(TetrominoEnum::get)
            .toArray(Tetromino[]::new);

    for (var t : tetrominoes) test(1, t);
    for (var t : tetrominoes) test(-1, t);
  }
}
