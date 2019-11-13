package com.tetris.gui.playfield;

import com.tetris.RunTetris;
import com.tetris.constants.Constants;
import com.tetris.things.Square;
import com.tetris.things.Row;
import com.tetris.things.RowList;

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
  private int squareWidth;

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
    squareWidth = height / 20;

    setBackground(Color.black);
    BufferedImage buffImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    drawBoard(gbi);

    validate();
    g2.drawImage(buffImg, null, 0, 0);
  }

  private void drawSquares(Graphics2D gbi, RowList rows) {
    Dimension d = getSize();
    if (d.getWidth() == 0) return;

    height = (int) (d.getHeight());
    squareWidth = height / 20;

    for (Row r : rows.get()) {
      if (r.getY() > Constants.topRow) continue;

      for (Square square : r.get()) {
        if (null != square.getColor()) gbi.setColor(square.getColor());
        else gbi.setColor(Color.black);

        Rectangle2D innerRect =
            new Rectangle2D.Double(
                squareWidth * square.getX() + 1,
                squareWidth * (Constants.topRow - r.getY()),
                squareWidth,
                squareWidth);
        gbi.fill(innerRect);
        gbi.setColor(Color.lightGray);
        gbi.draw(innerRect);
      }
    }
  }

  private void drawBoard(Graphics2D gbi) {
    if (null == runTetris) return;

    if (null != runTetris.getCurrentPiece())
      drawSquares(gbi, runTetris.getCurrentPiece().getSquares());

    if (null != runTetris.getPlayfield())
      IntStream.range(0, Constants.height).forEach(i -> drawSquares(gbi, runTetris.getPlayfield()));

    if (null != runTetris.getSinkingPieces())
      runTetris.getSinkingPieces().forEach(block -> drawSquares(gbi, block));

    Dimension size = getSize();
    height = (int) size.getHeight();
    squareWidth = height / 20;

    gbi.setColor(Color.lightGray);
    Rectangle2D boardOutline =
        new Rectangle2D.Double(
            0, 0, squareWidth * Constants.width + 1, squareWidth * Constants.height + 1);
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
