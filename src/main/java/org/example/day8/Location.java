package org.example.day8;

import lombok.Data;

import java.util.Objects;

@Data
public class Location {
    private String location;
    private String left;
    private String right;

    public Location(String location, String left, String right) {
        this.location = location;
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location1 = (Location) o;
        return Objects.equals(location, location1.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
