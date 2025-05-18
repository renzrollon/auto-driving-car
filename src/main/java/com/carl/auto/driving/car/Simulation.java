package com.carl.auto.driving.car;

import java.util.*;

public class Simulation {
    private final Field field;
    private final Map<Car, String> carCommands = new HashMap<>();
    private final Set<String> occupiedPositions = new HashSet<>();

    private Simulation(Builder builder) {
        this.field = builder.field;
        this.carCommands.putAll(builder.carCommands);

        // Initialize occupied positions with the starting positions of all cars
        for (Car car : carCommands.keySet()) {
            updatePosition(car.getX(), car.getY());
        }
    }


    private String getCollidingCars(String positionKey) {
        List<String> collidingCars = new ArrayList<>();
        for (Car car : carCommands.keySet()) {
            if ((car.getX() + "," + car.getY()).equals(positionKey)) {
                collidingCars.add(car.getName());
            }
        }
        return String.join(" ", collidingCars);
    }

    public void executeCommand(Car car, char commandChar) {
        String oldPositionKey = car.getX() + "," + car.getY();

        CarCommand command = CarCommandFactory.getCommand(commandChar);

        // Validate and execute the command
        command.validate(car, field);
        command.execute(car);
    }

    private void checkForCollision(String oldPositionKey, String newPositionKey, int commandIndex) {
        if (occupiedPositions.contains(newPositionKey) && !newPositionKey.equals(oldPositionKey)) {
            System.out.println("Collision detected!");
            System.out.println("Cars involved: " + getCollidingCars(newPositionKey));
            System.out.println("Collision position: " + newPositionKey.replace(",", " "));
            System.out.println("Commands executed: " + (commandIndex + 1));
            throw new IllegalStateException("Simulation ended due to collision.");
        }
    }

    public void executeAllCommands() {
        int commandIndex = 0;

        while (hasCommandsRemaining(commandIndex)) {
            for (Map.Entry<Car, String> entry : carCommands.entrySet()) {
                processCarCommand(entry.getKey(), entry.getValue(), commandIndex);
            }
            commandIndex++;
        }
    }

    private boolean hasCommandsRemaining(int commandIndex) {
        return carCommands.values().stream().anyMatch(commands -> commandIndex < commands.length());
    }

    private void processCarCommand(Car car, String commandString, int commandIndex) {
        if (commandIndex < commandString.length()) {
            char commandChar = commandString.charAt(commandIndex);
            processCommand(car, commandChar, commandIndex);
        }
    }

    private void processCommand(Car car, char commandChar, int commandIndex) {
        String oldPositionKey = car.getX() + "," + car.getY();
        executeCommand(car, commandChar);
        String newPositionKey = car.getX() + "," + car.getY();

        checkForCollision(oldPositionKey, newPositionKey, commandIndex);
        updateOccupiedPositions(oldPositionKey, newPositionKey);
    }

    private void updateOccupiedPositions(String oldPositionKey, String newPositionKey) {
        occupiedPositions.remove(oldPositionKey);
        occupiedPositions.add(newPositionKey);
    }

    private void updatePosition(int newX, int newY) {
        String newPositionKey = newX + "," + newY;

        if (occupiedPositions.contains(newPositionKey)) {
            throw new IllegalStateException("Collision detected at position: " + newPositionKey);
        }

        occupiedPositions.add(newPositionKey);
    }

    public Field getField() {
        return field;
    }

    public Map<Car, String> getCarCommands() {
        return Map.copyOf(carCommands);
    }

    public static class Builder {
        private Field field;
        private final Map<Car, String> carCommands = new HashMap<>();
        private final Set<String> occupiedPositions = new HashSet<>();

        public Builder setField(int width, int height) {
            this.field = new Field(width, height);
            return this;
        }

        public Builder addCar(Car car, String commandString) {
            if (field == null) {
                throw new IllegalStateException("Field must be set before adding cars.");
            }
            if (!field.isWithinBounds(car.getX(), car.getY())) {
                throw new IllegalArgumentException("Car's initial position is out of field bounds.");
            }
            String positionKey = car.getX() + "," + car.getY();
            if (occupiedPositions.contains(positionKey)) {
                throw new IllegalStateException("Collision detected at position: " + positionKey);
            }
            carCommands.put(car, commandString);
            occupiedPositions.add(positionKey);
            return this;
        }

        public Simulation build() {
            return new Simulation(this);
        }
    }
}