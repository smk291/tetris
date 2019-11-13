package com.tetrisrevision.things;

import com.tetrisrevision.things.tetrominoes.TetrominoEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This class creates block queues and randomizes the queue according to official tetris guidelines.
 *
 * <p>Currently there's no way to switch out a block.
 */
public class TetrominoQueue {
  private ArrayList<TetrominoEnum> queue;
  private ArrayList<TetrominoEnum> backupQueue;

  public TetrominoQueue(ActiveBlock p) {
    setQs();
    resetCurrentPiece(p);
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

  public void resetCurrentPiece(ActiveBlock block) {
    block.reset(queue.get(0).get());

    queue.remove(0);

    if (backupQueue.size() == 0) backupQueue = getQ();

    queue.add(backupQueue.get(0));
    backupQueue.remove(0);
  }

  public ArrayList<TetrominoEnum> getQueue() {
    return queue;
  }
}
