package com.tetrisrevision;

import com.tetrisrevision.tetrominos.Tetromino;
import com.tetrisrevision.tetrominos.TetrominoEnum;

import javax.swing.*;
import javax.swing.border.Border;
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

    validate();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    Dimension d = getSize();

    setBackground(Color.black);
    BufferedImage buffImg = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

//    drawTitle(gbi);
    drawBoard(gbi);
    drawQueue(g2, runTetris.getTetrominoQueue());

    g2.drawImage(buffImg, null, 50, 100);
  }

//  private void outlineBoard(Graphics2D gbi) {
//    Rectangle2D boardOutline = new Rectangle2D.Double(400 / 18 * 12, 400 / 18 * 24, 0, 0);
//
//
//    gbi.fill(comp);
//  }

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
    int w = 400 / 18;

    for (Cell cell : cells) {
      if (!printAll && cell.isEmpty()) continue;

      if (null != cell.getColor())
        gbi.setColor(cell.getColor());
      else
        gbi.setColor(Color.black);

      Rectangle2D innerRect =
          new Rectangle2D.Double(w * (int) cell.getX() + 1, w * (int) cell.getY() + 1, w, w);

      gbi.fill(innerRect);
    }
  }

  private void drawBoard(Graphics2D gbi) {
    if (null == runTetris) return;

    gbi.setColor(Color.lightGray);
    Rectangle2D boardOutline = new Rectangle2D.Double(0, 0, 400 / 18 * Blocks2d.getWidth() + 2, 400 / 18 * Blocks2d.getHeight() + 2);

    gbi.draw(boardOutline);

    if (null != runTetris.getCurrentPiece().getCells())
      drawBlocks(gbi, runTetris.getCurrentPiece().getCells(), true);

    if (null != runTetris.getBlocks2d())
      IntStream.range(0, Blocks2d.getHeight())
          .forEach(
              i -> {
                Cell[] row = runTetris.getBlocks2d().getRow(i);

                if (null != row) drawBlocks(gbi, row, false);
              });

    if (null != runTetris.getSinkingPieces().getPieces())
      runTetris
          .getSinkingPieces()
          .getPieces()
          .forEach(piece -> drawBlocks(gbi, piece.toArray(new Cell[0]), true));
  }

  private void drawQueue(Graphics2D g2, TetrominoQueue queue) {
    Dimension d = getSize();
    int w = d.width / 2 * 3;

    BufferedImage buffImg = new BufferedImage(w / 12 * 4, w / 12 * 14, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    for (int i = 0; i < 3; i++) {
      TetrominoEnum t = queue.getQueue().get(i);
      TetrisPiece tp = new TetrisPiece(t.get());

      tp.setCenter(1, 2 + i * 5);

      drawBlocks(gbi, tp.getCells(), true);

      g2.drawImage(buffImg, null, 300, 140);
    }
  }

  private void drawScore() {

  }

  @Override
  public void repaint() {
    super.repaint();

    Graphics g = getGraphics();
    Graphics2D g2 = (Graphics2D) g;

    Dimension d = getSize();

    BufferedImage buffImg = new BufferedImage(1000,  400, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    drawBoard(gbi);

    if (g2 != null && null != runTetris && runTetris.getTetrominoQueue() != null) {
      drawQueue(gbi, runTetris.getTetrominoQueue());

    }
  }
}
