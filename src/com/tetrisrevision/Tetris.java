package com.tetrisrevision;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class Tetris {
  public static void main(String[] args) {
    RunTetris g = new RunTetris(10, 24);
    TetrisGUI tetrisGUI = new TetrisGUI(g);
    g.setTetrisGUI(tetrisGUI);
    tetrisGUI.init();

    g.play();
  }
}

class BoardCompositer extends JPanel {
  private AffineTransform at = new AffineTransform();
//  private Cell[] cells = new Cell[4];
  private RunTetris runTetris;
  BoardCompositer(RunTetris runTetris) {
//    cells[0] = new Cell(1, 15);
//    cells[1] = new Cell(2, 15);
//    cells[2] = new Cell(3, 15);
//    cells[3] = new Cell(4, 15);
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

//    gbi.setColor(Color.RED);
//    Rectangle2D r2d = new Rectangle2D.Double(w / 12, h / 26, w / 12 * 10, h / 26 * 24);
//    gbi.fill(r2d);

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
    gbi.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    gbi.drawString("Tetris", titleXOffset, titleYOffset);
  }

  private void drawBlocks(Graphics2D gbi, Cell[] cells) {
    Dimension d = getSize();
    int w = d.width;
    int h = d.height;

    gbi.setColor(new Color(0.0f, 0.0f, 1.0f, 1.0f));

    for (Cell cell : cells) {
      Rectangle2D innerRect =
          new Rectangle2D.Double(
              (w / 12) * (int) cell.getX(),
              (h / 26) * (int) cell.getY(),
              w / 12,
              h / 26
          );

      gbi.fill(innerRect);
    }
  }

  private void drawBoard(Graphics2D gbi) {
    if (null == runTetris)
      return;

    if (null != runTetris.getCurrentPiece2d().getCells())
      drawBlocks(gbi, runTetris.getCurrentPiece2d().getCells());

    if (null != runTetris.getBlocks2d())
      IntStream.range(0, 24).forEach(i -> {
        ArrayList<Cell> row = runTetris.getBlocks2d().getRow(i);
        if (null != row)
          drawBlocks(gbi, row.toArray(new Cell[0]));
      });

    if (null != runTetris.getSinkingPieces2d().getPieces())
     runTetris.getSinkingPieces2d().getPieces().forEach(piece -> {
       drawBlocks(gbi, piece.toArray(new Cell[0]));
     });
  }

  @Override
  public void repaint() {
    super.repaint();
//    Dimension d = getSize();
    int w = 400;
    int h = 700;

//    Graphics g =getGraphics();
//    Graphics2D g2 = (Graphics2D) g;

    BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    drawBoard(gbi);

//    if (null != buffImg)
//      g2.drawImage(buffImg, null, 0, 0);
  }
}
