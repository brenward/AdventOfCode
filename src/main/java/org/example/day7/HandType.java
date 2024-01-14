package org.example.day7;

import lombok.Getter;

public enum HandType {
    FIVE_OF_A_KIND(1),
    FOUR_OF_A_KIND(2),
    FULL_HOUSE(3),
    THREE_OF_A_KIND(4),
    TWO_PAIR(5),
    ONE_PAIR(6),
    HIGH_CARD(7);

    @Getter
    private int rank;

    HandType(int rank){
        this.rank = rank;
    }

}
