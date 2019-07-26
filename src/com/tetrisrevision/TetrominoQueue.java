package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;
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
  private ArrayList<TetrominoEnum> queue;
  private ArrayList<TetrominoEnum> backupQueue;

  TetrominoQueue() {
    this.queue = new ArrayList<>();
    this.backupQueue = new ArrayList<>();

    setQs();
  }

  private void setQs() {
    queue = getQ();
    backupQueue = getQ();
  }

  private ArrayList<TetrominoEnum> getQ() {
    TetrominoEnum[] tetrominos = TetrominoEnum.values();
    ArrayList<TetrominoEnum> tetrominosAsList = new ArrayList<>(Arrays.asList(tetrominos));
    Collections.shuffle(tetrominosAsList);

    return tetrominosAsList;
  }

  void resetCurrentPiece(TetrisPiece piece) {
    piece.reset(queue.get(0).get());

    queue.remove(0);

    if (backupQueue.size() == 0)
      backupQueue = getQ();

    queue.add(backupQueue.get(0));
    backupQueue.remove(0);
  }

  public ArrayList<TetrominoEnum> getQueue() {
    return queue;
  }
}
