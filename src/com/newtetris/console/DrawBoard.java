package com.newtetris.console;

import com.newtetris.GUI;
import com.newtetris.Game;
import com.newtetris.playfield.Cell;
import com.newtetris.playfield.Coords;
import com.newtetris.playfield.PlayField;
import com.newtetris.tetrispiece.TetrisPiece;

import java.util.ArrayList;

public class DrawBoard implements GUI {
    private int height;
    private int width;

    public DrawBoard(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public void draw(PlayField playField) {
        for (int i = 0; i < height; i++) {
            drawByRow(playField.getCellRow(i));
        }

        System.out.print("  ");

        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }

        System.out.println();
    }

    public void draw(Cell[][] cells) {
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

    public void drawBoardIncludingPiece(Game g) {
        g.putPieceOnBoard(g.getFallingPiece().playFieldCoords());

        draw(g.getPlayField());

        g.removePieceFromBoard(g.getFallingPiece().playFieldCoords());

        for (ArrayList<Coords> c : g.getSinkingPieces()) {
            g.removePieceFromBoard(c);
        }
    }
}
