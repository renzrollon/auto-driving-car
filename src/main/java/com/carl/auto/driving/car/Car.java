package com.carl.auto.driving.car;

// Car class
public class Car {
    private final String name;
    private int x;
    private int y;
    private Direction direction;

    private Car(Builder builder) {
        this.name = builder.name;
        this.x = builder.x;
        this.y = builder.y;
        this.direction = builder.direction;
    }

    public void rotateLeft() {
        direction = direction.left();
    }

    public void rotateRight() {
        direction = direction.right();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String name;
        private int x;
        private int y;
        private Direction direction;

        public Builder(String name, int x, int y, Direction direction) {
            this.name = name;
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPosition(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder setDirection(Direction direction) {
            this.direction = direction;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}

