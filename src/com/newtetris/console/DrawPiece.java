package com.newtetris.console;

import com.newtetris.Coords;
import com.newtetris.pieces.TetrisPiece;

import java.util.ArrayList;
import java.util.Arrays;

public class DrawPiece {
    public void drawPiece(TetrisPiece t) {
        int width;
        int height;
        int xMin;
        int yMin;

        for (int i = 0; i < t.getRotationSteps(); i++){
            width = 5;
            height = 4;
            xMin = 2;
            yMin = 2;

            String[][] a = new String[height][width];

            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    a[j][k] = " ";
                }
            }

            Coords[] c = t.getPieceByRotation(i);

            for (Coords q : c) {
                a[yMin + q.getY()][xMin + q.getX()] = "*";
            }

            a[yMin][xMin] = "*";

            for (String[] as : a) {
                for (String p  : as) {
                    System.out.print(" " + p + " ");
                }

                System.out.println(i);
            }
        }
    }

    public void drawPiece(TetrisPiece t, int r) {
        int xMax = getXMax(t, r);
        int yMax = getYMax(t, r);
        int xMin = getXMin(t, r);
        int yMin = getYMin(t, r);
        int width = xMax - xMin + 1;
        int height = yMax - yMin + 1;
        String[][] a = new String[height][width];
        double centerX = Math.floor(((double) xMax - (double) xMin + 1d) / 2d);
        double centerY = Math.floor(((double) yMax - (double) yMin + 1d) / 2d);

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
                "array[0].length: " + a[0].length + "\n" +
                "centerX: " + centerX + "\n" +
                "centerY: " + centerY
        );

        for (int j = 0; j < height; j++) {
            for (int k = 0; k < width; k++) {
                a[j][k] = " ";
            }
        }

        Coords[] c = t.getPieceByRotation(r);

        for (Coords q : c) {
            int coordX = q.getX();
            int coordY = q.getY();
            int centeredX = q.getX() + (int) centerX;
            int centeredY = q.getY() + (int) centerY;

            System.out.println(
                    "x: " + coordX + "\n" +
                    "y: " + coordY + "\n" +
                    "coord y: " + centeredY + "\n" +
                    "coord x: " + centeredX
            );

            a[centeredY][centeredX] = "*";
        }

        a[yMin + height][xMin + width] = "*";

        for (String[] as : a) {
            for (String p  : as) {
                System.out.print("" + p + "");
            }

            System.out.println(r);
        }
    }

    public void drawPiece2(TetrisPiece t, int r) {
        int xMax = getXMax(t, r);
        int yMax = getYMax(t, r);
        int xMin = getXMin(t, r);
        int yMin = getYMin(t, r);
        int width = xMax - xMin + 1;
        int height = yMax - yMin + 1;
        String[][] a = new String[height][width];
        double centerX = Math.floor(((double) xMax - (double) xMin) / 2d);
        double centerY = Math.floor(((double) yMax - (double) yMin) / 2d);

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
                        "array[0].length: " + a[0].length + "\n" +
                        "centerX: " + centerX + "\n" +
                        "centerY: " + centerY
        );

        for (int j = 0; j < height; j++) {
            for (int k = 0; k < width; k++) {
                a[j][k] = " ";
            }
        }

        Coords[] c = t.getPieceByRotation(r);

        for (Coords q : c) {
            int coordX = q.getX();
            int coordY = q.getY();
//            int centeredX = q.getX() + (int) centerX;
//            int centeredY = q.getY() + (int) centerY;
            int adjustedX = q.getX() - xMin;
            int adjustedY = q.getY() - yMin;
            System.out.println(
                    "x: " + coordX + "\n" +
                            "y: " + coordY + "\n" +
                            "xMin: " + xMin + "\n" +
                            "yMin: " + yMin + "\n" +
//                            "coord y: " + centeredY + "\n" +
//                            "coord x: " + centeredX
                            "adjustedX: " + adjustedX + "\n" +
                            "adjustedY: " + adjustedY + "\n"
            );

            a[adjustedY][adjustedX] = "*";
        }

//        a[yMin + height][xMin + width] = "*";

        for (String[] as : a) {
            for (String p  : as) {
                System.out.print(p);
            }

            System.out.println(r);
        }
    }

    public int getXMax(TetrisPiece t, int rotationStep) {
        Coords[] cs = t.getPieceByRotation(rotationStep);
        int xMax = -3;

        for (Coords c : cs) {
            xMax = (c.getX() > xMax) ? c.getX() : xMax;
        }

        return xMax;
    }

    public int getXMin(TetrisPiece t, int rotationStep) {
        Coords[] cs = t.getPieceByRotation(rotationStep);
        int xMin = 3;

        for (Coords c : cs) {
            xMin = (c.getX() < xMin) ? c.getX() : xMin;
        }

        return xMin;
    }


    public int getYMax(TetrisPiece t, int rotationStep) {
        Coords[] cs = t.getPieceByRotation(rotationStep);
        int yMax = -3;

        for (Coords c : cs) {
            yMax = (c.getY() > yMax) ? c.getY() : yMax;
        }

        return yMax;
    }


    public int getYMin(TetrisPiece t, int rotationStep) {
        Coords[] cs = t.getPieceByRotation(rotationStep);
        int yMin = 3;

        for (Coords c : cs) {
            yMin = (c.getY() < yMin) ? c.getY() : yMin;
        }

        return yMin;
    }
}
