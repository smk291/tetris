package com.tetrisrevision;

import com.tetrisrevision.tetrominos.TetrominoEnum;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

class TetrisGUI {
  private Timer timer;
  private Timer timer2;
  private JFrame frame = new JFrame("Tetris");
  private boolean RIGHT_TO_LEFT = false;
  private RunTetris runTetris;
  PlayFieldGUI bc = new PlayFieldGUI(runTetris);
  boolean shift = false;
  int gridy = 0;
  int datagridy0 = gridy++;
  int datagridy1 = gridy++;
  int datagridy2 = gridy++;
  int datagridy3 = gridy++;
  int playfieldgridy = gridy++;

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
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0;

    layout.rowHeights = new int[]{70, 70, 70, 590};
    JPanel gameData = new GameDataRow(runTetris, new int[] {datagridy0, datagridy1, datagridy2, datagridy3});
    frame.setLayout(layout);
    frame.getContentPane().add(gameData, c);


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

    timer = new Timer(1000, e -> runTetris.dropCurrentPiece());
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

class GameDataRow extends JPanel {
  RunTetris runTetris;
  int[] gridyArray;
  JLabel scoreLabel = new JLabel();
  JLabel levelLabel = new JLabel();
  JLabel score = new JLabel();
  JLabel level = new JLabel();
  JLabel linesClearedLabel = new JLabel();
  JLabel comboCountLabel = new JLabel();
  JLabel linesCleared = new JLabel();
  JLabel comboCount = new JLabel();

  GameDataRow(RunTetris runTetris, int[] gridyArray) {
    this.runTetris = runTetris;
    this.gridyArray = gridyArray;
  }

  void createRow(JLabel leftLabel, JLabel rightLabel, int gridy, String label1, String label2, GridBagLayout layOut, GridBagConstraints c, Font font) {
    c.fill = GridBagConstraints.BOTH;
    c.gridy = gridy;
    c.weightx = 0.50;
    c.gridwidth = 1;
    c.anchor = GridBagConstraints.WEST;
    leftLabel.setText(label1);
    leftLabel.setForeground(Color.WHITE);
    leftLabel.setFont(font);
    layOut.setConstraints(leftLabel, c);
    add(leftLabel);

    c.anchor = GridBagConstraints.EAST;
    rightLabel.setText(label2);
    rightLabel.setForeground(Color.WHITE);
    rightLabel.setFont(font);
    layOut.setConstraints(rightLabel, c);
    add(rightLabel);
  }

  void drawRows() {
    if (runTetris == null)
      return;

    setBackground(Color.BLACK);
    GridBagLayout layout = new GridBagLayout();
    setLayout(layout);
    GridBagConstraints c = new GridBagConstraints();

    c.insets = new Insets(0, 20, 0, 20);
    c.weightx = 1.0;
    c.weighty = 1.0;
    Font newFont = new Font("Monospaced", Font.BOLD, 12);

    createRow(
        scoreLabel,
        levelLabel,
        gridyArray[0],
        "Score:",
        "Level:",
        layout,
        c,
        newFont);
    createRow(score, level,
        gridyArray[1],
        Integer.toString((int) runTetris.getRecordKeeping().getScore()),
        Integer.toString((int) runTetris.getRecordKeeping().getLevel()),
        layout,
        c,
        newFont);
    createRow(
        linesClearedLabel,
        comboCountLabel,
        gridyArray[2],
        "Lines cleared:",
        "Combo count:",
        layout,
        c,
        newFont);
    createRow(
        linesCleared,
        comboCount,
        gridyArray[3],
        Integer.toString((int) runTetris.getRecordKeeping().getLinesCleared()),
        Integer.toString((int) runTetris.getRecordKeeping().getComboCount()) ,
        layout,
        c,
        newFont
    );
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    drawRows();
  }

  @Override
  public void repaint() {
    super.repaint();
    drawRows();
  }
}