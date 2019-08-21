package com.tetrisrevision;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

public class RowList implements Iterable<Row>, List<Row> {
  private static int width;
  private static int height;
  private ArrayList<Row> rows = new ArrayList<>();

  class PlayFieldListIterator implements ListIterator<Row> {
    int count = 0;

    PlayFieldListIterator() { }

    PlayFieldListIterator(int i)
    {
      count = i;
    }

    @Override
    public boolean hasNext() {
      return rows.size() > 0 && count < rows.size() - 1;
    }

    @Override
    public Row next() {
      return rows.get(++count);
    }

    @Override
    public boolean hasPrevious() {
      return count > 0 && rows.size() > 0;
    }

    @Override
    public Row previous() {
      return rows.get(--count);
    }

    @Override
    public int nextIndex() {
      return ++count;
    }

    @Override
    public int previousIndex() {
      return --count;
    }

    @Override
    public void remove() {
      rows.remove(count);
    }

    @Override
    public void set(Row row) {
      for (Row r : rows) {
        if (r.getY() == row.getY()) {
          rows.remove(r);
        }
      }

      rows.add(row);
    }

    @Override
    public void add(Row row) {
      rows.add(row);
    }
  }

  RowList() {
    clear();
  }

  RowList(int width, int height) {
    RowList.width = width;
    RowList.height = height;

    clear();
  }

  /*
   * Combine Rows: for all Rows in `p,` if this has Row with same `y` value, combine the rows, else add p Row to this
   */
  void addAll(RowList p)
  {
    p.forEach(this::add);
  }

  /*
   * If containing Row exists, add block to it; else create Row, add Block to it, and then add Row to this
   */

  void addBlock(double y, Block block) {
    if (PlacementTester.isOutOfBounds(y, block)) return;

    getSingleRow(y).ifPresentOrElse(
            r -> r.add(block),
            () -> {
              Row r = new Row(y);
              r.add(block);
              rows.add(r);
            });
  }

  static int getWidth() {
    return width;
  }

  static int getHeight() {
    return height;
  }

  boolean isCellEmpty(double x, double y) {
    return (PlacementTester.inBounds(x, y)) && getBlock(x, y).isEmpty();
  }

  boolean isRowEmpty(double y) {
    return BoundsTester.yInBounds(y) && getSingleRow(y).isEmpty();
    // If a Row's size is 0, it shouldn't exist. Fix this.
  }

  boolean isRowFull(double y) {
    return BoundsTester.yInBounds(y) && getSingleRow(y).isPresent() && 10 == getSingleRow(y).get().size();
  }

//  private boolean removeBlock(Row row, double x) {
//    AtomicBoolean removalSuccess = new AtomicBoolean(false);
//
//    row.get(x).ifPresent(r -> removalSuccess.set(remove(r)));
//
//    return removalSuccess.get();
//  }
//
//  private boolean removeBlock(double y, double x) {
//    AtomicBoolean returnVal = new AtomicBoolean(false);
//
//    getSingleRow(y).ifPresent(r -> returnVal.set(removeBlock(r, x)));
//
//    return returnVal.get();
//  }
//
//  boolean removeBlock(Row r, Block b) {
//    return removeBlock(r, b.getX());
//  }
//
//  boolean removeBlock(double y, Block b) {
//    return removeBlock(y, b.getX());
//  }

  Optional<Row> getSingleRow(double y) {
    for (Row row : this) {
      if (row.getY() != y) continue;

      return Optional.of(row);
    }

    return Optional.empty();
  }

  private Optional<Row> getSingleRow(Row r) {
    return getSingleRow(r.getY());
  }

  /*
   * Get Optional<Block> from `rows`
   */

  Optional<Block> getBlock(double x, double y)
  {
    AtomicReference<Optional<Block>> b = new AtomicReference<>();

    getSingleRow(y).ifPresentOrElse (
            r -> b.set(r.get(x)),
            () -> b.set(Optional.empty())
    );

    return b.get();
  }

  /*
   * Get first index of Row whose `y` equals the parameter
   */

//  public int indexOf(double y) {
//    for (int i = 0, s = rows.size(); i < s; i++)
//    {
//      if (rows.get(i).getY() == y)
//      {
//        return i;
//      }
//    }
//
//    return -1;
//  }

  /*
   * Get last index of Row whose `y` equals the parameter
   */

//  public int lastIndexOf(double y) {
//    for (int s = rows.size(), i = s - 1; i >= 0; i--)
//    {
//      if (rows.get(i).getY() == y)
//      {
//        return i;
//      }
//    }
//
//    return -1;
//  }

  /*
   * Test whether `rows` contains a Row whose `y` equals the parameter
   */

//  boolean contains(double y)
//  {
//    return rows.stream().anyMatch(r -> r.getY() == y);
//  }

