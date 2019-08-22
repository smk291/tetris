package com.tetrisrevision.tetrominos;

import com.tetrisrevision.Constants;

public abstract class RelativeCoords {
  final static int[] l1u2 = {Constants.left(), Constants.up() * 2};
  final static int[] u2 = {0, Constants.up() * 2};
  final static int[] r1u2 = {Constants.right(), Constants.up() * 2};

  final static int[] l2u1 = {Constants.left() * 2, Constants.up()};
  final static int[] l1u1 = {Constants.left(), Constants.up()};
  final static int[] u1 = {0, Constants.up()};
  final static int[] r1u1 = {Constants.right(), Constants.up()};
  final static int[] r2u1 = {Constants.right() * 2, Constants.up()};

  final static int[] l2 = {Constants.left() * 2, 0};
  final static int[] l1 = {Constants.left(), 0};
  final static int[] r1 = {Constants.right(), 0};
  final static int[] r2 = {Constants.right() * 2, 0};

  final static int[] l2d1 = {Constants.left() * 2, Constants.down()};
  final static int[] l1d1 = {Constants.left(), Constants.down()};
  final static int[] d1 = {0, Constants.down()};
  final static int[] r1d1 = {Constants.right(), Constants.down()};
  final static int[] r2d1 = {Constants.right() * 2, Constants.down()};

  final static int[] l1d2 = {Constants.left(), Constants.down() * 2};
  final static int[] d2 = {0, Constants.down() * 2};
  final static int[] r1d2 = {Constants.right(), Constants.down() * 2};

}
