package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class TetrisPiece {
    private int prevOrientation;
    private int orientation;
    private Point center;
    private Tetromino tetromino;
    private boolean addToBoard;

    public TetrisPiece(Tetromino t) {
        resetSelf();
        this.tetromino = t;
    }

    void reset(Tetromino t) {
        resetSelf();
        this.tetromino = t;
    }

    private void resetSelf() {
        this.center = new Point(4, 0);
        this.orientation = 0;
        addToBoard = false;
    }

    void setFromTetromino(Tetromino t) {
        this.tetromino = t;
    }

    void translateCenter(int x, int y) {
        this.center.setLocation(
                this.center.getX() + x,
                this.center.getY() + y
        );
    }

    int getOrientation() {
        return this.orientation;
    }

    void setOrientation(int o) {
        this.orientation = o;
    }

    void incrOrientation(int i) {
        this.orientation += i;
    }

    int getPrevOrientation() {
        return prevOrientation;
    }

    void setPrevOrientation(int prevOrientation) {
        this.prevOrientation = prevOrientation;
    }

    Point[] getPieceLocation() {
        return Arrays.stream(tetromino.getOffsets()[orientation]).map(p -> new Point(
                (int) this.center.getX() + p[0],
                (int) this.center.getY() + p[1]
        )).toArray(Point[]::new);
    }

    int getOrientationMax() {
        return tetromino.getOffsets().length;
    }

    HashMap<Integer, HashMap<Integer, Integer[][]>> getKickData() {
        return tetromino.getKickData();
    }

    void setCenter(Point pt) {
        this.center = pt;
    }

    void setCenter(int x, int y) {
        this.center = new Point(x, y);
    }

    boolean isAddToBoard() {
        return addToBoard;
    }

    void setAddToBoard(boolean addToBoard) {
        this.addToBoard = addToBoard;
    }
}
