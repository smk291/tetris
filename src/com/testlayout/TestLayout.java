package com.testlayout;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class TestLayout {
  private static JFrame frame = new JFrame("Tetris");

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  static private void createAndShowGUI() {
    GridBagLayout gbl = new GridBagLayout();
    frame.getContentPane().setBackground(Color.black);
    frame.getContentPane().setLayout(gbl);

    Border border0 = BorderFactory.createLineBorder(Color.RED, 1);

    GridBagLayout gbl0 = new GridBagLayout();
    JPanel panel0 = new JPanel();
    panel0.setBorder(border0);
    panel0.setBackground(Color.black);
    panel0.setLayout(gbl0);

    Border border1 = BorderFactory.createLineBorder(Color.green);
    GridBagLayout gbl1 = new GridBagLayout();
    GridBagConstraints gbc1 = new GridBagConstraints();
    JPanel panel1 = new JPanel();
    panel1.setLayout(gbl1);
    panel1.setBackground(Color.blue);
    panel1.setBorder(border1);
    gbc1.weightx = 1.0;
    gbc1.weighty = 0.0;
    gbc1.fill = GridBagConstraints.HORIZONTAL;

    System.out.println(panel1.getInsets().toString());

    Border border2 = BorderFactory.createLineBorder(Color.magenta);
    GridBagLayout gbl2 = new GridBagLayout();
    GridBagConstraints gbc2 = new GridBagConstraints();
    JPanel panel2 = new JPanel();
    panel2.setLayout(gbl2);
    panel2.setBackground(new Color(160, 231, 160));
    panel2.setBorder(border2);
    gbc2.gridy = 1;
    gbc2.weightx = 1.0;
    gbc2.weighty = 1.0;
    gbc2.gridwidth = 6;
    gbc2.fill = GridBagConstraints.BOTH;

    JLabel label1 = new JLabel("Test1", SwingConstants.LEFT);
    label1.setForeground(Color.white);
    label1.setFont(new Font("Monospaced", Font.BOLD, 15));
    label1.setBorder(border0);

    JLabel label2 = new JLabel("Test2", SwingConstants.RIGHT);
    label2.setForeground(Color.white);
    label2.setFont(new Font("Monospaced", Font.BOLD, 15));
    label2.setBorder(border0);

    GridBagLayout gbl3 = new GridBagLayout();
    GridBagConstraints gbc3 = new GridBagConstraints();
    JPanel panel3 = new JPanel();
    panel3.setLayout(gbl3);
    panel3.setBackground(new Color(111, 181, 181));
    gbc3.gridx = 0;
    gbc3.gridy = 0;
    gbc3.weightx = 0.0;
//    gbc3.fill = GridBagConstraints.HORIZONTAL;

    GridBagLayout gbl4 = new GridBagLayout();
    GridBagConstraints gbc4 = new GridBagConstraints();
    JPanel panel4 = new JPanel();
    panel4.setLayout(gbl4);
    panel4.setBackground(new Color(111, 181, 181));
//    gbc4.gridx = 1;
    gbc4.gridx = 2;
    gbc4.gridy = 0;
    gbc4.weightx = 0.0;
//    gbc4.fill = GridBagConstraints.HORIZONTAL;

    GridBagLayout gbl5 = new GridBagLayout();
    GridBagConstraints gbc5 = new GridBagConstraints();
    JPanel panel5 = new JPanel();
    panel5.setLayout(gbl5);
    panel5.setBackground(new Color( 82, 144, 173));
    gbc5.gridx = 1;
    gbc5.gridy = 0;
    gbc5.weightx = 1.0;
    gbc5.fill = GridBagConstraints.BOTH;
    
    panel3.add(label1);
    panel4.add(label2);

    panel1.add(panel3, gbc3);
    panel1.add(panel5, gbc5);
    panel1.add(panel4, gbc4);

    panel0.add(panel1, gbc1);
    panel0.add(panel2, gbc2);

    System.out.println(frame.getSize().width + "," + frame.getSize().height);

    frame.setContentPane(panel0);

    frame.pack();
    frame.setSize(new Dimension(400, 800));
    frame.setVisible(true);
    frame.setFocusable(true);
  }

  static void init() {
   SwingUtilities.invokeLater(TestLayout::createAndShowGUI);
  }

  public static void main(String[] args) {
    init();
  }
}


