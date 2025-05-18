package com.carl.auto.driving.car;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarCommandTest {
    @Test
    public void testMoveForwardCommand() {
        Car car = new Car.Builder("Car1", 0, 0, Direction.N).build();
        CarCommand moveForward = new MoveForwardCommand();

        moveForward.execute(car);

        assertEquals(0, car.getX());
        assertEquals(1, car.getY());
        assertEquals(Direction.N, car.getDirection());
    }

    @Test
    public void testMoveForwardCommandFacingEast() {
        Car car = new Car.Builder("Car1", 0, 0, Direction.E).build();
        CarCommand moveForward = new MoveForwardCommand();

        moveForward.execute(car);

        assertEquals(1, car.getX());
        assertEquals(0, car.getY());
        assertEquals(Direction.E, car.getDirection());
    }

    @Test
    public void testMoveForwardCommandFacingSouth() {
        Car car = new Car.Builder("Car1", 0, 0, Direction.S).build();
        CarCommand moveForward = new MoveForwardCommand();

        moveForward.execute(car);

        assertEquals(0, car.getX());
        assertEquals(-1, car.getY());
        assertEquals(Direction.S, car.getDirection());
    }

    @Test
    public void testMoveForwardCommandFacingWest() {
        Car car = new Car.Builder("Car1", 0, 0, Direction.W).build();
        CarCommand moveForward = new MoveForwardCommand();

        moveForward.execute(car);

        assertEquals(-1, car.getX());
        assertEquals(0, car.getY());
        assertEquals(Direction.W, car.getDirection());
    }

    @Test
    public void testRotateLeftCommand() {
        Car car = new Car.Builder("Car1", 0, 0, Direction.N).build();
        CarCommand rotateLeft = new RotateLeftCommand();

        rotateLeft.execute(car);

        assertEquals(0, car.getX());
        assertEquals(0, car.getY());
        assertEquals(Direction.W, car.getDirection());
    }

    @Test
    public void testRotateRightCommand() {
        Car car = new Car.Builder("Car1", 0, 0, Direction.N).build();
        CarCommand rotateRight = new RotateRightCommand();

        rotateRight.execute(car);

        assertEquals(0, car.getX());
        assertEquals(0, car.getY());
        assertEquals(Direction.E, car.getDirection());
    }

    @Test
    public void testValidateWithinBounds() {
        Field field = new Field(5, 5);
        Car car = new Car.Builder("Car1", 2, 2, Direction.N).build();
        MoveForwardCommand command = new MoveForwardCommand();

        assertDoesNotThrow(() -> command.validate(car, field));
    }

    @Test
    public void testValidateOutOfBoundsNorth() {
        Field field = new Field(5, 5);
        Car car = new Car.Builder("Car1", 2, 4, Direction.N).build();
        MoveForwardCommand command = new MoveForwardCommand();

        Exception exception = assertThrows(IllegalStateException.class, () -> command.validate(car, field));
        assertEquals("Move would place the car out of field bounds.", exception.getMessage());
    }

    @Test
    public void testValidateOutOfBoundsEast() {
        Field field = new Field(5, 5);
        Car car = new Car.Builder("Car1", 4, 2, Direction.E).build();
        MoveForwardCommand command = new MoveForwardCommand();

        Exception exception = assertThrows(IllegalStateException.class, () -> command.validate(car, field));
        assertEquals("Move would place the car out of field bounds.", exception.getMessage());
    }

    @Test
    public void testValidateOutOfBoundsSouth() {
        Field field = new Field(5, 5);
        Car car = new Car.Builder("Car1", 2, 0, Direction.S).build();
        MoveForwardCommand command = new MoveForwardCommand();

        Exception exception = assertThrows(IllegalStateException.class, () -> command.validate(car, field));
        assertEquals("Move would place the car out of field bounds.", exception.getMessage());
    }

    @Test
    public void testValidateOutOfBoundsWest() {
        Field field = new Field(5, 5);
        Car car = new Car.Builder("Car1", 0, 2, Direction.W).build();
        MoveForwardCommand command = new MoveForwardCommand();

        Exception exception = assertThrows(IllegalStateException.class, () -> command.validate(car, field));
        assertEquals("Move would place the car out of field bounds.", exception.getMessage());
    }
}