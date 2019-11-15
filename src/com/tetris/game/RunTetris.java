package com.tetris.game;

import com.tetris.game.mechanics.ChangePlayfield;
import com.tetris.game.mechanics.LockDelay;
import com.tetris.game.mechanics.Movement;
import com.tetris.game.constants.Constants;
import com.tetris.game.recordkeeping.GameRecordKeeping;
import com.tetris.game.things.RowList;
import com.tetris.game.things.ActiveBlock;
import com.tetris.game.things.TetrominoQueue;
import com.tetris.game.things.Tetromino;
import com.tetris.gui.GUIMain;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;

/**
 * `RunTetris` is an instance of the game. It stores all the state that the game needs
 * and contains methods that call the methods that amount to the game's mechanics. It
 * wasn't necessary to put all of these methods-calling-methods in RunTetris.
 * They're meant to encapsulate the highest-level logic (or encapsulate its encapsulations)
 * that the game uses, the methods that amount to the complete game and all of its rules.
 *
 * I don't particularly like the way this code is organized.
 *
 * Adding multiplayer will require a lot of changes, but solely adding another instance
 * of the game ought to be no harder than instantiating this class a second time.
 */
public class RunTetris {
  private ActiveBlock currentBlock = new ActiveBlock();
  private TetrominoQueue tetrominoQueue = new TetrominoQueue(currentBlock);
  private Tetromino holdBlock;
  private RowList playfield = new RowList();
  private ArrayList<RowList> sinkingBlocks = new ArrayList<>();
  private LockDelay lockDelay;
  private GameRecordKeeping recordKeeping = new GameRecordKeeping();
  private GUIMain gui;
  private Timer timer;
  private Timer timer2;
  private boolean gameOver = false;

  public RunTetris(GUIMain gui) {
    tetrominoQueue.resetCurrentBlock(currentBlock);
    lockDelay = new LockDelay(this);
    this.gui = gui;

    setActiveBlockTimer();
    setSinkingBlockTimer();
  }

  public ActiveBlock getActiveBlock() {
    return currentBlock;
  }

  public RowList getPlayfield() {
    return playfield;
  }

  public LockDelay getLockDelay() {
    return lockDelay;
  }

  public ArrayList<RowList> getSinkingBlocks() {
    return sinkingBlocks;
  }

  public void setSinkingBlocks(ArrayList<RowList> rls) {
    sinkingBlocks.addAll(rls);

    setSinkingBlockTimer();
  }

  public GameRecordKeeping getRecordKeeping() {
    return recordKeeping;
  }

  public void dropSinkingBlocks() {
    Movement.dropSinkingBlocks(this);
  }

  public void addSinkingBlockToPlayfield(RowList sinkingBlock) {
    ChangePlayfield.addSinkingBlockToBoard(this, sinkingBlock);
  }

  public void addBlockToPlayfield(ActiveBlock block) {
    ChangePlayfield.addActiveBlockToPlayfield(this, block);
  }

  public void dropActiveBlock() {
    Movement.translateBlock(this, 0, Constants.down);
  }

  public void translate(int x, int y) {
    Movement.translateBlock(this, x, y);
  }

  public void rotate(int incr) {
    Movement.rotate(this, incr);
  }

  public TetrominoQueue getTetrominoQueue() {
    return tetrominoQueue;
  }

  public @Nullable Tetromino getHoldPiece() {
    return holdBlock;
  }

  public void setHoldPiece() {
    if (holdBlock == null) {
      holdBlock = currentBlock.getTetromino();
      tetrominoQueue.resetCurrentBlock(currentBlock);
    } else {
      Tetromino tmp = holdBlock;
      holdBlock = currentBlock.getTetromino();
      currentBlock.reset(tmp);
    }
  }

  void setDropTimerDelay(int ms) {
    timer.setDelay(ms);
  }

  public void setActiveBlockTimer() {
    if (timer != null && timer.isRunning())
      timer.stop();

    timer =
        new Timer(
            recordKeeping.getDelayByLevel((int) recordKeeping.getLevel()),
            e -> {
              dropActiveBlock();

              gui.repaintFrame();
            });
    timer.start();

  }

  public void setSinkingBlockTimer() {
    if (timer2 != null && timer2.isRunning())
      timer2.stop();

    timer2 =
        new Timer(
            200,
            e -> {
              dropSinkingBlocks();

              gui.repaintFrame();
            });
    timer2.start();
  }

  public void endGame() {
    timer.stop();
    timer2.stop();
    gameOver = true;
  }

  public boolean isGameOver() {
    return gameOver;
  }
}
