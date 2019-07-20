package com.tetrisrevision;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Board extends Component {
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(400, 490);
  }

  @Override
  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;

    Rectangle2D r2d = new Rectangle2D.Double(100, 10, 200, 480);

    g2.setColor(Color.black);

    g2.draw(r2d);
  }

  //  @Override
  //  public void paint(Graphics g) {
  //    Graphics2D g2 = (Graphics2D) g;
  //    Rectangle2D r = new Rectangle2D.Double(0, 0, 20, 20);
  //    g2.draw(r);
  //  }
}
