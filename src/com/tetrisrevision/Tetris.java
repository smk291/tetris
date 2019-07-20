package com.tetrisrevision;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tetris {
  public static void main(String[] args) {
    Game g = new Game();
    //    TetrisGUI tetrisGUI = new TetrisGUI();

    g.play();
  }
}

class BoardCompositer extends JPanel {
  private AffineTransform at = new AffineTransform();
  private Cell[] cells = new Cell[4];
  private ArrayList<Rectangle2D> rects = new ArrayList<>();

  BoardCompositer() {
    cells[0] = new Cell(1, 15);
    cells[1] = new Cell(2, 15);
    cells[2] = new Cell(3, 15);
    cells[3] = new Cell(4, 15);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    Dimension d = getSize();
    int w = d.width;
    int h = d.height;

    BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    gbi.setColor(Color.black);

    Font font = new Font("Serif", Font.BOLD, 20);
    g.setFont(font);
    FontRenderContext frc = g2.getFontRenderContext();
    Rectangle2D titleBounds = font.getStringBounds("Tetris", frc);
    int titleXOffset = (int) (getWidth() - titleBounds.getWidth()) / 2;
    int titleYOffset = 50;

    gbi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    gbi.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    gbi.drawString("Tetris", titleXOffset, titleYOffset);

    gbi.setColor(Color.RED);
    Rectangle2D r2d = new Rectangle2D.Double(w / 12, h / 26, w / 12 * 10, h / 26 * 24);
    gbi.fill(r2d);

    gbi.setColor(new Color(0.0f, 0.0f, 1.0f, 1.0f));
    for (int i = 0; i < cells.length; i++) {
      Rectangle2D innerRect =
          new Rectangle2D.Double(
              (w / 12) * (int) cells[i].getX(), (h / 26) * (int) cells[i].getY(), w / 12, h / 26);
      gbi.fill(innerRect);
    }

    g2.drawImage(buffImg, null, 0, 0);
  }
}
