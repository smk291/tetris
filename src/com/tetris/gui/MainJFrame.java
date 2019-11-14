package com.tetris.gui;

import com.tetris.game.RunTetris;
import com.tetris.gui.constants.GUIConstants;

import javax.swing.*;
import java.awt.*;

class MainJFrame extends JFrame {
  MainJFrame(RunTetris run) {
    JPanel panelMain = new MainJPanel(run);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(GUIConstants.frameBackGround);
    GridBagLayout layout = new GridBagLayout();
    setLayout(layout);

    GridBagConstraints gbcMain = new GridBagConstraints();
    gbcMain.weightx = 1.0;
    gbcMain.weighty = 1.0;
    gbcMain.fill = GridBagConstraints.BOTH;
    gbcMain.gridwidth = 6;
    gbcMain.gridheight = 11;

    add(panelMain, gbcMain);
  }
}
