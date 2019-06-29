package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;
import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RunTetris {
    private static int[] delayPerLevel = {500, 450, 400, 350, 300, 250, 200, 150, 100, 50};
    //    private Timer t;
//    private int currentLevel;
//    private Timer timer;
    private static boolean gameOver = false;
    private static GUI gui;
    private static PlayField p;
    private static TetrisPiece falling;
    private static Tetromino[] q;
    private static ArrayList<ArrayList<Point>> sinkingPieces;

    RunTetris() { }

    public static void setStaticVariables(TetrisPiece falling, Tetromino[] q, PlayField p, ArrayList<ArrayList<Point>> sinkingPieces, GUI gui) {
        RunTetris.falling = falling;
        RunTetris.q = q;
        RunTetris.p = p;
        RunTetris.sinkingPieces = sinkingPieces;
        RunTetris.gui = gui;
    }

    public static boolean continueGame() {
        ModifyPlayField.AddAndRemove.removeFallingPiece();
        ModifyPlayField.AddAndRemove.removeSinkingPieces();

        for (int i = 0, sinkingPiecesSize = sinkingPieces.size(); sinkingPieces.size() > 0 && i < sinkingPiecesSize; i++) {
            ArrayList<Point> piece = sinkingPieces.get(i);

            boolean canSink = Change.Position.softDropSinkingPiece(piece);

            if (!canSink) {
                ModifyPlayField.AddAndRemove.addSinkingPiece(piece);
                ModifyPlayField.RowDeleter.apply(piece.toArray(new Point[0]));
                sinkingPieces.remove(piece);
                i--;
                sinkingPiecesSize--;
            }
        }

        boolean canDrop = Change.Position.translateFallingPiece(0, 1);

        if (!canDrop) {
            ModifyPlayField.AddAndRemove.addFallingPiece();
            int searchFrom = ModifyPlayField.RowDeleter.apply(falling.getPieceLocation());

            if (searchFrom > 0) {
                FindSinkingPieces.findFloatingPieces(searchFrom);
            }

            FallingPieceAndQueue.swap(0);

            if (!Test.Position.fallingPositionIsValidNoMin()) {
                return false;
            }

            gui.drawBoardIncludingPiece();
        } else {
            falling.translateCenter(0, -1);
        }

        return true;
    }

    public static void keyboardInput() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Command:");
        String command = keyboard.nextLine();

        switch (command) {
            case "h":
                Change.Position.translateFallingPiece(-1, 0);
                break;
            case "l":
                Change.Position.translateFallingPiece(1, 0);
                break;
            case "j":
                Change.Position.translateFallingPiece(0, 1);
                break;
            case "k":
                Change.Position.translateFallingPiece(0, -1);
                break;
            case "[":
                Change.Orientation.rotate(-1);
                break;
            case "]":
                Change.Orientation.rotate(1);
                break;
            case "J":
                Change.Position.hardDrop();
                break;
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
                falling.setFromTetromino(TetrominoEnum.values()[Integer.parseInt(command) - 1].get());
                falling.setCenter(new Point(4, 3));

                break;
            case "itest":
                PlayField.createEmpty();
                falling.setFromTetromino(TetrominoEnum.I.get());

                for (int y = PlayField.getHeight() - 4; y < PlayField.getHeight(); y++) {
                    for (int x = 0, l = PlayField.getWidth(); x < l; x++) {
                        if (x != 4) {
                            p.fillCell(new Point(x, y));
                        }
                    }
                }

                break;
            case "floattest":
                PlayField.createEmpty();

                for (int y = 0, boardHeight = PlayField.getHeight(); y < boardHeight; y++) {
                    for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
                        if (x == 0 || x == 9) {
                            p.fillCell(new Point(x, y));
                        }

                        if (y == 20) {
                            if (x > 4 && x < 8) {
                                p.fillCell(new Point(x, y));
                            }
                        } else if (y == 21) {
                            if (x == 6) {
                                p.fillCell(new Point(x, y));
                            }
                        } else if (y == 22) {
                            if (x != 2) {
                                p.fillCell(new Point(x, y));
                            }
                        }
                    }
                }

                break;
            case "q":
                PlayField.createEmpty();

                for (int y = 0, boardHeight = PlayField.getHeight(); y < boardHeight; y++) {
                    for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {

                        if (y == 15) {
                            if (x != 2) {
                                p.fillCell(new Point(x, y));
                            }
                        }

                        if (y > 15) {
                            if (x == 2) {
                                p.fillCell(new Point(x, y));
                            }
                        }

                        if (y == 16) {
                            if (x == 8 || x == 9) {
                                p.fillCell(new Point(x, y));
                            }
                        }

                        if (y == 17) {
                            if (x == 8) {
                                p.fillCell(new Point(x, y));
                            }
                        }

                        if (y == 14 || y == 13) {
                            if (x == 0 || x == 4 || x == 6) {
                                p.fillCell(new Point(x, y));
                            }
                        } else if (y == 21) {
                            if (x == 4) {
                                p.fillCell(new Point(x, y));
                            }
                        } else if (y == 22) {
                            if (x == 3 || x == 4)
                                p.fillCell(new Point(x, y));
                        } else if (y == 23) {
                            if (x == 0 || x == 4 || x == 6)
                                p.fillCell(new Point(x, y));
                        }
                    }
                }

                falling.setFromTetromino(TetrominoEnum.values()[0].get());
                falling.setCenter(new Point(1, 9));

                break;
            case "bounds":
                PlayField.createEmpty();


                for (int y = 0, boardHeight = PlayField.getHeight(); y < boardHeight; y++) {
                    for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {

                        if (y == 18) {
                            if (x != 0 && !(x > 2 && x < 8)) {
                                p.fillCell(new Point(x, y));
                            }
                        }

                        if (y == 19 || y == 20 || y == 21) {
                            if (x != 5) {
                                p.fillCell(new Point(x, y));
                            }

                        }

                        if (y == 22 || y == 23) {
                            if (x != 0 && x != 5) {
                                p.fillCell(new Point(x, y));
                            }
                        }
                    }
                }

                falling.setCenter(new Point(4, 9));

                break;
            case "w":
                PlayField.createEmpty();

                for (int i = 0; i < PlayField.getWidth(); i++) {
                    if (i != 5) {
                        p.fillCell(new Point(i, 23));
                    }
                }

                break;
            case "e":
                PlayField.createEmpty();

                for (int i = 0; i < PlayField.getWidth(); i++) {
                    if (i == 4) {
                        PlayField.fillCell(new Point(i, 21));
                    }

                    if (i > 2) {
                        PlayField.fillCell(new Point(i, 22));
                    }

                    if (i > 0 && i < 4 || i > 4) {
                        PlayField.fillCell(new Point(i, 23));
                    }
                }

                falling.setFromTetromino(TetrominoEnum.J.get());
                falling.setOrientation(0);
                falling.setCenter(new Point(1, 19));

                break;
            default:
                break;
        }

        gui.drawBoardIncludingPiece();
    }
}
