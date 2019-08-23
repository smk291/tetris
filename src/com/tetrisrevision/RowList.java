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

  int getYMinIdx() {
    int min = 20;

    for (int i = 0; i < rows.size(); i++) {
      if (rows.get(i).getY() < min) {
        min = i;
      }
    }

    return min;
  }

  int getYMaxIdx() {
    int max = -1;

    for (int i = 0; i < rows.size(); i++) {
      if (rows.get(i).getY() > max) {
        max = i;
      }
    }

    return max;
  }

  int deleteContiguous(int idx, int offset) {
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
  public int size() {
    return rows.size();
  }

  int getLowestFullRow(RowList p) {
    int startY = 20;

    rows.sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));

    for (int i = 0; i < p.get().size(); i++) {
      if (p.get(i).getY() < startY) {
        startY = (int) p.get(i).getY();
      }
    }

    int idx = -1;
    int startIdx = -1;

    for (int i = 0; i < rows.size(); i++) {
      if (rows.get(i).getY() == startY) {
        startIdx = i;

        break;
      }
    }

    for (int i = startIdx; i < rows.size(); i++) {
      if (rows.get(i).size() == 10) {
        return i;
      }
    }

    return idx;
  }

  int getHighestFullRow(RowList p) {
    rows.sort((Row r, Row r2) -> (int) (r.getY() - r2.getY()));
    int startY = -1;

    for (int i = 0; i < p.get().size(); i++) {
      if (p.get(i).getY() > startY) {
        startY = (int) p.get(i).getY();
      }
    }

    int idx = -1;
    int startIdx = -1;

    for (int i = 0; i < rows.size(); i++) {
      if (rows.get(i).getY() == startY) {
        startIdx = i;

        break;
      }
    }

    for (int i = startIdx; i >= 0; i--) {
      if (rows.get(i).size() == 10) {
        return i;
      }
    }

    return idx;
  }
}
