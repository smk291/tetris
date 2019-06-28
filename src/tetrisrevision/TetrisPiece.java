package tetrisrevision;

import tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;

public class TetrisPiece {
    private static PlayField p;
    private int[][][] offsets;
    private int prevOrientation;
    private int orientation;
    private int orientationMax;
    private Point2D center;
    private HashMap<Integer, HashMap<Integer, Integer[][]>> kickData;

    public TetrisPiece(Tetromino tetromino) {
        resetOrientationAndLocation();
        setFromTetromino(tetromino);
    }

    public static void setStaticVariables(PlayField p) {
        TetrisPiece.p = p;
    }

    public void reset(Tetromino t) {
        resetOrientationAndLocation();

//        System.out.println("TetrisPiece reset Tetromino is null " + t);

        setFromTetromino(t);
    }

    public void resetOrientationAndLocation() {
        this.center = new Point(4, 0);
        this.orientation = 0;
    }

    public void setFromTetromino(Tetromino t) {
//        System.out.println("TetrisPiece setFromTetromino is null " + t);
        this.orientationMax = t.getOffsets().length;
        this.offsets = t.getOffsets();
        this.kickData = t.getKickData();
    }

//    public void setCenter(Point2D center) {
//        this.center = center;
//    }

//    public Point2D getCenter() {
//        return center;
//    }

    public void translateCenter(int x, int y) {
        this.center.setLocation(
                this.center.getX() + x,
                this.center.getY() + y
        );
    }

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int o) {
        this.orientation = o;
    }

    public void incrOrientation(int i) {
        this.orientation += i;
    }

    public int getPrevOrientation() {
        return prevOrientation;
    }

    public void setPrevOrientation(int prevOrientation) {
        this.prevOrientation = prevOrientation;
    }

    public Point[] getPieceLocation() {
        System.out.print("TetrisPiece getPieceLocation ");

        Arrays.stream(offsets[orientation]).forEach(p ->
            System.out.print(" { " + (p[0] + (int) this.center.getX()) + ", " + ((int) this.center.getY() + p[1]) + " }, ")
        );

        System.out.println();

        return Arrays.stream(offsets[orientation]).map(p ->
            new Point((int) this.center.getX() + p[0], (int) this.center.getY() + p[1])
        ).toArray(Point[]::new);
    }

    public int getOrientationMax() {
        return orientationMax;
    }

//    public void setOrientationMax(int orientationMax) {
//        this.orientationMax = orientationMax;
//    }

    public HashMap<Integer, HashMap<Integer, Integer[][]>> getKickData() {
        return kickData;
    }

    public void setCenter(Point pt) {
        this.center = pt;
    }

//    public void setKickData(HashMap<Integer, HashMap<Integer, Integer[][]>> kickData) {
//        this.kickData = kickData;
//    }
}
