package com.tetrisrevision.gui;

import com.tetrisrevision.RunTetris;

import javax.swing.*;
import java.awt.*;

public class MainTetris extends JFrame {
  JPanel panelMain;
  GridBagConstraints gbcMain = new GridBagConstraints();
  GridBagLayout layout = new GridBagLayout();

  public MainTetris(RunTetris run) {
    panelMain = new PanelMain(run);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(Color.black);
    setLayout(layout);

    gbcMain.weightx = 1.0;
    gbcMain.weighty = 1.0;
    gbcMain.fill = GridBagConstraints.BOTH;
    gbcMain.gridwidth = 6;
    gbcMain.gridheight = 11;

    add(panelMain, gbcMain);
    setSize(new Dimension(600, 400));
  }
}
