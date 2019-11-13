package com.tetris.recordkeeping;

import com.tetris.things.Center;
import com.tetris.things.RowList;
import com.tetris.things.ActiveBlock;

public class TSpinTracker {
  private boolean lastActionIsRotation;
  private boolean lastActionIsKick;
  private Integer lastKick;

  public TSpinTracker() {
    reset();
  }

  private static boolean areThreeOrMoreCornersFilled(ActiveBlock block, RowList rowList) {
    int cornersFilled = 0;
    Center center = block.getCenter();

    int x = center.getX();
    int y = center.getY();

    cornersFilled += cornerFilled(rowList, x, y, -1, -1);
    cornersFilled += cornerFilled(rowList, x, y, -1, 1);
    cornersFilled += cornerFilled(rowList, x, y, 1, -1);
    cornersFilled += cornerFilled(rowList, x, y, 1, 1);

    return cornersFilled >= 3;
  }

  private static int cornerFilled(RowList rowList, int x, int y, int x2, int y2) {
    return rowList.cellIsNotEmpty(x + x2, y + y2) ? 1 : 0;
  }

  public void reset() {
    this.lastActionIsRotation = false;
    this.lastActionIsKick = false;
    this.lastKick = null;
  }

  public void setLastActionIsRotation(boolean b) {
    this.lastActionIsRotation = b;
  }

  public void setLastActionIsKick(boolean b) {
    this.lastActionIsKick = b;
  }

  public void setLastKick(Integer nullableInteger) {
    this.lastKick = nullableInteger;
  }

  public boolean isTSpin(ActiveBlock block, RowList rowList) {
    return areThreeOrMoreCornersFilled(block, rowList)
        || (null != lastKick && lastKick == 4)
        || (!areThreeOrMoreCornersFilled(block, rowList) && lastKick != null && lastKick == 4);
  }

  public boolean isTSpinMini(ActiveBlock block, RowList rowList) {
    return areThreeOrMoreCornersFilled(block, rowList) && (lastKick == null || lastKick != 4);
  }
}
