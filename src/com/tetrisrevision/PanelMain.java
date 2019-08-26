package com.tetrisrevision;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PanelMain extends JPanel {
  Border borderMain = BorderFactory.createLineBorder(new Color(0, 145, 155));
  GridBagLayout gblMain = new GridBagLayout();

//  JPanel leftMost = new JPanel();
//  GridBagConstraints leftmostC = new GridBagConstraints();
//  JLabel leftMostLabel = new JLabel("leftMost");

  JPanel topmost = new JPanel();
  GridBagConstraints topmostC = new GridBagConstraints();
  JLabel topmostLabel = new JLabel("uppermost");

  JPanel rightmost = new JPanel();
  GridBagConstraints rightmostC = new GridBagConstraints();
  JLabel rightmostLabel = new JLabel("rightmost");

  JPanel holdPiece = new JPanel();
  GridBagConstraints holdPieceC = new GridBagConstraints();
  JLabel holdPieceLabel = new JLabel("holdPiece");

  JPanel subHoldPieceLeft = new JPanel();
  GridBagConstraints subHoldPieceLeftC = new GridBagConstraints();
  JLabel subHoldPieceLeftLabel = new JLabel("subHoldPieceLeft");

  JPanel subHoldPieceRight = new JPanel();
  GridBagConstraints subHoldPieceRightC = new GridBagConstraints();
  JLabel subHoldPieceRightLabel = new JLabel("subHoldPieceRight");

  JPanel bottommost = new JPanel();
  GridBagConstraints bottommostC = new GridBagConstraints();
  JLabel bottommostLabel = new JLabel("bottommost");

  JPanel playField = new JPanel();
  GridBagConstraints playFieldC = new GridBagConstraints();
  JLabel playFieldLabel = new JLabel("playField");

  JPanel queue = new JPanel();
  GridBagConstraints queueC = new GridBagConstraints();
  JLabel queueLabel = new JLabel("Queue");

  JPanel info = new JPanel();
  GridBagConstraints infoC = new GridBagConstraints();
  JLabel infoLabel = new JLabel("info");

  void insertPanel(double weightx, double weighty, int gridx, int gridy, int gridwidth, int gridheight, int fill, JLabel label, Border b, Color bkgnd) {
    JPanel panel = new JPanel();
    panel.setBackground(bkgnd);
    panel.setBorder(b);
    GridBagConstraints gbc = new GridBagConstraints();
    label.setForeground(Color.white);
    panel.add(label);

    gbc.weightx = weightx;
    gbc.weighty = weighty;
    gbc.gridx = gridx;
    gbc.gridy = gridy;
    gbc.gridwidth = gridwidth;
    gbc.gridheight = gridheight;
    gbc.fill = fill;

    add(panel, gbc);
  }

  PanelMain() {
    setLayout(gblMain);
    setBorder(borderMain);
    setBackground(new Color(20, 20, 80));
    setForeground(Color.white);
    System.out.println("Size is " + this.getHeight() + "," + this.getWidth());

    insertPanel(4.2, 1.0, 0, 0, 1, 11, GridBagConstraints.BOTH,
            new JLabel("L"), BorderFactory.createLineBorder(new Color(171, 181, 171)),
            Color.darkGray
    );

    insertPanel(4.2, 0, 1, 0, 4, 1, GridBagConstraints.HORIZONTAL,
            new JLabel("T"), BorderFactory.createLineBorder(new Color(150,0,0)),
            Color.darkGray
    );

    insertPanel(.01, 0, 1, 1, 2, 2, GridBagConstraints.BOTH,
            new JLabel("holdPiece"), BorderFactory.createLineBorder(new Color(0,150,0)),
            Color.darkGray
    );


    insertPanel(0.025, 1.0, 1, 3, 1, 7, GridBagConstraints.BOTH,
            new JLabel("l"), BorderFactory.createLineBorder(new Color(0,80,0)),
            Color.darkGray
    );

    insertPanel( 0.025, 1.0, 2, 3, 1, 7, GridBagConstraints.BOTH,
            new JLabel("r"), BorderFactory.createLineBorder(new Color(0,255,0)),
            Color.darkGray
    );

    insertPanel(3.8, 1.0, 3, 1, 1, 9, GridBagConstraints.BOTH,
            new JLabel("playfield"), BorderFactory.createLineBorder(new Color(90,70,60)),
            Color.darkGray
    );

    insertPanel(0.05,0,4,2,1,5, GridBagConstraints.BOTH,
            new JLabel("queue"), BorderFactory.createLineBorder(new Color(0, 171, 171)),
            Color.darkGray
    );

    insertPanel(0.05, 0.4, 4, 6, 1, 4, GridBagConstraints.BOTH,
            new JLabel("info"), BorderFactory.createLineBorder(new Color(250,180,190)),
            Color.darkGray
    );

    insertPanel(4.2, 0, 1, 10, 4, 1, GridBagConstraints.HORIZONTAL,
            new JLabel("B"), BorderFactory.createLineBorder(new Color(180, 70, 220)),
            Color.darkGray
    );

    insertPanel(4.2, 1.0, 5, 0, 1, 11, GridBagConstraints.BOTH,
            new JLabel("R"), BorderFactory.createLineBorder(Color.white), Color.darkGray
    );
  }
}
