package com.newtetris;

import com.newtetris.playfield.Cell;
import com.newtetris.playfield.Coords;
import com.newtetris.playfield.findfloatingpieces.FindFloatingPieces;
import com.newtetris.tetrispiece.TetrisPiece;
import com.newtetris.tetrispiece.pieces.IPiece;
import com.newtetris.tetrispiece.pieces.TetrisPiecesEnum;
import com.newtetris.tetrispiece.rotate.RotateLeft;
import com.newtetris.tetrispiece.rotate.RotateRight;
import com.newtetris.tetrispiece.shift.ShiftDown;
import com.newtetris.tetrispiece.shift.ShiftLeft;
import com.newtetris.tetrispiece.shift.ShiftRight;
import com.newtetris.tetrispiece.shift.ShiftUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class TurnLogic {
    public static int width;
    public static int height;
    private static int[] delayPerLevel = {500, 450, 400, 350, 300, 250, 200, 150, 100, 50};
    private Game g;
//    private Timer t;
//    private int currentLevel;
//    private Timer timer;
    private boolean gameOver = false;
    private GUI gui;

    TurnLogic(Game g, GUI gui, int width, int height) {
        this.g = g;
//        this.currentLevel = 0;
        this.gui = gui;
        TurnLogic.width = width;
        TurnLogic.height = height;
    }

    void playGame() {
        while (continueGame()) {
            keyboardInput();
        }
    }

    boolean continueGame() {
        boolean canDrop = softDrop(g.getFallingPiece());

        for (ArrayList<Coords> alc : g.getSinkingPieces()) {
            System.out.print("Sinking piece: ");

            for (Coords c : alc) {
                c.print();
                System.out.print(", ");
            }
            System.out.println();
        }

        System.out.println();
        g.dropSinkingPieces(g.getSinkingPieces());

        for (ArrayList<Coords> piece : g.getSinkingPieces()) {
            g.putPieceOnBoard(piece);
        }
        if (!canDrop) {
            // Wait until timer is up
            g.insertPieceIntoBoard();
            g.getPlayField().deleteFullRows(g.getFallingPiece().playFieldCoords(), this.g);

            for (ArrayList<Coords> alc : g.getSinkingPieces()) {
                System.out.print("Sinking piece: ");

                for (Coords c : alc) {
                    c.print();
                    System.out.print(", ");
                }
                System.out.println();
            }

            System.out.println();

            g.setNextPieceFalling();

            if (g.invalidPosition()) {
                return false;
            }

            g.resetNextPiece();
            gui.draw(g.getPlayField());
        } else {
            shiftUp(g.getFallingPiece());
        }

        return true;
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
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
                TetrisPiece p = new TetrisPiece();
                p.setTetromino(TetrisPiecesEnum.values()[Integer.parseInt(command) - 1].get());
                g.setFallingPiece(p);
                g.getFallingPiece().setCenter(4, 3);

                break;
            case "itest":
                g.getPlayField().createEmptyField();

                for (int i = height - 4; i < height; i++) {
                    for (int j = 0, l = g.getPlayField().getCellRow(i).length; j < l; j++) {
                        if (j != 4) {
                            g.getPlayField().fillCell(new Coords(j, i));
                        }
                    }
                }

                TetrisPiece ipiece = new TetrisPiece();
                ipiece.reset(new IPiece());
                g.setFallingPiece(ipiece);

                break;
            case "floattest":
                g.getPlayField().createEmptyField();

                Cell[][] board = g.getPlayField().getAllCells();

                for (int i = 0, boardHeight = board.length; i < boardHeight; i++) {
                    for (int j = 0, boardWidth = board[0].length; j < boardWidth; j++) {
                        if (j == 0 || j == 9) {
                            board[i][j].setFull();
                        }

                        if (i == 20) {
                            if (j > 4 && j < 8) {
                                board[i][j].setFull();
                            }
                        } else if (i == 21) {
                            if (j == 6) {
                                board[i][j].setFull();
                            }
                        } else if (i == 22) {
                            if (j != 2) {
                                board[i][j].setFull();
                            }
                        }
                    }
                }

                break;
            case "betterfloattest":
                g.getPlayField().createEmptyField();

                Cell[][] allCells = g.getPlayField().getAllCells();

                for (int i = 0, boardHeight = allCells.length; i < boardHeight; i++) {
                    for (int j = 0, boardWidth = allCells[0].length; j < boardWidth; j++) {

                        if (i == 15) {
                            if (j != 2) {
                                allCells[i][j].setFull();
                            }
                        }

                        if (i > 15) {
                            if (j == 2){
                                allCells[i][j].setFull();
                            }
                        }

                        if (i == 14 || i == 13) {
                            if (j == 4 || j == 6 || j == 8) {
                                allCells[i][j].setFull();
                            }
                        } else if (i == 21) {
                            if (j == 4) {
                                allCells[i][j].setFull();
                            }
                        } else if (i == 22) {
                            if (j == 4 || j == 6)
                                allCells[i][j].setFull();
                        } else if (i == 23) {
                            if (j == 4 || j == 6 || j == 8)
                                allCells[i][j].setFull();
                        }
                    }
                }

                g.getFallingPiece().setCenter(4, 9);

            default: break;
        }

        gui.drawBoardIncludingPiece(g);
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
        g.manipulateRotate(new RotateLeft(), new RotateRight(), g.getFallingPiece());
    }

    private void rotateRight() {
        g.manipulateRotate(new RotateRight(), new RotateLeft(), g.getFallingPiece());
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
}
