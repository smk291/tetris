//package com.tetrisrevision;
//
//import java.awt.*;
//import java.awt.font.FontRenderContext;
//import java.awt.geom.Rectangle2D;
//
//public class TetrisGui extends Component {
//  String gameTitle = "Tetris";
//  @Override
//  public Dimension getPreferredSize() {
//    return new Dimension(400, 50);
//  }
//
//  @Override
//  public void paint (Graphics g) {
////    Graphics2D g2 = (Graphics2D) g;
////    Composite origComposite = g2.getComposite();
////    g2.setComposite(origComposite);
//
//    Graphics2D g2 = (Graphics2D) g;
//
//    Font font = new Font("Serif", Font.BOLD, 20);
//    g.setFont(font);
//    FontRenderContext frc = g2.getFontRenderContext();
//
//    Rectangle2D titleBounds = font.getStringBounds(gameTitle, frc);
//    int titleXOffset = (int) (Tetris.getWindowSize().width - titleBounds.getWidth()) / 2;
//    int titleYOffset = 50;
//
//    g.setColor(Color.black);
//    g2.fillRect(titleXOffset, titleYOffset, (int) titleBounds.getWidth(), (int) titleBounds.getHeight());
//    g.drawString(gameTitle, titleXOffset, titleYOffset);
//  }
//
//}
