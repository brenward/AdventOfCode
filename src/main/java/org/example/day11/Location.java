package org.example.day11;

import lombok.Data;

@Data
public class Location {
    private int id;
    private char type;

    private Coord location;

    public Location(int id, char type) {
        this.id = id;
        this.type = type;
    }
}
