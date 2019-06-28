package tetrisrevision.console;

import tetrisrevision.GUI;
import tetrisrevision.Cell;
import tetrisrevision.ModifyPlayField;
import tetrisrevision.PlayField;

public class DrawBoard implements GUI {
    private static PlayField p;

    public DrawBoard() {
    }

    public static void setStaticVariables(PlayField p) {
        DrawBoard.p = p;
    }

    public void draw() {
        int bound = PlayField.getHeight();

        for (int i1 = 0; i1 < bound; i1++) {
            System.out.print(" |");
            for (Cell c : p.getCells()[i1]) {
                System.out.print(drawCell(c.isFull()));
            }
            System.out.println("|");
        }

        System.out.print("  ");

        int bound1 = PlayField.getWidth();

        for (int i = 0; i < bound1; i++) {
            String s = "-";
            System.out.print(s);
        }

        System.out.println();
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
