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
    double[] startingCoords = {0, 0};

    TetrisEnum(Color pieceColor, String pieceName) {
        this.pieceColor = pieceColor;
        this.pieceName = pieceName;
    }
}

abstract class TetrisPiece {
    private double[][] rotationTransformOffsets = {{0, 0}, {0, 0}, {0, 0}, {0, 0}}; ;
//    private double rotation = 0;
    private int rotationFactor = 0;
    Area piece;
    private AffineTransform transformer;
    Color pieceColor;

    TetrisPiece(int rotationFactor) {
        this.rotationFactor = rotationFactor;
    }

    public void rotate(RotationDirection rd) {
        transformer.rotate(
                rd.equals(RotationDirection.CLOCKWISE) ? Math.PI / 2 : -Math.PI /2,
                rotationTransformOffsets[rotationFactor][0],
                rotationTransformOffsets[rotationFactor][1]
        );
        piece.transform(transformer);

        if (rd.equals(RotationDirection.CLOCKWISE)){
            rotationFactor = rotationFactor != 3 ? rotationFactor++ : 0;
        } else {
            rotationFactor = rotationFactor != 0 ? rotationFactor -- : 3;
        }
    }
}

class TPiece extends TetrisPiece {
    private double[][] rotationTransformOffsets = { {100, 50}, {75, 25}, {75, 25}, {50, 50}};

    TPiece(double[] startingCoords, int rotationFactor) {
        super(rotationFactor);
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
    OPiece(double[] startingCoords, int rotationFactor) {
        super(rotationFactor);
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
    private double[][] rotationTransformOffset = {{-50, 0}, {0, 0}, {-50, 0}, {0, 0}};

    IPiece(double[] startingCoords, int rotationFactor) {
        super(rotationFactor);
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

    SPiece(double[] startingCoords, int rotationFactor) {
        super(rotationFactor);
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
    ZPiece(double[] startingCoords, int rotationFactor) {
        super(rotationFactor);
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
    LPiece(double[] startingCoords, int rotationFactor) {
        super(rotationFactor);
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
    JPiece(double[] startingCoords, int rotationFactor) {
        super(rotationFactor);
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

