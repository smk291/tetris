package com.tetrisrevision.helpers;

import java.awt.event.KeyEvent;

public class CommandKeyCodes {
  private static int switchHoldPiece = KeyEvent.VK_Q;
  private static int counterClockwise = KeyEvent.VK_LEFT;
  private static int clockwise = KeyEvent.VK_RIGHT;
  private static int hardDrop = KeyEvent.VK_DOWN;

  private static int left = KeyEvent.VK_LEFT;
  private static int right = KeyEvent.VK_RIGHT;
  private static int drop = KeyEvent.VK_DOWN;
  private static int up = KeyEvent.VK_UP;

  public static int getSwitchHoldPiece() {
    return switchHoldPiece;
  }

  public static void setSwitchHoldPiece(int switchHoldPiece) {
    CommandKeyCodes.switchHoldPiece = switchHoldPiece;
  }

  public static int getCounterClockwise() {
    return counterClockwise;
  }

  public static void setCounterClockwise(int counterClockwise) {
    CommandKeyCodes.counterClockwise = counterClockwise;
  }

  public static int getLeft() {
    return left;
  }

  public static void setLeft(int left) {
    CommandKeyCodes.left = left;
  }

  public static int getRight() {
    return right;
  }

  public static void setRight(int right) {
    CommandKeyCodes.right = right;
  }

  public static int getDrop() {
    return drop;
  }

  public static void setDrop(int drop) {
    CommandKeyCodes.drop = drop;
  }

  public static int getUp() {
    return up;
  }

  public static void setUp(int up) {
    CommandKeyCodes.up = up;
  }

  public static int getClockwise() {
    return clockwise;
  }

  public static void setClockwise(int clockwise) {
    CommandKeyCodes.clockwise = clockwise;
  }

  public static int getHardDrop() {
    return hardDrop;
  }

  public static void setHardDrop(int hardDrop) {
    CommandKeyCodes.hardDrop = hardDrop;
  }
}
