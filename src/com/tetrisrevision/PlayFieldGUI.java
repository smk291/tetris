package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class PlayFieldGUI extends JPanel {
  private AffineTransform at = new AffineTransform();
  private RunTetris runTetris;

  PlayFieldGUI(RunTetris runTetris) {
    this.runTetris = runTetris;

    validate();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    setBackground(Color.black);
    BufferedImage buffImg = new BufferedImage(1000, 600, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    drawBoard(gbi);

    if (null != runTetris) {
      drawQueue(g2, runTetris.getTetrominoQueue());
      drawText(gbi, runTetris.getRecordKeeping().getScore());
      drawText(gbi, runTetris.getRecordKeeping().getLinesCleared());
      drawText(gbi, runTetris.getRecordKeeping().getLevel());
    }

    validate();
    g2.drawImage(buffImg, null, 50, 100);
  }

  private void drawText(Graphics2D gbi, double s) {
    gbi.setColor(Color.lightGray);
    Font font = new Font("Serif", Font.BOLD, 20);
    gbi.setFont(font);
    FontRenderContext frc = gbi.getFontRenderContext();
    Rectangle2D titleBounds = font.getStringBounds(Double.toString(s), frc);
    int titleXOffset = (int) (getWidth() - titleBounds.getWidth()) / 2;
    int titleYOffset = 50;

    gbi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    gbi.setRenderingHint(
    RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    gbi.drawString(String.valueOf(s), titleXOffset, titleYOffset);
  }

  private void drawBlocks(Graphics2D gbi, Block[] blocks) {
    Dimension d = getSize();
    int w = 400 / 18;

    for (Block block : blocks) {
      if (null != block.getColor())
        gbi.setColor(block.getColor());
      else
        gbi.setColor(Color.black);

      Rectangle2D innerRect =
          new Rectangle2D.Double(w * block.getX() + 1, w * block.getY() + 1, w, w);
      gbi.fill(innerRect);
      gbi.setColor(Color.lightGray);
      gbi.draw(innerRect);
    }
  }

  private void drawBoard(Graphics2D gbi) {
    if (null == runTetris) return;



    if (null != runTetris.getCurrentPiece().getCells())
      drawBlocks(gbi, runTetris.getCurrentPiece().getCells());

    if (null != runTetris.getPlayField())
      IntStream.range(0, PlayField.getHeight())
          .forEach(i -> drawBlocks(gbi, runTetris.getPlayField().get(i).toArray(new Block[0])));

    if (null != runTetris.getSinkingPieces().getPieces())
      runTetris
          .getSinkingPieces()
          .getPieces()
          .forEach(piece -> drawBlocks(gbi, piece.toArray(new Block[0])));

    gbi.setColor(Color.lightGray);
    Rectangle2D boardOutline = new Rectangle2D.Double(0, 0, 400 / 18 * PlayField.getWidth() + 1, 400 / 18 * PlayField.getHeight() + 1);
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
      drawBlocks(gbi, tp.getCells());
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
      drawText(gbi, runTetris.getRecordKeeping().getScore());
      drawText(gbi, runTetris.getRecordKeeping().getLinesCleared());
      drawText(gbi, runTetris.getRecordKeeping().getLevel());
      drawQueue(gbi, runTetris.getTetrominoQueue());
    }
  }
}
