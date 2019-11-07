package com.tetrisrevision.gui;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.*;
import com.tetrisrevision.things.tetrominoes.TetrominoEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Queue extends JPanel {
  private AffineTransform at = new AffineTransform();
  private RunTetris runTetris;
  private int height;
  private int blockWidth;

  Queue(RunTetris runTetris, TetrominoQueue queue) {
    this.runTetris = runTetris;

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
      drawQueue(gbi, g2);
    }

    validate();
    g2.drawImage(buffImg, null, 0, 0);
  }

  private void drawBlocks(Graphics2D gbi, RowList rows) {
    Dimension d = getSize();

    if (d.getWidth() == 0) return;

    height = (int) (d.getHeight());
    blockWidth = height / 22;

    for (Row r : rows.get()) {
      if (r.getY() > Constants.topRow) continue;

      for (Block block : r.get()) {
        if (null != block.getColor()) gbi.setColor(block.getColor());
        else gbi.setColor(Color.black);

        Rectangle2D innerRect =
            new Rectangle2D.Double(
                blockWidth * block.getX() + 1,
                blockWidth * (Constants.topRow - r.getY()),
                blockWidth,
                blockWidth);
        gbi.fill(innerRect);
        gbi.setColor(Color.lightGray);
        gbi.draw(innerRect);
      }
    }
  }

  private void drawQueue(Graphics2D gbi, Graphics2D g2) {
    Dimension d = getSize();
    int w = d.width / 2 * 3;

    BufferedImage buffImg = new BufferedImage(w / 12 * 4, w / 12 * 14, BufferedImage.TYPE_INT_ARGB);

    for (int i = 0; i < 4; i++) {
      TetrominoEnum t = runTetris.getTetrominoQueue().getQueue().get(i);
      TetrisPiece tp = new TetrisPiece(t.get());
      tp.setCenter(2, (4 - i) * 4);
      drawBlocks(gbi, tp.getBlocks());
      gbi.drawImage(buffImg, null, height * 20, 0);
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
      drawQueue(gbi, g2);
    }
  }
}
