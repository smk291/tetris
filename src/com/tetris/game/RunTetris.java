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

  public RunTetris() {
    tetrominoQueue.resetCurrentBlock(currentBlock);
    lockDelay = new LockDelay(this);
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
    ChangePlayfield.addBlockToPlayfield(this, block);
  }

  public void dropActiveBlock(JFrame frame) {
    Movement.translateBlock(this, frame, 0, Constants.down);
  }

  public void translate(JFrame frame, int x, int y) {
    Movement.translateBlock(this, frame, x, y);
  }

  public void rotate(JFrame frame, int incr) {
    Movement.rotate(this, frame, incr);
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
}
