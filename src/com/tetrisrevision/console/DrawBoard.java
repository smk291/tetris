package com.tetrisrevision.console;

import com.tetrisrevision.GUI;
import com.tetrisrevision.ModifyPlayField;
import com.tetrisrevision.PlayField;

import java.util.Arrays;
import java.util.stream.IntStream;

public class DrawBoard implements GUI {
  private PlayField playField;
  private ModifyPlayField modifyPlayField;

  public DrawBoard(PlayField playField, ModifyPlayField modifyPlayField) {
    this.playField = playField;
    this.modifyPlayField = modifyPlayField;
  }

  public void draw() {
    int bound = PlayField.getHeight();

    IntStream.range(0, bound).forEach(this::drawRow);
    System.out.print("  ");
    IntStream.range(0, PlayField.getWidth()).mapToObj(i -> "-").forEach(System.out::print);
    System.out.println();
  }

  private void drawRow(int row) {
    System.out.print(" |");
    Arrays.stream(playField.getCells()[row])
        .map(c -> drawCell(c.isFull()))
        .forEach(System.out::print);
    System.out.println("| " + row);
  }

  public char drawCell(boolean isFull) {
    return isFull ? '□' : ' ';
  }

  public void drawBoardIncludingPiece() {
    modifyPlayField.addAndRemove.addFallingPiece();
    modifyPlayField.addAndRemove.addAllSinkingPieces();

    draw();

    modifyPlayField.addAndRemove.removeFallingPiece();
    modifyPlayField.addAndRemove.removeSinkingPieces();
  }
}
