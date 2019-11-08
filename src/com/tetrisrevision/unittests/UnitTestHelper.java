package com.tetrisrevision.unittests;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;

import java.awt.*;

public class UnitTestHelper {
  public static int[] xs = {Constants.fromLeft(-1), Constants.leftBound, Constants.fromLeft(5), Constants.fromLeft(8), Constants.fromLeft(9), Constants.fromLeft(10)};
  public static int[] ys = {Constants.fromBottom(-1), Constants.bottomRow, Constants.fromBottom(5), Constants.fromBottom(10), Constants.fromBottom(15), Constants.fromBottom(19), Constants.fromBottom(20)};

  public static Row getFullRow(int y) {
    Row r = new Row(y);

    for (int i = Constants.leftBound; i < Constants.rightBound + 1; i += Constants.right)
      r.add(new Block(i, Color.black));

    return r;
  }

  public static Row getEmptyRow(int y) {
    return new Row(y);
  }

  /*
  public static void boundsTestingXY(
      int[] invalidx,
      int[] invalidy,
      BiConsumer<BiPredicate<Integer, Integer>, BiFunction<Integer, Integer, String>> expectfalse,
      BiConsumer<BiPredicate<Integer, Integer>, BiFunction<Integer, Integer, String>> expecttrue,
      BiPredicate<Integer, Integer> boolfn,
      BiFunction<Integer, Integer, String> m
  ) {
    for (int y : UnitTestHelper.ys)
      for (int x : UnitTestHelper.xs) {
        if (Arrays.stream(invalidx).anyMatch(i -> i == x) || Arrays.stream(invalidy).anyMatch(i -> i == y))
          expectfalse(boolfn(x, y), m(x, y));
      }
  }
   */

  public static RowList fillPlayField(RowList rl) {
    rl.clear();

    for (int i = Constants.bottomRow; i != Constants.topRow; i += Constants.up) {

      rl.add(getFullRow(i));
    }

    return rl;
  }

  public static RowList getFullRowList(int[] yArr) {
    RowList rl = new RowList();

    for (int y : yArr)
      rl.add(getFullRow(y));

    return rl;
  }

  public static Row getRowWithBlocks(int y, int[] cellsToFill) {
    Row row = new Row(y);

    for (int x : cellsToFill)
      row.add(new Block(x, Color.red));

    return row;
  }

  public static RowList getRowsWithBlocks(int[] ys, int[][] cellsToFillByRow) {
    RowList rl = new RowList();

    for (int i = 0; i < ys.length; i++) {
      rl.add(getRowWithBlocks(ys[i], cellsToFillByRow[i]));
    }

    return rl;
  }
}

