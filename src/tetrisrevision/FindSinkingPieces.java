package tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.IntStream;

abstract public class FindSinkingPieces {
    private static PlayField p;
    private static ArrayList<ArrayList<Point>> sinkingPieces;

    public FindSinkingPieces() {}

    public static void setStaticVariables(
            PlayField p,
            ArrayList<ArrayList<Point>> sinkingPieces
    ) {
        FindSinkingPieces.p = p;
        FindSinkingPieces.sinkingPieces = sinkingPieces;
    }

    public static boolean cellAlreadySearched(Cell c) {
        for (ArrayList<Point> ps : sinkingPieces) {
            for (Point p : ps) {
                if (p.equals(c.getPoint()))
                    return true;
            }
        }

        return false;
    }

    public static void findFloatingPieces(int startingY, int incr) {
        SinkingPieceBuilder.setContinueRecursing(true);

        if (startingY == -1 || !Test.Position.pointIsValid(new Point(0, startingY)))
            return;

        Cell[][] cells = p.getCells();

        // System.out.println("looking at cells in row " + startingY + ".");
        // System.out.println("Cell " + i + ", " + startingY + ".");
        int bound = PlayField.getWidth();

        for (int i = 0; i < bound; i++) {
            SinkingPieceBuilder.setPiece(new ArrayList<>());
            Cell currentCell = cells[startingY][i];
            int adjacentRow = startingY + incr; // look at +1 and -1
            Cell cellAbove = cells[adjacentRow][i];

            if (!cellAlreadySearched(currentCell) && currentCell.isEmpty() && cellAbove.isFull()) {
                // System.out.println("Cell " + i + " is empty and cell above is full.");
                SinkingPieceBuilder.store(new Point(i, adjacentRow));

                // System.out.print("Floating piece: ");

                ArrayList<Point> newPiece = new ArrayList<>();

                for (Point c : SinkingPieceBuilder.getPiece()) {
                    newPiece.add(new Point((int) c.getX(), (int) c.getY()));
                    // System.out.print("{ " + c[0] + ", " +  c[1] + " }, ");
                }

                // System.out.println();
                // System.out.println("Size: " + f.piece.size() + ".");

                if (SinkingPieceBuilder.getPiece().size() > 0) {
                    sinkingPieces.add(newPiece);
                }
            }
        }
    }

    abstract public static class SinkingPieceBuilder {
        private static boolean continueRecursing = true;
        private static ArrayList<Point> piece = new ArrayList<>();
        private static PlayField p;

        SinkingPieceBuilder() { }

        public static ArrayList<Point> getPiece() {
            return piece;
        }

        public static void setPiece(ArrayList<Point> piece) {
            SinkingPieceBuilder.piece = piece;
        }

//        public static boolean isContinueRecursing() {
//            return continueRecursing;
//        }

        public static void setContinueRecursing(boolean continueRecursing) {
            SinkingPieceBuilder.continueRecursing = continueRecursing;
        }

        public static void setPlayField(PlayField p) {
            SinkingPieceBuilder.p = p;
        }

        public static void store(Point pt) {

            if (Test.Position.pointIsValid(pt))
                return;

            Cell[][] cells = p.getCells();

            Cell currentCell = cells[(int) pt.getY()][(int) pt.getX()];
            // System.out.println("Current cell: { " + currentCell.getX() + ", " + currentCell.getY() + " }.");

            if (currentCell.isFull()) {
                // System.out.println("Current cell: is full.");

                if (pt.getY() == 23) {
                    // System.out.println("Full cell: shape is connected. Ending search and clearing piece");
                    continueRecursing = false;

                    piece = new ArrayList<>();
                } else if (
                        continueRecursing &&
                                !Test.Position.pointIsValid(pt) &&
                                piece.stream().noneMatch(i -> i.equals(pt))
                ) {
                    // System.out.println("continue recursing. position is valid, piece doesn't contain this cell");
                    piece.add(pt);

                    store(new Point((int) pt.getX(), (int) pt.getY() + 1));
                    store(new Point((int) pt.getX(), (int) pt.getY() - 1));
                    store(new Point((int) pt.getX() + 1, (int) pt.getY()));
                    store(new Point((int) pt.getX() - 1, (int) pt.getY()));
                }
            }
        }
    }
}

