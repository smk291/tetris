package com.tetrisrevision;

import javax.swing.*;
import java.awt.*;

class MainTetris extends JFrame {
  JPanel panelMain;
  GridBagConstraints gbcMain = new GridBagConstraints();
  GridBagLayout layout = new GridBagLayout();

  MainTetris(RunTetris run) {
    System.out.println("MainTetris runTetris " + (run != null ? run.toString() : "null"));
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
