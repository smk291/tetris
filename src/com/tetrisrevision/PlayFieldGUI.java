package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class PlayFieldGUI extends JPanel {
  private AffineTransform at = new AffineTransform();
  private RunTetris runTetris;
  private int width;
  private int blockWidth;

  PlayFieldGUI(RunTetris runTetris) {
    this.runTetris = runTetris;

    validate();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    Dimension d = getSize();

    if (d.getWidth() == 0 || d.getHeight() == 0)
      return;

    width = (int) d.getWidth();
    blockWidth = width / 20;

    setBackground(Color.black);
    BufferedImage buffImg = new BufferedImage(width, 600, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    drawBoard(gbi);

    if (null != runTetris) {
      drawQueue(g2, runTetris.getTetrominoQueue());
    }

    validate();
    g2.drawImage(buffImg, null, 0, 50);
  }

  private void drawBlocks(Graphics2D gbi, RowList rows) {
    Dimension d = getSize();
    if (d.getWidth() == 0)
      return;

    width = (int) (d.getWidth());
    blockWidth = width / 20;

    for (Row r : rows) {
      for (Block block : r) {
        if (null != block.getColor())
          gbi.setColor(block.getColor());
        else
          gbi.setColor(Color.black);

        Rectangle2D innerRect = new Rectangle2D.Double(blockWidth * block.getX() + 1, blockWidth * r.getY() + 1, blockWidth, blockWidth);
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

    if (null != runTetris.getPlayField())
      IntStream.range(0, RowList.getHeight())
          .forEach(i -> drawBlocks(gbi, runTetris.getPlayField()));

    if (null != runTetris.getSinkingPieces())
      runTetris.getSinkingPieces().forEach(piece -> drawBlocks(gbi, piece));

    Dimension size = getSize();
    width = (int) size.getWidth();
    blockWidth = width / 20;

    gbi.setColor(Color.lightGray);
    Rectangle2D boardOutline = new Rectangle2D.Double(0, 0, blockWidth * RowList.getWidth() + 1, blockWidth * RowList.getHeight() + 1);
    gbi.draw(boardOutline);
  }

  private void drawQueue(Graphics2D g2, TetrominoQueue queue) {
    Dimension d = getSize();
    int w = (d.width != 0 ? d.width : 200) / 2 * 3;

    BufferedImage buffImg = new BufferedImage(w / 12 * 4, w / 12 * 14, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    for (int i = 0; i < 3; i++) {
      TetrominoEnum t = queue.getQueue().get(i);
      TetrisPiece tp = new TetrisPiece(t.get());
      tp.setCenter(1, 2 + i * 5);
      drawBlocks(gbi, tp.getBlocks());
      g2.drawImage(buffImg, null, 300, 140);
    }
  }

  @Override
  public void repaint() {
    super.repaint();

    Graphics g = getGraphics();
    Graphics2D g2 = (Graphics2D) g;

    BufferedImage buffImg = new BufferedImage(1000,  400, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    drawBoard(gbi);

    if (null != g2 && null != runTetris && null != runTetris.getTetrominoQueue()) {
      drawQueue(gbi, runTetris.getTetrominoQueue());
    }
  }
}
