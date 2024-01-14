package org.example.day12;

import lombok.Data;

@Data
public class Spring {
    private int id;
    private String record;
    private int[] arrangement;

    private long numArrangements;

    public Spring(int id, String record, int[] arrangement) {
        this.id = id;
        this.record = record;
        this.arrangement = arrangement;
        this.numArrangements = 0;
    }
}
