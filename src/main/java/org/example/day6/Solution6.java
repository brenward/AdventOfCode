package org.example.day6;

import java.util.ArrayList;
import java.util.List;

public class Solution6 {

    public static void main(String[] args){
        System.out.println("Day 6");

        System.out.println("--- Part 1 Sample ---");
        Race race1test = new Race(7,9L);
        Race race2test = new Race(15,40L);
        Race race3test = new Race(30,200L);
        List<PossibleRace> race1RacesTest = calculatePossibleRacesForRace(race1test);
        System.out.println("Race 1 options: " + race1RacesTest.size());
        List<PossibleRace> race2RacesTest = calculatePossibleRacesForRace(race2test);
        System.out.println("Race 2 options: " + race2RacesTest.size());
        List<PossibleRace> race3RacesTest = calculatePossibleRacesForRace(race3test);
        System.out.println("Race 3 options: " + race3RacesTest.size());
        System.out.println("Example Result: " + (race1RacesTest.size()*race2RacesTest.size()*race3RacesTest.size()));

        System.out.println("--- Part 1 ---");
        Race race1 = new Race(58,478L);
        Race race2 = new Race(99,2232L);
        Race race3 = new Race(64,1019L);
        Race race4 = new Race(69,1071L);
        List<PossibleRace> race1Races = calculatePossibleRacesForRace(race1);
        System.out.println("Race 1 options: " + race1Races.size());
        List<PossibleRace> race2Races = calculatePossibleRacesForRace(race2);
        System.out.println("Race 2 options: " + race2Races.size());
        List<PossibleRace> race3Races = calculatePossibleRacesForRace(race3);
        System.out.println("Race 3 options: " + race3Races.size());
        List<PossibleRace> race4Races = calculatePossibleRacesForRace(race4);
        System.out.println("Race 4 options: " + race4Races.size());
        System.out.println("Part 1 Result: " + (race1Races.size()*race2Races.size()*race3Races.size()*race4Races.size()));

        System.out.println("--- Part 2 Sample ---");
        Race part2sampleRace = new Race(71530,940200L);
        List<PossibleRace> part2SampleRaces = calculatePossibleRacesForRace(part2sampleRace);
        System.out.println("Part 2 Example Result: " + part2SampleRaces.size());

        System.out.println("--- Part 2 ---");
        Race part2race = new Race(58996469,478223210191071L);
        List<PossibleRace> part2Races = calculatePossibleRacesForRace(part2race);
        System.out.println("Part 2 Example Result: " + part2Races.size());

    }

    public static List<PossibleRace> calculatePossibleRacesForRace(Race race){
        List<PossibleRace> possibleRaces = new ArrayList<>();
        long maxDist = 0L;
        for(int i = 0; i<race.getTime();i++){
            long speed = i;
            long moveTime = race.getTime() - i;
            long distanceTravelled = speed * moveTime;
            if(distanceTravelled > race.getDistance()){
                possibleRaces.add(new PossibleRace(i, distanceTravelled));
            }

            if(distanceTravelled > maxDist){
                maxDist = distanceTravelled;
            }
        }
        System.out.println("Max Race: " + maxDist);
        return possibleRaces;
    }

}
