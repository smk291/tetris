package com.tetrisrevision;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 *
 * The PlayField is the area in which the game is played. PlayField is an abstract representation of the board's contents.
 * The data drawn on the board come from an array as long as the height of the board and ArrayList<Cell>'s that should be at most
 * the size of the width of the board. The Array of ArrayLists therefore does not represent the playfield. It represents
 * blocks (filled cells on the playfield). Variables representing row number use the letter y,
 * for a y coordinate on the board. Variables representing cell number use the letter x, for an x coordinate on the board.
 *
 * The upper left cell has coordinates {0,0}. The lower-right cell has coordinates {width - 1, height -1}. The y coordinate is the
 * block's index in the Array containing ArrayList<Cell>'s, but a row can be empty and the blocks it contains are unsorted; their order
 * is the order of their insertion. So a block's x coordinate isn't its index in the containing ArrayList<Cell>
 *
 * This approach reduces work, as the program loops through only the blocks it draws, not the entire playfield.
 *
 */

public class RowList {
  // There are no restrictions on the size of `rows`.
  // It can contain more Rows and more Blocks than should be allowed.
  private final ArrayList<Row> rows = new ArrayList<>();

  RowList() {
    clear();
  }

  ArrayList<Row> get() {
    return rows;
  }

  /* Combine RowLists: for all Rows in RowList `p,`
     if `this` has Row with same `y` value,
     combine the Rows, else add `p` Row to corresponding
     Row in `this` */
  void addRowList(RowList p)
  {
    p.forEach(this::add);
  }

  /* If containing Row exists, add block to it; else
     create Row, add Block to it, and then add Row to
     this */
  void addBlock(double y, Block block) {
    if (PlacementTester.isOutOfBounds(y, block)) return;

    get(y).ifPresentOrElse(
            r -> r.add(block),
            () -> {
              Row r = new Row(y);
              r.add(block);
              rows.add(r);
            });
  }

  /* Return Optional<Row> whose `y` value equals to the parameter */
  public Optional<Row> get(double y) {
    for (Row row : rows) {
      if (row.getY() == y)
        return Optional.of(row);
    }

    return Optional.empty();
  }

  public Optional<Row> get(Row r) {
    return get(r.getY());
  }

  /* Get Optional<Block> from `rows`*/
  Optional<Block> getBlock(double x, double y) {
    AtomicReference<Optional<Block>> b = new AtomicReference<>();

    get(y).ifPresentOrElse (
            r -> b.set(r.get(x)),
            () -> b.set(Optional.empty())
    );

    return b.get();
  }

  /* An empty cell is a valid space on the playfield that doesn't contain a block */
  boolean isEmptyCell(double x, double y) {
    return (PlacementTester.inBounds(x, y))
            && getBlock(x, y).isEmpty();
  }

  /* If a Row's size() equals `RowList.width`, it's full */
  boolean isFullRow(double y) {
    return BoundsTester.yInBounds(y)
            && get(y).isPresent() && Constants.width() == get(y).get().size();
  }

  /* If `rows` contains no other Row with an equivalent `y`, the parameter Row is added to `rows`.
     If `rows` does contain a Row with an equivalent `y`, the parameter Row's Blocks are added to that row */
  public boolean add(Row row) {
    AtomicBoolean addReturn = new AtomicBoolean(false);

    get(row).ifPresentOrElse(
            r -> addReturn.set(r.addAll(row.get())),
            () -> addReturn.set(rows.add(row)));

    return addReturn.get();
  }

  public void add(int i, Row row) {
    rows.add(i, row);
  }

  void clear() {
    rows.clear();
  }

  public @Nullable Row get(int i) {
    return rows.get(i);
  }

  void remove(int i) {
    rows.remove(i);
  }

  public Row set(int i, Row row) {
    return rows.set(i, row);
  }

  public int size() {
    return rows.size();
  }

  void forEach(Consumer<? super Row> action) {
    rows.forEach(action);
  }

  /* Returns first, lowest index of a full Row in `this`.
     `p` represents Blocks that were just added to `this`.
     Thus `p`'s `y` values should be the only possibly full rows,
     because its `y` values are the only places in `this` where
     the program has added blocks
  */
  int getLowestFullRow(RowList p) {
    // Sort so that `this` is ordered by `y`, ascending
    rows.sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));

    int idx = -1;

    // Loop through rows in `p` rather than `this` because
    // `p`'s `y`s should be the only rows that can be full
    for (int i = 0; i < p.size(); i++) {
      Row r = p.get(i);

      if (r == null || r.isEmpty())
        continue;

      Optional<Row> r2 = get(r);

      if (r2.isPresent() && r2.get().size() == 10) {
        idx = i;

        // break because first encountered full row will have the lowest `y` value
        break;
      }
    }

    return idx;
  }
}
