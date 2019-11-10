package com.tetrisrevision.things;

import com.tetrisrevision.helpers.Constants;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

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

  // Return new RowList with new Rows and new Blocks, all containing the same values as this.
  public RowList clone() throws CloneNotSupportedException {
    super.clone();

    RowList tmp = new RowList();

    for (Row r : rows) {
      tmp.add(r.clone());
    }

    return tmp;
  }

  // Add row's blocks to existing row or create and add row to this
  public void add(Row row) {
    getRowByY(row.getY()).ifPresentOrElse(r -> r.addAll(row.get()), () -> rows.add(row));
  }

  // Add all rows to this
  public void addRowList(RowList p) {
    p.forEach(this::add);
  }

  // Add block to row `y` or create row with y `y` and add block to it
  public void addBlock(int y, Block block) {
    getRowByY(y)
        .ifPresentOrElse(
            r -> r.add(block),
            () -> {
              Row r = new Row(y);
              r.add(block);
              rows.add(r);
            });
  }

  public boolean cellIsNotEmpty(int x, int y) {
    return getBlock(x, y).isPresent();
  }

  // empty RowList
  public void clear() {
    rows.clear();
  }

  /**
   *
   * `rows` must already be sorted by `y` ascending
   *
   * @param idx look for full rows contiguous with this row
   * @param offset number of rows deleted by a previous call(s) to this function
   * @return returns number of
   *
   * Set `i` to `idx`, and while row at `i` is full, remove row, increment `contig` and do not increment `i`
   * `i` isn't incremented because after removal of full row, next row is still at index `i`.
   * `i` increments when loop encounters a non-full row.
   * Then while row at `i` isn't full, "lower" it by decrementing its `y` by `contigDeleted` + `offset`
   * But if row is full, break and return `contigDeleted`.
   *
   * Method returns `contigDeleted` rather than continuing so that a separate class/method
   * can calculate the score.
   */
  public int deleteContiguousAndShift(int idx, int offset) {
    int contigDeleted = 0;

    for (int i = idx; i < rows.size(); ) {
      Row r = rows.get(i);

      if (i == idx && r.get().size() == Constants.width) {
        rows.remove(i);

        contigDeleted++;
      } else if (r.get().size() == Constants.width) {
        break;
      } else {
        r.setY(r.getY() - contigDeleted - offset);

        i++;
      }
    }

    return contigDeleted;
  }

  public void forEach(Consumer<? super Row> action) {
    rows.forEach(action);
  }

  public @Nullable Row get(int i) {
    if (i < 0 || i >= rows.size())
      return null;

    return rows.get(i);
  }

  public Optional<Block> getBlock(int x, int y) {
    AtomicReference<Optional<Block>> b = new AtomicReference<>();

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

  // This doesn't work unless all rows in insertedBlocks have corresponding rows
  // in this instance. Not sure whether this is a problem.
  public int highestFullRowIndexAfterInsertion(RowList insertedBlocks) {
    sortByY();
    insertedBlocks.sortByY();

    double startY = insertedBlocks.getHighestY();
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

  public boolean removeBlock(int x, int y) {
    AtomicBoolean b = new AtomicBoolean(false);

    getRowByY(y).ifPresent(row -> b.set(row.remove(x)));

    return b.get();
  }

  public boolean removeBlocks(RowList blocks) {
    boolean allRemovalsSuccessful = true;

    for (Row r : blocks.get()) {
      for (Block b : r.get()) {
        allRemovalsSuccessful = removeBlock(b.getX(), r.getY()) && allRemovalsSuccessful;
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
