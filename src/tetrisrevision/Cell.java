package tetrisrevision;

import java.awt.*;
import java.awt.geom.Point2D;

public class Cell {
    private static PlayField p;
    private Point2D pt;
    private boolean empty;
    private Color c;

    Cell (PlayField p, int y, int x) {
        Cell.p = p;
        this.empty = true;
        this.pt = new Point(x, y);
    }

    public void setEmpty(boolean status) {
        this.empty = status;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isFull() {
        return !this.empty;
    }
}
