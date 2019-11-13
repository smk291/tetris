package com.tetris.unittests.things;

import com.tetris.things.Square;
import com.tetris.things.Row;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RowTest {
  private Row r = new Row(3);
  private Square[] bs = new Square[] {new Square(4, Color.red), new Square(5, Color.blue)};

  @BeforeEach
  void beforeEach() {
    r.get().clear();
    r.setY(3);
  }

  @Test
  void testClone() throws CloneNotSupportedException {
    for (int i : new int[] {2, 3, 5}) {
      r.add(new Square(i, Color.red));
    }

    Row clonedR = r.clone();

    assertNotEquals(r, clonedR);
    assertEquals(r.getY(), clonedR.getY());
    assertEquals(r.get().size(), clonedR.get().size());

    for (int i = 0; i < r.get().size(); i++) {
      Square b = r.get().get(i);
      Square bCloned = clonedR.get().get(i);

      assertNotEquals(b, bCloned);
      assertEquals(b.getX(), bCloned.getX());
      assertEquals(b.getColor(), bCloned.getColor());
    }
  }

  @Test
  void get() {
    assertNotNull(r.get());
  }

  @Test
  void getY() {
    assertEquals(3, r.getY());
  }

  @Test
  void setY() {
    assertEquals(3, r.getY());
    r.setY(5);
    assertEquals(5, r.getY());
  }

  @Test
  void testGet() {
    int x = 2;
    r.add(new Square(x, Color.red));
    Optional<Square> b = r.get(x);
    assertTrue(b.isPresent());
    assertEquals(x, b.get().getX());
  }

  @Test
  void add() {
    Square c = new Square(1, Color.red);
    r.add(c);

    assertEquals(c, r.get().get(0));
    assertEquals(c.getX(), r.get().get(0).getX());
    assertEquals(c.getColor(), r.get().get(0).getColor());
  }

  @Test
  void addAll() {
    r.addAll(Arrays.asList(bs));

    assertEquals(bs.length, r.get().size());

    Optional<Square> b0 = r.get(bs[0].getX());
    assertTrue(b0.isPresent());
    assertEquals(4, b0.get().getX());
    assertEquals(Color.red, b0.get().getColor());

    Optional<Square> b1 = r.get(bs[1].getX());
    assertTrue(b1.isPresent());
    assertEquals(5, b1.get().getX());
    assertEquals(Color.blue, b1.get().getColor());
  }

  @Test
  void isEmpty() {
    assertTrue(r.isEmpty());
  }

  @Test
  void size() {
    assertEquals(0, r.get().size());
    r.addAll(Arrays.asList(bs));
    assertEquals(bs.length, r.get().size());
  }

  @Test
  void remove() {
    assertEquals(0, r.get().size());
    r.addAll(Arrays.asList(bs));
    assertEquals(bs.length, r.get().size());
    r.remove(4);
    assertEquals(bs.length - 1, r.get().size());
    assertEquals(bs[1].getX(), r.get().get(0).getX());
    assertEquals(bs[1].getColor(), r.get().get(0).getColor());
  }
}
