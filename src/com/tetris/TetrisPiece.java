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

enum TetrisEnum {
    IPiece(Color.CYAN),
    OPiece(Color.RED),
    TPiece(Color.GREEN),
    SPiece(Color.MAGENTA),
    ZPiece(Color.BLUE),
    JPiece(Color.yellow),
    LPiece(Color.ORANGE);

    Color pieceColor;

    TetrisEnum(Color pieceColor) {
        this.pieceColor = pieceColor;
    }
}

class TetrisBlock extends JPanel {
    private Color color = Color.CYAN;
    private Area a = new Area();
    private Rectangle2D.Double r = new Rectangle2D.Double();

    TetrisBlock (Color c, Point2D neCorner, Point2D swCorner) {
        r.setFrameFromDiagonal(neCorner, swCorner);
        Area a = new Area(r);
    }
}

abstract class TetrisPiece {
    private Color pieceColor = Color.CYAN;
    private int[][] pieceArr = {{}};
    private int[][] pieceArr90 = {{}};
    private int[][] pieceArr180 = {{}};
    private int[][] pieceArr270 = {{}};
    private HashMap<Integer, HashMap<Integer, Boolean>> pieceMap = new HashMap<>();
    private int rotationPermutations;
    private int[] leftEdgeByRotationPermutation;
    private int[] rightEdgeByRotationPermutation;
}

abstract class TPiece extends TetrisPiece {
    final static int[][] pieceArr =
        {
                      {0, -1},
             {-1, 0},/*{0,0},*/{1,  0}
        };
    final static int[][] pieceArr90 =
        {
                      {0, -1},
                     /*{0,0},*/{1, -1},
                      {0,  1}
        };
    final static int[][] pieceArr180 =
        {
             {-1, 0},/*{0,0},*/{1,  0},
                      {0,  0}
        };
    final static int[][] pieceArr270 =
        {
                      {0, -1},
             {-1, 0},/*{0, 0},*/
                      {0,  1}
        };
    final static int rotationPermutations = 4;
    final static int[] leftEdgeByRotationPermutation = {-1, 0, -1, -1};
    final static int[] rightEdgeByRotationPermutation = {1, 1, 1, 0};
    final static Color pieceColor = TetrisEnum.TPiece.pieceColor;
}

abstract class OPiece extends TetrisPiece {
    final static int[][] pieceArr =
        {
                      {0, -1}, {1,  1},
                    /*{0, 0},*/{0,  1}
        };
    final static int rotationPermutations = 1;
    final static int[] leftEdgeByRotationPermutation = {0};
    final static int[] rightEdgsByRotationPermutation = {1};
    final static Color pieceColor = TetrisEnum.OPiece.pieceColor;
}

abstract class IPiece extends TetrisPiece {
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
    final static Color pieceColor = TetrisEnum.IPiece.pieceColor;

    IPiece() { }
}

abstract class SPiece extends TetrisPiece {
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
    final static Color pieceColor = TetrisEnum.SPiece.pieceColor;
}

abstract class ZPiece extends TetrisPiece {
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
    final static Color pieceColor = TetrisEnum.ZPiece.pieceColor;
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
    final static Color pieceColor = TetrisEnum.LPiece.pieceColor;
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
    final static Color pieceColor = TetrisEnum.JPiece.pieceColor;
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
