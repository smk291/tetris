package com.tetris.game.actions;

import com.tetris.game.things.Row;
import com.tetris.game.things.RowList;

import java.util.ArrayList;
import java.util.Optional;

public class IdentifyFullRows {
  public static ArrayList<Integer> identify(RowList insertedBlock, RowList playfield) {
    ArrayList<Integer> fullRows = new ArrayList<>();

    for (int i = 0; i < insertedBlock.size(); i++) {
      Optional<Row> r = playfield.getRowByY(insertedBlock.get().get(i).getY());

      r.ifPresent(row -> {
        if (row.isFull())
          fullRows.add(row.getY());
      });
    }

    return fullRows;
  }
}
