package com.tetris;

class PieceRotator {
    private final int unrotatedWidth;
    private final int unrotatedHeight;

    PieceRotator(boolean[][] pieceArr){
        unrotatedHeight = pieceArr.length;
        unrotatedWidth = pieceArr[0].length;
    }

    public void rotateAndDrawPiece() {
        // Determine row from which to start drawing
            // What's the current row of the currently falling piece
        // Determine column from which to start drawing -- se corner
            // Initally assume it will be same as current se corner
            // In future:
                // Where is piece midpoint?
                    //
        // Determine whether rotation is legal
        // Rotate
    }
    public void draw0degreeRotation (boolean[][] pieceArr, double edgeLength, TetrisEnum[][] board, int[] currentRowOfFallingPiece, int currentNeCorner) {


        for (int i = 0; i < unrotatedHeight; i++) {
            for (int j = 0; j < unrotatedWidth; j++) {
                if (
                        pieceArr[i][j] &&
                        (i ) <= unrotatedHeight &&
                        (j ) <= unrotatedWidth
                ) {

                }
            }
        }
    }

    public void draw90degreeRotation () {

    }

    public void draw180degreeRotation() {

    }

    public void draw270degreeRotation() {

    }
}
