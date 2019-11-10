package com.tetrisrevision.unittests.things;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.unittests.UnitTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RowListTest {
  private RowList rl = new RowList();

  @BeforeEach
  void setUp() {
    rl.clear();
  }

  @AfterEach
  void tearDown() {}

  void insert(RowList r, int y, int x, Color c) {
    r.addBlock(y, new Block(x, c));
  }

  @Test
  void get() {
    assertNotNull(rl.get());
    insert(rl, 1, 5, Color.red);
    assertEquals(1, rl.get().size());
  }

  @Test
  void testClone() throws CloneNotSupportedException {
    int[] insert = new int[] {1, 4};
    Color insertColor = Color.red;
    RowList rlClone = rl.clone();

    assertNotSame(rlClone, rl);

    insert(rl, insert[0], insert[1], insertColor);
    rlClone = rl.clone();

    assertNotSame(rlClone, rl);

    Row r = rl.get().get(0);
    Row rCloned = rlClone.get().get(0);

    assertNotSame(rCloned, rl);
    assertEquals(rCloned.get().size(), r.get().size());

    Block b = r.get().get(0);
    Block bCloned = rCloned.get().get(0);

    assertNotEquals(bCloned, b);
    assertEquals(bCloned.getX(), b.getX());
    assertEquals(bCloned.getColor(), b.getColor());
  }

  @Test
  void add() {
    int[] newRows = {1};

    assertEquals(0, rl.get().size());

    for (int i : newRows) rl.add(new Row(i));

    assertEquals(newRows.length, rl.get().size());
  }

  @Test
  void addRowList() {
    RowList newRL = new RowList();

    int[] newRows = {2, 3, 4};

    for (int i : newRows) {
      newRL.add(new Row(i));
    }

    assertEquals(newRows.length, newRL.get().size());

    rl.addRowList(newRL);

    assertEquals(newRows.length, rl.get().size());

    for (int i = 0; i < rl.get().size(); i++) {
      assertSame(newRL.get().get(i), rl.get().get(i));
    }

    int[] newBlock = {2, 2};
    Color newBlockColor = Color.green;
    newRL.addBlock(newBlock[0], new Block(newBlock[1], newBlockColor));

    rl.addRowList(newRL);

    Optional<Row> newRLRow = newRL.getRowByY(newBlock[1]);
    Optional<Row> rlRow = rl.getRowByY(newBlock[1]);

    assertTrue(newRLRow.isPresent());
    assertTrue(rlRow.isPresent());

    Optional<Block> newRLBlock = newRLRow.get().get(newBlock[0]);
    Optional<Block> rlBlock = rlRow.get().get(newBlock[0]);

    assertTrue(newRLBlock.isPresent());
    assertTrue(rlBlock.isPresent());
    assertSame(newRLBlock.get(), rlBlock.get());
    assertEquals(newRLBlock.get().getX(), rlBlock.get().getX());
    assertEquals(newRLBlock.get().getColor(), rlBlock.get().getColor());
  }

  @Test
  void addBlock() {
    int[] block = {1, 1};
    Color c = Color.green;
    rl.addBlock(block[1], new Block(block[0], c));

    assertEquals(1, rl.get().size());
    assertEquals(block[1], rl.get().get(0).getY());

    Optional<Block> b = rl.getBlock(block[0], block[1]);

    assertTrue(b.isPresent());
    assertEquals(block[0], b.get().getX());
    assertEquals(c, b.get().getColor());
  }

  @Test
  void cellIsNotEmpty() {
    int[] block = {3, 2};
    Color c = Color.yellow;
    rl.addBlock(block[1], new Block(block[0], c));

    assertTrue(rl.cellIsNotEmpty(block[0], block[1]));
  }

  @Test
  void clear() {
    int[] block = {3, 2};
    Color c = Color.yellow;
    rl.addBlock(block[1], new Block(block[0], c));

    assertTrue(rl.cellIsNotEmpty(block[0], block[1]));

    rl.clear();

    assertEquals(0, rl.get().size());
  }

  @Test
  void forEach() {
    int[][] coords = new int[][] {{0, 0}, {1, 1}};
    int incr = 1;
    rl = UnitTestHelper.getRowsWithBlocks(coords);

    assertAll(
        () -> {
          Row r = rl.get().get(0);
          Row r1 = rl.get().get(1);

          assertNotNull(r);
          assertNotNull(r1);

          rl.forEach(row -> row.setY(r.getY() + incr));

          assertEquals(coords[0][1] + incr, r.getY());
          assertEquals(coords[1][1] + incr, r1.getY());
        });
  }

  @Test
  void testGet() {
    int y = 0;
    rl.add(new Row(y));

    Row r = rl.get(0);

    assertEquals(1, rl.get().size());
    assertNotNull(r);
    assertEquals(y, r.getY());
  }

  @Test
  void getBlock() {
    int[][] coords = {{0, 2}, {1, 3}};
    rl = UnitTestHelper.getRowsWithBlocks(coords);

    Optional<Block> b = rl.getBlock(coords[0][0], coords[0][1]);

    assertTrue(b.isPresent());
    assertEquals(coords[0][0], b.get().getX());

    Optional<Block> b1 = rl.getBlock(coords[1][0], coords[1][1]);

    assertTrue(b1.isPresent());
    assertEquals(coords[1][0], b1.get().getX());
  }

  @Test
  void getHighestY() {
    int[] ys = {0, 10, 11};

    for (int y : ys) rl.add(new Row(y));

    assertEquals(ys[2], rl.getHighestY());
  }

  @Test
  void getHighestFullRowIndexAfterInsertion() {
    int[] ys = {0, 10, 11, 13};
    int[] ys2 = {0, 10, 11, 13};
    rl = UnitTestHelper.getFullRowList(ys);

    RowList rl2 = new RowList();

    for (int y : ys2) rl2.add(new Row(y));

    assertEquals(ys.length, rl.get().size());
    assertEquals(ys2.length, rl2.get().size());

    for (int i : new int[] {0, 1, 2}) assertTrue(rl.rowIsFull(i));

    assertEquals(ys.length - 1, rl.highestFullRowIndexAfterInsertion(rl2));
  }

  @Test
  void getLowestY() {
    int[] ys = {0, 10, 11, 13};
    rl = UnitTestHelper.getFullRowList(ys);

    assertEquals(ys[0], rl.getLowestY());
  }

  @Test
  void lowestFullRowIndexAfterInsertion() {
    int[] ys = {0, 10, 11, 13};
    int[] ys2 = {0, 10, 11, 13};
    rl = UnitTestHelper.getFullRowList(ys);

    RowList rl2 = new RowList();

    for (int y : ys2) rl2.add(new Row(y));

    assertEquals(ys.length, rl.get().size());
    assertEquals(ys2.length, rl2.get().size());

    for (int i : new int[] {0, 1, 2}) assertTrue(rl.rowIsFull(i));

    assertEquals(ys[0], rl.lowestFullRowIndexAfterInsertion(rl2));
  }

  @Test
  void getRowByY() {
    int[] ys = {0, 10, 11, 13};
    rl = UnitTestHelper.getFullRowList(ys);

    Optional<Row> r = rl.getRowByY(ys[1]);

    assertTrue(r.isPresent());
    assertEquals(ys[1], r.get().getY());
  }

  @Test
  void getRowIdxFromY() {
    int[] ys = {0, 10, 11, 13};
    rl = UnitTestHelper.getFullRowList(ys);
    assertEquals(1, rl.getRowIdxFromY(10));
  }

  @Test
  void removeBlock() {
    int[][] coords = {{0, 2}, {1, 3}};
    rl = UnitTestHelper.getRowsWithBlocks(coords);

    Optional<Block> b = rl.getBlock(coords[0][0], coords[0][1]);

    assertTrue(b.isPresent());

    rl.removeBlock(coords[0][0], coords[0][1]);
    b = rl.getBlock(coords[0][0], coords[0][1]);

    assertFalse(b.isPresent());
  }

  @Test
  void removeBlocks() {
    int[][] coords = {{0, 2}, {1, 3}, {2, 4}, {4, 6}};
    rl = UnitTestHelper.getRowsWithBlocks(coords);
    int[][] removeCoords = {coords[1], coords[3]};
    int[][] remainingCoords = {coords[0], coords[2]};
    RowList rl2 = UnitTestHelper.getRowsWithBlocks(removeCoords);

    for (int[] c : coords) assertTrue(rl.cellIsNotEmpty(c[0], c[1]));

    rl.removeBlocks(rl2);

    for (int[] c : remainingCoords) assertTrue(rl.cellIsNotEmpty(c[0], c[1]));

    for (int[] c : removeCoords) assertFalse(rl.cellIsNotEmpty(c[0], c[1]));
  }

  @Test
  void rowIsFull() {
    int[] ys = {1};
    rl = UnitTestHelper.getFullRowList(ys);

    assertEquals(ys.length, rl.get().size());
    assertTrue(rl.rowIsFull(0));
  }

  @Test
  void size() {
    assertEquals(0, rl.size());
    rl.add(new Row(0));
    assertEquals(1, rl.size());
  }

  @Test
  void sortByY() {
    int[] ys = {11, 12, 9, 3};
    int[] sorted = {3, 9, 11, 12};
    for (int y : ys) rl.add(new Row(y));

    ArrayList<Row> rows = rl.get();

    for (int i : new int[] {0, 1, 2, 3}) assertEquals(ys[i], rows.get(i).getY());

    rl.sortByY();

    for (int i = 0; i < 4; i++) assertEquals(sorted[i], rows.get(i).getY());
  }

  // Full rows at these `y` values
  int[] fullRows = new int[]{1,2,5,6};
  // Nonfull rows at these `y` values
  int [] nonFullRowIdx = {0,3,4,7};
  // Cells in non-full rows
  int [][] nonFullRows = {{0},{1,2}, {3,4,5},{6,7,8,9}};

  /**
   * The method should delete the first two rows at `y` of fullRows[0] and fullRows[1]
   * It should "lower" the `y` of the non-full rows above those by the number of deleted rows
   * The next full row should not be contiguous with the ones deleted.
   * Its `y` should be the same
   * Same goes for the `y` of all Rows above it, full or not
   */

  @Test
  void deleteContiguousAndShift() {
    rl = UnitTestHelper.getFullRowList(fullRows);

    for (int i = 0; i < nonFullRowIdx.length; i++)
      rl.add(UnitTestHelper.getRowWithBlocks(nonFullRowIdx[i], nonFullRows[i]));

    rl.sortByY();

    // The RowList has the right number of rows
    assertEquals(fullRows.length + nonFullRows.length, rl.get().size());

    int contigDeleted = rl.deleteContiguousAndShift(fullRows[0], 0);
    int expectedContigDeleted = 0;

    do {
      expectedContigDeleted++;
    } while (fullRows[expectedContigDeleted + 1] - 1 == fullRows[expectedContigDeleted]);

    // The correct number of rows were deleted
    assertEquals(++expectedContigDeleted, contigDeleted);

    for (int i = 0 ; nonFullRowIdx[i] < fullRows[0]; i++) {
      int nonFullY = nonFullRowIdx[i];
      int[] nonFullRow = nonFullRows[i];

      for (int value : nonFullRow) {
        // Rows/blocks below the lowest deleted row are unchanged
        assertTrue(rl.cellIsNotEmpty(value, nonFullY));
      }
    }

    // The proper number of rows remain
    assertEquals(fullRows.length + nonFullRows.length - contigDeleted, rl.get().size());

    // None of the set of contiguous full rows starting at `fullRows[0]` is still present
//    for (int i = 0; i < contigDeleted; i++) {
//      int deletedFullRowY = fullRows[i];
//
//      // If a row is present at `deletedFullRowY`, then it shouldn't be one of the full rows
//      // If there is and it's full, then it was contiguous with the other full rows that were deleted.
//      // That is, it would have been part of the contiguous set of full rows, and it should have been deleted.
//      Optional<Row> r = rl.getRowByY(deletedFullRowY);
//      r.ifPresent(row -> assertNotEquals(Constants.width, row.size()));
//    }

    checkFullRows(0, contigDeleted, false);

    // First full row after the contiguous deleted rows
    int nextFullRowByY = fullRows[contigDeleted];

//    int l;

    // Make sure the non-full rows that were between the deleted rows and the next full row have been 'lowered`
//    for (l = 1; nonFullRowIdx[l] < nextFullRowByY; l++) {
//      // Get "lowered" y of non-full row.
//      int nonFullY = nonFullRowIdx[l] - contigDeleted;
//      Optional<Row> optR = rl.getRowByY(nonFullY);
//      // A row is present at that `y`
//      assertTrue(optR.isPresent());
//
//      Row r = optR.get();
//      int[] expectedBlocks = nonFullRows[l];
//
//      // The row contains the expected number of blocks
//      assertEquals(expectedBlocks.length, r.get().size());
//
//      for (int x : expectedBlocks)
//        // The row contains the expected blocks
//        assertTrue(rl.getBlock(x, nonFullY).isPresent());
//    }

    int l = checkNonFullRows(1, nextFullRowByY, contigDeleted);

    // The next full row is at `y` of `fullRows[contigDeleted]`.
    // The method shouldn't have deleted it, nor should it have changed its `y`.
    // It instead should have exited the loop and returned `contig`.
    // So make sure there are full rows at `fullRows[contigDeleted] to fullRows[fullRows.length - 1]`.
//    for (int i = contigDeleted; i < fullRows.length; i++) {
//      nextFullRowByY = fullRows[i];
//      Optional<Row> r = rl.getRowByY(nextFullRowByY);
//      // The row with that `y` exists
//      assertTrue(r.isPresent());
//      // The row is full
//      assertEquals(Constants.width, r.get().size());
//    }

    checkFullRows(contigDeleted, fullRows.length, true);

    // And if there are non-full rows about that next full row, their `y`s should be unchanged too
//    for (; l < nonFullRowIdx.length; l++) {
//      Optional<Row> optR = rl.getRowByY(nonFullRowIdx[l]);
//
//      // The row with the expected `y` exists.
//      assertTrue(optR.isPresent());
//
//      Row r = optR.get();
//
//      int[] expectedBlocks = nonFullRows[l];
//      // It has the expected number of blocks
//      assertEquals(expectedBlocks.length, r.get().size());
//
//      for (int x : expectedBlocks)
//        // The expected blocks are present
//        assertTrue(rl.getBlock(x, nonFullRowIdx[l]).isPresent());
//    }
//
    checkNonFullRows(l, nonFullRowIdx.length, 0);
  }

  int checkNonFullRows(int startFrom, int stopAt, int offset) {
    int l;

    for (l = startFrom; nonFullRowIdx[l] < stopAt; l++) {
      // Get y of non-full row.
      int nonFullY = nonFullRowIdx[l] - offset;
      Optional<Row> optR = rl.getRowByY(nonFullY);
      // A row is present at that `y`
      assertTrue(optR.isPresent());

      Row r = optR.get();
      int[] expectedBlocks = nonFullRows[l];

      // The row contains the expected number of blocks
      assertEquals(expectedBlocks.length, r.get().size());

      for (int x : expectedBlocks)
        // The row contains the expected blocks
        assertTrue(rl.getBlock(x, nonFullY).isPresent());
    }

    System.out.println("3");

    return l;
  }

  void checkFullRows(int startFrom, int stopAt, boolean shouldBePresent) {
    System.out.println("5");
    for (int i = startFrom; i < stopAt; i++) {
      int deletedFullRowY = fullRows[i];
      Optional<Row> r = rl.getRowByY(deletedFullRowY);

      // If !shouldBePresent, then if a row is present at `deletedFullRowY`, then it shouldn't be
      // full. If there is a row and it's full, then it was contiguous with the other full rows that were
      // deleted; it would have been part of the contiguous set of full rows, and it should have been
      // deleted.
      if (!shouldBePresent) {
        r.ifPresent(row -> assertNotEquals(Constants.width, row.size()));
      // If shouldBePresent, just make sure the row exists and is full
      } else {
        // The row with that `y` exists
        assertTrue(r.isPresent());
        // The row is full
        assertEquals(Constants.width, r.get().size());
      }
    }
  }
}
