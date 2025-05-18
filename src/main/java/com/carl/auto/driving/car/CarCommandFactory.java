package com.carl.auto.driving.car;

import java.util.HashMap;
import java.util.Map;

public class CarCommandFactory {
    private static final Map<Character, CarCommand> commandMap = new HashMap<>();

    static {
        commandMap.put('L', new RotateLeftCommand());
        commandMap.put('R', new RotateRightCommand());
        commandMap.put('F', new MoveForwardCommand());
    }

    public static CarCommand getCommand(char commandChar) {
        CarCommand command = commandMap.get(Character.toUpperCase(commandChar));
        if (command == null) {
            throw new IllegalArgumentException("Invalid command type: " + commandChar);
        }
        return command;
    }
}
