package com.carl.auto.driving.car;

public enum Direction {
    N("NORTH"), E("EAST"), S("SOUTH"), W("WEST");

    private final String fullName;

    Direction(String fullName) {
        this.fullName = fullName;
    }

    public Direction left() {
        return values()[(ordinal() + 3) % 4];
    }

    public Direction right() {
        return values()[(ordinal() + 1) % 4];
    }

    @Override
    public String toString() {
        return name(); // Returns N, E, S, W
    }

    public String getFullName() {
        return fullName; // Returns the full name if needed
    }
}
