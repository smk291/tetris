package com.tetris;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

enum RotationDirection {
    CLOCKWISE, COUNTERCLOCKWISE
}

enum ColorFromPiece {
    IPiece(Color.CYAN),
    OPiece(Color.RED),
    TPiece(Color.GREEN),
    SPiece(Color.MAGENTA),
    ZPiece(Color.BLUE),
    JPiece(Color.yellow),
    LPiece(Color.ORANGE);

    Color pieceColor;

     ColorFromPiece (Color pieceColor) {
        this.pieceColor = pieceColor;
    }
}

class TetrisBlock extends JPanel {
    private Color color;
    private Area a;
    private Rectangle2D.Double r;

    TetrisBlock (Color c, int[][] boardCoordinates, Board board, Point2D.Double neCorner, Point2D.Double swCorner) {
        r = new Rectangle2D.Double();
        r.setFrameFromDiagonal(neCorner, swCorner);
        a = new Area(r);
        color = c;
    }

    public Color getColor() {
        return color;
    }

    public Area getArea() {
        return a;
    }

    public Rectangle2D.Double getRectangle() {
        return r;
    }
}

abstract class TetrisPiece {
    private static Color pieceColor = Color.CYAN;
    private static int[][] pieceArr = {{}};
    private static int[][] pieceArr90 = {{}};
    private static int[][] pieceArr180 = {{}};
    private static int[][] pieceArr270 = {{}};
    private static int rotationPermutations;
    private static int[] leftEdgeByRotationPermutation;
    private static int[] rightEdgeByRotationPermutation;
}

class TPiece extends TetrisPiece {
    private final static int[][] pieceArr =
        {
                      {0, -1},
             {-1, 0},/*{0,0},*/{1,  0}
        };
    private final static int[][] pieceArr90 =
        {
                      {0, -1},
                     /*{0,0},*/{1, -1},
                      {0,  1}
        };
    private final static int[][] pieceArr180 =
        {
             {-1, 0},/*{0,0},*/{1,  0},
                      {0,  0}
        };
    private final static int[][] pieceArr270 =
        {
                      {0, -1},
             {-1, 0},/*{0, 0},*/
                      {0,  1}
        };

    private final static int rotationPermutations = 4;
    private final static int[] leftEdgeByRotationPermutation = {-1, 0, -1, -1};
    private final static int[] rightEdgeByRotationPermutation = {1, 1, 1, 0};
    private final static Color pieceColor =  ColorFromPiece.TPiece.pieceColor;

    public static int[][] getPieceByRotation(int rotation) {
        if (rotation > 0 && rotation < rotationPermutations) {
            switch(rotation) {
                case 0:
                    return pieceArr;
                case 1:
                    return pieceArr90;
                case 2:
                    return pieceArr180;
                case 3:
                    return pieceArr270;
            }
        }

        return new int[][]{{}};
    }

    public static Color getColor() {
        return pieceColor;
    }
}

class OPiece extends TetrisPiece {
    final static int[][] pieceArr =
        {
                      {0, -1}, {1,  1},
                    /*{0, 0},*/{0,  1}
        };
    final static int rotationPermutations = 1;
    final static int[] leftEdgeByRotationPermutation = {0};
    final static int[] rightEdgsByRotationPermutation = {1};
    final static Color pieceColor =  ColorFromPiece.OPiece.pieceColor;
}

class IPiece extends TetrisPiece {
    final static int[][] pieceArr =
        {
    {-2, 0}, {-1, 0},/*{0,0},*/{1,  0}
        };
    final static int[][] pieceArr90 =
        {
                      {0, -2},
                      {0, -1},
                    /*{0,  0},*/
                      {0,  1}
        };
    final static int rotationPermutations = 2;
    final static int[] leftEdgeByRotationPermutation = {-1, 0};
    final static int[] rightEdgsByRotationPermutation = {2, 0};
    final static Color pieceColor =  ColorFromPiece.IPiece.pieceColor;
}

