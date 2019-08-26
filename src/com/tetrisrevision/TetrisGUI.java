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
    gbcMain.gridwidth = 6;
    gbcMain.gridheight = 11;
//    layout.rowWeights = new double[] {1, 0, 0, 1, 0, 1};
//    layout.columnWeights = new double[] {.33, .2, .2, .33, .2, .33};

    add(panelMain, gbcMain);
    setSize(new Dimension(600, 400));
  }
}

