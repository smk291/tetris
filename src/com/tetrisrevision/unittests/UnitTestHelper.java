package com.tetrisrevision.unittests;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

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

    for (int i = Constants.leftBound; i < Constants.width; i += Constants.right)
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

  public static String printRowList(RowList rl) {
    StringBuilder s = new StringBuilder();

    for (Row r : rl.get()) {
      for (Block b : r.get()) {
        s.append("{").append(b.getX()).append(",").append(r.getY()).append("},");
      }
    }

    return s.toString();
  }

  public static StringBuilder[] printRowListBoard(RowList rl) {
    StringBuilder[] s = new StringBuilder[Constants.height];

    for (int y = 0; y < Constants.height; y++) {
      s[y] = new StringBuilder();

      Optional<Row> r = rl.getRowByY(y);

      s[y].append("|");

      for (int x = 0; x < Constants.width; x++)
        s[y].append(rl.cellIsNotEmpty(x, y) ? "*" : x < 10 ? " " : "  ");

      s[y].append("|");
    }

    return s;
  }

  public static void printBoard(StringBuilder[] s) {
    for (StringBuilder sb : s)
      System.out.println(sb.toString());

    System.out.println(" ---------- ");
  }

  public static void buildAndPrintBoard(RowList rl) {
    printBoard(printRowListBoard(rl));
  }

  public static void printRowLine(Row r) {
    printRowLine(r, "");
  }

  public static void printRowLine(Row r, String prefix) {
    StringBuilder s = new StringBuilder();
    s.append(prefix);
    s.append(r.getY() > 9 ? r.getY() : " " + r.getY()).append(" |");

    for (int x = 0; x <  Constants.width; x++) {
      if (r.get(x).isPresent())
        s.append(x);
      else
        s.append(x < 10 ? " " : "  ");
    }

    s.append("|");

    System.out.println(s.toString());
  }
  public static void printHorizBorder(String s) {
    System.out.println(s + "   ----------");
  }
  public static void printHorizBorder() {
    printHorizBorder("");
  }

  public static void printLines(RowList rl) {
    rl.sortByY();
    printHorizBorder();

    ArrayList<Row> get = rl.get();
    for (int i = get.size() - 1; i >= 0; i--) {
      Row r = get.get(i);
      printRowLine(r);
    }

    printHorizBorder();
  }

  public static void printNonFullLines(RowList rl) {
    rl.sortByY();
    printHorizBorder();

    ArrayList<Row> get = rl.get();
    for (int i = get.size() - 1; i >= 0; i--) {
      Row r = get.get(i);
      if (r.size() > 0 && r.size() < Constants.width)
        printRowLine(r);
    }

    printHorizBorder();
  }

  public static void printLines(RowList rl, String s) {
    rl.sortByY();
    printHorizBorder(s);

    ArrayList<Row> get = rl.get();
    for (int i = get.size() - 1; i >= 0; i--) {
      Row r = get.get(i);
      printRowLine(r, s);
    }

    printHorizBorder(s);
  }

  public static void printNonFullLines(RowList rl, String s) {
    rl.sortByY();
    printHorizBorder(s);

    ArrayList<Row> get = rl.get();
    for (int i = get.size() - 1; i >= 0; i--) {
      Row r = get.get(i);
      if (r.size() > 0 && r.size() < Constants.width)
        printRowLine(r, s);
    }

    printHorizBorder(s);
  }
}
