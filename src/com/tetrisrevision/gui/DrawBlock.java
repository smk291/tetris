package com.tetrisrevision.gui;

import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

abstract public class DrawBlock {
  private static int computeEdgeLength(JPanel container) {
    Dimension d = container.getSize();

    if (d.getWidth() == 0)
      return 0;

    int height = (int) (d.getHeight());
    return height / 22;
  }

  public static void drawBlocks(Graphics2D gbi, RowList rows, JPanel container) {
    int edgeLength = computeEdgeLength(container);

    for (Row r : rows.get()) {
      if (r.getY() > Constants.topRow) continue;

      for (Block block : r.get()) {
        if (null != block.getColor()) gbi.setColor(block.getColor());
        else gbi.setColor(Color.black);

        Rectangle2D innerRect =
            new Rectangle2D.Double(
                edgeLength * block.getX() + 1,
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
