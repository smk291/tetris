package com.tetris.gui.displaydata;

import com.tetris.game.RunTetris;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;

public class GameData extends JPanel {
  private RunTetris runTetris;

  GameData(RunTetris runTetris) {
    this.runTetris = runTetris;

    validate();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    setBackground(new Color(0, 0, 0, 124));
    BufferedImage buffImg = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    if (null != runTetris) {
      drawAllText(gbi);
    }

    validate();
    g2.drawImage(buffImg, null, 50, 100);
  }

  private void drawText(Graphics2D gbi, double s, int offsetx, int offsety, Color c) {
    setBackground(new Color(0, 0, 0, 124));
    gbi.setColor(c);
    Font font = new Font("Serif", Font.BOLD, 20);
    gbi.setFont(font);
    FontRenderContext frc = gbi.getFontRenderContext();

    gbi.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    gbi.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    gbi.drawString(String.valueOf(s), offsetx, offsety);
  }

  private void drawAllText(Graphics2D gbi) {
    drawText(gbi, runTetris.getRecordKeeping().getScore(), 150, 150, Color.BLUE);
    drawText(gbi, runTetris.getRecordKeeping().getLinesCleared(), 200, 250, Color.RED);
    drawText(gbi, runTetris.getRecordKeeping().getLevel(), 300, 300, Color.MAGENTA);
    drawText(gbi, runTetris.getRecordKeeping().getComboCount(), 367, 256, Color.GREEN);
  }

  @Override
  public void repaint() {
    super.repaint();

    Graphics g = getGraphics();
    Graphics2D g2 = (Graphics2D) g;
    setBackground(new Color(0, 0, 0, 124));

    BufferedImage buffImg = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gbi = buffImg.createGraphics();

    if (null != g2 && null != runTetris && null != runTetris.getTetrominoQueue()) {
      drawAllText(gbi);
    }
  }
}
