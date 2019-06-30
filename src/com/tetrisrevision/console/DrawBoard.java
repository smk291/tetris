package com.tetrisrevision.console;

import com.tetrisrevision.GUI;
import com.tetrisrevision.Cell;
import com.tetrisrevision.ModifyPlayField;
import com.tetrisrevision.PlayField;

public class DrawBoard implements GUI {
    public void draw() {
        int bound = PlayField.getHeight();


        for (int i1 = 0; i1 < bound; i1++)
            drawRow(i1);

        System.out.print("  ");

        for (int i = 0; i < PlayField.getWidth(); i++) {
            String s = "-";
            System.out.print(s);
        }

        System.out.println();
    }

    private void drawRow(int row) {
            System.out.print(" |");

            for (Cell c : PlayField.getCells()[row])
                System.out.print(drawCell(c.isFull()));

            System.out.println("| " + row);
    }

    public char drawCell(boolean isFull) {
        return isFull ? '*' : ' ';
    }

    public void drawBoardIncludingPiece() {
        ModifyPlayField.AddAndRemove.addFallingPiece();
        ModifyPlayField.AddAndRemove.addAllSinkingPieces();

        draw();

        ModifyPlayField.AddAndRemove.removeFallingPiece();
        ModifyPlayField.AddAndRemove.removeSinkingPieces();
    }
}
