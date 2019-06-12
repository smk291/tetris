package com.tetris;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

enum RotationDirection {
    CLOCKWISE, COUNTERCLOCKWISE
}

enum TetrisEnum {
    IPiece(Color.CYAN, "I"),
    OPiece(Color.RED, "O"),
    TPiece(Color.GREEN, "T"),
    SPiece(Color.MAGENTA, "S"),
    ZPiece(Color.BLUE, "Z"),
    JPiece(Color.yellow, "J"),
    LPiece(Color.ORANGE, "L");

    Color pieceColor;
    String pieceName;
    TetrisPiece piece;

    TetrisEnum(Color pieceColor, String pieceName) {
        this.pieceColor = pieceColor;
        this.pieceName = pieceName;
    }
}

abstract class TetrisPiece {
    Area piece;
    Color pieceColor;
    boolean[][] pieceArr;
    TetrisPiece() { }

    public void rotate(RotationDirection rd) {
        AffineTransform transformer = new AffineTransform();

        transformer.rotate(
            Math.PI / 2,
            piece.getBounds2D().getCenterX(),
            piece.getBounds2D().getCenterY()
        );

        piece.transform(transformer);
    }
}

class TPiece extends TetrisPiece {
    boolean[][] pieceArr =
    {{false, true, false},
     {true,  true, true}};

    TPiece(double[] startingCoords) {
        super();
        pieceColor = TetrisEnum.TPiece.pieceColor;

        Area t1 = new Area(
                new Rectangle2D.Double(
                        0 + startingCoords[0],
                        50 + startingCoords[1],
                        150,
                        50
                )
        );
        Area t2 = new Area(
                new Rectangle2D.Double(
                        50 + startingCoords[0],
                        0 + startingCoords[1],
                        50,
                        50
                )
        );

        t1.add(t2);

        piece = t1;
    }
}

class OPiece extends TetrisPiece {
    boolean[][] pieceArr = new boolean[][] {{true, true,}, {true, true}};

    OPiece(double[] startingCoords) {
        super();
        pieceColor = TetrisEnum.OPiece.pieceColor;
        super.piece = new Area(
                new Rectangle2D.Double(
                        0 + startingCoords[0],
                        0 + startingCoords[1],
                        100,
                        100
                )
        );
    }

    public void rotate(RotationDirection rd) { }
}

class IPiece extends TetrisPiece {
    boolean[][] pieceArr = new boolean[][] {{true, true, true, true}};

    IPiece(double[] startingCoords) {
        super();
        pieceColor = TetrisEnum.IPiece.pieceColor;

        super.piece = new Area(
                new Rectangle2D.Double(
                        0 + startingCoords[0],
                        0 + startingCoords[1],
                        200,
                        50
                )
        );
    }
}

class SPiece extends TetrisPiece {
    boolean[][] pieceArr = new boolean[][]{{false, true, true}, {true, true, false}};

    SPiece(double[] startingCoords) {
        super();
        pieceColor = TetrisEnum.SPiece.pieceColor;

        Area s1 = new Area(
                new Rectangle2D.Double(
                        50 + startingCoords[0],
                        0 + startingCoords[1],
                        100,
                        50
                )
        );
        Area s2 = new Area(
                new Rectangle2D.Double(
                        0 + startingCoords[0],
                        50 + startingCoords[1],
                        100,
                        50
                )
        );

        s1.add(s2);

        super.piece = s1;
    }
}

class ZPiece extends TetrisPiece {
    boolean[][] pieceArr = new boolean[][]{{true, true, false}, {false, true, true}};

    ZPiece(double[] startingCoords) {
        super();
        pieceColor = TetrisEnum.ZPiece.pieceColor;

        Area z1 = new Area(
                new Rectangle2D.Double(
                        0 + startingCoords[0],
                        0 + startingCoords[1],
                        100, 50
                )
        );
        Area z2 = new Area(
                new Rectangle2D.Double(
                        50 + startingCoords[0],
                        50 + startingCoords[1],
                        100,
                        50
                )
        );

        z1.add(z2);

        super.piece = z1;
    }
}

class LPiece extends TetrisPiece {
    boolean[][] pieceArr = new boolean[][]{{true, false}, {true, false}, {true, true}};

    LPiece(double[] startingCoords) {
        super();
        pieceColor = TetrisEnum.LPiece.pieceColor;

        Area l1 = new Area(
                new Rectangle2D.Double(
                    0 + startingCoords[0],
                    0 + startingCoords[1],
                    50,
                    150
                )
        );
        Area l2 = new Area(
                new Rectangle2D.Double(
                50 + startingCoords[0],
                    100 + startingCoords[1],
                    50,
                    50
                )
        );
        l1.add(l2);

        super.piece = l1;
    }
}

class JPiece extends TetrisPiece {
    boolean [][] pieceArr = new boolean[][] {{false, true}, {false, true}, {true, true}};

    JPiece(double[] startingCoords) {
        super();
        pieceColor = TetrisEnum.JPiece.pieceColor;

        Area j1 = new Area(
                new Rectangle2D.Double(
                    50 + startingCoords[0],
                    0 + startingCoords[1],
                    50,
                    150
                )
        );
        Area j2 = new Area(
                new Rectangle2D.Double(
                    0 + startingCoords[0],
                    100 + startingCoords[1],
                    50,
                    50
                )
        );

        j1.add(j2);

        super.piece = j1;
    }
}

