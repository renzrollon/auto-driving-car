package com.carl.auto.driving.car;

// RotateLeftCommand class
class RotateLeftCommand implements CarCommand {
    @Override
    public void execute(Car car) {

        car.rotateLeft();
    }
}
