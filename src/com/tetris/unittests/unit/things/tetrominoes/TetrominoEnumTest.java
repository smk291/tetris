package com.tetris.unittests.unit.things.tetrominoes;

import com.tetris.things.tetrominoes.TetrominoEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TetrominoEnumTest {
  @Test
  void getTetromino() {
    assertNotNull(TetrominoEnum.getTetromino());
  }

  @Test
  void get() {
    assertNotNull(TetrominoEnum.I.get());
    assertNotNull(TetrominoEnum.O.get());
    assertNotNull(TetrominoEnum.L.get());
    assertNotNull(TetrominoEnum.J.get());
    assertNotNull(TetrominoEnum.T.get());
    assertNotNull(TetrominoEnum.S.get());
    assertNotNull(TetrominoEnum.I.get());
  }
}
