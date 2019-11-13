package com.tetris;

import com.tetris.gamemechanics.ChangePlayfield;
import com.tetris.gamemechanics.LockDelay;
import com.tetris.gamemechanics.Movement;
import com.tetris.constants.Constants;
import com.tetris.recordkeeping.GameRecordKeeping;
import com.tetris.things.RowList;
import com.tetris.things.ActiveBlock;
import com.tetris.things.TetrominoQueue;
import com.tetris.things.Tetromino;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;

/**
 * `RunTetris` is an instance of the game. It stores all the state that the game needs
 * and contains methods that call the methods that amount to the game's mechanics. It
 * wasn't necessary to put all of these methods-calling-methods in RunTetris.
 * They're there to signal that they're the methods that handle the essential functions
 * and interactions that amount to the complete game and all of its rules.
 *
 * Adding multiplayer will require a lot of changes, but solely adding another instance
 * of the game ought to be not harder than instantiating this class.
 */
public class RunTetris {
  private ActiveBlock currentBlock = new ActiveBlock();
  private TetrominoQueue tetrominoQueue = new TetrominoQueue(currentBlock);
  private Tetromino holdPiece;
  private RowList playfield = new RowList();
  private ArrayList<RowList> sinkingPieces = new ArrayList<>();
  private LockDelay lockDelay;
  private GameRecordKeeping recordKeeping = new GameRecordKeeping();

  public RunTetris() {
    tetrominoQueue.resetCurrentPiece(currentBlock);
    lockDelay = new LockDelay(this);
  }

  public ActiveBlock getCurrentPiece() {
    return currentBlock;
  }

  public RowList getPlayfield() {
    return playfield;
  }

  public LockDelay getLockDelay() {
    return lockDelay;
  }

  public ArrayList<RowList> getSinkingPieces() {
    return sinkingPieces;
  }

  public void setSinkingPieces(ArrayList<RowList> rls) {
    sinkingPieces.addAll(rls);
  }

  public GameRecordKeeping getRecordKeeping() {
    return recordKeeping;
  }

  public void dropSinkingPieces() {
    Movement.dropSinkingPieces(this);
  }

  public void addSinkingPieceToBoard(RowList sinkingPiece) {
    ChangePlayfield.addSinkingPieceToBoard(this, sinkingPiece);
  }

  public void addPieceToPlayfield(ActiveBlock block) {
    ChangePlayfield.addPieceToPlayfield(this, block);
  }

  public void dropCurrentPiece(JFrame frame) {
    Movement.translatePiece(this, frame, 0, Constants.down);
  }

  public void translatePiece(JFrame frame, int x, int y) {
    Movement.translatePiece(this, frame, x, y);
  }

  public void rotate(JFrame frame, int incr) {
    Movement.rotate(this, frame, incr);
  }

  public TetrominoQueue getTetrominoQueue() {
    return tetrominoQueue;
  }

  public @Nullable Tetromino getHoldPiece() {
    return holdPiece;
  }

  public void setHoldPiece() {
    if (holdPiece == null) {
      holdPiece = currentBlock.getTetromino();
      tetrominoQueue.resetCurrentPiece(currentBlock);
    } else {
      Tetromino tmp = holdPiece;
      holdPiece = currentBlock.getTetromino();
      currentBlock.reset(tmp);
    }
  }
}
