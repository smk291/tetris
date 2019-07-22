package com.tetrisrevision.console;

import com.tetrisrevision.*;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PrintToConsole implements GUI {
  public void field(PlayField field) {
    int bound = PlayField.getHeight();

    IntStream.range(0, bound).forEach(i -> row(i, field));
    System.out.print("  ");
    IntStream.range(0, PlayField.getWidth()).mapToObj(i -> "-").forEach(System.out::print);
    System.out.println();
  }

  private void row(int row, PlayField field) {
    System.out.print(" |");
    Arrays.stream(field.getCells()[row])
        .map(c -> cell(c.isFull()))
        .forEach(System.out::print);
    System.out.println("| " + row);
  }

  public char cell(boolean isFull) {
    return isFull ? '□' : ' ';
  }

  public void field(TetrisPiece piece, SinkingPieces sinkingPieces, PlayField field) {
    AddAndRemove.add(piece, field);
    AddAndRemove.addFromList(sinkingPieces, field);

    field(field);

    AddAndRemove.remove(piece, field);
    AddAndRemove.removeFromList(sinkingPieces, field);
  }
}
