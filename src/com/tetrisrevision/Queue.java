package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class Queue extends JPanel {
  private AffineTransform at = new AffineTransform();
  private RunTetris runTetris;
  private int height;
  private int blockWidth;
  private TetrominoQueue queue;

  Queue(RunTetris runTetris, TetrominoQueue queue) {
    this.runTetris = runTetris;
    this.queue = queue;

    validate();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    Dimension d = getSize();

    if (d.getWidth() == 0 || d.getHeight() == 0) return;

    height = (int) d.getHeight();
    blockWidth = height / 22;

    setBackground(Color.black);
    BufferedImage buffImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();


    if (null != runTetris) {
      drawBoard(gbi, g2);
    }

    validate();
    g2.drawImage(buffImg, null, 0, 50);
  }

  private void drawBlocks(Graphics2D gbi, RowList rows) {
    Dimension d = getSize();
    if (d.getWidth() == 0) return;

    height = (int) (d.getHeight());
    blockWidth = height / 22;

    for (Row r : rows.get()) {
      if (r.getY() > Constants.topRow)
        continue;

      for (Block block : r.get()) {
        if (null != block.getColor()) gbi.setColor(block.getColor());
        else gbi.setColor(Color.black);

        Rectangle2D innerRect =
            new Rectangle2D.Double(
                blockWidth * block.getX() + 1, blockWidth * (Constants.topRow - r.getY()), blockWidth, blockWidth);
        gbi.fill(innerRect);
        gbi.setColor(Color.lightGray);
        gbi.draw(innerRect);
      }
    }
  }

  private void drawBoard(Graphics2D gbi, Graphics2D g2) {
    Dimension d = getSize();
    int w = (d.width != 0 ? d.width : 200) / 2 * 3;

    BufferedImage buffImg = new BufferedImage(w / 12 * 4, w / 12 * 14, BufferedImage.TYPE_INT_ARGB);

    for (int i = 4; i >= 0; i--) {
      TetrominoEnum t = queue.getQueue().get(i);
      TetrisPiece tp = new TetrisPiece(t.get());
      tp.setCenter(1, 2 + i * 5);
      drawBlocks(gbi, tp.getBlocks());
      g2.drawImage(buffImg, null, height * 20, 140);
    }

  }

  @Override
  public void repaint() {
    super.repaint();

    if (getSize().getWidth() == 0 || getSize().getHeight() == 0) return;

    Graphics g = getGraphics();
    Graphics2D g2 = (Graphics2D) g;

    BufferedImage buffImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    if (null != g2 && null != runTetris && null != runTetris.getTetrominoQueue()) {
      drawBoard(gbi, g2);
    }
  }
}