  /*
   * Return null or row whose `y` value equals to the parameter
   */
  public @Nullable Row get(double y) {
    for (Row row : rows) {
      if (row.getY() == y)
        return row;
    }

    return null;
  }

  /*
   * Remove from `rows` a Row whose `y` value equals the parameter
   */
//  public boolean remove(double y) {
//    AtomicReference<Boolean> returnVal = new AtomicReference<>(false);
//
//    getSingleRow(y).ifPresent(r -> returnVal.set(rows.remove(r)));
//
//    return returnVal.get();
//  }

  /*
   * Change `y` of Row
   */

  void shiftRow(double from, double to) {
    getSingleRow(from).ifPresent(r -> r.setY(to));
  }

  /*
   * Get an ArrayList<Row> whose Rows have a `y` value within the given.
   */

  public List<Row> subList(double yMin, double yMax) {
    return rows.stream().filter(r -> r.getY() >= yMin && r.getY() < yMax).collect(Collectors.toList());
  }

  /*****
   *
   *
   * Start implementing List<Row>:
   *
   *
   *****/

  /*
   * @row - If `rows` contains no other Row with an equivalent `y`, the parameter Row is added to `rows`.
   *        If `rows` does contain a Row with an equivalent `y`, the parameter Row's Blocks are added to that row
   */
  @Override
  public boolean add(Row row) {
    AtomicBoolean addReturn = new AtomicBoolean(false);

    getSingleRow(row).ifPresentOrElse(
            r -> addReturn.set(r.addAll(row)),
            () -> addReturn.set(rows.add(row)));

    return addReturn.get();
  }

  @Override
  public void add(int i, Row row) {
    rows.add(i, row);
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends Row> collection) {           // Should I modify addAll so that it uses add(Row)?
    return rows.addAll(collection);
  }

  @Override
  public boolean addAll(int i, @NotNull Collection<? extends Row> collection) {
    return rows.addAll(i, collection);
  }

  @Override
  public void clear() {
    rows.clear();
  }

  @Override
  public boolean contains(Object o) {
    return rows.contains(o);
  }

  @Override
  public boolean containsAll(@NotNull Collection collection) {
    return rows.containsAll(collection);
  }

  @Nullable
  @Override
  public Row get(int i) {
    return rows.get(i);
  }

  @Override
  public int indexOf(Object o) {
    return rows.indexOf(o);
  }

  @Override
  public boolean isEmpty() {
    return rows.size() == 0;
  }

  @Override
  public int lastIndexOf(Object o) {
    return rows.lastIndexOf(o);
  }

  @Override
  public Row remove(int i) {
    return rows.remove(i);
  }

  @Override
  public boolean remove(Object o) {
    return rows.remove(o);
  }

  @Override
  public boolean removeAll(@NotNull Collection<?> collection) {
    return rows.removeAll(collection);
  }

  @Override
  public boolean removeIf(Predicate<? super Row> filter) {
    return rows.removeIf(filter);
  }

  @Override
  public void replaceAll(UnaryOperator<Row> operator) {
    rows.replaceAll(operator);
  }

  @NotNull
  @Override
  public Row set(int i, Row row) {
    return rows.set(i, row);
  }

  @Override
  public int size() {
    return rows.size();
  }

  @Override
  public void sort(Comparator<? super Row> c) {
    rows.sort(c);
  }

  @NotNull
  @Override
  public List<Row> subList(int i, int j) {
    return rows.subList(i, j);
  }

  /******
   *
   *
   * Implement Iterable
   * Methods whose behavior differs from default/assumed:
   *  listIterator() -- returns custom ListIterator<Row>
   *
   *
   *
   *****/

  @NotNull
  @Override
  public Iterator<Row> iterator() {
    return rows.iterator();
  }

  public void forEach(Consumer<? super Row> action) {
    rows.forEach(action);
  }

  @Override
  public Spliterator<Row> spliterator() {
    return rows.spliterator();
  }

  @NotNull
  public Object[] toArray() {
    return rows.toArray(new Object[0]);
  }

  @NotNull
  @Override
  public ListIterator<Row> listIterator() {
    return new PlayFieldListIterator();
  }

  @NotNull
  @Override
  public PlayFieldListIterator listIterator(int i) {
    return new PlayFieldListIterator(i);
  }

  @Override
  public Stream<Row> stream() {
    return rows.stream();
  }

  @Override
  public Stream<Row> parallelStream() {
    return rows.parallelStream();
  }

  @Override
  public boolean retainAll(@NotNull Collection<?> collection) {
    return rows.retainAll(collection);
  }

  @NotNull
  @Override
  public Object[] toArray(@NotNull Object[] objects) {
    return new Object[0];
  }

  @Override
  public Row[] toArray(IntFunction generator) {
    return new Row[0];
  }
}
