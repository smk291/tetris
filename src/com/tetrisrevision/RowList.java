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

  private Optional<Row> getRow(double y) {
    for (Row row : rows) {
      if (row.getY() == y) {
        return Optional.of(row);
      }
    }

    return Optional.empty();
  }

  private Optional<Row> getRow(Row r) {
    return getRow(r.getY());
  }

  Optional<Block> getBlock(double x, double y) {
    AtomicReference<Optional<Block>> b = new AtomicReference<>();

    getRow(y).ifPresentOrElse(r -> b.set(r.get(x)), () -> b.set(Optional.empty()));

    return b.get();
  }

  boolean cellIsEmpty(double x, double y) {
    return getBlock(x, y).isEmpty();
  }

  boolean isFullRow(double y) {
    return getRow(y).isPresent()
        && Constants.width == getRow(y).get().size();
  }

  public boolean add(Row row) {
    AtomicBoolean addReturn = new AtomicBoolean(false);

    getRow(row)
        .ifPresentOrElse(
            r -> addReturn.set(r.addAll(row.get())), () -> addReturn.set(rows.add(row)));

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

  int getLowestFullRow(RowList p) {
    p.get().sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));
    rows.sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));

    System.out.print("p rows: " + p.get().size() + ", ");
    System.out.print("p ys: ");

    for (Row r : p.get()) {
      System.out.print((int) r.getY() + " ");
    }
    System.out.println();

    System.out.print("ys: ");

    for (Row r : rows) {
        System.out.print((int) r.getY() + " ");
    }

    System.out.println();

    int idx = -1;

    for (int i = 0; i < p.size(); i++) {
      Row r = p.get(i);

      if (r == null || r.isEmpty()) {
        continue;
      }

      Optional<Row> r2 = getRow(r);

      if (r2.isPresent() && r2.get().size() == 10) {
        idx = i;

        break;
      }
    }

    return idx;
  }
}
