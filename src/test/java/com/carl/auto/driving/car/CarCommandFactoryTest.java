package com.carl.auto.driving.car;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarCommandFactoryTest {

    @Test
    public void testCreateMoveForwardCommand() {
        CarCommand command = CarCommandFactory.getCommand('F');
        assertNotNull(command);
        assertInstanceOf(MoveForwardCommand.class, command);
    }

    @Test
    public void testCreateRotateLeftCommand() {
        CarCommand command = CarCommandFactory.getCommand('L');
        assertNotNull(command);
        assertInstanceOf(RotateLeftCommand.class, command);
    }

    @Test
    public void testCreateRotateRightCommand() {
        CarCommand command = CarCommandFactory.getCommand('R');
        assertNotNull(command);
        assertInstanceOf(RotateRightCommand.class, command);
    }

    @Test
    public void testCreateInvalidCommand() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                CarCommandFactory.getCommand('X')
        );
        assertEquals("Invalid command type: X", exception.getMessage());
    }
}