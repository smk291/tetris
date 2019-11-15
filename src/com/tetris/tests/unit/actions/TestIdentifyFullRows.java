package com.tetris.tests.unit.actions;

import com.tetris.game.actions.IdentifyFullRows;
import com.tetris.game.things.Row;
import com.tetris.game.things.RowList;
import com.tetris.tests.unit.UnitTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestIdentifyFullRows {
  private int[] fullRowsByY = {2, 6, 7, 9, 12};
  private int[] nonFullRowsByY = {0, 1, 3, 4, 5, 8, 10, 11};
  int[] nonFullRow = {0, 1, 2, 3, 4, 5, 6, 7, 8};
  int[] fullRowsToClear1 = {6, 8}; // range, second value noninclusive
  int[] fullRowsToClear2 = {7, 10};
  private RowList rl = new RowList();
  RowList rl1 = new RowList();
  RowList rl2 = new RowList();

  @BeforeEach
  void resetRowList() {
    rl.get().clear();

    int i = 0;
    for (int y : fullRowsByY) {
      rl.add(UnitTestHelper.getFullRow(y));
      assertTrue(rl.get().get(i).getY() == y && rl.get().get(i).isFull());
      i++;
    }

    for (int y : nonFullRowsByY) {
      int n1 = (int) (Math.random() * 9);
      int n2 = (int) (Math.random() * 9);
      n2 = n2 == n1 ? (int) (Math.random() * 8) : n2;

      rl.add(
          UnitTestHelper.getRowWithSquares(
              y, Arrays.copyOfRange(nonFullRow, Math.min(n1, n2), Math.max(n1, n2))));

      assertEquals(rl.get().get(i).getY(), y);
      assertFalse(rl.get().get(i).isFull());

      i++;
    }

    rl.sortByY();

    rl1.clear();
    IntStream.range(fullRowsToClear1[0], fullRowsToClear1[fullRowsToClear1.length - 1]).forEach(y ->
      rl1.add(UnitTestHelper.getFullRow(y)));

    assertEquals(fullRowsToClear1[1] - fullRowsToClear1[0], rl1.get().size());
    assertTrue(rl1.get().stream().allMatch(Row::isFull));

    rl2.clear();
    IntStream.range(fullRowsToClear2[0], fullRowsToClear2[fullRowsToClear2.length - 1]).forEach(y ->
      rl2.add(UnitTestHelper.getFullRow(y)));

    assertEquals(fullRowsToClear2[1] - fullRowsToClear2[0], rl2.size());
    assertTrue(rl2.get().stream().allMatch(Row::isFull));
  }

  @Test
  void getFullRows() {
    for (int y : fullRowsByY) {
      assertTrue(rl.getRowByY(y).isPresent());
      assertTrue(rl.getRowByY(y).get().isFull());
    }
  }

  @Test
  void getNonFullRows() {
    for (int y : nonFullRowsByY) {
      assertTrue(rl.getRowByY(y).isPresent());
      assertTrue(!rl.getRowByY(y).get().isFull());
    }
  }

  void getFullRows(RowList tRL, int[] fullY, int numberFull) {
    ArrayList<Integer> fullRows = IdentifyFullRows.identify(tRL, rl);

    assertEquals(numberFull, fullRows.size());

    for (int y : fullRows) {
      assertTrue(rl.getRowByY(y).isPresent());
      assertTrue(rl.getRowByY(y).get().isFull());
    }
  }

  @Test
  void getFullRows1() {
    getFullRows(rl1, fullRowsToClear1, 2);
  }

  @Test
  void getFullRows2() {
    getFullRows(rl2, fullRowsToClear2, 2);
  }
}
