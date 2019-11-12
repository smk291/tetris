package com.tetrisrevision.things;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public class Row implements Cloneable, Iterable {
  private final ArrayList<Block> blocks;
  private int y;

  public Row(int y) {
    this.y = y;
    this.blocks = new ArrayList<>();
  }

  public Row clone() {
    Row tmp = new Row(y);

    for (Block b : blocks) {
      try {
        tmp.add(b.clone());
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
    }

    return tmp;
  }

  public ArrayList<Block> get() {
    return blocks;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public Optional<Block> get(int x) {
    for (Block block : blocks) {
      if (block.getX() == x) {
        return Optional.of(block);
      }
    }

    return Optional.empty();
  }

  public boolean add(Block b) {
    return blocks.add(b);
  }

  public boolean addAll(Collection<Block> collection) {
    return blocks.addAll(collection);
  }

  public boolean isEmpty() {
    return blocks.isEmpty();
  }

  public int size() {
    return blocks.size();
  }

  public boolean remove(int x) {
    for (int i = 0; i < blocks.size(); i++) {
      if (blocks.get(i).getX() == x) {
        return blocks.remove(blocks.get(i));
      }
    }

    return false;
  }

  @NotNull
  @Override
  public Iterator iterator() {
    return blocks.iterator();
  }

  @Override
  public void forEach(Consumer action) {
    blocks.forEach(action);
  }

  @Override
  public Spliterator spliterator() {
    return blocks.spliterator();
  }
}
