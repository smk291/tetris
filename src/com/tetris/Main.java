package com.tetris;

import javafx.scene.transform.Affine;
import sun.plugin.dom.css.Rect;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.JComboBox;

//I, O, T, S, Z, J, LPiece


class Surface extends JPanel {
    TetrisPiece piece = new TPiece(new double[] {100, 100}, 0);

    private void draw (Graphics2D g2d, Color color, Area a) {
        g2d.setColor(color);
        g2d.fill(a);
        g2d.draw(a);
    }

    private void doDrawing(Graphics g) throws InterruptedException {
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(200,200);
        draw(g2d, piece.pieceColor, piece.piece);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        try {
            doDrawing(g);
        } catch (InterruptedException e) {
            System.out.print(e.toString());
        }
    }
}

enum Pieces {
    I(), O, T, S, Z, J, L
}

class PieceCombobox<T> extends JComboBox<T> {
    Pieces[] options = Pieces.values();

}


class Main extends JFrame {
    Main() {
        initUI();
    }

    private JLabel label = new JLabel("Piece");
    private TetrisPiece piece = new TPiece(new double[]{200, 200}, 0);
    Surface t = new Surface();

    private void setPiece(String item) {
        label.setText(item.toString());

        switch(item) {
            case "IPiece":
                piece = new IPiece(new double[] {100,100}, 0);
                t.piece = piece;
                t.invalidate();
                t.validate();
                t.repaint();
                break;
            case "OPiece":
                piece = new OPiece(new double[] {100,100}, 0);
                t.piece = piece;
                break;
            case "TPiece":
                piece = new TPiece(new double[] {100,100}, 0);
                t.piece = piece;
                break;
            case "SPiece":
                piece = new SPiece(new double[] {100,100}, 0);
                t.piece = piece;
                break;
            case "ZPiece":
                piece = new ZPiece(new double[] {100,100}, 0);
                t.piece = piece;
                break;
            case "JPiece":
                piece = new JPiece(new double[] {100,100}, 0);
                t.piece = piece;
                break;
            case "LPiece":
                piece = new LPiece(new double[] {100,100}, 0);
                t.piece = piece;
                break;
        }
    }

    private void initUI() {

        JPanel labelContainer = new JPanel();
        labelContainer.add(label);
        add(labelContainer);

        JPanel menu = new JPanel();
        menu.setMaximumSize(new Dimension(400, 200));

        JComboBox<TetrisEnum> pieceCombobox = new JComboBox<>(TetrisEnum.values());

        pieceCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                setTitle(e.getItem().toString());
                label.setText(e.getItem().toString());
            }
        });


        JButton rotateLeft = new JButton();
        JButton rotateRight = new JButton();

        rotateLeft.setText("Rotate left");
        rotateRight.setText("Rotate right");

        menu.add(pieceCombobox);
        menu.add(rotateLeft);
        menu.add(rotateRight);
        menu.add(this.label);

        add(menu, BorderLayout.NORTH);
        add(t);

        setTitle("Tetris");
        setSize(500, 700);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main ex = new Main();
                ex.setVisible(true);
            }
        });
    }
}

//    double[][] tPoints = new double[][]{
//            {51, 0},
//            {101, 0},
//            {101, 51},
//            {151, 51},
//            {151, 101},
//            {0, 101},
//            {0, 51},
//            {51, 51},
//            {51, 0}
//    };
//
//    setCoords(tStart, tPoints);
//
//    GeneralPath tPath = new GeneralPath();
//
//        tPath.moveTo(tPoints[0][0], tPoints[0][1]);
//
//                for (int k = 1; k < tPoints.length; k++)
//        tPath.lineTo(tPoints[k][0], tPoints[k][1]);
//
//        tPath.closePath();
//        g2d.fill(tPath);
//        g2d.draw(tPath);

