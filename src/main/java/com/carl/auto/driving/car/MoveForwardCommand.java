package com.carl.auto.driving.car;

public class MoveForwardCommand implements CarCommand{

    @Override
    public void execute(Car car) {
        switch (car.getDirection()) {
            case N -> car.setPosition(car.getX(), car.getY() + 1);
            case E -> car.setPosition(car.getX() + 1, car.getY());
            case S -> car.setPosition(car.getX(), car.getY() - 1);
            case W -> car.setPosition(car.getX() - 1, car.getY());
        }
    }

    @Override
    public void validate(Car car, Field field) {
        int newX = car.getX();
        int newY = car.getY();

        switch (car.getDirection()) {
            case N -> newY++;
            case E -> newX++;
            case S -> newY--;
            case W -> newX--;
        }

        if (!field.isWithinBounds(newX, newY)) {
            throw new IllegalStateException("Move would place the car out of field bounds.");
        }
    }
}
