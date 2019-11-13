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
 * They're there to encapsulate the highest-level logic that the game uses. These are the
 * methods that amount to the complete game and all of its rules.
 *
 * Adding multiplayer will require a lot of changes, but solely adding another instance
 * of the game ought to be no harder than instantiating this class a second time.
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
