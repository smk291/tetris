package com.tetris.unittests;

import com.tetris.constants.Constants;
import com.tetris.things.Square;
import com.tetris.things.Row;
import com.tetris.things.RowList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTestHelperTest {
  @Test
  static void getFullRow() {
    Row r = UnitTestHelper.getFullRow(0);
    assertEquals(
        10, r.get().size(), "Row should contain ten squares. It contains " + r.get().size());
  }

  @Test
  void getEmptyRow() {
    assertTrue(
        UnitTestHelper.getEmptyRow(0).isEmpty(), "Row should be empty, containing no squares");
  }

  @Test
  void fillPlayField() {
    int[] allCells = IntStream.range(Constants.leftBound, Constants.width).toArray();
    int[] allRows = IntStream.range(Constants.bottomRow, Constants.height).toArray();

    assertAll(
        () -> {
          assertAll(
              () -> {
                assertEquals(Constants.width, allCells.length);

                for (int i : new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
                  assertTrue(Arrays.stream(allCells).anyMatch(c -> c == i));
              });

          assertAll(
              () -> {
                assertEquals(Constants.height, allRows.length);

                for (int i :
                    new int[] {
                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19
                    }) assertTrue(Arrays.stream(allRows).anyMatch(r -> r == i));
              });

          RowList rl = UnitTestHelper.fillPlayField(new RowList());

          for (int y : allRows) {
            Row r;
            assertTrue(rl.getRowByY(y).isPresent(), "Row " + y + " is not present.");
            r = rl.getRowByY(y).get();

            Integer[] filledCells = r.get().stream().map(Square::getX).toArray(Integer[]::new);

            assertAll(
                () -> {
                  assertEquals(Constants.width, filledCells.length);

                  for (int i : allCells)
                    assertTrue(Arrays.stream(filledCells).anyMatch(c -> c == i));
                });
          }
        });
  }

  @Test
  void getFullRowList() {
    RowList rl = UnitTestHelper.getFullRowList(new int[] {0});

    assertAll(
        () -> {
          assertEquals(
              1, rl.get().size(), "RowList should be one element long, not" + rl.get().size());
          assertEquals(
              Constants.width,
              rl.get().get(0).get().size(),
              "Row should be 10 wide, i.e. full, not " + rl.get().get(0).get().size());

          int[] allCells = IntStream.range(Constants.leftBound, Constants.width).toArray();
          Integer[] squares =
              rl.get().get(0).get().stream().map(Square::getX).toArray(Integer[]::new);

          assertAll(
              () -> {
                assertEquals(allCells.length, squares.length);
                for (int i : squares) assertTrue(Arrays.stream(allCells).anyMatch(c -> c == i));
              });
        });
  }

  @Test
  void getRowWithSquares() {
    int[] testInts = {0, 2, 5, 6};
    Row r = UnitTestHelper.getRowWithSquares(0, testInts);
    RowList rl = new RowList();
    rl.add(r);

    assertAll(
        () -> {
          assertEquals(1, rl.size());
          Integer[] row0 = rl.get().get(0).get().stream().map(Square::getX).toArray(Integer[]::new);

          assertAll(
              () -> {
                for (int i : testInts) assertTrue(Arrays.stream(row0).anyMatch(b -> b == i));
              });
        });
  }

  @Test
  void getRowsWithSquares() {
    int[][] testCoords = {{0, 0}, {2, 0}, {5, 0}, {6, 0}, {1, 1}, {2, 1}, {3, 1}, {4, 1}};
    RowList rl = UnitTestHelper.getRowsWithSquares(testCoords);

    assertAll(
        () -> {
          assertEquals(2, rl.size());

          assertAll(
              () -> {
                for (int[] c : testCoords) assertTrue(rl.cellIsNotEmpty(c[0], c[1]));
              });

          int squares = 0;

          for (Row r : rl.get()) squares += r.get().size();

          assertEquals(testCoords.length, squares);
        });
  }
}
