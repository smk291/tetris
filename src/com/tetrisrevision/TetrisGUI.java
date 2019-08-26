package com.tetrisrevision;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class TetrisGUI {
  boolean shift = false;
  private Timer timer;
  private Timer timer2;
//  private JFrame frame = new JFrame("Tetris");
  private RunTetris runTetris;
  PlayFieldGUI bc = new PlayFieldGUI(runTetris);
  JFrame tetrisFrame = new MainTetris();

  TetrisGUI(RunTetris runTetris) {
    this.runTetris = runTetris;
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  private void createAndShowGUI() {
//    layout.rowHeights = new int[] {70, 70, 70, 590};
//      JFrame tetrisFrame = new JPanelMain();


//    JPanel gameData =
//        new GameDataRow(runTetris, new int[] {datagridy0, datagridy1, datagridy2, datagridy3});
//    frame.getContentPane().add(gameData, c);

//    Border borderTest = BorderFactory.createLineBorder(Color.red, 1);
//    JPanel test = new JPanel();
//    test.setBackground(Color.black);
//    test.setBorder(borderTest);
//    JLabel testLabel = new JLabel("Test label");
//    testLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
//    testLabel.setForeground(Color.white);
//    testLabel.setBorder(borderTest);
//    GridBagLayout gb = new GridBagLayout();
//    GridBagConstraints gbc = new GridBagConstraints();
//    gbc.weightx = 1.0;
//    gbc.weighty = 1.0;
//    gbc.gridx = 0;
//    gbc.gridy = 0;
//    c.fill = GridBagConstraints.BOTH;
//    test.setLayout(gb);
//    test.setMinimumSize(new Dimension(frame.getSize().width, 70));
//    test.add(testLabel, gbc);

//    c.fill = GridBagConstraints.BOTH;

//    bc = new PlayFieldGUI(runTetris);
//    GridBagLayout layOut = new GridBagLayout();
//    frame.getContentPane().setLayout(layOut);
//    c.fill = GridBagConstraints.BOTH;
//
    //    c.insets = new Insets(0, 20, 0, 20);
//    c.weightx = 1.0;
//    c.weighty = 1.0;
//    c.gridy = playfieldgridy;
//    JPanel jp = new JPanel(new BorderLayout());
//
//    jp.add(bc, BorderLayout.CENTER);
//
//    frame.getContentPane().add(jp, c);

    tetrisFrame.pack();
    tetrisFrame.setSize(new Dimension(600, 400));
    tetrisFrame.setVisible(true);
    tetrisFrame.setFocusable(true);
  }

  void init() {
    try {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
    } catch (UnsupportedLookAndFeelException
        | IllegalAccessException
        | InstantiationException
        | ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    UIManager.put("swing.boldMetal", Boolean.FALSE);
    SwingUtilities.invokeLater(this::createAndShowGUI);

    tetrisFrame.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {}

          @Override
          public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = true;
            else runTetris.keyboardInput(e, shift);
          }

          @Override
          public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) shift = false;
          }
        });

    timer = new Timer(10000, e -> runTetris.dropCurrentPiece());
    timer.start();

    timer2 = new Timer(200, e -> runTetris.dropSinkingPieces());
    timer2.start();
  }

  void setDropTimerDelay(int ms) {
    timer.setDelay(ms);
  }

  void endGame() {
    timer.stop();
    timer2.stop();
  }

  PlayFieldGUI getBoardCompositor() {
    return bc;
  }
}

class MainTetris extends JFrame {
  JPanel panelMain = new PanelMain();
  GridBagConstraints gbcMain = new GridBagConstraints();
  GridBagLayout layout = new GridBagLayout();

  MainTetris() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(Color.black);
    setLayout(layout);

    gbcMain.weightx = 1.0;
    gbcMain.weighty = 1.0;
    gbcMain.fill = GridBagConstraints.BOTH;
    gbcMain.gridwidth = 7;
    gbcMain.gridheight = 11;

    add(panelMain, gbcMain);
    setSize(new Dimension(600, 400));
  }
}

class PanelMain extends JPanel {
  Border borderMain = BorderFactory.createLineBorder(new Color(0, 145, 155));
  GridBagLayout gblMain = new GridBagLayout();

  JPanel leftMost = new JPanel();
  GridBagConstraints leftmostC = new GridBagConstraints();

