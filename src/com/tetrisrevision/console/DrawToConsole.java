package com.tetrisrevision.console;

import com.tetrisrevision.AddAndRemove;
import com.tetrisrevision.GUI;
import com.tetrisrevision.PlayField;
import com.tetrisrevision.TetrisPiece;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class DrawToConsole implements GUI {
  public void draw(PlayField field) {
    int bound = PlayField.getHeight();

    IntStream.range(0, bound).forEach(i -> drawRow(i, field));
    System.out.print("  ");
    IntStream.range(0, PlayField.getWidth()).mapToObj(i -> "-").forEach(System.out::print);
    System.out.println();
  }

  private void drawRow(int row, PlayField field) {
    System.out.print(" |");
    Arrays.stream(field.getCells()[row])
        .map(c -> drawCell(c.isFull()))
        .forEach(System.out::print);
    System.out.println("| " + row);
  }

  public char drawCell(boolean isFull) {
    return isFull ? '□' : ' ';
  }

  public void drawBoardIncludingPiece(TetrisPiece piece, ArrayList<ArrayList<Point>> sinkingPieces, PlayField field) {
    AddAndRemove.addFallingPiece(piece, field);
    AddAndRemove.addAllSinkingPieces(sinkingPieces, field);

    draw(field);

    AddAndRemove.removeFallingPiece(piece, field);
    AddAndRemove.removeSinkingPieces(sinkingPieces, field);
  }
}
