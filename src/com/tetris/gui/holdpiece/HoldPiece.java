package com.tetris.gui.holdpiece;

import com.tetris.game.RunTetris;
import com.tetris.gui.helpers.DrawSquare;
import com.tetris.game.things.ActiveBlock;
import com.tetris.game.things.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HoldPiece extends JPanel {
  private RunTetris runTetris;
  private int height;
  private JLabel labelHoldPiece = new JLabel("Press q");

  public HoldPiece(RunTetris runTetris) {
    this.runTetris = runTetris;
    labelHoldPiece.setForeground(Color.white);
    labelHoldPiece.setFont(new Font("Monospaced", 0, 10));

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
    BufferedImage buffImg =
        new BufferedImage(getWidth(), getHeight() * 5, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    if (null != runTetris) {
      drawQueue(gbi);
    }

    validate();

    g2.drawImage(buffImg, null, 0, 0);
  }

  private void drawQueue(Graphics2D gbi) {
    Dimension d = getSize();
    int w = d.width / 2 * 3;
    BufferedImage buffImg = new BufferedImage(w / 3, w / 6 * 7, BufferedImage.TYPE_INT_ARGB);
    Tetromino t = runTetris.getHoldPiece();

    if (t != null) {
      remove(labelHoldPiece);
      ActiveBlock tp = new ActiveBlock(t);
      tp.setCenter(2, 16);
      DrawSquare.drawSquares(gbi, tp.getSquares(), this);
      gbi.drawImage(buffImg, null, height * 4, 0);
    } else {
      this.add(labelHoldPiece);
    }
  }

  @Override
  public void repaint() {
    super.repaint();

    if (getSize().getWidth() == 0 || getSize().getHeight() == 0) return;

    BufferedImage buffImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    if (null != gbi && null != runTetris && null != runTetris.getTetrominoQueue()) {
      drawQueue(gbi);
    }
  }
}
