package com.tetrisrevision;

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
    private static ArrayList<ArrayList<Point>> sinkingPieces;
    private static String lastCommand;

    static void setStaticVariables(
            TetrisPiece falling,
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

            boolean canSink = ChangePiece.Position.trySoftDropSinkingPiece(piece);

            if (!canSink) {
                ModifyPlayField.AddAndRemove.addSinkingPiece(piece);
                int searchFrom = ModifyPlayField.RowDeleter.apply(piece);

                sinkingPieces.remove(piece);

                if (searchFrom > 0)
                    FindSinkingPieces.resetVariablesAndRunSearch(searchFrom);

                i--;

                gui.drawBoardIncludingPiece();
            }

            ChangePiece.Position.tryRaiseSinkingPiece(piece);
        }
    }

    private static boolean softDropFallingPiece() {
        boolean canDrop = ChangePiece.Position.tryTranslateFallingPiece(0, 1);

        if (
                !canDrop &&
                        falling.isAddToBoard() &&
                        (lastCommand.equals("j") || (lastCommand.equals("J")))
        ) {
            ModifyPlayField.AddAndRemove.addFallingPiece();
            int searchFrom = ModifyPlayField.RowDeleter.apply(falling.getPieceLocation());

            if (searchFrom > 0)
                FindSinkingPieces.resetVariablesAndRunSearch(searchFrom);

            ChangePiecesAndQueue.getNextPiece();

            if (!Test.Position.isInBoundsAndEmptyNoRowMin())
                return false;
        } if (!canDrop) {
            falling.setAddToBoard(true);
        } else {
            falling.setAddToBoard(false);
            falling.getCenter().translate(0, -1);
        }

        return true;
    }

    static boolean continueGame() {
        gui.drawBoardIncludingPiece();
        ModifyPlayField.AddAndRemove.removeFallingPiece();
        ModifyPlayField.AddAndRemove.removeSinkingPieces();
        softDropSinkingPieces();
        ChangePiece.Position.trySoftDropSinkingPieces();

        return softDropFallingPiece();
    }

    static void keyboardInput() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Command:");
        String command = keyboard.nextLine();

        lastCommand = command;

        switch (command) {
            case "h":
                ChangePiece.Position.tryTranslateFallingPiece(-1, 0);
                break;
            case "l":
                ChangePiece.Position.tryTranslateFallingPiece(1, 0);
                break;
            case "j":
                ChangePiece.Position.tryTranslateFallingPiece(0, 1);
                break;
            case "k":
                ChangePiece.Position.tryTranslateFallingPiece(0, -1);
                break;
            case "[":
                ChangePiece.Rotation.tryRotate(-1);
                break;
            case "]":
                ChangePiece.Rotation.tryRotate(1);
                break;
            case "J":
                ChangePiece.Position.hardDrop();

                break;
            default:
                InputTests.accept(command);

                break;
        }
    }
}
