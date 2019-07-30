package com.tetrisrevision;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * The PlayField is the area in which the game is played. PlayField is an abstract representation of the board's contents.
 * The data drawn on the board come from an array as long as the height of the board and ArrayList<Cell>'s that should be at most
 * the size of the width of the board. The Array of ArrayLists therefore does not represent the playfield. It represents
 * blocks (filled cells on the playfield). Variables representing row number use the letter y,
 * for a y coordinate on the board. Variables representing cell number use the letter x, for an x coordinate on the board.
 *
 * The upper left cell has coordinates {0,0}. The lower-right cell has coordinates {width - 1, height -1}. The y coordinate is the
 * block's index in the Array containing ArrayList<Cell>'s, but a row can be empty and the blocks it contains are unsorted; their order
 * is the order of their insertion. So a block's x coordinate isn't its index in the containing ArrayList<Cell>
 *
 * This approach reduces work, as the program loops through only the blocks it draws, not the entire playfield.
 *
 */

public class PlayField {
  private static int width;
  private static int height;
  private ArrayList<Block>[] blocksByRow;

  PlayField(int width, int height) {
    PlayField.width = width;
    PlayField.height = height;
    blocksByRow = (ArrayList<Block>[]) new ArrayList[height];

    empty();
  }

  static int getWidth() {
    return width;
  }

  static int getHeight() {
    return height;
  }

  ArrayList<Block>[] get() {
    return blocksByRow;
  }

  Optional<Block> get(double x, double y) {
    for (Block c : get(y)) if (c.getX() == x) return Optional.of(c);

    return Optional.empty();
  }

  ArrayList<Block> get(double y) {
    return blocksByRow[(int) y];
  }

  private void set(double y, ArrayList<Block> row) {
    blocksByRow[(int) y] = row;
  }

  void set(TetrisPiece piece) {
    set(piece.getCells());
  }

  void set(ArrayList<Block> piece) {
    set(piece.toArray(new Block[0]));
  }

  private void set(Block[] piece) {
    for (Block block : piece) copy(block);
  }

  private void set(Block c) {
    get(c.getY()).add(c);
  }

  boolean isFull(int x, int y) {
    return !isEmpty(x, y);
  }

  boolean isFull(int y) {
    return BoundsTester.yInBounds(y) && 10 == get(y).size();
  }

  boolean isEmpty(double x, double y) {
    return (PlacementTester.isOutOfBounds(x, y)) || get(x, y).isEmpty();
  }

  boolean isEmpty(int y) {
    return BoundsTester.yInBounds(y) && 0 == get(y).size();
  }

  void copy(int rowFrom, int rowTo) {
    ArrayList<Block> row = get(rowFrom);
    row.forEach(c -> c.setY(rowTo));
    set(rowTo, row);
  }

  void copy(Block block) {
    if (PlacementTester.isOutOfBounds(block)) return;

    Optional<Block> tmpCell = get(block.getX(), block.getY());

    if (tmpCell.isPresent()) tmpCell.get().setColor(block.getColor());
    else set(block);
  }

  void empty() {
    for (int y = 0; y < height; y++) blocksByRow[y] = new ArrayList<>();
  }

  void empty(int row) {
    blocksByRow[row] = new ArrayList<>();
  }

  void empty(Block c) {
    ArrayList<Block> row = get(c.getY());

    for (int i = 0, s = row.size(); i < s; i++) {
      Block tmpC = row.get(i);

      if (tmpC.getX() == c.getX()) {
        row.remove(i);

        break;
      }
    }
  }
}
