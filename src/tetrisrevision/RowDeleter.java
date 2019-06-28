package tetrisrevision;

import java.awt.*;
import java.util.Arrays;
import java.util.Set;

public class RowDeleter {
    private static PlayField p;

    RowDeleter(PlayField p) {
        this.p = p;
    }

    public int apply(int[] testRows) {
        int startAt = -1;

        int[] fullRows = Arrays.stream(testRows).filter(row -> p.rowIsFull(row)).toArray();

        for (int row : fullRows) {
            if (row > startAt) {
                startAt = row;
            }
        }

        int sinkToRow = startAt + 1;

        if (startAt > -1) {
            int shift = 0;

            while (!p.rowIsEmpty(startAt) && (startAt + shift) >= 0) {
                while (p.rowIsFull(startAt + shift)) {
                    shift--;
                }

                p.copyRow(startAt, startAt + shift);
            }

            for (int i = startAt, halt = startAt + shift; i >= 0 && i > halt; i--) {
                p.emptyRow(i);
            }
        }

        return sinkToRow;
    }
}
