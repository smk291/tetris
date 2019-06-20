package com.newtetris;

import com.newtetris.playfield.Cell;
import com.newtetris.playfield.PlayField;
import com.newtetris.tetrispiece.TetrisPiece;

public interface GUI {
    void draw(PlayField playField);

    void draw(Cell[][] cells);

    void drawByRow(Cell[] row);

    char drawCell(boolean isFull);

    Cell[][] putPieceOnBoard(Game g);

    void removePieceFromBoard(Game g);

    void drawBoardIncludingPiece(Game g);
}

