package com.tetrisrevision.unittests.things;

import com.tetrisrevision.constants.Constants;
import com.tetrisrevision.things.Square;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.unittests.UnitTestHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
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
    r.addSquare(y, new Square(x, c));
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

    Square b = r.get().get(0);
    Square bCloned = rCloned.get().get(0);

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

    int[] newSquare = {2, 2};
    Color newSquareColor = Color.green;
    newRL.addSquare(newSquare[0], new Square(newSquare[1], newSquareColor));

    rl.addRowList(newRL);

    Optional<Row> newRLRow = newRL.getRowByY(newSquare[1]);
    Optional<Row> rlRow = rl.getRowByY(newSquare[1]);

    assertTrue(newRLRow.isPresent());
    assertTrue(rlRow.isPresent());

    Optional<Square> newRLSquare = newRLRow.get().get(newSquare[0]);
    Optional<Square> rlSquare = rlRow.get().get(newSquare[0]);

    assertTrue(newRLSquare.isPresent());
    assertTrue(rlSquare.isPresent());
    assertSame(newRLSquare.get(), rlSquare.get());
    assertEquals(newRLSquare.get().getX(), rlSquare.get().getX());
    assertEquals(newRLSquare.get().getColor(), rlSquare.get().getColor());
  }

  @Test
  void addSquare() {
    int[] square = {1, 1};
    Color c = Color.green;
    rl.addSquare(square[1], new Square(square[0], c));

    assertEquals(1, rl.get().size());
    assertEquals(square[1], rl.get().get(0).getY());

    Optional<Square> b = rl.getSquare(square[0], square[1]);

    assertTrue(b.isPresent());
    assertEquals(square[0], b.get().getX());
    assertEquals(c, b.get().getColor());
  }

  @Test
  void cellIsNotEmpty() {
    int[] square = {3, 2};
    Color c = Color.yellow;
    rl.addSquare(square[1], new Square(square[0], c));

    assertTrue(rl.cellIsNotEmpty(square[0], square[1]));
  }

  @Test
  void clear() {
    int[] square = {3, 2};
    Color c = Color.yellow;
    rl.addSquare(square[1], new Square(square[0], c));

    assertTrue(rl.cellIsNotEmpty(square[0], square[1]));

    rl.clear();

    assertEquals(0, rl.get().size());
  }

  @Test
  void forEach() {
    int[][] coords = new int[][] {{0, 0}, {1, 1}};
    int incr = 1;
    rl = UnitTestHelper.getRowsWithSquares(coords);

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
  void getSquare() {
    int[][] coords = {{0, 2}, {1, 3}};
    rl = UnitTestHelper.getRowsWithSquares(coords);

    Optional<Square> b = rl.getSquare(coords[0][0], coords[0][1]);

    assertTrue(b.isPresent());
    assertEquals(coords[0][0], b.get().getX());

    Optional<Square> b1 = rl.getSquare(coords[1][0], coords[1][1]);

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
  void removeSquare() {
    int[][] coords = {{0, 2}, {1, 3}};
    rl = UnitTestHelper.getRowsWithSquares(coords);

    Optional<Square> b = rl.getSquare(coords[0][0], coords[0][1]);

    assertTrue(b.isPresent());

    rl.removeSquare(coords[0][0], coords[0][1]);
    b = rl.getSquare(coords[0][0], coords[0][1]);

    assertFalse(b.isPresent());
  }

  @Test
  void removeSquares() {
    int[][] coords = {{0, 2}, {1, 3}, {2, 4}, {4, 6}};
    rl = UnitTestHelper.getRowsWithSquares(coords);
    int[][] removeCoords = {coords[1], coords[3]};
    int[][] remainingCoords = {coords[0], coords[2]};
    RowList rl2 = UnitTestHelper.getRowsWithSquares(removeCoords);

    for (int[] c : coords) assertTrue(rl.cellIsNotEmpty(c[0], c[1]));

    rl.removeSquares(rl2);

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
  private int[] fullRows = new int[]{1,2,5,6};
  // Nonfull rows at these `y` values
  private int [] nonFullRowIdx = {0,3,4,7};
  // Cells in non-full rows
  private int [][] nonFullRows = {{0},{1,2}, {3,4,5},{6,7,8,9}};

  /**
   * The method should delete the first two rows at `y` of fullRows[0] and fullRows[1] It should
   * "lower" the `y` of the non-full rows above those by the number of deleted rows The next full
   * row should not be contiguous with the ones deleted. Its `y` should be the same Same goes for
   * the `y` of all Rows above it, full or not
   */
  @Test
  void deleteContiguousAndShift() {
    rl = UnitTestHelper.getFullRowList(fullRows);

    for (int i = 0; i < nonFullRowIdx.length; i++)
      rl.add(UnitTestHelper.getRowWithSquares(nonFullRowIdx[i], nonFullRows[i]));

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

    for (int i = 0; nonFullRowIdx[i] < fullRows[0]; i++) {
      int nonFullY = nonFullRowIdx[i];
      int[] nonFullRow = nonFullRows[i];

      for (int value : nonFullRow) {
        // Rows/squares below the lowest deleted row are unchanged
        assertTrue(rl.cellIsNotEmpty(value, nonFullY));
      }
    }

    // The proper number of rows remain
    assertEquals(fullRows.length + nonFullRows.length - contigDeleted, rl.get().size());

    // Make sure none of the set of contiguous full rows starting at `fullRows[0]` is still present
    checkFullRows(0, contigDeleted, false);

    // First full row after the contiguous deleted rows
    int nextFullRowByY = fullRows[contigDeleted];

    // Make sure the non-full rows that were between the deleted rows and the next full row have
    // been 'lowered`
    int l = checkNonFullRows(1, nextFullRowByY, contigDeleted);

    // The next full row is at `y` of `fullRows[contigDeleted]`.
    // The method shouldn't have deleted it, nor should it have changed its `y`.
    // It instead should have exited the loop and returned `contig`.
    // So make sure there are full rows at `fullRows[contigDeleted] to fullRows[fullRows.length -
    // 1]`.
    checkFullRows(contigDeleted, fullRows.length, true);

    // Makes sure non-full rows above that next full row have the unchanged `y` values
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
      int[] expectedSquares = nonFullRows[l];

      // The row contains the expected number of squares
      assertEquals(expectedSquares.length, r.get().size());

      for (int x : expectedSquares)
        // The row contains the expected squares
        assertTrue(rl.getSquare(x, nonFullY).isPresent());
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
      // full. If there is a row and it's full, then it was contiguous with the other full rows that
      // were
      // deleted; it would have been part of the contiguous set of full rows, and it should have
      // been
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
