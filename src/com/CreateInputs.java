package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

class CreatInputs {
  static JComboBox primitive, line, paint, trans, stroke;
  static private JButton redraw;

  public void render(JApplet japplet, ItemListener listener, ActionListener alistener) {

    GridBagLayout layOut = new GridBagLayout();
    japplet.getContentPane().setLayout(layOut);
    GridBagConstraints c = new GridBagConstraints();

    c.weightx = 1.0;
    c.fill = GridBagConstraints.BOTH;
    JLabel primLabel = new JLabel();
    primLabel.setText("Primitive");
    Font newFont = japplet.getFont().deriveFont(1);
    primLabel.setFont(newFont);
    primLabel.setHorizontalAlignment(JLabel.CENTER);
    layOut.setConstraints(primLabel, c);
    japplet.getContentPane().add(primLabel);

    JLabel lineLabel = new JLabel();
    lineLabel.setText("Lines");
    lineLabel.setFont(newFont);
    lineLabel.setHorizontalAlignment(JLabel.CENTER);
    layOut.setConstraints(lineLabel, c);
    japplet.getContentPane().add(lineLabel);

    JLabel paintLabel = new JLabel();
    paintLabel.setText("Paints");
    paintLabel.setFont(newFont);
    paintLabel.setHorizontalAlignment(JLabel.CENTER);
    layOut.setConstraints(paintLabel, c);
    japplet.getContentPane().add(paintLabel);

    c.gridwidth = GridBagConstraints.RELATIVE;
    JLabel transLabel = new JLabel();
    transLabel.setText("Transforms");
    transLabel.setFont(newFont);
    transLabel.setHorizontalAlignment(JLabel.CENTER);
    layOut.setConstraints(transLabel, c);
    japplet.getContentPane().add(transLabel);

    c.gridwidth = GridBagConstraints.REMAINDER;
    JLabel strokeLabel = new JLabel();
    strokeLabel.setText("Rendering");
    strokeLabel.setFont(newFont);
    strokeLabel.setHorizontalAlignment(JLabel.CENTER);
    layOut.setConstraints(strokeLabel, c);
    japplet.getContentPane().add(strokeLabel);

    GridBagConstraints ls = new GridBagConstraints();
    ls.weightx = 1.0;
    ls.fill = GridBagConstraints.BOTH;
    primitive = new JComboBox<>( new String[]{
        "rectangle",
        "ellipse",
        "text"});
    primitive.addItemListener(listener);
    newFont = newFont.deriveFont(0, 14.0f);
    primitive.setFont(newFont);
    layOut.setConstraints(primitive, ls);
    japplet.getContentPane().add(primitive);

    line = new JComboBox<>( new Object []{
        "thin",
        "thick",
        "dashed"});
    line.addItemListener(listener);
    line.setFont(newFont);
    layOut.setConstraints(line, ls);
    japplet.getContentPane().add(line);

    paint = new JComboBox<>( new Object[]{
        "solid",
        "gradient",
        "polka"});
    paint.addItemListener(listener);
    paint.setFont(newFont);
    layOut.setConstraints(paint, ls);
    japplet.getContentPane().add(paint);

    ls.gridwidth = GridBagConstraints.RELATIVE;

    trans = new JComboBox<>( new Object[]{
        "Identity",
        "rotate",
        "scale",
        "shear"});
    trans.addItemListener(listener);
    trans.setFont(newFont);
    layOut.setConstraints(trans, ls);
    japplet.getContentPane().add(trans);

    ls.gridwidth = GridBagConstraints.REMAINDER;
    stroke = new JComboBox<>( new Object[]{
        "Stroke",
        "Fill",
        "Stroke & Fill"});
    stroke.addItemListener(listener);
    stroke.setFont(newFont);
    layOut.setConstraints(stroke, ls);
    japplet.getContentPane().add(stroke);

    GridBagConstraints button = new GridBagConstraints();
    button.gridwidth = GridBagConstraints.REMAINDER;
    redraw = new JButton("Redraw");
    redraw.addActionListener(alistener);
    redraw.setFont(newFont);
    layOut.setConstraints(redraw, button);
    japplet.getContentPane().add(redraw);

    GridBagConstraints tP = new GridBagConstraints();
    tP.fill = GridBagConstraints.BOTH;
    tP.weightx = 1.0;
    tP.weighty = 1.0;
    tP.gridwidth = GridBagConstraints.REMAINDER;
//    display = new TransPanel();
//    layOut.setConstraints(display, tP);
//    display.setBackground(Color.white);
//    japplet.getContentPane().add(display);

  }
}
