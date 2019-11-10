package com.tetrisrevision.unittests;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;

import java.awt.*;

public class UnitTestHelper {
  public static int[] xs = {
    Constants.fromLeft(-1),
    Constants.leftBound,
    Constants.fromLeft(5),
    Constants.fromLeft(8),
    Constants.fromLeft(9),
    Constants.fromLeft(10)
  };
  public static int[] ys = {
    Constants.fromBottom(-1),
    Constants.bottomRow,
    Constants.fromBottom(5),
    Constants.fromBottom(10),
    Constants.fromBottom(15),
    Constants.fromBottom(19),
    Constants.fromBottom(20)
  };

  public static Row getFullRow(int y) {
    Row r = new Row(y);

    for (int i = Constants.leftBound; i < Constants.rightBound + 1; i += Constants.right)
      r.add(new Block(i, Color.black));

    return r;
  }

  public static Row getEmptyRow(int y) {
    return new Row(y);
  }

  public static RowList fillPlayField(RowList rl) {
    rl.clear();

    for (int i = Constants.bottomRow; i != Constants.height; i += Constants.up) {
      rl.add(getFullRow(i));
    }

    return rl;
  }

  public static RowList getFullRowList(int[] yArr) {
    RowList rl = new RowList();

    for (int y : yArr) rl.add(getFullRow(y));

    return rl;
  }

  public static Row getRowWithBlocks(int y, int[] cellsToFill) {
    Row row = new Row(y);

    for (int x : cellsToFill) row.add(new Block(x, Color.red));

    return row;
  }

  public static RowList getRowsWithBlocks(int[][] coordinateArray) {
    RowList rl = new RowList();

    for (int[] coords : coordinateArray) rl.addBlock(coords[1], new Block(coords[0], Color.blue));

    return rl;
  }

  public static RowList[] getRowListArrays(int[][][] coordinateArrays) {
    RowList[] rlArr = new RowList[coordinateArrays.length];

    for (int i = 0; i < coordinateArrays.length; i++)
      rlArr[i] = getRowsWithBlocks(coordinateArrays[i]);

    return rlArr;
  }

  public static String printRow(Row r) {
    StringBuilder s = new StringBuilder();

    for (Block b : r.get()) {
      s.append("{").append(b.getX()).append(", ").append(r.getY()).append("}, ");
    }

    return s.toString();
  }
}
