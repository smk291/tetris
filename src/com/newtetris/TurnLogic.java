package com.newtetris;

import com.newtetris.playfield.Coords;
import com.newtetris.playfield.PlayField;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.rotate.RotateLeft;
import com.newtetris.tetrispiece.rotate.RotateRight;
import com.newtetris.tetrispiece.shift.ShiftDown;
import com.newtetris.tetrispiece.shift.ShiftLeft;
import com.newtetris.tetrispiece.shift.ShiftRight;
import com.newtetris.tetrispiece.shift.ShiftUp;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;
import java.util.stream.Stream;

class TurnLogic {
    private static int[] delayPerLevel = {500, 450, 400, 350, 300, 250, 200, 150, 100, 50};
    private Game g;
//    private Timer t;
//    private int currentLevel;
//    private Timer timer;
    private boolean gameOver = false;
    private GUI gui;

    TurnLogic(Game g, GUI gui) {
        this.g = g;
//        this.currentLevel = 0;
        this.gui = gui;
    }

    boolean gameInProgress() {
        return !gameOver;
    }

    void turn1() {
        if (gameOver) {
            return;
        }

        boolean canDrop = softDrop(g.getFallingPiece());

        if (!canDrop) {
            g.insertPieceIntoBoard();

            gui.draw(g.getPlayField());

            Coords[] playFieldCoords = g.getFallingPiece().playFieldCoords();

            PlayField field = g.getPlayField();

            Integer[] fullRows = Arrays.stream(playFieldCoords)
                    .map(Coords::getY)
                    .filter(field::rowIsFull)
//                    .sorted(Comparator.comparingInt(a -> a))
                    .sorted((a, b) -> a - b)
                    .toArray(Integer[]::new);

            if (fullRows.length > 0) {
                for (int row = fullRows[0] - 1, shift = 1; !field.rowIsEmpty(row); row--) {
                    if (field.rowIsFull(row + shift)) {
                        shift++;
                    }

                    field.setRow(row + shift, field.getCellRow(row));
                }
            }

            g.setNextPieceFalling();
            g.resetNextPiece();

            gui.draw(g.getPlayField());

            if (g.invalidPosition()) {
                gameOver = true;
            }
        } else {
            shiftUp(g.getFallingPiece());
        }
    }

    void keyboardInput() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Command:");
        String command = keyboard.nextLine();

        switch (command) {
            case "h":
                shiftLeft();
                break;
            case "l":
                shiftRight();
                break;
            case "j":
                softDrop(g.getFallingPiece());
                break;
            case "k":
                shiftUp(g.getFallingPiece());
                break;
            case "[":
                rotateLeft();
                break;
            case "]":
                rotateRight();
                break;
            case "J":
                hardDrop(g.getFallingPiece());
                break;
            default: break;
        }
    }

    private void shiftLeft() {
        g.manipulate(new ShiftLeft(), new ShiftRight(), g.getFallingPiece());
    }

    private void shiftRight() {
        g.manipulate(new ShiftRight(), new ShiftLeft(), g.getFallingPiece());
    }

    private boolean softDrop(TetrisPiece t) {
        return g.manipulate(new ShiftDown(), new ShiftUp(), t);
    }

    private boolean shiftUp(TetrisPiece t) {
        return g.manipulate(new ShiftUp(), new ShiftDown(), t);
    }

    private void rotateLeft() {
        g.manipulate(new RotateLeft(), new RotateRight(), g.getFallingPiece());
    }

    private void rotateRight() {
        g.manipulate(new RotateRight(), new RotateLeft(), g.getFallingPiece());
    }

    private void hardDrop(TetrisPiece t) {
        while (Arrays.stream(t.playFieldCoords())
                .allMatch(i ->
                        i.getY() + 1 < Game.height &&
                                g.getPlayField().getCell(i.sum(0, 1)).isEmpty()
                )
        ) {
            t.setCenter(t.getCenter().sum(0, 1));
        }
    }

    void printToConsole() {
        gui.drawBoardIncludingPiece(g);

        gui.removePieceFromBoard(g);
    }
}
