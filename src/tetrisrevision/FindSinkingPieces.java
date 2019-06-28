package tetrisrevision;

import java.awt.*;
import java.util.ArrayList;

public class FindSinkingPieces {
    private static PlayField p;
    private static PieceLocationValidator locationValidator;
    private static int width;
    private static ArrayList<ArrayList<Point>> sinkingPieces;
    private static SinkingPieceBuilder f = new SinkingPieceBuilder();

    public FindSinkingPieces() {}

    public static void setStaticVariables(
            int width,
            PlayField p,
            PieceLocationValidator tester,
            ArrayList<ArrayList<Point>> sinkingPieces
    ) {
        FindSinkingPieces.width = width;
        FindSinkingPieces.p = p;
        FindSinkingPieces.locationValidator = tester;
        FindSinkingPieces.sinkingPieces = sinkingPieces;
    }

    public boolean cellAlreadySearched(Cell c) {
        for (ArrayList<Point> ps : sinkingPieces) {
            for (Point p : ps) {
                if (p.equals(c.getPoint()))
                    return true;
            }
        }

        return false;
    }



    public void findFloatingPieces(int startingY, int incr) {
        SinkingPieceBuilder.continueRecursing = true;

        if (!locationValidator.pointIsValid(new Point(0, startingY)))
            return;

        Cell[][] cells = p.getCells();

        for (int i = 0; i < width; i++) {
            f.piece = new ArrayList<Point>();
            // System.out.println("looking at cells in row " + startingY + ".");
            // System.out.println("Cell " + i + ", " + startingY + ".");

            Cell currentCell = cells[startingY][i];
            int adjacentRow = startingY - incr;
            Cell cellAbove = cells[adjacentRow][i];

            if (!cellAlreadySearched(currentCell) && currentCell.isEmpty() && cellAbove.isFull()) {
                // System.out.println("Cell " + i + " is empty and cell above is full.");
                f.store(adjacentRow, i);

                // System.out.print("Floating piece: ");

                ArrayList<Point> newPiece = new ArrayList<>();

                for (Point c : f.piece) {
                    newPiece.add(new Point((int) c.getX(), (int) c.getY()));
                    // System.out.print("{ " + c[0] + ", " +  c[1] + " }, ");
                }

                // System.out.println();
                // System.out.println("Size: " + f.piece.size() + ".");

                if (f.piece.size() > 0) {
                    sinkingPieces.add(newPiece);
                }
            }
        }
    }
}

class SinkingPieceBuilder {
    public static boolean continueRecursing = true;
    public ArrayList<Point> piece = new ArrayList<>();
    private static PieceLocationValidator tester;
    private static PlayField p;

    SinkingPieceBuilder() { }

    public static void setTester(PieceLocationValidator tester) {
        SinkingPieceBuilder.tester = tester;
    }

    public static void setPlayField(PlayField p) {
        SinkingPieceBuilder.p = p;
    }

    public void store(int y, int x) {

        if (tester.coordinatesAreInvalid(x, y))
            return;

        Cell[][] cells = p.getCells();

        Cell currentCell = cells[y][x];
        // System.out.println("Current cell: { " + currentCell.getX() + ", " + currentCell.getY() + " }.");

        if (currentCell.isFull()) {
        // System.out.println("Current cell: is full.");

            if (y == 0) {
                // System.out.println("Full cell: shape is connected. Ending search and clearing piece");
                continueRecursing = false;

                piece = new ArrayList<>();
            } else if (
                    continueRecursing &&
                            !tester.coordinatesAreValid(x, y) &&
                            piece.stream().noneMatch(i -> (int) i.getX() == x && (int) i.getY() == y)
            ) {
                // System.out.println("continue recursing. position is valid, piece doesn't contain this cell");
                this.piece.add(new Point(x, y));

                this.store(y + 1, x);
                this.store(y - 1, x);
                this.store(y, x + 1);
                this.store(y, x - 1);
            }
        }
    }
}
