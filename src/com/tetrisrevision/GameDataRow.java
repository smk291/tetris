package com.tetrisrevision;

import javax.swing.*;
import java.awt.*;

public class GameDataRow extends JPanel {
  RunTetris runTetris;
  int[] gridyArray;
  JLabel scoreLabel = new JLabel();
  JLabel levelLabel = new JLabel();
  JLabel score = new JLabel();
  JLabel level = new JLabel();
  JLabel linesClearedLabel = new JLabel();
  JLabel comboCountLabel = new JLabel();
  JLabel linesCleared = new JLabel();
  JLabel comboCount = new JLabel();

  GameDataRow(RunTetris runTetris, int[] gridyArray) {
    this.runTetris = runTetris;
    this.gridyArray = gridyArray;
  }

  void createRow(
      JLabel leftLabel,
      JLabel rightLabel,
      int gridy,
      String label1,
      String label2,
      GridBagLayout layOut,
      GridBagConstraints c,
      Font font) {
    c.fill = GridBagConstraints.BOTH;
    c.gridy = gridy;
    c.weightx = 0.50;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.WEST;
    leftLabel.setText(label1);
    leftLabel.setForeground(Color.WHITE);
    leftLabel.setFont(font);
    layOut.setConstraints(leftLabel, c);
    add(leftLabel);

    c.anchor = GridBagConstraints.EAST;
    rightLabel.setText(label2);
    rightLabel.setForeground(Color.WHITE);
    rightLabel.setFont(font);
    layOut.setConstraints(rightLabel, c);
    add(rightLabel);
  }

  void drawRows() {
    if (runTetris == null) return;

    setBackground(Color.BLACK);
//    GridBagLayout layout = new GridBagLayout();
    BorderLayout borderLayout = new BorderLayout();
//    setLayout(layout);
    setLayout(borderLayout);
//    GridBagConstraints c = new GridBagConstraints();

//    c.insets = new Insets(0, 20, 0, 20);
//    c.weightx = 1.0;
//    c.weighty = 1.0;
//    Font newFont = new Font("Monospaced", Font.BOLD, 12);

//    createRow(scoreLabel, levelLabel, gridyArray[0], "Score:", "Level:", layout, c, newFont);
//    createRow(
//        score,
//        level,
//        gridyArray[1],
//        Integer.toString((int) runTetris.getRecordKeeping().getScore()),
//        Integer.toString((int) runTetris.getRecordKeeping().getLevel()),
//        layout,
//        c,
//        newFont);
//    createRow(
//        linesClearedLabel,
//        comboCountLabel,
//        gridyArray[2],
//        "Lines cleared:",
//        "Combo count:",
//        layout,
//        c,
//        newFont);
//    createRow(
//        linesCleared,
//        comboCount,
//        gridyArray[3],
//        Integer.toString((int) runTetris.getRecordKeeping().getLinesCleared()),
//        Integer.toString((int) runTetris.getRecordKeeping().getComboCount()),
//        layout,
//        c,
//        newFont);
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
