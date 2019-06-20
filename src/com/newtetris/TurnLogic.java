package com.newtetris;

import com.newtetris.playfield.Coords;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.pieces.IPiece;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;

public class TurnLogic {
    private Game g;
    private Timer t;
    private static int[] delayPerLevel = { 500, 450, 400, 350, 300, 250, 200, 150, 100, 50 };
    private int currentLevel;
    private Timer timer;
    private boolean gameOver = false;
    private GUI gui;

    public TurnLogic(Game g, GUI gui) {
        this.g = g;
        this.currentLevel = 0;
        this.gui = gui;
    }

    TurnLogic (Game g, int startingLevel) {
        this.g = g;
        this.currentLevel = startingLevel;
    }

    public boolean gameInProgress() {
        return !gameOver;
    }

    public void turn1() {
        if (gameOver) {
            return;
        }

        boolean canDrop = g.softDrop();

        if (!canDrop) {
            g.insertPieceIntoBoard();

            gui.draw(g.getPlayField());

            Coords[] playFieldCoords = g.getFallingPiece().playfieldCoords();

            Arrays.stream(playFieldCoords).map(Coords::getY)
                    .sorted((a, b) -> a - b)
                    .forEach(row -> {
                            if (g.getPlayField().rowIsFull(row)) {
                                    g.getPlayField().deleteRows(1, row);
                            }
                    });

            g.setNextPieceFalling();
            g.resetNextPiece();

            gui.draw(g.getPlayField());

            if (g.invalidPosition()) {
                gameOver = true;
            }
        } else {
            g.shiftUp();
        }
    }

    public void keyboardInput() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Command:");
        String command = keyboard.nextLine();

        switch(command) {
            case "h":
                g.shiftLeft();
                break;
            case "l":
                g.shiftRight();
                break;
            case "j":
                g.softDrop();
                break;
            case "[":
                g.rotateLeft();
                break;
            case "]":
                g.rotateRight();
                break;
            case "J":
                g.hardDrop();
                break;
            case "k":
                g.shiftUp();
                break;
            case "itest":
                g.getPlayField().resetCells();

                for (int i = 20; i < 24; i++) {
                    for (int j = 0; j < g.getPlayField().getCellRow(i).length; j++) {
                        if (j != 4) {
                            g.getPlayField().setCellFull(new Coords(j, i));
                        }
                    }
                }

                TetrisPiece ipiece = new TetrisPiece();
                ipiece.reset(new IPiece());
                g.setFallingPiece(ipiece);

                break;
            default:
                break;
        }
    }

    public void printToConsole() {
        gui.drawBoardIncludingPiece(g);

        gui.removePieceFromBoard(
                g.getFallingPiece(),
                g.getFallingPiece().getOrientation(),
                g.getPlayField()
        );
    }
}
