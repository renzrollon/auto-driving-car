package com.carl.auto.driving.car;

import java.util.Scanner;

public class SimulationCLI {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Simulation simulation = initializeSimulation(scanner);
            executeSimulation(simulation);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static Simulation initializeSimulation(Scanner scanner) {
        // Read field dimensions
        System.out.println("Enter field dimensions (width height):");
        String[] fieldInput = scanner.nextLine().split(" ");
        int fieldWidth = Integer.parseInt(fieldInput[0]);
        int fieldHeight = Integer.parseInt(fieldInput[1]);

        // Build simulation
        Simulation.Builder builder = new Simulation.Builder();
        builder.setField(fieldWidth, fieldHeight);

        // Menu loop
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1 - Add a car");
            System.out.println("2 - Start simulation");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println("Enter car name:");
                String carName = scanner.nextLine();

                System.out.println("Enter car's initial position and direction (x y direction):");
                String[] carInput = scanner.nextLine().split(" ");
                int carX = Integer.parseInt(carInput[0]);
                int carY = Integer.parseInt(carInput[1]);
                Direction carDirection = Direction.valueOf(carInput[2].toUpperCase());

                System.out.println("Enter command string for the car:");
                String commandString = scanner.nextLine();

                Car car = new Car.Builder(carName, carX, carY, carDirection).build();
                builder.addCar(car, commandString);
            } else if (choice.equals("2")) {
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        return builder.build();
    }

    private static void executeSimulation(Simulation simulation) {
        simulation.executeAllCommands();

        // Print final positions and directions of all cars
        System.out.println("Final positions and directions:");
        for (Car car : simulation.getCarCommands().keySet()) {
            System.out.println(car.getName() + ": " + car.getX() + " " + car.getY() + " " + car.getDirection());
        }
    }
}