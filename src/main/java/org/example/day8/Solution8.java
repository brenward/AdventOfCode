package org.example.day8;

import org.example.day7.Hand;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution8 {

    private static final String MAIN_LEFT_RIGHT = "LRRLRLRRRLLRLRRRLRLLRLRLRRLRLRRLRRLRLRLLRRRLRRLLRRRLRRLRRRLRRLRLRLLRRLRLRRLLRRRLLLRRRLLLRRLRLRRLRLLRRRLRRLRRRLRRLLRRRLRRRLRRRLRLRRLRLRRRLRRRLRRLRLRRLLRRRLRRLLRRLRRLRLRLRRRLRLLRRRLRRLRRRLLRRLLLLLRRRLRRLLLRRRLRRRLRRLRLLLLLRLRRRLRRRLRLRRLLLLRLRRRLLRRRLRRRLRLRLRRLRRLRRLRLRLLLRLRRLRRLRRRLRRRLLRRRR";

    public static void main(String[] args){
        System.out.println("Day 9");

        System.out.println("--- Part 1 Sample ---");
        System.out.println("Part1 sample1 Num moves: " + calculateNumMoves("RL", getSampleData1()));
        System.out.println("Part1 sample2 Num moves: " + calculateNumMoves("LLR", getSampleData2()));

        System.out.println("--- Part 1 ---");
        System.out.println("Part 1 Num moves: " + calculateNumMoves(MAIN_LEFT_RIGHT,getData()));


        System.out.println("--- Part 2 Sample ---");
        System.out.println("Part 2 sample Num moves: " + calculateSimultaneousMoves("LR",getSampleDataPart2()));

        System.out.println("--- Part 2 ---");
        System.out.println("Part 2 Num moves: " + calculateSimultaneousMoves(MAIN_LEFT_RIGHT,getData()));
    }

    public static Map<String,Location> getSampleData1(){
        Map<String,Location> data = new HashMap<>();
        Location l1 = new Location("AAA","BBB","CCC");
        data.put(l1.getLocation(),l1);
        Location l2 = new Location("BBB","DDD","EEE");
        data.put(l2.getLocation(),l2);
        Location l3 = new Location("CCC","ZZZ","GGG");
        data.put(l3.getLocation(),l3);
        Location l4 = new Location("DDD","DDD","DDD");
        data.put(l4.getLocation(),l4);
        Location l5 = new Location("EEE","EEE","EEE");
        data.put(l5.getLocation(),l5);
        Location l6 = new Location("GGG","GGG","GGG");
        data.put(l6.getLocation(),l6);
        Location l7 = new Location("ZZZ","ZZZ","ZZZ");
        data.put(l7.getLocation(),l7);
        return data;
    }

    public static Map<String,Location> getSampleData2(){
        Map<String,Location> data = new HashMap<>();
        Location l1 = new Location("AAA","BBB","BBB");
        data.put(l1.getLocation(),l1);
        Location l2 = new Location("BBB","AAA","ZZZ");
        data.put(l2.getLocation(),l2);
        Location l3 = new Location("ZZZ","ZZZ","ZZZ");
        data.put(l3.getLocation(),l3);
        return data;
    }

    public static Map<String,Location> getSampleDataPart2(){
        Map<String,Location> data = new HashMap<>();
        Location l1 = new Location("11A","11B","XXX");
        data.put(l1.getLocation(),l1);
        Location l2 = new Location("11B","XXX","11Z");
        data.put(l2.getLocation(),l2);
        Location l3 = new Location("11Z","11B","XXX");
        data.put(l3.getLocation(),l3);
        Location l4 = new Location("22A","22B","XXX");
        data.put(l4.getLocation(),l4);
        Location l5 = new Location("22B","22C","22C");
        data.put(l5.getLocation(),l5);
        Location l6 = new Location("22C","22Z","22Z");
        data.put(l6.getLocation(),l6);
        Location l7 = new Location("22Z","22B","22B");
        data.put(l7.getLocation(),l7);
        Location l8 = new Location("XXX","XXX","XXX");
        data.put(l8.getLocation(),l8);
        return data;
    }

    public static Map<String,Location> getData(){
        Map<String,Location> data = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\day8\\data.txt"))) {
            String line = reader.readLine();
            while(line != null){
                String[] splitLine = line.split("=");
                String location = splitLine[0].trim();

                String[] leftRightSplit = splitLine[1].split(",");
                String left = leftRightSplit[0].replace('(',' ').trim();
                String right = leftRightSplit[1].replace(')',' ').trim();

                Location loc = new Location(location,left,right);
                data.put(loc.getLocation(),loc);
                line = reader.readLine();;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }

        return data;
    }

    public static int calculateNumMoves(String leftRightSeq, Map<String,Location> data){
        int numMoves = 0;
        int locationInSequence = 0;
        Location currentLocation = data.get("AAA");
        while(!currentLocation.getLocation().equals("ZZZ")){
            char nextMove = leftRightSeq.charAt(locationInSequence++);
            if(nextMove == 'L'){
                currentLocation = data.get(currentLocation.getLeft());
            }else{
                currentLocation = data.get(currentLocation.getRight());
            }
            if(locationInSequence == leftRightSeq.length()){
                locationInSequence = 0;
            }
            numMoves++;
        }
        return numMoves;
    }


    public static long calculateSimultaneousMoves(String leftRightSeq, Map<String,Location> data){
        long totalNumMoves = 1;

        List<Location> startLocations = getLocationsEndingInChar('A', data);
        List<Long> individualNumMoves = new ArrayList<>();
        for(Location loc: startLocations){
            long numMoves = 0;
            int locationInSequence = 0;
            while(loc.getLocation().charAt(2) != 'Z'){
                char nextMove = leftRightSeq.charAt(locationInSequence++);
                loc = moveOneStep(nextMove,loc,data);
                if(locationInSequence == leftRightSeq.length()){
                    locationInSequence = 0;
                }
                numMoves++;
            }
            individualNumMoves.add(numMoves);
        }

        for(int i=0;i<individualNumMoves.size();i++){
            totalNumMoves = lcm(totalNumMoves,individualNumMoves.get(i));
        }

        return totalNumMoves;
    }


    public static Location moveOneStep(char move, Location currentLocation, Map<String,Location> data){
        if(move == 'L'){
            return data.get(currentLocation.getLeft());
        }else{
            return data.get(currentLocation.getRight());
        }
    }

    public static boolean allLocationsEndInChar(char endChar, List<Location> locations){
        for(Location loc: locations){
            char lastChar = loc.getLocation().charAt(2);
            if(lastChar != endChar){
                return false;
            }
        }
        return true;
    }

    public static List<Location> getLocationsEndingInChar(char endChar, Map<String,Location> data){
        List<Location> locations = new ArrayList<>();
        for(Location loc: data.values()){
            char lastChar = loc.getLocation().charAt(2);
            if(lastChar == endChar){
                locations.add(loc);
            }
        }
        return locations;
    }

    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}
