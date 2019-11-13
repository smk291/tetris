package com.tetrisrevision.actions;

import com.tetrisrevision.constants.Constants;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.ActiveBlock;

/**
 * `Rotator` contains all logic for rotating a block. Only tetrominos rotate.
 *
 * Rotation works as follows:
 * (1) Increment the `TetrisPiece`'s `rotation` value.
 * (2) Test the validity of the tetromino's resulting position.
 * (3) If the position is valid (meaning it's not overlapping with any squares and isn't out of bounds), the function returns an integer greater than -1
 * (4) If the position isn't valid (meaning it's either out of bounds or overlaps with other squares), first attempt a wall kick.
 * (5) If wall kick doesn't return -1, one of the walk kicks is a valid position. The block's center has moved accordingly. The function returns an integer.
 *     Possible wall kicks are stored in tetrominos as arrays of relative coordinates; the TetrisPiece moves, changing its x and y, by those numbers.
 *     If the resulting position is valid, the kick function returns the index of the relative coordinates that resulted in a successful `kick`.
 * (6) If the kick function returns -1, none of the wall kicks is a valid position.
 * (7) Next, attempt a floor kick, raising the block up by one row. If this produces a valid position, return a positive (and meaningless) positive integer
 *     greater than -1
 * (8) If the floor kick doesn't work, return -1, meaning the block can't rotate by the given increment. Its position and rotation will be unchanged.
 *
 */
public abstract class Rotator {
  public static int apply(int incr, ActiveBlock block, RowList rowList) {
    int rotation = block.getRotation();
    int prevRotation = block.getPrevRotation();

    // Incrementing rotation sets both `prevRotation` and `rotation`
    block.incrementRotation(incr);

    if (!PlacementTester.cellsCanBeOccupied(block, rowList)) {
      int kickValue = tryKick(block, rowList);
      if (kickValue > -1) {
        return kickValue;
      }

      if (tryLift(block, rowList)) {
        return Constants.width + 1;
      }

      // block.incrementRotation(-incr) would produce an incorrect result:
      // If the block's original `rotation` is 0 and `prevRotation` is 3, and the player attempts clockwise rotation,
      // `Rotator` increments rotation to `1` and `prevRotation` to `0`. `incrementRotation(-incr)` would change those
      // values to `0` and `1`, not the desired values of `0` and `3`
      block.setRotation(rotation);
      block.setPrevRotation(prevRotation);

      return -1;
    }

    if (block.getTetromino().isTPiece()) {
      setTSpinData(block, null, false);
    }

    return Constants.width + 1;
  }

  // This function returns an integer only for purposes of unit testing, to confirm that the correct wall kicks are
  // tested in the correct order.
  private static Integer tryKick(ActiveBlock block, RowList rowList) {
    Integer kickIdx = WallKicker.tryKick(block, rowList);

    if (null == kickIdx) {
      return -1;
    }

    if (block.getTetromino().isTPiece()) {
      setTSpinData(block, kickIdx, true);
    }

    return kickIdx;
  }

  private static boolean tryLift(ActiveBlock block, RowList rowList) {
    boolean canDrop = Translater.translate(block, rowList, 0, Constants.down, true);

    if (!canDrop && Translater.translate(block, rowList, 0, Constants.up, false)) {
      if (block.getTetromino().isTPiece()) {
        setTSpinData(block, null, false);
      }

      return true;
    }

    return false;
  }

  private static void setTSpinData(ActiveBlock block, Integer kickIdx, boolean lastActionIsKick) {
    block.gettSpinTracker().setLastKick(kickIdx);
    block.gettSpinTracker().setLastActionIsRotation(true);
    block.gettSpinTracker().setLastActionIsKick(lastActionIsKick);
  }
}
