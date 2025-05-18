package com.carl.auto.driving.car;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {

    @Test
    public void testSetField() {
        Simulation.Builder builder = new Simulation.Builder();
        builder.setField(5, 5);
        Simulation simulation = builder.build();

        assertNotNull(simulation.getField());
        assertEquals(5, simulation.getField().getWidth());
        assertEquals(5, simulation.getField().getHeight());
    }

    @Test
    public void testAddCarWithinBounds() {
        Simulation.Builder builder = new Simulation.Builder();
        builder.setField(5, 5);
        Car car = new Car.Builder("Car1", 2, 2, Direction.N).build();
        builder.addCar(car, "FFR");

        Simulation simulation = builder.build();
        assertEquals(1, simulation.getCarCommands().size());
        assertTrue(simulation.getCarCommands().containsKey(car));
        assertEquals("FFR", simulation.getCarCommands().get(car));
    }

    @Test
    public void testAddCarOutOfBounds() {
        Simulation.Builder builder = new Simulation.Builder();
        builder.setField(5, 5);
        Car car = new Car.Builder("Car1", 6, 6, Direction.N).build();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.addCar(car, "FFR"));
        assertEquals("Car's initial position is out of field bounds.", exception.getMessage());
    }

    @Test
    public void testBuildWithoutField() {
        Simulation.Builder builder = new Simulation.Builder();
        Car car = new Car.Builder("Car1", 2, 2, Direction.N).build();

        Exception exception = assertThrows(IllegalStateException.class, () -> builder.addCar(car, "FFR"));
        assertEquals("Field must be set before adding cars.", exception.getMessage());
    }

    @Test
    public void testBuildWithCommands() {
        Simulation.Builder builder = new Simulation.Builder();
        builder.setField(5, 5);
        Car car = new Car.Builder("Car1", 0, 0, Direction.N).build();
        builder.addCar(car, "FFRFF");

        Simulation simulation = builder.build();
        simulation.executeAllCommands();

        assertEquals(1, simulation.getCarCommands().size());
        assertEquals(2, car.getX());
        assertEquals(2, car.getY());
        assertEquals(Direction.E, car.getDirection());
    }

    @Test
    public void testAddMultipleCarsWithinBounds() {
        Simulation.Builder builder = new Simulation.Builder();
        builder.setField(10, 10);

        Car car1 = new Car.Builder("Car1", 2, 2, Direction.N).build();
        Car car2 = new Car.Builder("Car2", 5, 5, Direction.E).build();

        builder.addCar(car1, "FFR");
        builder.addCar(car2, "LFF");

        Simulation simulation = builder.build();

        assertEquals(2, simulation.getCarCommands().size());
        assertTrue(simulation.getCarCommands().containsKey(car1));
        assertTrue(simulation.getCarCommands().containsKey(car2));
    }

    @Test
    public void testExecuteCommandsForMultipleCars() {
        Simulation.Builder builder = new Simulation.Builder();
        builder.setField(10, 10);

        Car car1 = new Car.Builder("Car1", 0, 0, Direction.N).build();
        Car car2 = new Car.Builder("Car2", 5, 5, Direction.E).build();

        builder.addCar(car1, "FFRFF");
        builder.addCar(car2, "LFF");

        Simulation simulation = builder.build();
        simulation.executeAllCommands();

        // Verify car1's final position and direction
        assertEquals(2, car1.getX());
        assertEquals(2, car1.getY());
        assertEquals(Direction.E, car1.getDirection());

        // Verify car2's final position and direction
        assertEquals(5, car2.getX());
        assertEquals(7, car2.getY());
        assertEquals(Direction.N, car2.getDirection());
    }

    @Test
    public void testCollisionBetweenCars() {
        Simulation.Builder builder = new Simulation.Builder();
        builder.setField(5, 5);

        Car car1 = new Car.Builder("Car1", 0, 1, Direction.N).build();
        Car car2 = new Car.Builder("Car2", 0, 1, Direction.S).build();

        builder.addCar(car1, "F");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> builder.addCar(car2, "F"));
        assertEquals("Collision detected at position: 0,1", exception.getMessage());
    }

    @Test
    public void testAddMultipleCarsOutOfBounds() {
        Simulation.Builder builder = new Simulation.Builder();
        builder.setField(5, 5);

        Car car1 = new Car.Builder("Car1", 6, 6, Direction.N).build();
        Car car2 = new Car.Builder("Car2", 0, 0, Direction.E).build();

        builder.addCar(car2, "FF");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> builder.addCar(car1, "FF"));
        assertEquals("Car's initial position is out of field bounds.", exception.getMessage());
    }
}