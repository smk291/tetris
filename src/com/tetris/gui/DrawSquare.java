package com.tetris.gui;

import com.tetris.constants.Constants;
import com.tetris.things.Square;
import com.tetris.things.Row;
import com.tetris.things.RowList;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

abstract public class DrawSquare {
  private static int computeEdgeLength(JPanel container) {
    Dimension d = container.getSize();

    if (d.getWidth() == 0)
      return 0;

    int height = (int) (d.getHeight());
    return height / 22;
  }

  public static void drawSquares(Graphics2D gbi, RowList rows, JPanel container) {
    int edgeLength = computeEdgeLength(container);

    for (Row r : rows.get()) {
      if (r.getY() > Constants.topRow) continue;

      for (Square square : r.get()) {
        if (null != square.getColor()) gbi.setColor(square.getColor());
        else gbi.setColor(Color.black);

        Rectangle2D innerRect =
            new Rectangle2D.Double(
                edgeLength * square.getX() + 1,
                edgeLength * (Constants.topRow - r.getY()),
                edgeLength,
                edgeLength);
        gbi.fill(innerRect);
        gbi.setColor(Color.lightGray);
        gbi.draw(innerRect);
      }
    }
  }
}
