package com.newtetris.console;

import com.newtetris.playfield.PlayField;
import com.newtetris.Cell;
import com.newtetris.coords.Coords;
import com.newtetris.tetrispiece.TetrisPiece;

public class DrawBoard {
    private static int y = 24;
    private static int x = 10;

    public static void draw(PlayField playField) {
        for (int i = 0; i < y; i++) {
            drawByRow(playField.getCellRow(i));
        }

        System.out.print("  ");

        for (int i = 0; i < x; i++) {
            System.out.print("-");
        }

        System.out.println();
    }

    public static void draw(Cell[][] cells) {
        for (int i = 0; i < cells.length; i++) {
            drawByRow(cells[i]);
        }
    }

    public static void drawByRow(Cell[] row) {
        System.out.print(" |");

        for (Cell c : row) {
            System.out.print(cellFilledToChar(c.isFull()));
        }

        System.out.println("|");
    }

    public static char cellFilledToChar(boolean isFull) {
        return isFull ? '*' : ' ';
    }

    public static Cell[][] putPieceOnBoard(TetrisPiece t, int rotation, PlayField playField) {
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
}
