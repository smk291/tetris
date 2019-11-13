package com.tetris.tests.unit.actions;

import com.tetris.game.actions.PlacementTester;
import com.tetris.game.things.Square;
import com.tetris.game.things.Row;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;
import com.tetris.game.things.tetrominoes.OBlock;
import com.tetris.tests.unit.UnitTestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.tetris.game.constants.Constants.*;
import static com.tetris.tests.unit.UnitTestHelper.*;
import static org.junit.jupiter.api.Assertions.*;

class PlacementTesterTest {
  List<int[]> expectedCoords =
      Arrays.asList(
          new int[] {fromLeft(4), bottomRow},
          new int[] {fromLeft(5), bottomRow},
          new int[] {fromLeft(4), fromBottom(1)},
          new int[] {fromLeft(5), fromBottom(1)});
  private RowList playField = new RowList();
  private ActiveBlock t = new ActiveBlock(new OBlock());

  PlacementTesterTest() {
    // Fill bottom row
    playField.add(getFullRow(0));
    // Position o block such that it overlaps with bottom row
    t.setCenter(fromLeft(4), bottomRow);
  }

  @Test
  void blockPositionTest() {
    assertAll(
        () -> {
          RowList p = t.getSquares();
          // Ensure there are two rows
          assertEquals(2, p.get().size(), "There aren't two rows.");

          for (Row r1 : p.get()) {
            assertEquals(
                2,
                r1.get().size(),
                "Row " + r1.getY() + " is " + r1.get().size() + " long instead of 2");
            for (Square b : r1.get())
              assertTrue(
                  expectedCoords.stream().anyMatch(c -> c[0] == b.getX() && c[1] == r1.getY()),
                  "Coords are wrong.");
          }
        });
  }

  Boolean ccbo(ActiveBlock t1, RowList r1) {
    return PlacementTester.cellsCanBeOccupied(t1, r1);
  }

  // Ensure method correctly determines whether a cell can be occupied
  @Test
  void cellsCanBeOccupied() {
    // Block and bottom row should overlap. Squares in block should not be able to fill corresponding
    // cells on board
    assertFalse(
        ccbo(t, playField),
        "Function should return false, as not all cells filled by block can be filled on playfield. There is overlap.");
    // Move block up 1
    t.setCenter(fromLeft(4), fromBottom(1));
    // Block and row shouldn't overlap. All cells block fields should be empty on playfield.
    assertTrue(
        ccbo(t, playField),
        "Function should return true. All cells filled by block can be filled on playfield");
    // Move block to disallowed position
    t.setCenter(fromLeft(-1), fromBottom(1));
    // Cells are out of bounds
    assertFalse(
        ccbo(t, playField),
        "Function should return false. Block position is out of bounds. Cells out of bounds cannot be filled.");
  }

  @Test
  void inBounds() {
    for (int y : ys)
      for (int x : xs)
        if (x > 9 || x < 0 || y > 19 || y < 0) {
          assertFalse(
              PlacementTester.inBounds(x, y),
              "Cell {" + x + ", " + y + "} should be out of bounds.");
        } else {
          assertTrue(
              PlacementTester.inBounds(x, y), "Cell {" + x + ", " + y + "} should be in bounds.");
        }
  }

  void testCannotOccupy(RowList squaresToTest, RowList fullCells, RowList playField) {
    // Loop through rows to be tested
    for (Row r : squaresToTest.get()) {
      Row row = null;

      // Get row containing cells that should be full
      if (fullCells.getRowByY(r.getY()).isPresent()) row = fullCells.getRowByY(r.getY()).get();

      // Loop through squares to be tested
      for (Square b : r.get())
        // If there's a square in squaresToTest and a square in the corresponding cell in fullCells,
        // then it shouldn't be possible to occupy the cell
        if (row != null && fullCells.cellIsNotEmpty(b.getX(), row.getY())) {
          assertTrue(
              PlacementTester.cellCannotBeOccupied(r.getY(), b.getX(), playField),
              "Cell at {" + b.getX() + ", " + r.getY() + "} should be full or out of bounds");
          // Else there's a square in squaresToTest that isn't present in fullCells;
        } else {
          assertFalse(
              PlacementTester.cellCannotBeOccupied(r.getY(), b.getX(), this.playField),
              "Cell at {" + b.getX() + ", " + r.getY() + "} should be empty and in bounds");
        }
    }
  }

  @Test
  void cellCannotBeOccupied() throws CloneNotSupportedException {
    RowList rlTest = UnitTestHelper.getFullRowList(new int[] {0, 1});
    RowList rlFull = UnitTestHelper.getFullRowList(new int[] {0});

    testCannotOccupy(rlTest, rlFull, playField);

    playField.get().clear();

    RowList rlTest2 = UnitTestHelper.getFullRowList(IntStream.range(bottomRow, height).toArray());
    RowList rlFull2 = UnitTestHelper.getFullRowList(new int[] {});

    testCannotOccupy(rlTest2, rlFull2, playField);

    playField = rlTest2.clone();
    rlFull2 = rlTest2.clone();

    testCannotOccupy(rlTest2, rlFull2, playField);
  }
}
