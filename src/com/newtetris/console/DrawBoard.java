package com.newtetris.console;

import com.newtetris.GUI;
import com.newtetris.Game;
import com.newtetris.playfield.PlayField;
import com.newtetris.playfield.Cell;
import com.newtetris.playfield.Coords;
import com.newtetris.tetrispiece.TetrisPiece;

public class DrawBoard implements GUI {
    private static int y = 24;
    private static int x = 10;

    public void draw(PlayField playField) {
        for (int i = 0; i < y; i++) {
            drawByRow(playField.getCellRow(i));
        }

        System.out.print("  ");

        for (int i = 0; i < x; i++) {
            System.out.print("-");
        }

        System.out.println();
    }

    public void draw(Cell[][] cells) {
        for (Cell[] cell : cells) {
            drawByRow(cell);
        }
    }

    public void draw(Game g) {
        Cell[][] cells = g.getPlayField().getAllCells();

        for (Cell[] cell : cells) {
            drawByRow(cell);
        }
    }

    public void drawByRow(Cell[] row) {
        System.out.print(" |");

        for (Cell c : row) {
            System.out.print(drawCell(c.isFull()));
        }

        System.out.println("|");
    }

    public char drawCell(boolean isFull) {
        return isFull ? '*' : ' ';
    }

    public Cell[][] putPieceOnBoard(TetrisPiece t, int rotation, PlayField playField) {
        Cell[][] cellsCopy = playField.getAllCells().clone();
        Coords[] a = t.getShape();
        Coords u = t.getCenter();

        for (Coords c : a) {
            if (
                    (c.getX() + u.getX() > -1 && c.getX() + u.getX() < 10) &&
                    (c.getY() + u.getY() > -1 && c.getY() + u.getY() < 24)
            ) {
                cellsCopy[u.getY() + c.getY()][c.getX() + u.getX()].setFull();
            }
        }

        return cellsCopy;
    }

    public void removePieceFromBoard(TetrisPiece t, int rotation, PlayField playField) {
        Cell[][] cellsCopy = playField.getAllCells().clone();
        Coords[] a = t.getShape();
        Coords u = t.getCenter();

        for (Coords c : a) {
            if (
                    (c.getX() + u.getX() > -1 && c.getX() + u.getX() < 10) &&
                            (c.getY() + u.getY() > -1 && c.getY() + u.getY() < 24)
            ) {
                cellsCopy[u.getY() + c.getY()][c.getX() + u.getX()].setEmpty();
            }
        }
    }

    public void drawBoardIncludingPiece(Game g) {
        draw(putPieceOnBoard(g.getFallingPiece(), g.getFallingPiece().getOrientation(), g.getPlayField()));
    }
}
