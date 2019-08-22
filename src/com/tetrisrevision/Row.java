package com.tetrisrevision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class Row {
  private final ArrayList<Block> blocks;
  private double y;

  Row(double y) {
    this.y = y;
    this.blocks = new ArrayList<>();
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
      if (block.getX() == x) return Optional.of(block);
    }

    return Optional.empty();
  }

  boolean allMatch(Predicate<Block> tester) {
    return blocks.stream().allMatch(tester);
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
}
