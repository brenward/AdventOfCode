package org.example.day6;

import lombok.Data;

@Data
public class PossibleRace {
    private long holdTime;
    private long travelDistance;

    public PossibleRace(long holdTime, long travelDistance){
        this.holdTime = holdTime;
        this.travelDistance = travelDistance;
    }
}
