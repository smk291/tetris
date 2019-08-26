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

    insertPanel(
            1.0,
            1.0,
            0,
            0,
            1,
            11,
            GridBagConstraints.BOTH,
            new JLabel("Leftmost"),
            BorderFactory.createLineBorder(new Color(171, 181, 171)),
            Color.darkGray
    );

//    leftMost.setBackground(new Color(121, 141, 121));
//    leftMost.setBorder(BorderFactory.createLineBorder(new Color(171, 181, 171)));
//    leftMostLabel.setForeground(Color.white);
//    leftMost.add(leftMostLabel);
//
//    leftmostC.weightx = 1.0;
//    leftmostC.weighty = 1.0;
//    leftmostC.gridx = 0;
//    leftmostC.gridy = 0;
//    leftmostC.gridwidth = 1;
//    leftmostC.gridheight = 11;
//    leftmostC.fill = GridBagConstraints.BOTH;
//
//    add(leftMost, leftmostC);

      insertPanel(
              1.0,
              1.0,
              1,
              0,
              5,
              1,
              GridBagConstraints.BOTH,
              new JLabel("topmost"),
              BorderFactory.createLineBorder(new Color(150,0,0)),
              Color.darkGray
      );

//
//    topmost.setBackground(new Color(100,100,100));
//    topmost.setBorder(BorderFactory.createLineBorder(new Color (150, 0, 0)));
//    topmostLabel.setForeground(Color.white);
//    topmost.add(topmostLabel);
//
//    topmostC.weightx = 1.0;
//    topmostC.weighty = 1.0;
//    topmostC.gridx = 1;
//    topmostC.gridy = 0;
//    topmostC.gridwidth = 5;
//    topmostC.gridheight = 1;
//    topmostC.fill = GridBagConstraints.BOTH;
//
//    add(topmost, topmostC);
//

    insertPanel(
            1.0,
            1.0,
            1,
            1,
            2,
            2,
            GridBagConstraints.BOTH,
            new JLabel("holdPiece"),
            BorderFactory.createLineBorder(new Color(0,150,0)),
            Color.darkGray
    );
//    holdPiece.setBackground(new Color(101, 77, 77));
//    holdPiece.setBorder(BorderFactory.createLineBorder(new Color(0, 150, 0)));
//    holdPieceLabel.setForeground(Color.white);
//    holdPiece.add(holdPieceLabel);
//
//    holdPieceC.weightx = 1.0;
//    holdPieceC.weighty = 1.0;
//    holdPieceC.gridx = 1;
//    holdPieceC.gridy = 1;
//    holdPieceC.gridwidth = 2;
//    holdPieceC.gridheight = 2;
//    holdPieceC.fill = GridBagConstraints.BOTH;
//
//    add(holdPiece, holdPieceC);

    subHoldPieceLeft.setBackground(new Color(80, 0 ,0));
    subHoldPieceLeft.setBorder(BorderFactory.createLineBorder(new Color(0, 80, 0)));
    subHoldPieceLeftLabel.setForeground(Color.white);
    subHoldPieceLeft.add(subHoldPieceLeftLabel);

    subHoldPieceLeftC.weightx = 1.0;
    subHoldPieceLeftC.weighty = 1.0;
    subHoldPieceLeftC.gridx = 1;
    subHoldPieceLeftC.gridy = 3;
    subHoldPieceLeftC.gridwidth = 1;
    subHoldPieceLeftC.gridheight = 7;
    subHoldPieceLeftC.fill = GridBagConstraints.BOTH;

    add(subHoldPieceLeft, subHoldPieceLeftC);

    subHoldPieceRight.setBackground(new Color(0, 0, 80));
    subHoldPieceRight.setBorder(BorderFactory.createLineBorder(new Color(0,255,0)));
    subHoldPieceRightLabel.setForeground(Color.white);
    subHoldPieceRight.add(subHoldPieceRightLabel);

    subHoldPieceRightC.weightx = 1.0;
    subHoldPieceRightC.weighty = 1.0;
    subHoldPieceRightC.gridx = 2;
    subHoldPieceRightC.gridy = 3;
    subHoldPieceRightC.gridwidth = 1;
    subHoldPieceRightC.gridheight = 7;
    subHoldPieceRightC.fill = GridBagConstraints.BOTH;

    add(subHoldPieceRight, subHoldPieceRightC);

    playField.setBackground(new Color(10,30,10));
    playField.setBorder(BorderFactory.createLineBorder(new Color(90, 70, 60)));
    playFieldLabel.setForeground(Color.white);
    playField.add(playFieldLabel);

    playFieldC.weightx = 1.0;
    playFieldC.weighty = 1.0;
    playFieldC.gridx = 3;
    playFieldC.gridy = 1;
    playFieldC.gridwidth = 1;
    playFieldC.gridheight = 9;
    playFieldC.fill = GridBagConstraints.BOTH;

    add(playField, playFieldC);

    queue.setBackground(new Color(220, 69,  11));
    queue.setBorder(BorderFactory.createLineBorder(new Color(0, 171, 171)));
    queueLabel.setForeground(Color.white);
    queue.add(queueLabel);

    queueC.weightx = 1.0;
    queueC.weighty = 1.0;
    queueC.gridx = 4;
    queueC.gridy = 2;
    queueC.gridwidth = 1;
    queueC.gridheight = 5;
    queueC.fill = GridBagConstraints.BOTH;

    add(queue, queueC);

    info.setBackground(new Color(100,100,100));
    info.setBorder(BorderFactory.createLineBorder(new Color(250, 180, 190)));
    infoLabel.setForeground(Color.white);
    info.add(infoLabel);

    infoC.weightx = 1.0;
    infoC.weighty = 1.0;
    infoC.gridx = 4;
    infoC.gridy = 6;
    infoC.gridwidth = 1;
    infoC.gridheight = 4;
    infoC.fill = GridBagConstraints.BOTH;

    add(info, infoC);

    bottommost.setBackground(new Color(70, 0, 70));
    bottommost.setBorder(BorderFactory.createLineBorder(new Color(180, 70, 220)));
    bottommostLabel.setForeground(Color.white);
    bottommost.add(bottommostLabel);

    bottommostC.weightx = 1.0;
    bottommostC.weighty = 1.0;
    bottommostC.gridx = 1;
    bottommostC.gridy = 10;
    bottommostC.gridwidth = 5;
    bottommostC.gridheight = 1;
    bottommostC.fill = GridBagConstraints.BOTH;

    add(bottommost, bottommostC);

    rightmost.setBackground(new Color(0, 90, 0));
    rightmost.setBorder(BorderFactory.createLineBorder(Color.white, 2));
    rightmostLabel.setForeground(Color.white);
    rightmost.add(rightmostLabel);

    rightmostC.weightx = 1.0;
    rightmostC.weighty = 1.0;
    rightmostC.gridx = 5;
    rightmostC.gridy = 0;
    rightmostC.gridwidth = 2;
    rightmostC.gridheight = 11;
    rightmostC.fill = GridBagConstraints.BOTH;

    add(rightmost, rightmostC);

  }
}