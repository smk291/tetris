package com.tetrisrevision.gamemechanics;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.actions.PlacementTester;
import com.tetrisrevision.actions.RowDeleter;
import com.tetrisrevision.actions.SinkingPieceFinder;
import com.tetrisrevision.recordkeeping.GameRecordKeeping;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;

import java.util.ArrayList;

public class ChangePlayfield {
  public static void addSinkingPieceToBoard(RunTetris rt, RowList sinkingPiece) {
    RowList playfield = rt.getPlayfield();
    TetrisPiece currentPiece = rt.getCurrentPiece();
    GameRecordKeeping records = rt.getRecordKeeping();
    ArrayList<RowList> sinkingPieces = rt.getSinkingPieces();

    playfield.addRowList(sinkingPiece);

    ArrayList<Integer> deletedRowIdx =
        RowDeleter.apply(sinkingPiece, currentPiece, playfield, records);

    sinkingPieces.remove(sinkingPiece);

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingPieceFinder().findSinkingPieces(i, playfield, sinkingPieces));
    }
  }

  public static void addPieceToPlayfield(RunTetris rt, TetrisPiece piece) {
    RowList playfield = rt.getPlayfield();
    GameRecordKeeping records = rt.getRecordKeeping();

    playfield.addRowList(piece.getBlocks());

    ArrayList<Integer> deletedRowIdx =
        RowDeleter.apply(piece.getBlocks(), piece, playfield, records);

    if (deletedRowIdx.size() > 0) {
      deletedRowIdx.forEach(
          i -> new SinkingPieceFinder().findSinkingPieces(i, playfield, rt.getSinkingPieces()));
    }

    // If pieces other than current piece can be added, then this needs to change
    rt.getTetrominoQueue().resetCurrentPiece(piece);

    if (!PlacementTester.cellsCanBeOccupied(piece, playfield)) {
      //

      return;
    }
  }
}
