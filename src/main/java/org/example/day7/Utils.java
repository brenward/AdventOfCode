package org.example.day7;

import org.apache.commons.lang3.StringUtils;

public class Utils {

    public static int getCardRank(char card){
        if(card == '2'){
            return 2;
        }
        if(card == '3'){
            return 3;
        }
        if(card == '4'){
            return 4;
        }
        if(card == '5'){
            return 5;
        }
        if(card == '6'){
            return 6;
        }
        if(card == '7'){
            return 7;
        }
        if(card == '8'){
            return 8;
        }
        if(card == '9'){
            return 9;
        }
        if(card == 'T'){
            return 10;
        }
        if(card == 'J'){
            return 11;
        }
        if(card == 'Q'){
            return 12;
        }
        if(card == 'K'){
            return 13;
        }
        if(card == 'A'){
            return 14;
        }

        return 0;
    }

    public static int getCardRankPart2(char card){
        if(card == '2'){
            return 2;
        }
        if(card == '3'){
            return 3;
        }
        if(card == '4'){
            return 4;
        }
        if(card == '5'){
            return 5;
        }
        if(card == '6'){
            return 6;
        }
        if(card == '7'){
            return 7;
        }
        if(card == '8'){
            return 8;
        }
        if(card == '9'){
            return 9;
        }
        if(card == 'T'){
            return 10;
        }
        if(card == 'J'){
            return 1;
        }
        if(card == 'Q'){
            return 12;
        }
        if(card == 'K'){
            return 13;
        }
        if(card == 'A'){
            return 14;
        }

        return 0;
    }

    public static int getJCount(String hand){
        return StringUtils.countMatches(hand,'J');

    }
}
