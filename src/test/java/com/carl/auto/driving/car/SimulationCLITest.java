package com.carl.auto.driving.car;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimulationCLITest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(outputStream));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testSimulationCLIWithMenu() {
        String input = "10 10\n1\nCar1\n1 2 N\nFFRFFFRRLF\n2\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        SimulationCLI.main(new String[]{});

        String output = outputStream.toString().replace("\n", " ");

        // Assert CLI prompts
        assertTrue(output.contains("Enter field dimensions (width height):"));
        assertTrue(output.contains("Choose an option:"));
        assertTrue(output.contains("1 - Add a car"));
        assertTrue(output.contains("2 - Start simulation"));
        assertTrue(output.contains("Enter car name:"));
        assertTrue(output.contains("Enter car's initial position and direction (x y direction):"));
        assertTrue(output.contains("Enter command string for the car:"));

        // Assert final output
        assertTrue(output.contains("Final positions and directions:"));
        assertTrue(output.contains("4 3 S"));
    }

    @Test
    public void testInvalidMenuOption() {
        String input = "10 10\n3\n2\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        SimulationCLI.main(new String[]{});

        String output = outputStream.toString().replace("\n", " ");

        // Assert invalid menu option message
        assertTrue(output.contains("Invalid option. Please try again."));
    }

    @Test
    public void testSimulationCLIWithMultipleCars() {
        String input = "10 10\n1\nCar1\n0 0 N\nFFRFF\n1\nCar2\n1 1 E\nLFF\n2\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        SimulationCLI.main(new String[]{});

        String output = outputStream.toString().replace("\n", " ");

        // Assert final output for multiple cars
        assertTrue(output.contains("Final positions and directions:"));
        assertTrue(output.contains("Car1: 2 2 E"));
        assertTrue(output.contains("Car2: 1 3 N"));
    }

    @Test
    public void testCollisionDetectionWithCommandCounter() {
        String input = "10 10\n1\nA\n1 2 N\nFFRFFFFRRL\n1\nB\n7 8 W\nFFLFFFFFFF\n2\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        SimulationCLI.main(new String[]{});

        String output = outputStream.toString().replace("\n", " ");

        assertTrue(output.contains("Cars involved: B A") || output.contains("Cars involved: A B"));
        assertTrue(output.contains("Collision position: 5 4"));
        assertTrue(output.contains("7"));
        assertTrue(output.contains("Error: Simulation ended due to collision."));
    }
}