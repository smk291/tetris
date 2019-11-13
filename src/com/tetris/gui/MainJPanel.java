package com.tetris.gui;

import com.tetris.game.RunTetris;
import com.tetris.gui.holdpiece.HoldPiece;
import com.tetris.gui.blockqueue.Queue;
import com.tetris.gui.playfield.PlayField;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainJPanel extends JPanel {
  Border borderMain = BorderFactory.createLineBorder(new Color(0, 145, 155));
  GridBagLayout gblMain = new GridBagLayout();

  JPanel leftMost = new JPanel();
  GridBagConstraints leftmostC = new GridBagConstraints();

  JPanel topmost = new JPanel();
  GridBagConstraints topmostC = new GridBagConstraints();

  JPanel rightmost = new JPanel();
  GridBagConstraints rightmostC = new GridBagConstraints();

  JPanel holdBlock;
  GridBagConstraints holdBlockC = new GridBagConstraints();

  JPanel subHoldPieceLeft = new JPanel();
  GridBagConstraints subHoldPieceLeftC = new GridBagConstraints();

  JPanel subHoldPieceRight = new JPanel();
  GridBagConstraints subHoldPieceRightC = new GridBagConstraints();

  JPanel bottommost = new JPanel();
  GridBagConstraints bottommostC = new GridBagConstraints();

  JPanel playFieldContainer = new JPanel();
  JPanel playField;
  GridBagConstraints playFieldContainerC = new GridBagConstraints();
  GridBagConstraints playFieldC = new GridBagConstraints();
  //  JPanel queue = new JPanel();
  JPanel queue;
  GridBagConstraints queueC = new GridBagConstraints();

  JPanel info = new JPanel();
  GridBagConstraints infoC = new GridBagConstraints();

  public MainJPanel(RunTetris runTetris) {
    GridBagLayout gblPlayfield = new GridBagLayout();
    playFieldContainer.setLayout(gblPlayfield);
    playFieldContainerC.gridwidth = 3;
    playFieldContainerC.gridheight = 3;

    playField = new PlayField(runTetris);
    playFieldC.gridx = 0;
    playFieldC.gridy = 1;
    playFieldC.gridwidth = 3;
    playFieldC.gridheight = 1;
    playFieldC.weightx = 1.0;
    playFieldC.weighty = 1.0;
    playFieldC.fill = GridBagConstraints.BOTH;

    JPanel playfieldLower = new JPanel();
    GridBagConstraints playFieldLowerC = new GridBagConstraints();
    playFieldLowerC.gridx = 0;
    playFieldLowerC.gridy = 2;
    playFieldLowerC.gridwidth = 3;
    playFieldLowerC.gridheight = 1;
    playFieldLowerC.weighty = 0.0;
    playFieldLowerC.weightx = 1.0;

    JPanel playFieldUpper = new JPanel();
    GridBagConstraints playFieldUpperC = new GridBagConstraints();
    playFieldUpperC.gridx = 0;
    playFieldUpperC.gridy = 0;
    playFieldUpperC.gridwidth = 3;
    playFieldUpperC.gridheight = 1;
    playFieldUpperC.weighty = 0.0;
    playFieldUpperC.weightx = 1.0;

//        JPanel playFieldLeft = new JPanel();
//        GridBagConstraints playFieldLeftC = new GridBagConstraints();
//        playFieldLeftC.gridx = 0;
//        playFieldLeftC.gridy = 1;
//        playFieldLeftC.gridwidth = 1;
//        playFieldLeftC.gridheight = 1;
//        playFieldLeftC.weighty = 1.0;
//        playFieldLeftC.weightx = 0.0;
////
//        JPanel playFieldRight = new JPanel();
//        GridBagConstraints playFieldRightC = new GridBagConstraints();
//        playFieldRightC.gridx = 2;
//        playFieldRightC.gridy = 1;
//        playFieldRightC.gridwidth = 1;
//        playFieldRightC.gridheight = 1;
//        playFieldRightC.weighty = 1.0;
//        playFieldRightC.weightx = 0.0;

    playFieldContainer.add(playFieldUpper, playFieldUpperC);
//        playFieldContainer.add(playFieldLeft, playFieldLeftC);
    playFieldContainer.add(playField, playFieldC);
//        playFieldContainer.add(playFieldRight, playFieldRightC);
    playFieldContainer.add(playfieldLower, playFieldLowerC);

    if (runTetris != null) queue = new Queue(runTetris, runTetris.getTetrominoQueue());

    setLayout(gblMain);
    setBorder(borderMain);
    setBackground(new Color(20, 20, 80));
    setForeground(Color.white);

    //    System.out.println(playFieldContainer.getComponent().)

    holdBlock = new HoldPiece(runTetris);

    init();
  }

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

  void init() {
    insertPanel(leftMost, 4, 1.0, 0, 0, 1, 13, GridBagConstraints.BOTH, new JLabel("L"), leftmostC);

    insertPanel(
        topmost, 4.5, 0, 1, 0, 4, 1, GridBagConstraints.HORIZONTAL, new JLabel("T"), topmostC);

    insertPanel(
        holdBlock,
        .50,
        .05,
        1,
        1,
        2,
        2,
        GridBagConstraints.BOTH,
        new JLabel("holdBlock"),
        holdBlockC);

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
        playFieldContainer,
        3.8,
        1.0,
        3,
        1,
        1,
        9,
        GridBagConstraints.BOTH,
        new JLabel("playfield"),
        playFieldContainerC);

    insertPanel(queue, 1.0, 1.0, 4, 2, 1, 5, GridBagConstraints.BOTH, new JLabel("queue"), queueC);

    insertPanel(info, 0.05, 0.4, 4, 6, 1, 4, GridBagConstraints.BOTH, new JLabel("info"), infoC);

    insertPanel(
        bottommost,
        4.5,
        0,
        1,
        10,
        4,
        1,
        GridBagConstraints.HORIZONTAL,
        new JLabel("B"),
        bottommostC);

    insertPanel(
        rightmost, 4, 1.0, 5, 0, 1, 11, GridBagConstraints.BOTH, new JLabel("R"), rightmostC);
  }
}