  JPanel uppermost = new JPanel();
  GridBagConstraints uppermostC = new GridBagConstraints();

  JPanel remainder = new JPanel();
  GridBagConstraints remainderC = new GridBagConstraints();

  JPanel holdPiece = new JPanel();
  GridBagConstraints holdPieceC = new GridBagConstraints();

  JPanel subHoldPieceLeft = new JPanel();
  GridBagConstraints subHoldPieceLeftC = new GridBagConstraints();

  JPanel subHoldPieceRight = new JPanel();
  GridBagConstraints subHoldPieceRightC = new GridBagConstraints();

  JPanel bottommost = new JPanel();
  GridBagConstraints bottommostC = new GridBagConstraints();

  PanelMain() {
    setLayout(gblMain);
    setBorder(borderMain);
    setBackground(new Color(20, 20, 80));
    System.out.println("Size is " + this.getHeight() + "," + this.getWidth());

    leftMost.setBackground(new Color(121, 141, 121));
    leftMost.setBorder(BorderFactory.createLineBorder(new Color(171, 181, 171)));

    leftmostC.weightx = 1.0;
    leftmostC.weighty = 1.0;
    leftmostC.gridx = 0;
    leftmostC.gridy = 0;
    leftmostC.gridwidth = 1;
    leftmostC.gridheight = 11;
    leftmostC.fill = GridBagConstraints.BOTH;

    add(leftMost, leftmostC);

    uppermost.setBackground(new Color(100,100,100));
    uppermost.setBorder(BorderFactory.createLineBorder(new Color (150, 0, 0)));

    uppermostC.weightx = 1.0;
    uppermostC.weighty = 1.0;
    uppermostC.gridx = 1;
    uppermostC.gridy = 0;
    uppermostC.gridwidth = 6;
    uppermostC.gridheight = 1;
    uppermostC.fill = GridBagConstraints.BOTH;

    add(uppermost, uppermostC);

    holdPiece.setBackground(new Color(101, 77, 77));
    holdPiece.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0)));

    holdPieceC.weightx = 1.0;
    holdPieceC.weighty = 1.0;
    holdPieceC.gridx = 1;
    holdPieceC.gridy = 1;
    holdPieceC.gridwidth = 2;
    holdPieceC.gridheight = 2;
    holdPieceC.fill = GridBagConstraints.BOTH;

    add(holdPiece, holdPieceC);

    subHoldPieceLeft.setBackground(new Color(80, 0 ,0));
    subHoldPieceLeft.setBorder(BorderFactory.createLineBorder(new Color(0, 80, 0)));

    subHoldPieceLeftC.weightx = 1.0;
    subHoldPieceLeftC.weighty = 1.0;
    subHoldPieceLeftC.gridx = 1;
    subHoldPieceLeftC.gridy = 3;
    subHoldPieceLeftC.gridwidth = 1;
    subHoldPieceLeftC.gridheight = 8;
    subHoldPieceLeftC.fill = GridBagConstraints.BOTH;

    add(subHoldPieceLeft, subHoldPieceLeftC);

    subHoldPieceRight.setBackground(new Color(0, 0, 80));
    subHoldPieceRight.setBorder(BorderFactory.createLineBorder(new Color(0,255,0)));

    subHoldPieceRightC.weightx = 1.0;
    subHoldPieceRightC.weighty = 1.0;
    subHoldPieceRightC.gridx = 2;
    subHoldPieceRightC.gridy = 3;
    subHoldPieceRightC.gridwidth = 1;
    subHoldPieceRightC.gridheight = 8;
    subHoldPieceRightC.fill = GridBagConstraints.BOTH;

    add(subHoldPieceRight, subHoldPieceRightC);

    bottommost.setBackground(new Color(70, 0, 70));
    bottommost.setBorder(BorderFactory.createLineBorder(new Color(180, 70, 220)));

    bottommostC.weightx = 1.0;
    bottommostC.weighty = 1.0;
    bottommostC.gridx = 1;
    bottommostC.gridy = 10;

    remainder.setBackground(new Color(0, 90, 0));

    remainderC.weightx = 1.0;
    remainderC.weighty = 1.0;
    remainderC.gridx = 3;
    remainderC.gridy = 3;
    remainderC.gridwidth = 4;
    remainderC.gridheight = 9;
    remainderC.fill = GridBagConstraints.BOTH;

    add(remainder, remainderC);
  }
}