package com.newtetris;

public class DrawBoardConsole {
    private static int width = 10;
    private static int height = 24;

    public static void draw(Board board) {
        for (int i = 0; i < height; i++) {
            drawByRow(board.getCellRow(i));
        }

        System.out.print("  ");

        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }

        System.out.println();
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
}
