package tetrisrevision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FindSinkingPieces {
    private static PlayField p;
    private static PieceLocationValidator tester;
    private static int width;
    private static ArrayList<ArrayList<Integer[]>> sinkingPieces;
    private static ArrayList<Cell> cellsAlreadySearched = new ArrayList<>();

    public boolean cellAlreadySearched(Integer[] t) {
        for (ArrayList<Integer[]> c : sinkingPieces) {
            for (Integer[] i : c) {
                if (Arrays.equals(i, t))
                    return true;
            }
        }

        return false;
    }


    public void setStaticVariables(
            int width,
            PlayField p,
            PieceLocationValidator tester,
            ArrayList<ArrayList<Integer[]>> sinkingPieces
    ) {
        FindSinkingPieces.width = width;
        FindSinkingPieces.p = p;
        FindSinkingPieces.tester = tester;
        FindSinkingPieces.sinkingPieces = sinkingPieces;
    }

    public boolean cellAlreadySearched(Cell c) {
        return cellsAlreadySearched.contains(c);
    }

    public void findFloatingPieces(int startingY, int incr) {
        SinkingPieceBuilder.continueRecursing = true;
        SinkingPieceBuilder f = new SinkingPieceBuilder();

        if (!tester.pointIsValid(new Point(0, startingY)))
            return;

        Cell[][] cells = p.getCells();

        for (int i = 0; i < width; i++) {
            f.piece = new ArrayList<Integer[]>();
            // System.out.println("looking at cells in row " + startingY + ".");
            // System.out.println("Cell " + i + ", " + startingY + ".");

            Cell currentCell = cells[startingY][i];
            int adjacentRow = startingY - incr;
            Cell cellAbove = cells[adjacentRow][i];

            if (!cellAlreadySearched(currentCell) && currentCell.isEmpty() && cellAbove.isFull()) {
                // System.out.println("Cell " + i + " is empty and cell above is full.");

                f.store(adjacentRow, i);

                // System.out.print("Floating piece: ");

                ArrayList<Integer[]> newPiece = new ArrayList<Integer[]>();

                for (Integer[] c : f.piece) {
                    newPiece.add(new Integer[]{c[0], c[1]});
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
    public ArrayList<Integer[]> piece = new ArrayList<>();
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
                            piece.stream().noneMatch(i -> i[0] == x && i[1] == y)
            ) {
                // System.out.println("continue recursing. position is valid, piece doesn't contain this cell");
                this.piece.add(new Integer[]{x, y});

                this.store(y + 1, x);
                this.store(y - 1, x);
                this.store(y, x + 1);
                this.store(y, x - 1);
            }
        }
    }
}
