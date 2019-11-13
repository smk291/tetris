package com.tetris.things.tetrominoes;

import com.tetris.things.Tetromino;

import java.util.Random;

public enum TetrominoEnum {
  I(new IBlock()),
  O(new OBlock()),
  L(new LBlock()),
  J(new JBlock()),
  T(new TBlock()),
  S(new SBlock()),
  Z(new ZBlock());

  public static final TetrominoEnum[] blocksArray = values();
  public static final int enumSize = blocksArray.length;
  public static final Random r = new Random();

  Tetromino block;

  TetrominoEnum(Tetromino block) {
    this.block = block;
  }

  public static Tetromino getTetromino() {
    return blocksArray[r.nextInt(enumSize)].block;
  }

  public Tetromino get() {
    return this.block;
  }
}
