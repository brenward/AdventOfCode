package org.example.day6;

import lombok.Data;

@Data
public class Race {
    private long time;
    private long distance;

    public Race(long time, long distance){
        this.time = time;
        this.distance = distance;
    }
}
