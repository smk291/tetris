package com.tetris.game.actions;

import com.tetris.game.constants.Constants;
import com.tetris.game.things.Square;
import com.tetris.game.things.Row;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;

/**
 * `Translater` works much like `Rotator` (see notes in `Rotator.java` and the readme): it applies the desired action,
 * and then undoes it if the resulting position is invalid.
 *
 *
 */
public abstract class Translater {
  public static boolean translate(RowList squares, RowList field, int y) {
    squares.forEach(r -> r.setY(r.getY() + y));

    boolean validPosition = true;

    // NOTE: needless. simplify.
    // Overload `cellsCannotBeOccupied`, adding code below, so that it takes a `RowList` as a parameter
    for (Row r : squares.get()) {
      for (Square b : r.get()) {
        if (PlacementTester.cellCannotBeOccupied(r.getY(), b.getX(), field)) {
          validPosition = false;
        }
      }
    }

    if (!validPosition) squares.forEach(r -> r.setY(r.getY() - y));

    return validPosition;
  }

  public static boolean translate(ActiveBlock block, RowList field, int x, int y, boolean test) {
    block.getCenter().translate(x, y);

    boolean validPosition = PlacementTester.cellsCanBeOccupied(block, field);

    if (validPosition) {
      if (test) {
        block.getCenter().translate(-x, -y);
      }

      if (block.getTetromino().isTBlock()) {
        block.gettSpinTracker().reset();
      }

      return true;
    }

    block.getCenter().translate(-x, -y);

    return false;
  }

  public static int hardDrop(ActiveBlock block, RowList field) {
    int cells = 0;

    do {
      cells++;

    } while (translate(block, field, 0, Constants.down, false));

    if (cells > 0 && block.getTetromino().isTBlock()) {
      block.gettSpinTracker().reset();
    }

    return cells;
  }
}
