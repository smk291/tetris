package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import java.awt.*;

class InputTests {
    private static TetrisPiece falling;

    static void setStaticVariables(TetrisPiece falling) {
        InputTests.falling = falling;
    }

    static void accept(String command) {
        switch(command){
            case"1":
            case"2":
            case"3":
            case"4":
            case"5":
            case"6":
            case"7":
                falling.setFromTetromino(TetrominoEnum.values()[Integer.parseInt(command)-1].get());
                falling.setCenter(new Point(4,3));

                break;
            case"itest": // tetris-deletion test
                PlayField.createEmpty();
                falling.setFromTetromino(TetrominoEnum.I.get());

                for(int y = PlayField.getHeight() - 4; y < PlayField.getHeight(); y++)
                    for (int x = 0, l = PlayField.getWidth(); x < l; x++)
                        if (x != 4)
                            PlayField.fillCell(x, y);

                break;
            case"floattest": // sink test
                PlayField.createEmpty();

                    for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
                        if (x > 4 && x < 8)
                            PlayField.fillCell(x, 20);
                        if (x == 6)
                            PlayField.fillCell(x, 21);
                        if (x != 2)
                            PlayField.fillCell(x, 22);
                }

                break;
            case"q": // sinking-piece motion independent per piece
                PlayField.createEmpty();

                for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
                    if (x != 2)
                        PlayField.fillCell(x, 15);

                    for (int y = 16; y < PlayField.getHeight(); y++)
                        if (x == 2)
                            PlayField.fillCell(x, y);

                    if (x == 8 || x == 9)
                        PlayField.fillCell(x, 16);

                    if (x == 8)
                        PlayField.fillCell(x, 17);

                    if (x == 0 || x == 4 || x == 6) {
                        PlayField.fillCell(x, 14);
                        PlayField.fillCell(x, 13);
                        PlayField.fillCell(x, 23);
                    }

                    if (x == 4)
                        PlayField.fillCell(x, 21);
                    if (x == 3 || x == 4 || x == 6 )
                        PlayField.fillCell(x, 22);
                }

                falling.setFromTetromino(TetrominoEnum.values()[0].get());
                falling.setCenter(1, 9);

                break;
            case"bounds": // kick
                PlayField.createEmpty();


                for (int x = 0, boardWidth = PlayField.getWidth(); x < boardWidth; x++) {
                    if (x != 0 && !(x > 2 && x < 8))
                        PlayField.fillCell(new Point(x, 18));

                    if (x != 5) {
                        PlayField.fillCell(new Point(x, 19));
                        PlayField.fillCell(new Point(x, 20));
                        PlayField.fillCell(new Point(x, 21));
                    }

                    if (x != 0 && x != 5) {
                        PlayField.fillCell(new Point(x, 22));
                        PlayField.fillCell(new Point(x, 23));
                    }
                }

                falling.setCenter(4,9);

                break;
            case"w": // row delete
                PlayField.createEmpty();

                for(int i = 0; i<PlayField.getWidth(); i++) {
                    if (i != 5)
                        PlayField.fillCell(i, 23);
                }

                break;
            case"e": // row delete after sink
                PlayField.createEmpty();

                for(int i = 0; i<PlayField.getWidth();i++) {
                    if (i == 4)
                        PlayField.fillCell(i, 21);
                    if (i > 2)
                        PlayField.fillCell(i, 22);
                    if (i > 0 && i < 4 || i > 4)
                        PlayField.fillCell(i, 23);
                }

                falling.setFromTetromino(TetrominoEnum.J.get());
                falling.setRotation(0);
                falling.setCenter(1,19);

                break;
            case"r": // recursive sinking blocks
                PlayField.createEmpty();

                for(int x = 0; x < PlayField.getWidth();x++) {
                    if (x == 4)
                        PlayField.fillCell(x, 15);
                    if (x > 2)
                        PlayField.fillCell(x, 16);
                    if (x > 0 && x < 4 || x > 4)
                        PlayField.fillCell(x, 17);
                    if (x > 7)
                        PlayField.fillCell(x, 18);

                    if (x == 2 || x == 0 || x == 4 || x == 6)
                        for (int y = 18; y < 24; y++)
                            PlayField.fillCell(x, y);

                    if (x < 8) {
                        PlayField.fillCell(x, 19);
                        PlayField.fillCell(x, 21);
                    }

                    if (x >= 8) {
                        PlayField.fillCell(x, 20);
                        PlayField.fillCell(x, 22);
                    }
                }

                falling.setFromTetromino(TetrominoEnum.J.get());
                falling.setRotation(0);
                falling.setCenter(1,12);

                break;
            case "t":
                // Test piece motion after soft drop
                break;
            case "y":
                // Test t-spin
        }
    }
}