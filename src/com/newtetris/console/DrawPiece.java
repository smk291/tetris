package com.newtetris.console;

import com.newtetris.Coords;
import com.newtetris.pieces.Tetromino;

public class DrawPiece {
    public void drawPiece(Tetromino t, int rotation) {
        int width = getWidthByRotation(t, rotation);
        int height = getHeightByRotation(t, rotation);

        String[][] a = createContainer(height, width);

        Coords[] pieceOffsets = t.getShapeByRotation(rotation);

        for (Coords coords : pieceOffsets) {
            int y = getAdjustedY(t, coords, rotation);
            int x = getAdjustedX(t, coords, rotation);

            a[y][x] = "*";
        }

        for (String[] as : a) {
            for (String p  : as) {
                System.out.print(p);
            }

            System.out.println(/* rotation */);
        }

        System.out.println();
    }

    public int getXMax(Tetromino t, int rotationStep) {
        Coords[] cs = t.getShapeByRotation(rotationStep);
        int xMax = -3;

        for (Coords c : cs) {
            xMax = (c.getX() > xMax) ? c.getX() : xMax;
        }

        return xMax;
    }

    public int getXMin(Tetromino t, int rotationStep) {
        Coords[] cs = t.getShapeByRotation(rotationStep);
        int xMin = 3;

        for (Coords c : cs) {
            xMin = (c.getX() < xMin) ? c.getX() : xMin;
        }

        return xMin;
    }

    public int getYMax(Tetromino t, int rotationStep) {
        Coords[] cs = t.getShapeByRotation(rotationStep);
        int yMax = -3;

        for (Coords c : cs) {
            yMax = (c.getY() > yMax) ? c.getY() : yMax;
        }

        return yMax;
    }

    public int getYMin(Tetromino t, int rotationStep) {
        Coords[] cs = t.getShapeByRotation(rotationStep);
        int yMin = 3;

        for (Coords c : cs) {
            yMin = (c.getY() < yMin) ? c.getY() : yMin;
        }

        return yMin;
    }

    public String[][] createContainer(int height, int width) {
        String[][] a = new String[height][width];

        for (int j = 0; j < height; j++) {
            for (int k = 0; k < width; k++) {
                a[j][k] = " ";
            }
        }

        return a;
    }

    public int getAdjustedX(Tetromino t, Coords c, int r) {
        int xMin = getXMin(t, r);

        return c.getX() - xMin + (xMin == 0 ? 1 : 0);
    }

    public int getAdjustedY(Tetromino t, Coords c, int r) {
        int yMin = getYMin(t, r);

        return c.getY() - yMin;
    }

    public void printInfo(Tetromino t, int r) {
        int xMax = getXMax(t, r);
        int yMax = getYMax(t, r);
        int xMin = getXMin(t, r);
        int yMin = getYMin(t, r);
        int width = xMax - xMin + (xMin == 0 ? 2 : 1);
        int height = yMax - yMin + 1;
        String[][] a = new String[height][width];

        System.out.println(
                "class: " + t.getClass() + "\n" +
                "rotation: " + r + "\n" +
                "xMin: " + xMin + "\n" +
                "yMin: " + yMin + "\n" +
                "xMax: " + xMax + "\n" +
                "yMax: " + yMax + "\n" +
                "width: " + width + "\n" +
                "height: " + height + "\n" +
                "array.length: " + a.length + "\n" +
                "array[0].length: " + a[0].length
        );
    }

    public void printMoreInfo() {
//        System.out.println(
//                "x: " + coordX + "\n" +
//                "y: " + coordY + "\n" +
//                "xMin: " + xMin + "\n" +
//                "yMin: " + yMin + "\n" +
//                "adjustedX: " + adjustedX + "\n" +
//                "adjustedY: " + adjustedY + "\n"
//        );
    }

    public int getHeightByRotation(Tetromino t, int r) {
        int yMax = getYMax(t, r);
        int yMin = getYMin(t, r);

        return yMax - yMin + 1;
    }

    public int getWidthByRotation(Tetromino t, int r) {
        int xMax = getXMax(t, r);
        int xMin = getXMin(t, r);

        return xMax - xMin + (xMin == 0 ? 2 : 1);
    }
}
