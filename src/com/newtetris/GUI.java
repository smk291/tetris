package com.newtetris;

import com.newtetris.playfield.Cell;
import com.newtetris.playfield.PlayField;
import com.newtetris.tetrispiece.TetrisPiece;

public interface GUI {
    public void draw(PlayField playField);

    public void draw(Cell[][] cells);

    public void draw(Game g);

    public void drawByRow(Cell[] row);

    public char drawCell(boolean isFull);

    public Cell[][] putPieceOnBoard(TetrisPiece t, int rotation, PlayField playField);

    public void removePieceFromBoard(TetrisPiece t, int rotation, PlayField playField);

    public void drawBoardIncludingPiece(Game g);
}

