package com.tetrisrevision;

public class Tetris {
  public static void main(String[] args) {
    Game g = new Game();

    g.play();
  }
}


//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.geom.AffineTransform;
//import java.awt.geom.Rectangle2D;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import javax.swing.*;
//import javax.swing.event.*;
//
//class Tetris extends JApplet /*implements ChangeListener*/ {
//  TetrisGui t;
//
//  private static Dimension windowSize = new Dimension(400, 700);
//
//  static Dimension getWindowSize() {
//    return windowSize;
//  }
//
//  @Override
//  public void init() {
//    UIManager.put("swing.boldMetal", Boolean.FALSE);
//  }
//
//  @Override
//  public void start() {
//    initComponents();
//  }
//
//  private static void drawGUI() {
//    JFrame f = new JFrame("Tetris");
//    f.setPreferredSize(windowSize);
//    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//    JApplet app = new Tetris();
//    app.init();
//    app.start();
//    f.add(app);
//    f.pack();
//    f.setVisible(true);
//  }
//
//  public static void main (String[] args) {
//    javax.swing.SwingUtilities.invokeLater(Tetris::drawGUI);
//  }
//
//  private void initComponents() {
//    setLayout(new BorderLayout());
//
////    JPanel p = new JPanel();
////    Board board = new Board();
////    board.drawCell(20, 0);
////    p.add(new TetrisGui());
////    p.add(board);
////    p.add(new GuiCell());
////    add(p);
//
//    BoardCompositer bc = new BoardCompositer();
//    add(bc);
//  }
//}
//
//class BoardCompositer extends JPanel {
//  private AffineTransform aff = new AffineTransform();
//
//  BoardCompositer() {
//  }
//
//  @Override
//  public void paintComponent(Graphics g) {
//    super.paintComponent(g);
//    Graphics2D g2 = (Graphics2D) g;
//    add(new TetrisGui());
//    Dimension d = getSize();
//    int w = d.width;
//    int h = d.height;
//    BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//    Graphics2D gbi = buffImg.createGraphics();
//
//    int rectx = w/4;
//    int recty = h/4;
//
//    gbi.setColor(Color.RED);
//    Rectangle2D r2d = new Rectangle2D.Double(100, 60, 200, 480);
//    gbi.fill(r2d);
//    gbi.setColor(new Color(0.0f, 0.0f, 1.0f, 1.0f));
//    Rectangle2D innerRect = new Rectangle2D.Double(rectx, recty, 150, 100);
//    gbi.fill(innerRect);
//
//    g2.drawImage(buffImg, null, 0, 0);
//
//    int delay = 1000;
//    ActionListener translator = e -> {
//      aff.translate(50, 0);
//
//      repaint();
//    };
//
//    Timer timer = new Timer(delay, translator);
//
//    timer.start();
//  }
//}