package com.tetrisrevision;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class BoardCompositer extends JPanel {
  private AffineTransform at = new AffineTransform();
  private RunTetris runTetris;

  BoardCompositer(RunTetris runTetris) {
    this.runTetris = runTetris;
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

    drawTitle(gbi);
    drawBoard(gbi);

    g2.drawImage(buffImg, null, 0, 0);
  }

  private void drawTitle(Graphics2D gbi) {
    gbi.setColor(Color.black);
    Font font = new Font("Serif", Font.BOLD, 20);
    gbi.setFont(font);
    FontRenderContext frc = gbi.getFontRenderContext();
    Rectangle2D titleBounds = font.getStringBounds("Tetris", frc);
    int titleXOffset = (int) (getWidth() - titleBounds.getWidth()) / 2;
    int titleYOffset = 50;

    gbi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    gbi.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    gbi.drawString("Tetris", titleXOffset, titleYOffset);
  }

  private void drawBlocks(Graphics2D gbi, Cell[] cells, boolean printAll) {
    Dimension d = getSize();
    int w = d.width;
    int h = d.height;

    for (Cell cell : cells) {
      if (!printAll && cell.isEmpty()) continue;

      if (null != cell.getColor()) gbi.setColor(cell.getColor());
      else gbi.setColor(Color.black);

      Rectangle2D innerRect =
          new Rectangle2D.Double(
              (w / 12) * (int) cell.getX(), (w / 12) * (int) cell.getY(), w / 12, w / 12);

      gbi.fill(innerRect);
    }
  }

  private void drawBoard(Graphics2D gbi) {
    if (null == runTetris) return;

    if (null != runTetris.getCurrentPiece().getCells())
      drawBlocks(gbi, runTetris.getCurrentPiece().getCells(), true);

    if (null != runTetris.getBlocks2d())
      IntStream.range(0, 24)
          .forEach(
              i -> {
                Cell[] row = runTetris.getBlocks2d().getRow(i);
                if (null != row) drawBlocks(gbi, row, false);
              });

    if (null != runTetris.getSinkingPieces().getPieces())
      runTetris
          .getSinkingPieces()
          .getPieces()
          .forEach(
              piece -> {
                drawBlocks(gbi, piece.toArray(new Cell[0]), true);
              });
  }

  @Override
  public void repaint() {
    super.repaint();
    int w = 400;
    int h = 700;

    BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    drawBoard(gbi);
  }
}
