package com.tetrisrevision.unittests.actions;

import com.tetrisrevision.actions.PlacementTester;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.tetrominoes.OPiece;
import com.tetrisrevision.unittests.UnitTestHelper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.tetrisrevision.unittests.UnitTestHelper.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlacementTesterTest {
  private RowList playField = new RowList();
  private TetrisPiece t = new TetrisPiece(new OPiece());

  PlacementTesterTest() {
    // Fill bottom row
    playField.add(getFullRow(0));
    // Ensure that row is full
    // Position o piece such that it overlaps with bottom row
    t.setCenter(Constants.fromLeft(4), Constants.bottomRow);
    // o piece's expected coordinates
    List<int[]> expectedCoords =
        Arrays.asList(
            new int[] {Constants.fromLeft(4), Constants.bottomRow},
            new int[] {Constants.fromLeft(5), Constants.bottomRow},
            new int[] {Constants.fromLeft(4), Constants.fromBottom(1)},
            new int[] {Constants.fromLeft(5), Constants.fromBottom(1)});

    // Check piece's position
    assertTrue(
        () -> {
          RowList p = t.getBlocks();
          // Ensure there are two rows
          if (p.get().size() != 2) return false;

          for (Row r1 : p.get()) {
            // Ensure each row contains two blocks
            if (r1.get().size() != 2) return false;
            // Ensure each block is where expected
            for (Block b : r1.get())
              if (!expectedCoords.contains(new int[] {b.getX(), r1.getY()})) return false;
          }

          return true;
        },
        "O piece should fill four cells: {4,0}, {5,0}, {4,1}, {5,1}. It doesn't.");
  }

  Boolean ccbo(TetrisPiece t1, RowList r1) {
    return PlacementTester.cellsCanBeOccupied(t1, r1);
  }

  // Ensure method correctly determines whether a cell can be occupied
  @Test
  void cellsCanBeOccupied() {
    // Piece and bottom row should overlap. Blocks in piece should not be able to fill corresponding
    // cells on board
    assertFalse(ccbo(t, playField), "Function should return false, as not all cells filled by piece can be filled on playfield. There is overlap.");
    // Move piece up 1
    t.setCenter(Constants.fromLeft(4), Constants.fromBottom(1));
    // Piece and row shouldn't overlap. All cells piece fields should be empty on playfield.
    assertTrue(ccbo(t, playField), "Function should return true. All cells filled by piece can be filled on playfield");
    // Move piece to disallowed position
    t.setCenter(Constants.fromLeft(-1), Constants.fromBottom(1));
    // Cells are out of bounds
    assertFalse(ccbo(t, playField), "Function should return false. Piece position is out of bounds. Cells out of bounds cannot be filled.");
  }

  // Ensure method correctly determines whether cell is in bounds
  //  int[]invalidx = {xs[0], xs[4], xs[5]};
  //  int[]invalidy = {ys[0], ys[6]};
  //  BiFunction<Integer, Integer, String> m1 = (Integer x, Integer y) -> "Cell " + x + ", " + y + "
  // should be oob";
  //  BiFunction<Integer, Integer, String> m2 = (Integer x, Integer y) -> "Cell " + x + ", " + y + "
  // should be in bounds";
  @Test
  void inBounds() {
    for (int y : ys)
      for (int x : xs)
        if (x == xs[0] || x == xs[4] || x == xs[5] || y == ys[0] || y == ys[6]) {
          assertFalse(
              PlacementTester.inBounds(x, y),
              "Cell {" + x + ", " + y + "} should be out of bounds.");
        } else {
          assertTrue(
              PlacementTester.inBounds(x, y), "Cell {" + x + ", " + y + "} should be in bounds.");
        }
  }

  void testCannotOccupy(RowList blocksToTest, RowList fullCells, RowList playField) {
    // Loop through rows to be tested
    for (Row r : blocksToTest.get()) {
      Row row = null;

      // Get row containing cells that should be full
      if (fullCells.getRowByY(r.getY()).isPresent())
        row = fullCells.getRowByY(r.getY()).get();

      // Loop through blocks to be tested
      for (Block b : r.get())
        // If there's a block in blocksToTest and a block in the corresponding cell in fullCells, then it shouldn't be possible to occupy the cell
        if (row != null && fullCells.cellIsNotEmpty(b.getX(), row.getY())) {
          assertTrue(
              PlacementTester.cellCannotBeOccupied(r.getY(), b.getX(), playField),
              "Cell at {" + b.getX() + ", " + r.getY() + "} should be full or out of bounds");
        // Else there's a block in blocksToTest that isn't present in fullCells;
        } else {
          assertFalse(
              PlacementTester.cellCannotBeOccupied(r.getY(), b.getX(), this.playField),
              "Cell at {" + b.getX() + ", " + r.getY() + "} should be empty and in bounds");
        }
      }
  }

  @Test
  void cellCannotBeOccupied() throws CloneNotSupportedException {
    RowList rlTest = UnitTestHelper.getFullRowList(new int[]{0, 1});
    RowList rlFull = UnitTestHelper.getFullRowList(new int[]{0});

    System.out.println("Bottom row of playField should be full. It shouldn't be possible to fill any of the squares. Testing that row and row above.");

    testCannotOccupy(rlTest, rlFull, playField);

    System.out.println("Clear playfield. Make sure it's possible to fill all in-bounds cells");

    playField.get().clear();

    RowList rlTest2 = UnitTestHelper.getFullRowList(IntStream.range(Constants.bottomRow, Constants.height).toArray());
    RowList rlFull2 = UnitTestHelper.getFullRowList(new int[]{});

    testCannotOccupy(rlTest2, rlFull2, playField);

    System.out.println("Full playfield.");

    playField = rlTest2.clone();
    rlFull2 = rlTest2.clone();

    testCannotOccupy(rlTest2, rlFull2, playField);
  }
}
