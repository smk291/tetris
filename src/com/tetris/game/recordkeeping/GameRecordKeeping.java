package com.tetris.game.recordkeeping;

import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;
import com.tetris.game.things.Tetromino;

public final class GameRecordKeeping {
  private double score;
  private double linesCleared;
  private int level;
  private String last;
  private int comboCount;
  private int linesPerLevel = 20;
  private Tetromino holdBlock;

  public GameRecordKeeping() {
    reset();
  }

  private void reset() {
    score = 0;
    level = 0;
    comboCount = 0;
    last = "";
    linesCleared = 0;
  }

  public double getScore() {
    return score;
  }

  public double getLinesCleared() {
    return linesCleared;
  }

  public double getLevel() {
    return level;
  }

  void setLevel(int i) {
    level = i;
  }

  public double getComboCount() {
    return comboCount;
  }

  public void softDrop() {
    score++;
  }

  public void hardDrop(int cells) {
    score += 2 * cells;
  }

  public void incrCombo() {
    comboCount++;
  }

  public void resetCombo() {
    comboCount = 0;
  }

  private void incrementScore(double amount) {
    score += (level == 0 ? 1 : level) * amount * (comboCount > 1 ? 50 * comboCount : 1);
  }

  public void computeAndAdd(int rows, ActiveBlock block, RowList rowList) {
    TSpinTracker tst = block.gettSpinTracker();
    Tetromino t = block.getTetromino();

    if (t.isTBlock() && tst.isTSpinMini(block, rowList)) computeScoreTSpinMini(rows);
    else if (t.isTBlock() && tst.isTSpin(block, rowList)) computerScoreTSpin(rows);
    else computeAndAdd(rows);
  }

  private void computeAndAdd(int rows) {
    switch (rows) {
      case 1:
        single();

        break;
      case 2:
        doubleC();

        break;
      case 3:
        triple();

        break;
      case 4:
        tetris();

        break;
    }
  }

  private void computeScoreTSpinMini(int rows) {
    switch (rows) {
      case 1:
        tSpinSingleMini();

        break;
      case 2:
        tSpinDoubleMini();

        break;
    }
  }

  private void computerScoreTSpin(int rows) {
    switch (rows) {
      case 1:
        tSpinSingle();

        break;
      case 2:
        tSpinDouble();

        break;
      case 3:
        tSpinTriple();

        break;
    }
  }

  private void single() {
    incrementScore(100);
  }

  private void doubleC() {
    incrementScore(300);
  }

  private void triple() {
    incrementScore(500);
  }

  private void tetris() {
    incrementScore(800 * (last.equals("tetris") ? 1.5 : 1));

    last = "tetris";
  }

  private void tSpin(double score) {
    incrementScore(score * (last.equals("tspin") ? 1.5 : 1));

    last = "tspin";
  }

  private void tSpinSingleMini() {
    tSpin(100);
  }

  private void tSpinSingle() {
    tSpin(200);
  }

  void tSpinRegular() {
    tSpin(400);
  }

  private void tSpinDoubleMini() {
    tSpin(1200);
  }

  private void tSpinDouble() {
    tSpin(1200);
  }

  private void tSpinTriple() {
    tSpin(1600);
  }

  private int computeLevel() {
    return (int) Math.floor(linesCleared / linesPerLevel);
  }

  public void incrLinesCleared(int lines) {
    linesCleared += lines;

    if (computeLevel() > level) {
      level = (int) Math.floor(computeLevel());
    }
  }

  public int getDelayByLevel(int i) {
    while (true) {
      switch (i) {
        case 0:
          return 800;
        case 1:
          return 720;
        case 2:
          return 630;
        case 3:
          return 550;
        case 4:
          return 470;
        case 5:
          return 380;
        case 6:
          return 300;
        case 7:
          return 220;
        case 8:
          return 130;
        case 9:
          return 100;
        case 10:
          return 80;
        case 13:
          return 70;
        case 16:
          return 50;
        case 19:
          return 30;
        case 29:
          return 20;
      }

      i--;
    }
  }
}
