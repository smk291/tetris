package com.tetrisrevision;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class TetrisGUI {
  boolean shift = false;
  int gridy = 0;
  int datagridy0 = gridy++;
  int datagridy1 = gridy++;
  int datagridy2 = gridy++;
  int datagridy3 = gridy++;
  int playfieldgridy = gridy++;
  private Timer timer;
  private Timer timer2;
  private JFrame frame = new JFrame("Tetris");
  private boolean RIGHT_TO_LEFT = false;
  private RunTetris runTetris;
  PlayFieldGUI bc = new PlayFieldGUI(runTetris);

  TetrisGUI(RunTetris runTetris) {
    this.runTetris = runTetris;
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be invoked from the event
   * dispatch thread.
   */
  private void createAndShowGUI() {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBackground(Color.black);

    GridBagLayout layout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    c.weightx = 1.0;
    layout.rowHeights = new int[] {70, 70, 70, 590};
//    JPanel gameData =
//        new GameDataRow(runTetris, new int[] {datagridy0, datagridy1, datagridy2, datagridy3});
    frame.setLayout(layout);
//    frame.getContentPane().add(gameData, c);

    Border borderTest = BorderFactory.createLineBorder(Color.red, 1);
    JPanel test = new JPanel();
    test.setBackground(Color.black);
    test.setBorder(borderTest);
    JLabel testLabel = new JLabel("Test label");
    testLabel.setFont(new Font("Monospaced", Font.BOLD, 12));
    testLabel.setForeground(Color.white);
    testLabel.setBorder(borderTest);
    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 0;
    c.fill = GridBagConstraints.BOTH;
    test.setLayout(gb);
    test.setMinimumSize(new Dimension(frame.getSize().width, 70));
    test.add(testLabel, gbc);

    c.fill = GridBagConstraints.BOTH;


    frame.getContentPane().add(test);

    bc = new PlayFieldGUI(runTetris);
    GridBagLayout layOut = new GridBagLayout();
    frame.getContentPane().setLayout(layOut);
    c.fill = GridBagConstraints.BOTH;

    //    c.insets = new Insets(0, 20, 0, 20);
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.gridy = playfieldgridy;
    JPanel jp = new JPanel(new BorderLayout());

    jp.add(bc, BorderLayout.CENTER);

    frame.getContentPane().add(jp, c);

    frame.pack();
    frame.setSize(new Dimension(400, 800));
    frame.setVisible(true);
    frame.setFocusable(true);
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

    frame.addKeyListener(
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

