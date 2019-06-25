package com.newtetris.tetrispiece;

import java.util.HashMap;
import java.util.Map;

public class KickData {
//        J, L, S, T, Z Tetromino Wall Kick Data
//         Test 1   Test 2   Test 3   Test 4   Test 5
//        -0>>1  {{ 0, 0}, {-1, 0}, {-1, 1}, { 0,-2}, {-1,-2}}
//        1>>0  {{ 0, 0}, { 1, 0}, { 1,-1}, { 0, 2}, { 1, 2}}
//        1>>2  {{ 0, 0}, { 1, 0}, { 1,-1}, { 0, 2}, { 1, 2}}
//        2>>1  {{ 0, 0}, {-1, 0}, {-1, 1}, { 0,-2}, {-1,-2}}
//        2>>3  {{ 0, 0}, { 1, 0}, { 1, 1}, { 0,-2}, { 1,-2}}
//        3>>2  {{ 0, 0}, {-1, 0}, {-1,-1}, { 0, 2}, {-1, 2}}
//        3>>0  {{ 0, 0}, {-1, 0}, {-1,-1}, { 0, 2}, {-1, 2}}
//        -0>>3  {{ 0, 0}, { 1, 0}, { 1, 1}, { 0,-2}, { 1,-2}}
//
//        I Tetromino Wall Kick Data
//        Test 1   Test 2   Test 3   Test 4   Test 5
//        0>>1  { 0, 0}, {-2, 0}, { 1, 0}, {-2,-1}, { 1, 2}}
//        1>>0  { 0, 0}, { 2, 0}, {-1, 0}, { 2, 1}, {-1,-2}}
//        1>>2  { 0, 0}, {-1, 0}, { 2, 0}, {-1, 2}, { 2,-1}}
//        2>>1  { 0, 0}, { 1, 0}, {-2, 0}, { 1,-2}, {-2, 1}}
//        2>>3  { 0, 0}, { 2, 0}, {-1, 0}, { 2, 1}, {-1,-2}}
//        3>>2  { 0, 0}, {-2, 0}, { 1, 0}, {-2,-1}, { 1, 2}}
//        3>>0  { 0, 0}, { 1, 0}, {-2, 0}, { 1,-2}, {-2, 1}}
//        0>>3  { 0, 0}, {-1, 0}, { 2, 0}, {-1, 2}, { 2,-1)}
//
    public static HashMap<Integer, HashMap<Integer, Integer[][]>> kickData = new HashMap<Integer, HashMap<Integer, Integer[][]>>() {{
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
}
