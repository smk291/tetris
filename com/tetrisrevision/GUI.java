package com.tetrisrevision;

public interface GUI {
    void draw();

    char drawCell(boolean isFull);

    void drawBoardIncludingPiece();
}

