package com.tetrisrevision.tetrominos;

import com.tetrisrevision.Constants;

public abstract class Coords {
  static final int[] l1u2 = {Constants.left, Constants.up * 2};
  static final int[] u2 = {0, Constants.up * 2};
  static final int[] r1u2 = {Constants.right, Constants.up * 2};

  static final int[] l2u1 = {Constants.left * 2, Constants.up};
  static final int[] l1u1 = {Constants.left, Constants.up};
  static final int[] u1 = {0, Constants.up};
  static final int[] r1u1 = {Constants.right, Constants.up};
  static final int[] r2u1 = {Constants.right * 2, Constants.up};

  static final int[] l2 = {Constants.left * 2, 0};
  static final int[] l1 = {Constants.left, 0};
  static final int[] r1 = {Constants.right, 0};
  static final int[] r2 = {Constants.right * 2, 0};

  static final int[] l2d1 = {Constants.left * 2, Constants.down};
  static final int[] l1d1 = {Constants.left, Constants.down};
  static final int[] d1 = {0, Constants.down};
  static final int[] r1d1 = {Constants.right, Constants.down};
  static final int[] r2d1 = {Constants.right * 2, Constants.down};

  static final int[] l1d2 = {Constants.left, Constants.down * 2};
  static final int[] d2 = {0, Constants.down * 2};
  static final int[] r1d2 = {Constants.right, Constants.down * 2};
}
