package com.tetrisrevision;

import com.tetrisrevision.gamemechanics.ChangePlayfield;
import com.tetrisrevision.gamemechanics.LockDelay;
import com.tetrisrevision.gamemechanics.Movement;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.recordkeeping.GameRecordKeeping;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.TetrominoQueue;
import com.tetrisrevision.things.tetrominoes.Tetromino;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;

/**
 *
 *  
 *
 *
 */

public class RunTetris {
  private TetrisPiece currentPiece = new TetrisPiece();
  private TetrominoQueue tetrominoQueue = new TetrominoQueue(currentPiece);
  private Tetromino holdPiece;
  private RowList playfield = new RowList();
  private ArrayList<RowList> sinkingPieces = new ArrayList<>();
  private LockDelay lockDelay;
  private GameRecordKeeping recordKeeping = new GameRecordKeeping();

  public RunTetris() {
    tetrominoQueue.resetCurrentPiece(currentPiece);
    lockDelay = new LockDelay(this);
  }

  public TetrisPiece getCurrentPiece() {
    return currentPiece;
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

  public GameRecordKeeping getRecordKeeping() {
    return recordKeeping;
  }

  public void dropSinkingPieces() {
    Movement.dropSinkingPieces(this);
  }

  public void addSinkingPieceToBoard(RowList sinkingPiece) {
    ChangePlayfield.addSinkingPieceToBoard(this, sinkingPiece);
  }

  public void addPieceToPlayfield(TetrisPiece piece) {
    ChangePlayfield.addPieceToPlayfield(this, piece);
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
      holdPiece = currentPiece.getTetromino();
      tetrominoQueue.resetCurrentPiece(currentPiece);
    } else {
      Tetromino tmp = holdPiece;
      holdPiece = currentPiece.getTetromino();
      currentPiece.reset(tmp);
    }
  }

  public void setSinkingPieces(ArrayList<RowList> rls) {
    sinkingPieces.addAll(rls);
  }
}
