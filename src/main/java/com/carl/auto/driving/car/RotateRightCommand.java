package com.carl.auto.driving.car;

// RotateRightCommand class
class RotateRightCommand implements CarCommand {
    @Override
    public void execute(Car car) {
        car.rotateRight();
    }
}
