package com.tetris.game.things;

import com.tetris.game.constants.Constants;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * A `RowList` is an `ArrayList` of `Row`s. It represents a few different things in the game.
 *    * The playfield is stored as a `RowList`. Its size is unrestricted, but the mechanics of the game make it impossible for an
 *      instance's size to exceed the visible height of the playfield (which is 20)
 *    * What I call ["sinkingblocks"](https://github.com/smk291/tetris/blob/master/src/com/tetrisrevision/actions/SinkingPieceFinder.java) are also represented by `RowList`s. A sinking block is a set of squares that
 *      are contiguous with one another but not with the bottom of the playfield.
 *    * Tetrominos aren't themselves stored as `RowList`s, but `RowList`s are derived from tetrominos
 *      when computing actions like collision, movement and rotation.
 *
 * Note: most of the functions that add `Square`s and `Row`s to a `RowList` don't clone or create new instances. The
 * shared references increases the risk of some kinds of bugs.
 *
 * TODO: implement Iterable for easier iteration
 *
 */
public class RowList implements Cloneable {
  private final ArrayList<Row> rows = new ArrayList<>();

  public RowList() {
    clear();
  }
  public RowList(RowList rl) {
    addRowList(rl);
  }
  public ArrayList<Row> get() {
    return rows;
  }

  // Return new RowList with new Rows and new Squares, all containing the same values as the caller.
  public RowList clone() throws CloneNotSupportedException {
    super.clone();

    RowList tmp = new RowList();

    for (Row r : rows) {
      tmp.add(r.clone());
    }

    return tmp;
  }

  // Either add a `Row`'s squares to an existing `Row` in this `RowList` or add the `Row` to this `RowList`
  public void add(Row row) {
    getRowByY(row.getY()).ifPresentOrElse(
        r -> r.addAll(row.get()),
        () -> rows.add(row));
  }

  // Add all `Row`s to this `RowList`
  public void addRowList(RowList p) {
    p.forEach(this::add);
  }

  // Add square to existing `Row` `y` or create row with y `y` and add square to it
  public void addSquare(int y, Square square) {
    getRowByY(y)
        .ifPresentOrElse(
            r -> r.add(square),
            () -> {
              Row r = new Row(y);
              r.add(square);
              rows.add(r);
            });
  }

  public boolean cellIsNotEmpty(int x, int y) {
    return getSquare(x, y).isPresent();
  }

  // empty RowList
  public void clear() {
    rows.clear();
  }

  /**
   * `deleteContiguousAndShift` doesn't work properly unless `rows` is sorted by `y`, ascending.
   *
   * This method does a few things (maybe too many). It starts looping through `rows` at `idx` increments through the
   * list elements.
   *   (1) If the first row it examines is full, it deletes that row and all full rows contiguous with and above it.
   *       The method doesn't require any rows to be full/deleted in order to work properly.
   *   (2) The first time it encounters a row that isn't full (and this may be the first row it encounters), it
   *       stops deleting rows. Instead, while it encounters non-full rows, it decrements those rows' `y` values by
   *       the sum of `contigDeleted` (the number of rows deleted in step 1) and `offset` (the number of rows
   *       deleted by previous calls to this function).
   *   (3) Either the loop ends or it encounters another full row
   *   (4) If it encounters another full row, the loop ends and the function returns the number of deleted rows. That
   *       value is then used in another method to compute the score resulting form the deletion.
   *
   *   Almost always the function will run a maximum of twice in a row: when the user settles an i block
   *   vertically on the playfield, it's possible that, e.g., the bottom two and the top rows will be full. But for
   *   purposes of scoring a deletion, those two deletions are computed separately. Thus, this method ends at step (4)
   *   above rather than continuing, so that each call to the method corresponds to one set of congituous deletions, one
   *   set of lowered non-full rows and one addition to the player's score.
   *
   *   There are other ways of doing this (e.g. I could keep an arraylist of integers, each representing the number of
   *   contiguous, deleted rows
   *
   * @param idx look for full rows contiguous with this row, starting from this row and ascending. Note the value is
   *            the row index in the array list, not its `y` value on the playfield.
   * @param offset number of rows deleted by a previous call(s) to this function
   * @return returns number of contiguous deleted rows
   *
   */
  public int clearFullRowsAndShiftNonFull(int idx, int offset) {
    int contigDeleted = 0;

    for (int i = idx; i < rows.size(); ) {
      Row r = rows.get(i);

      // If (i == idx) either no rows have been deleted or the function has encountered only full rows
      // Thus the row is removed, contigDeleted increments, and i does not increment
      if (i == idx && r.get().size() == Constants.width) {
        rows.remove(i);

        contigDeleted++;
      // If (i != idx) and the row is full, then the loop breaks off and the method returns
      } else if (r.get().size() == Constants.width) {
        break;
      // If the row isn't full, then lower it
      } else {
        r.setY(r.getY() - contigDeleted - offset);

        i++;
      }
    }

    return contigDeleted;
  }

  public void forEach(Consumer<Row> action) {
    rows.forEach(action);
  }

  public @Nullable Row get(int i) {
    if (i < 0 || i >= rows.size())
      return null;

    return rows.get(i);
  }


  public Optional<Square> getSquare(int x, int y) {
    AtomicReference<Optional<Square>> b = new AtomicReference<>();

    getRowByY(y).ifPresentOrElse(r -> b.set(r.get(x)), () -> b.set(Optional.empty()));

    return b.get();
  }

  public double getHighestY() {
    if (rows.isEmpty()) {
      return -1;
    }

    double highestY = -1;

    for (Row r : rows) {
      if (r.getY() > highestY) {
        highestY = r.getY();
      }
    }

    return highestY;
  }

  /**
   * This doesn't work unless all rows in insertedSquares have corresponding rows in the calling instance. The purpose is
   * is to reduce the amount of work when deleting rows:
   * (1) Full rows should be present only where a block (sinking or tetromino) has just been inserted into the playfield
   * (2) Thus the inserted block's upper and lower bounds are the upper and lower bounds for possibly full rows.
   *
   * Thus I use this function and another below to bound the search for full rows and reduce wasted work.
   **/
  public int highestFullRowIndexAfterInsertion(RowList insertedSquares) {
    sortByY();
    insertedSquares.sortByY();

    double startY = insertedSquares.getHighestY();
    int startIdx = getRowIdxFromY(startY);

    for (int i = startIdx; i >= 0; i--) {
      if (rowIsFull(i)) {
        return i;
      }
    }

    return -1;
  }

  public double getLowestY() {
    if (rows.size() == 0) {
      return -1;
    }

    double lowestY = 20;

    for (Row row : rows) {
      if (row.getY() < lowestY) {
        lowestY = row.getY();
      }
    }

    return lowestY;
  }

  // See notes on `highestFullRowIndexAfterInsertion`
  public int lowestFullRowIndexAfterInsertion(RowList p) {
    sortByY();
    p.sortByY();

    double lowestY = p.getLowestY();
    int startIdx = getRowIdxFromY(lowestY);

    if (lowestY == -1 || startIdx == -1) return -1;

    for (int i = startIdx; i < rows.size(); i++) {
      if (rowIsFull(i)) {
        return i;
      }
    }

    return -1;
  }

  public Optional<Row> getRowByY(double y) {
    for (Row row : rows) {
      if (row.getY() == y) {
        return Optional.of(row);
      }
    }

    return Optional.empty();
  }

  public int getRowIdxFromY(double y) {
    for (int i = 0; i < rows.size(); i++) {
      if (rows.get(i).getY() == y) {
        return i;
      }
    }

    return -1;
  }

  public boolean removeSquare(int x, int y) {
    AtomicBoolean b = new AtomicBoolean(false);

    getRowByY(y).ifPresent(row -> b.set(row.remove(x)));

    return b.get();
  }

  public boolean removeSquares(RowList squares) {
    boolean allRemovalsSuccessful = true;

    for (Row r : squares.get()) {
      for (Square b : r.get()) {
        allRemovalsSuccessful = removeSquare(b.getX(), r.getY()) && allRemovalsSuccessful;
      }
    }

    return allRemovalsSuccessful;
  }

  public boolean rowIsFull(int i) {
    return rows.get(i).size() == Constants.width;
  }

  public int size() {
    return rows.size();
  }

  public void sortByY() {
    rows.sort((Row r, Row r2) -> r.getY() - r2.getY());
  }
}
