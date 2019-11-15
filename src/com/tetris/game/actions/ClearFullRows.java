package com.tetris.game.actions;

import com.tetris.game.things.Row;
import com.tetris.game.things.RowList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Supplier;

public class ClearFullRows {
  public static ArrayList<RowList> floatingRows(ArrayList<Integer> fullRows, RowList playfield) {
    ArrayList<RowList> floating = new ArrayList<>();
    int maxY = Collections.max(fullRows);
    int minY = Collections.min(fullRows);
    int minIdx = playfield.getRowIdxFromY(minY);

    Supplier<Row> getRow = () -> minIdx < playfield.size() ? playfield.get().get(minIdx) : null;

    while (minIdx < playfield.size()) {
      RowList rl = new RowList();

      while (getRow.get() != null && getRow.get().getY() < maxY && getRow.get().isFull())
        playfield.get().remove(minIdx);

      while (getRow.get() != null && getRow.get().isFull())
        rl.add(playfield.get().remove(minIdx));

      floating.add(rl);
    }

    return floating;
  }
}
