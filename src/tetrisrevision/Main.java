package tetrisrevision;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

@FunctionalInterface
interface Procedure {
    void run();

    default Procedure andThen(Procedure after){
        return () -> {
            this.run();
            after.run();
        };
    }

    default Procedure compose(Procedure before){
        return () -> {
            before.run();
            this.run();
        };
    }
}

public class Main {
    static int width = 10;
    static int height = 24;
    static ArrayList<ArrayList<Integer>> sinkingPieces;
}
