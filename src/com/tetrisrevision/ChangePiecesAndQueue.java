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
class ChangePiecesAndQueue {
  private TetrisPiece falling;
  private ArrayList<TetrominoEnum> q;
  private ArrayList<TetrominoEnum> backupQ;

  ChangePiecesAndQueue(
      TetrisPiece falling, ArrayList<TetrominoEnum> q, ArrayList<TetrominoEnum> backupQ
  ) {
    this.falling = falling;
    this.q = q;
    this.backupQ = backupQ;

    setQs();
  }

  private void setQs() {
    q = getQ();
    backupQ = getQ();
  }

  private ArrayList<TetrominoEnum> getQ() {
    TetrominoEnum[] ts = TetrominoEnum.values();
    ArrayList<TetrominoEnum> tList1 = new ArrayList<>(Arrays.asList(ts));
    Collections.shuffle(tList1);

    return tList1;
  }

  void getNextPiece() {
    falling.reset(q.get(0).get());

    q.remove(0);

    if (backupQ.size() == 0) {
      backupQ = getQ();
    }

    q.add(backupQ.get(0));
    backupQ.remove(0);
  }
}
