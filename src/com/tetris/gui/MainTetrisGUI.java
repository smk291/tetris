package com.tetris.gui;

import com.tetris.RunTetris;
import com.tetris.gui.constants.GUIConstants;

import javax.swing.*;
import java.awt.*;

class MainTetrisGUI extends JFrame {
  private JPanel panelMain;
  private GridBagConstraints gbcMain = new GridBagConstraints();
  private GridBagLayout layout = new GridBagLayout();

  MainTetrisGUI(RunTetris run) {
    panelMain = new PanelMain(run);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(GUIConstants.frameBackGround);
    setLayout(layout);

    gbcMain.weightx = 1.0;
    gbcMain.weighty = 1.0;
    gbcMain.fill = GridBagConstraints.BOTH;
    gbcMain.gridwidth = 6;
    gbcMain.gridheight = 11;

    add(panelMain, gbcMain);
  }
}
