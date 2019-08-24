package com.tetrisrevision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Row implements Cloneable {
  private final ArrayList<Block> blocks;
  private double y;

  Row(double y) {
    this.y = y;
    this.blocks = new ArrayList<>();
  }

  protected Row clone() {
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

  ArrayList<Block> get() {
    return blocks;
  }

  double getY() {
    return y;
  }

  void setY(double y) {
    this.y = y;
  }

  Optional<Block> get(double x) {
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

  boolean addAll(Collection<Block> collection) {
    return blocks.addAll(collection);
  }

  boolean isEmpty() {
    return blocks.isEmpty();
  }

  public int size() {
    return blocks.size();
  }

  boolean remove(double x) {
    for (int i = 0; i < blocks.size(); i++) {
      if (blocks.get(i).getX() == x) {
        return blocks.remove(blocks.get(i));
      }
    }

    return false;
  }
}
