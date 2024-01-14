package org.example.day7;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Data
public class Hand implements Comparable {
    private String cards;
    private int bet;
    private HandType type;


    public Hand(String cards,int bet){
        this.cards = cards;
        this.bet = bet;

        type = calculateType();
    }

    //Part 1 ranking
    /*private HandType calculateType(){
        Set<Character> chars = new HashSet<>();
        for(int i=0;i<5;i++){
            chars.add(cards.charAt(i));
        }
        switch (chars.size()){
            case 1:
                return HandType.FIVE_OF_A_KIND;
            case 2:
                int count2 = 0;
                for(Character c:chars){
                    count2 = StringUtils.countMatches(cards,c);
                    break;
                }
                if(count2 == 1 || count2 == 4){
                    return HandType.FOUR_OF_A_KIND;
                }else{
                    return HandType.FULL_HOUSE;
                }
            case 3:
                int highCount = 0;
                for(Character c:chars){
                    int count = StringUtils.countMatches(cards,c);
                    if(highCount < count){
                        highCount = count;
                    }
                }
                if(highCount == 3){
                    return HandType.THREE_OF_A_KIND;
                }else{
                    return HandType.TWO_PAIR;
                }
            case 4:
                return HandType.ONE_PAIR;
            default:
                return HandType.HIGH_CARD;

        }
    }*/

    private HandType calculateType(){
        int numJs = Utils.getJCount(cards);

        Set<Character> chars = new HashSet<>();
        for(int i=0;i<5;i++){
            chars.add(cards.charAt(i));
        }
        switch (chars.size()){
            case 1:
                return HandType.FIVE_OF_A_KIND;
            case 2:
                int count2 = 0;
                for(Character c:chars){
                    count2 = StringUtils.countMatches(cards,c);
                    break;
                }
                if(numJs == 0) {
                    if (count2 == 1 || count2 == 4) {
                        return HandType.FOUR_OF_A_KIND;
                    } else {
                        return HandType.FULL_HOUSE;
                    }
                }else{
                    return HandType.FIVE_OF_A_KIND;
                }
            case 3:
                int highCount = 0;
                for(Character c:chars){
                    int count = StringUtils.countMatches(cards,c);
                    if(highCount < count){
                        highCount = count;
                    }
                }
                if(numJs == 0) {
                    if (highCount == 3) {
                        return HandType.THREE_OF_A_KIND;
                    } else {
                        return HandType.TWO_PAIR;
                    }
                }else if(numJs > 1){
                    return HandType.FOUR_OF_A_KIND;
                }else{
                    int countFirstNotJ = 0;
                    if(cards.charAt(0) == 'J'){
                        countFirstNotJ = StringUtils.countMatches(cards,cards.charAt(1));
                    }else{
                        countFirstNotJ = StringUtils.countMatches(cards,cards.charAt(0));
                    }

                    if(countFirstNotJ == 2){
                        return HandType.FULL_HOUSE;
                    }else{
                        return HandType.FOUR_OF_A_KIND;
                    }
                }
            case 4:
                if(numJs == 0) {
                    return HandType.ONE_PAIR;
                }else{
                    return HandType.THREE_OF_A_KIND;
                }
            default:
                if(numJs == 0){
                    return HandType.HIGH_CARD;
                }else{
                    return HandType.ONE_PAIR;
                }
        }
    }

    @Override
    public int compareTo(Object o) {
        Hand h = (Hand)o;

        if(this.getCards().equalsIgnoreCase(h.getCards())){
            return 0;
        }

        if(this.getType().getRank() < h.getType().getRank()){
            return -1;
        }

        if(this.getType().getRank() > h.getType().getRank()){
            return 1;
        }

        if(this.getType().getRank() == h.getType().getRank()){
            return compareHighCards(this.getCards(), h.getCards());
        }

        return 0;
    }

    private int compareHighCards(String current, String objectToCompare){
        for(int i=0;i<5;i++){
            int rankCurrent = Utils.getCardRankPart2(current.charAt(i));
            int rankCompare = Utils.getCardRankPart2(objectToCompare.charAt(i));
            if(rankCurrent > rankCompare){
                return -1;
            }else if(rankCurrent < rankCompare){
                return  1;
            }
        }
        return 0;
    }
}