class SPiece extends TetrisPiece {
    final static int[][] pieceArr =
        {
                    /*{0, 0},*/{1,  0},
             {-1, 1}, {0,  1}
        };
    final static int[][] pieceArr90 =
        {
                      {0, -1},
                    /*{0, 0},*/{1,  0},
                               {1,  1}
        };
    final static int rotationPermutations = 2;
    final static int[] leftEdgeByRotationPermutation = {-1, 0};
    final static int[] rightEdgsByRotationPermutation = {1, 1};
    final static Color pieceColor =  ColorFromPiece.SPiece.pieceColor;
}

class ZPiece extends TetrisPiece {
    final static int[][] pieceArr =
        {
             {-1,0},/*{0, 0},*/
                      {0,  1}, {1,  1}
        };
    final static int[][] pieceArr90 =
       {
                      {0, -1},
             {-1, 0},/*{0, 0},*/
             {-1, 1}
       };
    final static int rotationPermutations = 2;
    final static int[] leftEdgeByRotationPermutation = {-1, 0};
    final static int[] rightEdgsByRotationPermutation = {1, 1};
    final static Color pieceColor =  ColorFromPiece.ZPiece.pieceColor;
}

class LPiece extends TetrisPiece {
    final static int[][] pieceArr =
        {
                      {0, -1},
                    /*{0,  0},*/
                      {0,  1}, {1,  1}
        };
    final static int[][] pieceArr90 =
        {
             {-1, 0},/*{0,0},*/{1,  0},
             {-1, 1}
        };
    final static int[][] pieceArr180 =
        {
             {-1,-1}, {0, -1},
                    /*{0,  0},*/
                      {0,  1}
        };
    final static int[][] piece270 =
        {
                               {1, -1},
             {-1, 0},/*{0,0},*/{1,  0}
        };
    final static int rotationPermutations = 4;
    final static int[] leftEdgeByRotationPermutation = {0, -1, 0, -1};
    final static int[] rightEdgsByRotationPermutation = {1, 1, 1, 1};
    final static Color pieceColor =  ColorFromPiece.LPiece.pieceColor;
}

class JPiece extends TetrisPiece {
    final static int[][] pieceArr =
       {
                     {0, -1},
                   /*{0,  0},*/
            {-1, 1}, {0,  1}
       };
    final static int[][] pieceArr90 =
        {
            {-1,-1},
            {-1, 0},/*{0, 0},*/{1,  0}
        };
    final static int[][] pieceArr180 =
        {
                      {0, -1}, {1, -1},
                    /*{0,  0},*/
                      {0,  1}
        };
    final static int[][] piece270 =
        {
            {-1, 0},/*{0, 0}*/ {1,  0},
                               {1,  1}
        };
    final static int rotationPermutations = 4;
    final static int[] leftEdgeByRotationPermutation = {0, -1, 0, -1};
    final static int[] rightEdgsByRotationPermutation = {1, 1, 1, 1};
    final static Color pieceColor =  ColorFromPiece.JPiece.pieceColor;
}

//    private void paintNBorder(Component c, Graphics g, int x, int y, int width, int height) {
//        this.paint.paintBorder(
//            c,
//            g,
//            (int) r.getMinX(),
//            (int) r.getMinY(),
//            (int) r.width,
//            (int) r.height / 10
//        );
//    }
//
//    private void paintSBorder(Component c, Graphics g, int x, int y, int width, int height) {
//        this.paintBorder(
//                c,
//                g,
//                (int) r.getMinX(),
//                (int) r.getMaxY() - (int) r.height / 10 * 9,
//                (int) r.width,
//                (int) r.height / 10
//        );
//    }
//
//    private void paintEBorder(Component c, Graphics g, int x, int y, int width, int height) {
//        this.paintBorder(
//                c,
//                g,
//                (int) r.getMinX(),
//                (int) r.getMinY(),
//                (int) r.width / 10,
//                (int) r.height
//        );
//    }
//
//    private void paintWBorder(Component c, Graphics g, int x, int y, int width, int height) {
//        this.paintBorder(
//                c,
//                g,
//                (int) r.getMaxX() - (int) r.width / 10,
//                (int) r.getMinY(),
//                (int) r.width / 10,
//                (int) r.height
//        );
//    }
//
//    public Insets getBorderInsets(Component c) {
//        return null;
//    }
//
//    public boolean isBorderOpaque() {
//        return true;
//    }
