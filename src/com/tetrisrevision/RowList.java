package com.tetrisrevision;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class RowList {
  private final ArrayList<Row> rows = new ArrayList<>();

  RowList() {
    clear();
  }

  ArrayList<Row> get() {
    return rows;
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

  void forEach(Consumer<? super Row> action) {
    rows.forEach(action);
  }

  public @Nullable Row get(int i) {
    if (i < 0 || i >= rows.size())
      return null;

    return rows.get(i);
  }

  Optional<Row> getRowByY(double y) {
    for (Row row : rows) {
      if (row.getY() == y) {
        return Optional.of(row);
      }
    }

    return Optional.empty();
  }

  Optional<Row> getRowByIdx(int i) {
    if (i > -1 && i < rows.size() && null != rows.get(i)) {
      return Optional.of(rows.get(i));
    }

    return Optional.empty();
  }

  Optional<Block> getBlock(double x, double y) {
    AtomicReference<Optional<Block>> b = new AtomicReference<>();

    getRowByY(y).ifPresentOrElse(r -> b.set(r.get(x)), () -> b.set(Optional.empty()));

    return b.get();
  }

  boolean isFullRow(int idx) {
    return getRowByIdx(idx).isPresent() && Constants.width == getRowByIdx(idx).get().size();
  }

  int deleteRows(int idx) {
    int contig = 0;

    for (int i = idx; i < rows.size(); ) {
      if (i == idx && rows.get(i).get().size() == Constants.width) {
        rows.remove(i);

        contig++;
      } else {
        Row r = rows.get(i);

        r.setY(r.getY() - contig);

        i++;
      }
    }

    return contig;
  }
  public int size() {
    return rows.size();
  }

  int getLowestFullRow(RowList p) {
    rows.sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));

    int idx = -1;

    for (int i = 0; i < rows.size(); i++) {
      if (rows.get(i).size() == 10) {
        System.out.println("Lowest full row: " + idx);

        return i;
      }
    }

    System.out.println("Lowest full row: " + idx);
    return idx;
  }

  int getHighestFullRow(RowList p) {
    rows.sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));

    int idx = -1;

    for (int i = rows.size() - 1; i >= 0; i--) {
      if (rows.get(i).size() == 10) {
        System.out.println("Highest full row: " + idx);

        return i;
      }
    }

    System.out.println("Highest full row: " + idx);

    return idx;
  }
}
