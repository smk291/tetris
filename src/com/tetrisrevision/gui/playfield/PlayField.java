package com.tetrisrevision.gui.playfield;

import com.tetrisrevision.RunTetris;
import com.tetrisrevision.helpers.Constants;
import com.tetrisrevision.things.Block;
import com.tetrisrevision.things.Row;
import com.tetrisrevision.things.RowList;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class PlayField extends JPanel {
  private AffineTransform at = new AffineTransform();
  private RunTetris runTetris;
  private int height;
  private int blockWidth;

  public PlayField(RunTetris runTetris) {
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
    blockWidth = height / 20;

    setBackground(Color.black);
    BufferedImage buffImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    drawBoard(gbi);

    validate();
    g2.drawImage(buffImg, null, 0, 0);
  }

  private void drawBlocks(Graphics2D gbi, RowList rows) {
    Dimension d = getSize();
    if (d.getWidth() == 0) return;

    height = (int) (d.getHeight());
    blockWidth = height / 20;

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

  private void drawBoard(Graphics2D gbi) {
    if (null == runTetris) return;

    if (null != runTetris.getCurrentPiece())
      drawBlocks(gbi, runTetris.getCurrentPiece().getBlocks());

    if (null != runTetris.getPlayfield())
      IntStream.range(0, Constants.height).forEach(i -> drawBlocks(gbi, runTetris.getPlayfield()));

    if (null != runTetris.getSinkingPieces())
      runTetris.getSinkingPieces().forEach(piece -> drawBlocks(gbi, piece));

    Dimension size = getSize();
    height = (int) size.getHeight();
    blockWidth = height / 20;

    gbi.setColor(Color.lightGray);
    Rectangle2D boardOutline =
        new Rectangle2D.Double(
            0, 0, blockWidth * Constants.width + 1, blockWidth * Constants.height + 1);
    gbi.draw(boardOutline);
  }

  @Override
  public void repaint() {
    super.repaint();

    if (getSize().getWidth() == 0 || getSize().getHeight() == 0) return;

    BufferedImage buffImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    drawBoard(gbi);
  }
}
