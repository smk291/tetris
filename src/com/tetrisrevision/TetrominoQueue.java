package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * **
 *
 * <p>This class creates piece queues and randomizes the queue according to official tetris
 * guidelines.
 *
 * <p>Currently there's no way to switch out a piece.
 */
class TetrominoQueue {
  private ArrayList<TetrominoEnum> q;
  private ArrayList<TetrominoEnum> backupQ;

  TetrominoQueue() {
    this.q = new ArrayList<>();
    this.backupQ = new ArrayList<>();

    setQs();
  }

  private void setQs() {
    q = getQ();
    backupQ = getQ();
  }

  private ArrayList<TetrominoEnum> getQ() {
    TetrominoEnum[] allTetrominos = TetrominoEnum.values();
    ArrayList<TetrominoEnum> allTetrominosList = new ArrayList<>(Arrays.asList(allTetrominos));
    Collections.shuffle(allTetrominosList);

    return allTetrominosList;
  }

  void resetCurrentPiece(TetrisPiece piece) {
    piece.reset(q.get(0).get());

    q.remove(0);

    if (backupQ.size() == 0) {
      backupQ = getQ();
    }

    q.add(backupQ.get(0));
    backupQ.remove(0);
  }
}
