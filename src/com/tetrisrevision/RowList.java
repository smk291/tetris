package com.tetrisrevision;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class RowList implements Cloneable {
  private final ArrayList<Row> rows = new ArrayList<>();

  RowList() {
    clear();
  }

  ArrayList<Row> get() {
    return rows;
  }

  protected RowList clone() throws CloneNotSupportedException {
    RowList tmp = new RowList();

    for (Row r : rows) {
      tmp.add(r.clone());
    }

    return tmp;
  }

  public void add(Row row) {
    getRowByY(row.getY())
        .ifPresentOrElse(
            r -> r.addAll(row.get()), () -> rows.add(row));
  }

  void addRowList(RowList p) {
    p.forEach(this::add);
  }

  void addBlock(double y, Block block) {
    getRowByY(y)
        .ifPresentOrElse(
            r -> r.add(block),
            () -> {
              Row r = new Row(y);
              r.add(block);
              rows.add(r);
            });
  }

  boolean cellIsEmpty(double x, double y) {
    return getBlock(x, y).isEmpty();
  }

  void clear() {
    rows.clear();
  }

  int deleteContiguousAndShift(int idx, int offset) {
    int contig = 0;

    for (int i = idx; i < rows.size(); ) {
      Row r = rows.get(i);

      if (i == idx && r.get().size() == Constants.width) {
        rows.remove(i);

        contig++;
      } else if (r.get().size() == Constants.width){
        break;
      } else {
        r.setY(r.getY() - contig - offset);

        i++;
      }
    }

    return contig;
  }

  void forEach(Consumer<? super Row> action) {
    rows.forEach(action);
  }

  public @Nullable Row get(int i) {
    if (i < 0 || i >= rows.size())
      return null;

    return rows.get(i);
  }

  Optional<Block> getBlock(double x, double y) {
    AtomicReference<Optional<Block>> b = new AtomicReference<>();

    getRowByY(y).ifPresentOrElse(r -> b.set(r.get(x)), () -> b.set(Optional.empty()));

    return b.get();
  }

  double getHighestY() {
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

  int getHighestYIfShared(RowList p) {
    sortByY();
    p.sortByY();

    double startY = p.getHighestY();
    int startIdx = getRowIdxFromY(startY);

    for (int i = startIdx; i >= 0; i--) {
      if (rowIsFull(i)) {
        return i;
      }
    }

    return -1;
  }

  double getLowestY() {
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

  int getLowestYIfShared(RowList p) {
    sortByY();
    p.sortByY();

    double lowestY = p.getLowestY();
    int startIdx = getRowIdxFromY(lowestY);

    if (lowestY == -1 || startIdx == -1)
      return -1;

    for (int i = startIdx; i < rows.size(); i++) {
      if (rowIsFull(i)) {
        return i;
      }
    }

    return -1;
  }

  Optional<Row> getRowByY(double y) {
    for (Row row : rows) {
      if (row.getY() == y) {
        return Optional.of(row);
      }
    }

    return Optional.empty();
  }

  int getRowIdxFromY(double y) {
    for (int i = 0; i < rows.size(); i++) {
      if (rows.get(i).getY() == y) {
        return i;
      }
    }

    return -1;
  }

  public boolean removeBlock(double x, double y) {
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

  void sortByY() {
    rows.sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));
  }
}
