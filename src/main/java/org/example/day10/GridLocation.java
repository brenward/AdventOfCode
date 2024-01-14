package org.example.day10;

import lombok.Data;

@Data
public class GridLocation {
    private char type;
    private Coord location;
    private Coord connection1;
    private Coord connection2;

    private int distanceFromStart1;
    private int distanceFromStart2;

    private int enclosed;

    public GridLocation(char type, Coord location, Coord connection1, Coord connection2, int distanceFromStart1, int distanceFromStart2) {
        this.type = type;
        this.connection1 = connection1;
        this.connection2 = connection2;
        this.location = location;
        this.distanceFromStart1 = distanceFromStart1;
        this.distanceFromStart2 = distanceFromStart2;
        this.enclosed = 0;
    }

    public GridLocation(char type, Coord location) {
        this.type = type;
        this.location = location;
        distanceFromStart1 = 0;
        distanceFromStart2 = 0;
        this.enclosed = 0;
    }

    public GridLocation clone(){
        GridLocation loc = new GridLocation(this.type, this.location, this.connection1, this.connection2, this.distanceFromStart1, this.distanceFromStart2);
        return loc;
    }
}
