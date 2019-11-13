package com.tetris.things;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

/**
 * A `Row` contains `squares`, an `ArrayList` of up to 10 squares, and a `y` value. `y` represents the position on the
 * y-axis of all of the squares that the `Row` contains. No method produces a `Square` without also adding that square
 * to a new or existing row.
 *
 * I'm not sure the ArrayList is the ideal data structure here. It has a few benefits but also some drawbacks.
 *
 * The benefit is that it simplifies some of the logic and reduces some of the work that the GUI performs: there's
 * only one square on the playfield, and so the GUI loops through just one `Row` and one `Square`, not 199 empty cells
 * and 1 full cell, comprising all 20 rows or a complete row of 10 cells, only 1 of which contains a `Square`.
 *
 * The drawback is that other operatiosn become more complicated, and operations that could be O(1) are now O(n).
 * If I were using arrays, I could look up a cell via its x and y coordinates (its indices in the Array of Rows.
 * I may replace ArrayLists with arrays.
 *
 */
public class Row implements Cloneable, Iterable {
  private final ArrayList<Square> squares;
  private int y;

  public Row(int y) {
    this.y = y;
    this.squares = new ArrayList<>();
  }

  public Row clone() throws CloneNotSupportedException {
//    Row clone = (Row) super.clone();
    Row tmp = new Row(y);

    for (Square b : squares) {
      try {
        tmp.add(b.clone());
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    }

    return tmp;
  }

  public ArrayList<Square> get() {
    return squares;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Optional<Square> get(int x) {
    for (Square square : squares) {
      if (square.getX() == x) {
        return Optional.of(square);
      }
    }

    return Optional.empty();
  }

  public boolean add(Square b) {
    return squares.add(b);
  }

  public boolean addAll(Collection<Square> collection) {
    return squares.addAll(collection);
  }

  public boolean isEmpty() {
    return squares.isEmpty();
  }

  public int size() {
    return squares.size();
  }

  public boolean remove(int x) {
    for (int i = 0; i < squares.size(); i++) {
      if (squares.get(i).getX() == x) {
        return squares.remove(squares.get(i));
      }
    }

    return false;
  }

  @NotNull
  @Override
  public Iterator iterator() {
    return squares.iterator();
  }

  @Override
  public void forEach(Consumer action) {
    squares.forEach(action);
  }

  @Override
  public Spliterator spliterator() {
    return squares.spliterator();
  }
}
