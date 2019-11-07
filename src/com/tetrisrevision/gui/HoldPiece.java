package com.tetrisrevision.gui;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;
import com.tetrisrevision.things.TetrisPiece;
import com.tetrisrevision.things.tetrominoes.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class HoldPiece extends JPanel {
  private RunTetris runTetris;
  private int height;
  private int blockWidth;
  private JLabel labelHoldPiece = new JLabel("Press q");

  HoldPiece(RunTetris runTetris) {
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
    blockWidth = height / 22;
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

  private void drawQueue(Graphics2D gbi) {
    Dimension d = getSize();
    int w = d.width / 2 * 3;

    BufferedImage buffImg = new BufferedImage(w / 12 * 4, w / 12 * 14, BufferedImage.TYPE_INT_ARGB);

    Tetromino t = runTetris.getHoldPiece();

    if (t != null) {
      remove(labelHoldPiece);
      TetrisPiece tp = new TetrisPiece(t);
      tp.setCenter(2, (4 - 0) * 4);
      drawBlocks(gbi, tp.getBlocks());
      gbi.drawImage(buffImg, null, height * 4, 0);
    } else {
      this.add(labelHoldPiece);
    }

    //    }
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
