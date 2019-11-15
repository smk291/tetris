package com.tetris.gui;

import com.tetris.game.RunTetris;
import com.tetris.gui.displaydata.GameData;
import com.tetris.gui.holdpiece.HoldPiece;
import com.tetris.gui.blockqueue.Queue;
import com.tetris.gui.playfield.Playfield;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class MainJPanel extends JPanel {
  private Border borderMain = BorderFactory.createLineBorder(new Color(0, 145, 155));
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

  JPanel playfieldContainer = new JPanel();
  JPanel playfield;
  GridBagConstraints playfieldContainerC = new GridBagConstraints();
  GridBagConstraints playfieldC = new GridBagConstraints();
  //  JPanel queue = new JPanel();
  JPanel queue;
  GridBagConstraints queueC = new GridBagConstraints();

  JPanel info = new JPanel();
  GridBagConstraints infoC = new GridBagConstraints();
  RunTetris rt;

  public MainJPanel(RunTetris runTetris) {
    rt = runTetris;
    GridBagLayout gblPlayfield = new GridBagLayout();
    playfieldContainer.setLayout(gblPlayfield);
    playfieldContainerC.gridwidth = 3;
    playfieldContainerC.gridheight = 3;

    playfield = new Playfield(runTetris);
    playfieldC.gridx = 0;
    playfieldC.gridy = 1;
    playfieldC.gridwidth = 3;
    playfieldC.gridheight = 1;
    playfieldC.weightx = 1.0;
    playfieldC.weighty = 1.0;
    playfieldC.fill = GridBagConstraints.BOTH;

    JPanel playfieldLower = new JPanel();
    GridBagConstraints playfieldLowerC = new GridBagConstraints();
    playfieldLowerC.gridx = 0;
    playfieldLowerC.gridy = 2;
    playfieldLowerC.gridwidth = 3;
    playfieldLowerC.gridheight = 1;
    playfieldLowerC.weighty = 0.0;
    playfieldLowerC.weightx = 1.0;

    JPanel playfieldUpper = new JPanel();
    GridBagConstraints playfieldUpperC = new GridBagConstraints();
    playfieldUpperC.gridx = 0;
    playfieldUpperC.gridy = 0;
    playfieldUpperC.gridwidth = 3;
    playfieldUpperC.gridheight = 1;
    playfieldUpperC.weighty = 0.0;
    playfieldUpperC.weightx = 1.0;

        JPanel playfieldLeft = new JPanel();
        GridBagConstraints playfieldLeftC = new GridBagConstraints();
        playfieldLeftC.gridx = 0;
        playfieldLeftC.gridy = 1;
        playfieldLeftC.gridwidth = 1;
        playfieldLeftC.gridheight = 1;
        playfieldLeftC.weighty = 1.0;
        playfieldLeftC.weightx = 0.0;
//
        JPanel playfieldRight = new JPanel();
        GridBagConstraints playfieldRightC = new GridBagConstraints();
        playfieldRightC.gridx = 2;
        playfieldRightC.gridy = 1;
        playfieldRightC.gridwidth = 1;
        playfieldRightC.gridheight = 1;
        playfieldRightC.weighty = 1.0;
        playfieldRightC.weightx = 0.0;

    playfieldContainer.add(playfieldUpper, playfieldUpperC);
//        playfieldContainer.add(playfieldLeft, playfieldLeftC);
    playfieldContainer.add(playfield, playfieldC);
//        playfieldContainer.add(playfieldRight, playfieldRightC);
    playfieldContainer.add(playfieldLower, playfieldLowerC);

    if (runTetris != null) queue = new Queue(runTetris, runTetris.getTetrominoQueue());

    setLayout(gblMain);
    setBorder(borderMain);
    setBackground(new Color(20, 20, 80));
    setForeground(Color.white);

    //    System.out.println(playfieldContainer.getComponent().)
//    topmost.setLayout(new BorderLayout());
//    topmost.add(new GameData(runTetris), playfieldC);
    holdBlock = new HoldPiece(runTetris);

    init();
  }

  private void insertPanel(
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
//        panel.add(label);

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

    topmost.setForeground(Color.white);
    topmost.add(new Score(rt));
    topmost.add(new Level(rt));
    topmost.add(new Lines(rt));
    topmost.add(new Combo(rt));

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
        playfieldContainer,
        3.8,
        1.0,
        3,
        1,
        1,
        9,
        GridBagConstraints.BOTH,
        new JLabel("playfield"),
        playfieldContainerC);

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

abstract class Data extends JLabel {
  RunTetris rt;

  Data(RunTetris rt) {
    this.rt = rt;
  }

  abstract void createLabel();

  void create(String s) {
    setForeground(Color.white);
    if (rt != null)
      setText(s);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    if (rt != null)
      createLabel();
  }

  @Override
  public void repaint() {
    super.repaint();

    if (rt != null)
      createLabel();
  }
}

class Level extends Data {
  void createLabel() {
     create("Level: " + String.format("%.0f", rt.getRecordKeeping().getLevel()));
  }

  Level(RunTetris rt) {
    super(rt);
  }
}

class Score extends Data {
  Score(RunTetris rt) {
    super(rt);
  }

  void createLabel() {
      create("Score: " + String.format("%.0f", rt.getRecordKeeping().getScore()));
  }
}

class Lines extends Data {
  Lines(RunTetris rt) {
    super(rt);
  }

  @Override
  void createLabel() {
    create("Lines: " + String.format("%.0f", rt.getRecordKeeping().getLinesCleared()));
  }
}

class Combo extends Data {
  Combo(RunTetris rt) {
    super(rt);
  }

  @Override
  void createLabel() {
    create("Combo : " + String.format("%.0f", rt.getRecordKeeping().getComboCount()));
  }
}