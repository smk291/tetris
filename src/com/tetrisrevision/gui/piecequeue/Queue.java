package com.tetrisrevision.gui.blockqueue;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.gui.DrawSquare;
import com.tetrisrevision.things.*;
import com.tetrisrevision.things.tetrominoes.TetrominoEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Queue extends JPanel {
  private AffineTransform at = new AffineTransform();
  private RunTetris runTetris;
  private int height;

  public Queue(RunTetris runTetris, TetrominoQueue queue) {
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
    setBackground(Color.black);
    BufferedImage buffImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    if (null != runTetris) {
      drawQueue(gbi, g2);
    }

    validate();
    g2.drawImage(buffImg, null, 0, 0);
  }

  private void drawQueue(Graphics2D gbi, Graphics2D g2) {
    Dimension d = getSize();
    int w = d.width / 2 * 3;

    BufferedImage buffImg = new BufferedImage(w / 12 * 4, w / 12 * 14, BufferedImage.TYPE_INT_ARGB);

    for (int i = 0; i < 4; i++) {
      TetrominoEnum t = runTetris.getTetrominoQueue().getQueue().get(i);
      ActiveBlock tp = new ActiveBlock(t.get());
      tp.setCenter(2, (4 - i) * 4);
      DrawSquare.drawSquares(gbi, tp.getSquares(), this);
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
