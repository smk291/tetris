package com.tetris.gui.displaydata;

import com.tetris.game.RunTetris;
import com.tetris.gui.constants.GUIConstants;

import javax.swing.*;
import java.awt.*;

public class GameDataRow extends JPanel {
  private RunTetris rt;
  Color fontColor = Color.white;
  Color labelFontColor = Color.white;
  int labelFontSize = 12;
  int fontSize = 12;
  String font = "Monospaced";
  Color backgroundColor = Color.black;
  String label;
  GameDataRow(RunTetris rt, String label) {
    this.rt = rt;
    this.label = label;
  }

  private void drawRows() {
    if (rt == null) return;

    setBackground(backgroundColor);
    BorderLayout borderLayout = new BorderLayout();
    setLayout(borderLayout);
    JLabel scoreLabel = new JLabel();
    scoreLabel.setForeground(fontColor);
    scoreLabel.setFont(new Font(font, Font.BOLD, fontSize));
    add(scoreLabel, BorderLayout.LINE_START);

    JLabel score = new JLabel(Integer.toString((int) rt.getRecordKeeping().getScore()));
    score.setForeground(labelFontColor);
    score.setFont(new Font(font, Font.BOLD, labelFontSize));
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
