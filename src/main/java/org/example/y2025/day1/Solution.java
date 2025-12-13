package org.example.y2025.day1;

import org.example.day7.Hand;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

public class Solution {

    public static void main(String[] args){
        System.out.println("Day 1: 2025");

        System.out.println("--- Part 1 Sample ---");
        ArrayList<String> sampleData = getSampleData();

        System.out.println("passcode: " + calculatePasscode(sampleData,50));

        System.out.println("--- Part 1 ---");
        ArrayList<String> part1SampleData = getData();
        System.out.println("passcode: " + calculatePasscode(part1SampleData, 50));


        System.out.println("--- Part 2 Sample ---");
        System.out.println("passcode: " + calculatePasscodeIncludingIntermediateZeros(sampleData,50));

        System.out.println("--- Part 2 ---");
        System.out.println("passcode: " + calculatePasscodeIncludingIntermediateZeros(part1SampleData, 50));
    }

    public static ArrayList<String> getSampleData(){
        ArrayList<String> data = new ArrayList<>();
        String l1 = "L68";
        data.add(l1);
        String l2 = "L30";
        data.add(l2);
        String l3 = "R48";
        data.add(l3);
        String l4 = "L5";
        data.add(l4);
        String l5 = "R60";
        data.add(l5);
        String l6 = "L55";
        data.add(l6);
        String l7 = "L1";
        data.add(l7);
        String l8 = "L99";
        data.add(l8);
        String l9 = "R14";
        data.add(l9);
        String l10 = "L82";
        data.add(l10);
        return data;
    }

    public static ArrayList<String> getData(){
        ArrayList<String> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\y2025\\day1\\data.txt"))) {
            String line = reader.readLine();
            while(line != null){
                data.add(line);
                line = reader.readLine();;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }

        return data;
    }

    public static long calculatePasscode(ArrayList<String> data, final int startPoint){
        int passcode = 0;
        int currentLocation = startPoint;

        for(String rotation: data){
            int direction = rotation.charAt(0) == 'R'? 1:-1;
            int turns = Integer.parseInt(rotation.substring(1)) * direction;
            currentLocation += turns;
            currentLocation = adjustToBeWithinHundred(currentLocation);
            if(currentLocation == 0){
                passcode++;
            }
        }

        return passcode;
    }

    public static int adjustToBeWithinHundred(int uncheckedLocation){
        while(uncheckedLocation > 99){
            uncheckedLocation -= 100;
        }

        while (uncheckedLocation < 0){
            uncheckedLocation += 100;
        }

        return uncheckedLocation;
    }

    public static long calculatePasscodeIncludingIntermediateZeros(ArrayList<String> data, final int startPoint){
        int passcode = 0;
        int currentLocation = startPoint;

        for(String rotation: data){
            int direction = rotation.charAt(0) == 'R'? 1:-1;
            int turns = Integer.parseInt(rotation.substring(1)) * direction;
            passcode += addIntermediateClicks(currentLocation,turns);
            currentLocation += turns;
            currentLocation = adjustToBeWithinHundred(currentLocation);
        }

        return passcode;
    }

    public static int addIntermediateClicks(int startLocation, int rotation){
        int numPassesZero = 0;
        int numFullRotations = rotation > 0? rotation/100: -1 * (rotation/100);
        numPassesZero += numFullRotations;
        int partialRotation = rotation % 100;
        if(startLocation + partialRotation == 0){
            numPassesZero ++;
        }else if(startLocation + partialRotation < 0){
            if(startLocation + partialRotation <= -100){
                numPassesZero += 1;
            }

            if(startLocation > 0){
                numPassesZero++;
            }
        }else if(startLocation + partialRotation > 0){
            if(startLocation + partialRotation >= 100){
                numPassesZero ++;
            }
        }

        return numPassesZero;
    }
}
