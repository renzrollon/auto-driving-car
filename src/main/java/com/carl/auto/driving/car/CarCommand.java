package com.carl.auto.driving.car;

public interface CarCommand {
    void execute(Car car);
    default void validate(Car car, Field field) {}
}
