package com.tetrisrevision.kickdata;


import java.util.HashMap;

public class KickData {
    private HashMap<Integer, HashMap<Integer, Integer[][]>> kickData = new HashMap<Integer, HashMap<Integer, Integer[][]>>() {{
        put(
                0,
                new HashMap<Integer, Integer[][]>(){{
                    put(1, new Integer[][] {{ 0, 0}, {-1, 0}, {-1, 1}, { 0,-2}, {-1,-2}});
                    put(3, new Integer[][] {{ 0, 0}, { 1, 0}, { 1, 1}, { 0,-2}, { 1,-2}});
                }}
        );
        put(
                1,
                new HashMap<Integer, Integer[][]>(){{
                    put(0, new Integer[][] {{ 0, 0}, { 1, 0}, { 1,-1}, { 0, 2}, { 1, 2}});
                    put(2, new Integer[][] {{ 0, 0}, { 1, 0}, { 1,-1}, { 0, 2}, { 1, 2}});
                }}
        );
        put(
                2,
                new HashMap<Integer, Integer[][]>(){{
                    put(1, new Integer[][] {{ 0, 0}, {-1, 0}, {-1, 1}, { 0,-2}, {-1,-2}});
                    put(3, new Integer[][] {{ 0, 0}, { 1, 0}, { 1, 1}, { 0,-2}, { 1,-2}});
                }}
        );
        put(
                3,
                new HashMap<Integer, Integer[][]>(){{
                    put(0, new Integer[][] {{ 0, 0}, {-1, 0}, {-1,-1}, { 0, 2}, {-1, 2}});
                    put(2, new Integer[][] {{ 0, 0}, {-1, 0}, {-1,-1}, { 0, 2}, {-1, 2}});
                }}
        );
    }};

    public HashMap<Integer, HashMap<Integer, Integer[][]>> get() {
        return kickData;
    }
}
