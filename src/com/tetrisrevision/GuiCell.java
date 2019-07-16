package com.tetrisrevision;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GuiCell extends Component {
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(20, 20);
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    Rectangle2D guiCell = new Rectangle2D.Double(0, 0, 20, 20);

    g2.setColor(Color.BLACK);
    g2.fill(guiCell);

    g2.draw(guiCell);
  }
}
