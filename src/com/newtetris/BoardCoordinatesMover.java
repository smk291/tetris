package com.newtetris;

import java.util.Arrays;
import java.util.function.Function;


class MoveSingleLeft {
    static Coords apply(Coords bc) {
        return bc.sum(-1, 0);
    }
}

class MoveSingleRight {
    public static Coords apply(Coords bc) {
        return bc.sum(1,0);
    }
}

class MoveSingleDown {
    static Coords apply(Coords bc) {
        return bc.sum(0,1);
    }
}

class MoveTemplate {
    static Coords[] apply(Coords[] coords, Function<Coords, Coords> mover) {
        return Arrays.stream(coords).map(mover).toArray(Coords[]::new);
    }
}

class MoveArrayDown implements Function<Coords[], Coords[]> {
    public Coords[] apply(Coords[] coords) {
        return Arrays.stream(coords).map(MoveSingleDown::apply).toArray(Coords[]::new);
    }
}

class MoveArrayRight implements Function<Coords[], Coords[]> {
    public Coords[] apply(Coords[] coords) {
        return Arrays.stream(coords).map(MoveSingleRight::apply).toArray(Coords[]::new);
    }
}

class MoveArrayLeft implements Function<Coords[], Coords[]> {
    public Coords[] apply(Coords[] coords) {
        return Arrays.stream(coords).map(MoveSingleRight::apply).toArray(Coords[]::new);
    }
}

