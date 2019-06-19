package com.tetris;

import javafx.scene.transform.Affine;
import sun.plugin.dom.css.Rect;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.JComboBox;

class Surface extends JPanel {
    ArrayList<TetrisPiece> pieces = new ArrayList<TetrisPiece>();

    void setPieces(ArrayList<TetrisPiece> t) {
        pieces = t;
    }

    private void draw (Graphics2D g2d) {
//        for (Tetromino p : pieces) {
////            g2d.setColor(p.pieceColor);
////            g2d.fill(p.piece);
//            g2d.draw(p.piece);
//        }
    }

    private void doDrawing(Graphics g) throws InterruptedException {
        Graphics2D g2d = (Graphics2D) g;
        Random r = new Random();
        g2d.translate(0, 0);
        draw(g2d);
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

class Tetris extends JFrame {
    Tetris() {
        initUI();
    }

    private JLabel label = new JLabel("Piece");
    private Surface t = new Surface();
    private ArrayList<TetrisPiece> pieces = new ArrayList<>();
    private ArrayList<Integer> integers = new ArrayList<>();

    private ArrayList<TetrisPiece> addPiece(String item) {
        label.setText(item);
        pieces = new ArrayList<>();

        switch(item) {
            case "IPiece":
                pieces.add(new IPiece());
                break;
            case "OPiece":
                pieces.add(new OPiece());
                break;
            case "TPiece":
                pieces.add(new TPiece());
                break;
            case "SPiece":
                pieces.add(new SPiece());
                break;
            case "ZPiece":
                pieces.add(new ZPiece());
                break;
            case "JPiece":
                pieces.add(new JPiece());
                break;
            case "LPiece":
                pieces.add(new LPiece());
                break;
        }

        this.integers.add(integers.size());
        this.t.setPieces(pieces);
        repaint();

        return pieces;
    }

    private void rotate(RotationDirection r) {
        for (TetrisPiece p : pieces) {
//            label.setText(p.piece.getBounds2D().toString() + " ");

            repaint();
        }
    }

    private void initUI() {

        JPanel labelContainer = new JPanel();
        labelContainer.add(label);
        add(labelContainer);

        JPanel menu = new JPanel();
        menu.setMaximumSize(new Dimension(400, 200));
        String[] s = new String[]{null, "IPiece", "OPiece", "TPiece", "SPiece", "ZPiece", "JPiece", "LPiece"};
        JComboBox<String> pieceCombobox = new JComboBox<>(s);

        pieceCombobox.addItemListener(e -> {
            setTitle(e.getItem().toString());
            label.setText(e.getItem().toString() + " " + pieces.size() + " " + t.pieces.size() + " " + integers.toString());
            pieces = addPiece(e.getItem().toString());
        });

        JButton rotateLeft = new JButton();
        JButton rotateRight = new JButton();

        rotateLeft.addActionListener(e -> rotate(RotationDirection.COUNTERCLOCKWISE));
        rotateRight.addActionListener(e -> rotate(RotationDirection.CLOCKWISE));

        rotateLeft.setText("Rotate left");
        rotateRight.setText("Rotate right");

        menu.add(pieceCombobox);
        menu.add(rotateLeft);
        menu.add(rotateRight);
        add(this.label, BorderLayout.SOUTH);

        add(menu, BorderLayout.NORTH);
        add(t);

        setTitle("Tetris");
        setSize(500, 700);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class Main{
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Tetris frame = new Tetris();
            Board newContentPane = new Board(new double[] {100d, 100d}, 20);
            newContentPane.setOpaque(true);
            frame.setContentPane(newContentPane);

            //Display the window.
            frame.pack();
            frame.setVisible(true);
            newContentPane.setVisible(true);

            frame.repaint();
        });
    }
}
