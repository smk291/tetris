package com.tetrisrevision.gui;

import com.tetrisrevision.RunTetris;

import javax.swing.*;
import java.awt.*;

public class GameDataRow extends JPanel {
  RunTetris runTetris;

  void drawRows() {
    if (runTetris == null) return;

    setBackground(Color.BLACK);
    BorderLayout borderLayout = new BorderLayout();
    setLayout(borderLayout);
    JLabel scoreLabel = new JLabel("Score");
    scoreLabel.setForeground(Color.WHITE);
    scoreLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
    add(scoreLabel, BorderLayout.LINE_START);

    JLabel score = new JLabel(Integer.toString((int) runTetris.getRecordKeeping().getScore()));
    score.setForeground(Color.white);
    score.setFont(new Font("Monospaced", Font.BOLD, 12));
    add(score, BorderLayout.LINE_END);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    drawRows();
  }

  @Override
  public void repaint() {
    super.repaint();
    drawRows();
  }
}
