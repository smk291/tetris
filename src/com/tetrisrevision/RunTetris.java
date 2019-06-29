package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RunTetris {
    // private static int[] delayPerLevel = {500, 450, 400, 350, 300, 250, 200, 150, 100, 50};
    // private Timer t;
    // private int currentLevel;
    // private Timer timer;
    private static GUI gui;
    private static TetrisPiece falling;
    private static PlayField p;
    private static Tetromino[] q;
    private static ArrayList<ArrayList<Point>> sinkingPieces;
    private static String lastCommand;

    RunTetris() { }

    public static void setStaticVariables(
            TetrisPiece falling,
            PlayField p,
            ArrayList<ArrayList<Point>> sinkingPieces,
            GUI gui
    ) {
        RunTetris.falling = falling;
        RunTetris.sinkingPieces = sinkingPieces;
        RunTetris.gui = gui;
    }

    private static void softDropSinkingPieces() {
        for (
                int i = 0;
                sinkingPieces.size() > 0 && i < sinkingPieces.size();
                i++
        ) {
            ArrayList<Point> piece = sinkingPieces.get(i);

            boolean canSink = Change.Position.softDropSinkingPiece(piece);

            if (!canSink) {
                ModifyPlayField.AddAndRemove.addSinkingPiece(piece);
                int searchFrom = ModifyPlayField.RowDeleter.apply(piece);

                sinkingPieces.remove(piece);

                if (searchFrom > 0) {
                    FindSinkingPieces.findFloatingPieces(searchFrom);
                    softDropSinkingPieces();
                }

                i--;

                gui.drawBoardIncludingPiece();
            }

            Change.Position.raiseSinkingPiece(piece);
        }
    }

    private static boolean softDropFallingPiece() {
        boolean canDrop = Change.Position.translateFallingPiece(0, 1);

        if (
                !canDrop &&
                        falling.isAddToBoard() &&
                        (lastCommand.equals("j") || (lastCommand.equals("J")))
        ) {
            ModifyPlayField.AddAndRemove.addFallingPiece();

            int searchFrom = ModifyPlayField.RowDeleter.apply(falling.getPieceLocation());

            if (searchFrom > 0) {
                FindSinkingPieces.findFloatingPieces(searchFrom);
            }

            FallingPieceAndQueue.swap(0);

            if (!Test.Position.fallingPositionIsValidNoMin()) {
                return false;
            }
        } if (!canDrop) {
            falling.setAddToBoard(true);
        } else {
            falling.setAddToBoard(false);
            falling.translateCenter(0, -1);
        }

        return true;
    }

    public static boolean continueGame() {
        gui.drawBoardIncludingPiece();

        ModifyPlayField.AddAndRemove.removeFallingPiece();
        ModifyPlayField.AddAndRemove.removeSinkingPieces();

        softDropSinkingPieces();
        Change.Position.softDropSinkingPieces();

        return softDropFallingPiece();
    }

    public static void keyboardInput() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Command:");
        String command = keyboard.nextLine();

        lastCommand = command;

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
            default:
                InputTests.accept(command);

                break;
        }
    }
}
