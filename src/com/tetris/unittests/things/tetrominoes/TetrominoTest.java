package com.tetris.unittests.things.tetrominoes;

import com.tetris.things.*;
import com.tetris.things.Square;
import com.tetris.things.tetrominoes.TBlock;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TetrominoTester {
  private final Tetromino tetromino;
  private final ActiveBlock t = new ActiveBlock();
  private final RowList[] expectedShapes;
  private final HashMap<Integer, HashMap<Integer, int[][]>> expectedKickData;
  private final Color expectedColor;

  TetrominoTester(
      Tetromino tetromino,
      RowList[] expectedShapes,
      HashMap<Integer, HashMap<Integer, int[][]>> expectedKickData,
      Color expectedColor) {
    this.tetromino = tetromino;
    t.setTetromino(this.tetromino);
    t.setCenter(TTestData.center[0], TTestData.center[1]);
    this.expectedShapes = expectedShapes;
    this.expectedKickData = expectedKickData;
    this.expectedColor = expectedColor;
  }

  Executable testShapes() {
    return () -> {
      t.setTetromino(tetromino);

      for (RowList expected : expectedShapes) {
        RowList actual = t.getSquares();

        for (Row actualRow : actual.get()) {
          Optional<Row> expectedRow = expected.getRowByY(actualRow.getY());
          assertTrue(expectedRow.isPresent());

          for (Square b : actualRow.get())
            assertTrue(expected.cellIsNotEmpty(b.getX(), actualRow.getY()));
        }

        t.incrementRotation(1);
      }
    };
  }

  Executable getKickData() {
    return () ->
        assertAll(
            () -> {
              HashMap<Integer, HashMap<Integer, int[][]>> actualKickData = tetromino.getKickData();

              assertEquals(actualKickData.size(), expectedKickData.size());

              for (int i = 0; i < actualKickData.size(); i++) {
                int leftRotation = i - 1 > -1 ? i - 1 : 3;

                // actualLeftRotationKickData
                int[][] actualLeft = actualKickData.get(i).get(leftRotation);
                // expectedLeftRotationKickData
                int[][] expectedLeft = expectedKickData.get(i).get(leftRotation);

                assertEquals(actualLeft.length, expectedLeft.length);

                for (int j = 0; j < actualLeft.length; j++) {
                  assertEquals(actualLeft[j][0], expectedLeft[j][0]);
                  assertEquals(actualLeft[j][1], expectedLeft[j][1]);
                }

                int rightRotation = i + 1 < 4 ? i + 1 : 0;

                // actualRightRotationKickData
                int[][] actualRight = actualKickData.get(i).get(rightRotation);
                // expectedRightRotationKickData
                int[][] expectedRight = expectedKickData.get(i).get(rightRotation);

                assertEquals(actualRight.length, expectedRight.length);

                for (int j = 0; j < actualRight.length; j++) {
                  assertEquals(actualRight[j][0], expectedRight[j][0]);
                  assertEquals(actualRight[j][1], expectedRight[j][1]);
                }
              }
            });
  }

  final Executable getColor() {
    return () -> assertEquals(expectedColor, tetromino.getColor());
  }

  Executable isTPiece() {
    return () -> {
      if (tetromino instanceof TBlock) {
        assertTrue(tetromino.isTPiece());
      } else {
        assertFalse(tetromino.isTPiece());
      }
    };
  }
}

class TetrominoTest {
  @TestFactory
  ArrayList<DynamicTest> runDynamically() {
    ArrayList<DynamicTest> tests = new ArrayList<>();

    for (int i = 0; i < 7; i++) {
      TetrominoTester tester =
          new TetrominoTester(
              TTestData.tetrominoes[i],
              TTestData.expectedShapes[i],
              TTestData.getKick(i),
              TTestData.colors[i]);

      Collections.addAll(
          tests,
          DynamicTest.dynamicTest("testShapes", tester.testShapes()),
          DynamicTest.dynamicTest("getKickData", tester.getKickData()),
          DynamicTest.dynamicTest("getKickData", tester.getKickData()),
          DynamicTest.dynamicTest("getColor", tester.getColor()),
          DynamicTest.dynamicTest("isTPiece", tester.isTPiece()));
    }

    return tests;
  }
}
