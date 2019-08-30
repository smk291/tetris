package com.tetrisrevision.gui;

import com.tetrisrevision.RunTetris;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PanelMain extends JPanel {
  Border borderMain = BorderFactory.createLineBorder(new Color(0, 145, 155));
  GridBagLayout gblMain = new GridBagLayout();

  JPanel leftMost = new JPanel();
  GridBagConstraints leftmostC = new GridBagConstraints();

  JPanel topmost = new JPanel();
  GridBagConstraints topmostC = new GridBagConstraints();

  JPanel rightmost = new JPanel();
  GridBagConstraints rightmostC = new GridBagConstraints();

  JPanel holdPiece = new JPanel();
  GridBagConstraints holdPieceC = new GridBagConstraints();

  JPanel subHoldPieceLeft = new JPanel();
  GridBagConstraints subHoldPieceLeftC = new GridBagConstraints();

  JPanel subHoldPieceRight = new JPanel();
  GridBagConstraints subHoldPieceRightC = new GridBagConstraints();

  JPanel bottommost = new JPanel();
  GridBagConstraints bottommostC = new GridBagConstraints();

  JPanel playField;
  GridBagConstraints playFieldC = new GridBagConstraints();

  //  JPanel queue = new JPanel();
  JPanel queue;
  GridBagConstraints queueC = new GridBagConstraints();

  JPanel info = new JPanel();
  GridBagConstraints infoC = new GridBagConstraints();

  void insertPanel(
      JPanel panel,
      double weightx,
      double weighty,
      int gridx,
      int gridy,
      int gridwidth,
      int gridheight,
      int fill,
      JLabel label,
      GridBagConstraints gbc) {
    panel.setBackground(Color.darkGray);
    panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    label.setForeground(Color.white);
//    panel.add(label);

    gbc.weightx = weightx;
    gbc.weighty = weighty;
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    gbc.gridwidth = gridwidth;
    gbc.gridheight = gridheight;
    gbc.fill = fill;

    add(panel, gbc);
  }

  public PanelMain(RunTetris runTetris) {
    playField = new PlayFieldGUI(runTetris);

    System.out.println("runTetris" + (runTetris != null ? runTetris.toString() : " null"));
    System.out.println(
        "runTetris.getTetrominoQueue()"
            + (runTetris != null && runTetris.getTetrominoQueue() != null
                ? runTetris.getTetrominoQueue().toString()
                : " null"));

    if (runTetris != null) queue = new Queue(runTetris, runTetris.getTetrominoQueue());

    setLayout(gblMain);
    setBorder(borderMain);
    setBackground(new Color(20, 20, 80));
    setForeground(Color.white);

    init();
  }

  void init() {
    insertPanel(
        leftMost,
        4.2,
        1.0,
        0,
        0,
        1,
        11,
        GridBagConstraints.BOTH,
        new JLabel("L"),
        leftmostC);

    insertPanel(
        topmost,
        4.2,
        0,
        1,
        0,
        4,
        1,
        GridBagConstraints.HORIZONTAL,
        new JLabel("T"),
        topmostC);

    insertPanel(
        holdPiece,
        0.01,
        0,
        1,
        1,
        2,
        2,
        GridBagConstraints.BOTH,
        new JLabel("holdPiece"),
        holdPieceC);

    insertPanel(
        subHoldPieceLeft,
        0.025,
        1.0,
        1,
        3,
        1,
        7,
        GridBagConstraints.BOTH,
        new JLabel("l"),
        subHoldPieceLeftC);

    insertPanel(
        subHoldPieceRight,
        0.025,
        1.0,
        2,
        3,
        1,
        7,
        GridBagConstraints.BOTH,
        new JLabel("r"),
        subHoldPieceRightC);

    insertPanel(
        playField,
        3.8,
        1.0,
        3,
        1,
        1,
        9,
        GridBagConstraints.BOTH,
        new JLabel("playfield"),
        playFieldC);

    insertPanel(
        queue,
        1.0,
        1.0,
        4,
        2,
        1,
        5,
        GridBagConstraints.BOTH,
        new JLabel("queue"),
        queueC);

    insertPanel(
        info,
        0.05,
        0.4,
        4,
        6,
        1,
        4,
        GridBagConstraints.BOTH,
        new JLabel("info"),
        infoC);

    insertPanel(
        bottommost,
        4.2,
        0,
        1,
        10,
        4,
        1,
        GridBagConstraints.HORIZONTAL,
        new JLabel("B"),
        bottommostC);

    insertPanel(
        rightmost,
        4.2,
        1.0,
        5,
        0,
        1,
        11,
        GridBagConstraints.BOTH,
        new JLabel("R"),
        rightmostC);
  }
}
