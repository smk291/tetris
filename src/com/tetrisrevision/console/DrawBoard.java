package com.tetrisrevision.console;

import com.tetrisrevision.GUI;
import com.tetrisrevision.ModifyPlayField;
import com.tetrisrevision.PlayField;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DrawBoard implements GUI {
  public void draw() {
    int bound = PlayField.getHeight();

    IntStream.range(0, bound).forEach(this::drawRow);
    System.out.print("  ");
    IntStream.range(0, PlayField.getWidth()).mapToObj(i -> "-").forEach(System.out::print);
    System.out.println();
  }

  private void drawRow(int row) {
    System.out.print(" |");
    Arrays.stream(PlayField.getCells()[row])
        .map(c -> drawCell(c.isFull()))
        .forEach(System.out::print);
    System.out.println("| " + row);
  }

  public char drawCell(boolean isFull) {
    return isFull ? '*' : ' ';
  }

  public void drawBoardIncludingPiece() {
    ModifyPlayField.AddAndRemove.addFallingPiece();
    ModifyPlayField.AddAndRemove.addAllSinkingPieces();

    draw();

    ModifyPlayField.AddAndRemove.removeFallingPiece();
    ModifyPlayField.AddAndRemove.removeSinkingPieces();
  }
}
