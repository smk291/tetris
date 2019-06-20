package com.newtetris.tetrispiece.rotate;

import com.newtetris.tetrispiece.Manipulator;
import com.newtetris.tetrispiece.TetrisPiece;

abstract public class Rotate extends Manipulator {
    public static void applyRotate (TetrisPiece t, int increment) {
        System.out.println("Increment: " + increment);
        System.out.println("Current orientation: " + t.getOrientation());
        int uniqueOrientations = t.getUniqueOrientations();
        System.out.println("Unique orientations: " + t.getUniqueOrientations());
        int newOrientation = t.getOrientation() + increment;
        System.out.println("new Orientation: " + newOrientation);

        if (newOrientation < 0) {
            System.out.println("orientation is negative: " + newOrientation);
            newOrientation = uniqueOrientations - 1;
        } else if (newOrientation >= uniqueOrientations) {
            System.out.println("orientation is greater than possible orientations: " + newOrientation + ", which is higher than " + uniqueOrientations);
            newOrientation = 0;
        }

        System.out.println("After conditionals, newOrientation is " + newOrientation);


        System.out.print("Orientation set. It was " + t.getOrientation());

        t.setOrientation(newOrientation);

        System.out.println(" and is now " + t.getOrientation());

    }

    abstract public void apply (TetrisPiece t);
}

