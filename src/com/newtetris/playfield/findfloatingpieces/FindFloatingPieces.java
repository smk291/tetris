package com.newtetris.playfield.findfloatingpieces;

import com.newtetris.Game;
import com.newtetris.playfield.Cell;
import com.newtetris.playfield.Coords;
import com.newtetris.playfield.PlayField;

import java.util.ArrayList;
import java.util.Arrays;

public class FindFloatingPieces {
    public void findFloatingPieces(int startingY, Game g) {
        PlayField p = g.getPlayField();

        FloatingPieceFinder.continueRecursing = true;
        FloatingPieceFinder f = new FloatingPieceFinder();

        if (startingY > 23) {
            return;
        }

        System.out.println("Running function");

        for (int i = 0; i < p.getCellRow(startingY).length; i++) {
            f.piece = new ArrayList<Integer[]>();
            System.out.println("looking at cells in row " + startingY + ".");
            System.out.println("Cell " + i + ", " + startingY + ".");
            Cell currentCell = p.getCell(i, startingY);
            int yAbove = startingY - 1;
            Cell cellAbove = p.getCell(i, yAbove);

            if (!g.cellAlreadySearched(currentCell) && currentCell.isEmpty() && cellAbove.isFull()) {
                System.out.println("Cell " + i + " is empty and cell above is full.");

                f.store(startingY, yAbove, i, g);

                System.out.print("Floating piece: ");

                ArrayList<Coords> newPiece = new ArrayList<Coords>();

                for (Integer[] c : f.piece) {
                    newPiece.add(new Coords(c[0], c[1]));
                    System.out.print("{ " + c[0] + ", " +  c[1] + " }, ");
                }

                System.out.println();

                System.out.println("Size: " + f.piece.size() + ".");

                if (f.piece.size() > 0) {
                    g.addSinkingPiece(newPiece);
                }
            }
        }
    }
}

class FloatingPieceFinder {
    public static boolean continueRecursing = true;
    public ArrayList<Integer[]> piece = new ArrayList<>();
    public void store(int originalY, int y, int x, Game g) {
        PlayField p = g.getPlayField();
        Cell currentCell = p.getCell(x, y);
        Coords currentCoords = new Coords(x, y);

        System.out.println("Current cell: { " + currentCell.getX() + ", " + currentCell.getY() + " }.");

        if (currentCell.isFull()) {
            System.out.println("Current cell: is full.");

            if (y == originalY) {
                System.out.println("Full cell: shape is connected. Ending search and clearing piece");
                continueRecursing = false;

                piece = new ArrayList<>();
            } else if (continueRecursing && !g.invalidPosition(x, y) && piece.stream().noneMatch(i -> i[0] == x && i[1] == y)) {
                System.out.println("continue recursing. position is valid, piece doesn't contain this cell");

                this.piece.add(new Integer[]{x, y});

                this.store(originalY, y + 1, x, g);
                this.store(originalY, y - 1, x, g);
                this.store(originalY, y, x + 1, g);
                this.store(originalY, y, x - 1, g);
            }
        }
    }
}