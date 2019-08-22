package com.tetrisrevision;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
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
    getRow(row.getY())
        .ifPresentOrElse(
            r -> r.addAll(row.get()), () -> rows.add(row));
  }

  void addRowList(RowList p) {
    p.forEach(this::add);
  }

  void addBlock(double y, Block block) {
    getRow(y)
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
    return rows.get(i);
  }

  Optional<Row> getRow(double y) {
    for (Row row : rows) {
      if (row.getY() == y) {
        return Optional.of(row);
      }
    }

    return Optional.empty();
  }

  Optional<Row> getRow(int i) {
    if (null != get(i)) {
      return Optional.of(get(i));
    }

    return Optional.empty();
  }

  Optional<Block> getBlock(double x, double y) {
    AtomicReference<Optional<Block>> b = new AtomicReference<>();

    getRow(y).ifPresentOrElse(r -> b.set(r.get(x)), () -> b.set(Optional.empty()));

    return b.get();
  }

  boolean isFullRow(double y) {
    return getRow(y).isPresent()
        && Constants.width == getRow(y).get().size();
  }

  public int size() {
    return rows.size();
  }

  int getLowestFullRow(RowList p) {
    p.get().sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));
    rows.sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));

    int idx = -1;

    for (int i = 0; i < p.size(); i++) {
      Row r = p.get(i);

      if (r == null || r.isEmpty()) {
        continue;
      }

      if (getRow(r.getY()).isPresent() && getRow(r.getY()).get().size() == 10) {
        return i;
      }
    }

    return idx;
  }
}
