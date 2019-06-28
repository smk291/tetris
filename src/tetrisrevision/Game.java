package tetrisrevision;

import tetrisrevision.tetrominos.TetrisPiecesEnum;
import tetrisrevision.tetrominos.Tetromino;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private static int width = 10;
    private static int height = 24;

    private static ArrayList<ArrayList<Point>> sinkingPieces = new ArrayList<>();
    private Tetromino[] q = new Tetromino[7];
    private PlayField p = new PlayField();
    private TetrisPiece falling = new TetrisPiece(TetrisPiecesEnum.getPiece());

    private FindSinkingPieces findSinkingPieces = new FindSinkingPieces();
    private MakeNextPieceFallingPiece resetFallingPiece = new MakeNextPieceFallingPiece();
    private ModifyPlayField playfieldModifier = new ModifyPlayField();
    private PieceKicker kicker = new PieceKicker();
    private PieceLocationValidator locationValidator = new PieceLocationValidator();
    private PieceRotator rotator = new PieceRotator();
    private PieceShifter shifter = new PieceShifter();
    private RowDeleter rowDeleter = new RowDeleter();

    Game() {
        initializeVariables();
    }

    public void initializeVariables() {
        for (Tetromino t : this.q) {
           t = TetrisPiecesEnum.getPiece();
        }

        PlayField.setStaticVariables(sinkingPieces,falling,q,width,height);
        TetrisPiece.setStaticVariables(p);

        FindSinkingPieces.setStaticVariables(width,p, locationValidator, sinkingPieces);
        MakeNextPieceFallingPiece.setStaticVariables(falling, q);
        ModifyPlayField.setStaticVariables(p);
        PieceKicker.setStaticVariables(falling, locationValidator);
        PieceLocationValidator.setStaticVariables(width, height, p, sinkingPieces, falling, q);
        PieceRotator.setStaticVariables(falling, locationValidator, kicker);
        PieceShifter.setStaticVariables(falling, locationValidator);
        RowDeleter.setStaticVariables(p);
    }

}
