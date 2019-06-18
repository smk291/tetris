package com.newtetris;

public class UserCursor extends User {
    public int getX() {
        return currentPosition.getX();
    }

    public void setX(int newX) {
        currentPosition.setX(newX);
    }

    public  int getY() {
        return currentPosition.getY();
    }

    public  void setY(int newY) {
        currentPosition.setY(newY);
    }

    public Coords get() {
        return currentPosition;
    }

    public void set(Coords newCurrentPosition) {
        currentPosition = newCurrentPosition;
    }
}
