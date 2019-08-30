package com.tetrisrevision.things;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class Row implements Cloneable {
  private final ArrayList<Block> blocks;
  private double y;

  public Row(double y) {
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

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public Optional<Block> get(double x) {
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

  public boolean remove(double x) {
    for (int i = 0; i < blocks.size(); i++) {
      if (blocks.get(i).getX() == x) {
        return blocks.remove(blocks.get(i));
      }
    }

    return false;
  }
}
