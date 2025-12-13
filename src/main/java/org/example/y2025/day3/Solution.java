package org.example.y2025.day3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Solution {

    public static void main(String[] args){
        System.out.println("Day 3: 2025");

        System.out.println("--- Part 1 Sample ---");
        ArrayList<String> sampleData = getSampleData();

        System.out.println("max joltage: " + calculateTotalMaxJoltage(sampleData));

        System.out.println("--- Part 1 ---");
        ArrayList<String> part1SampleData = getData();
        System.out.println("max joltage: " + calculateTotalMaxJoltage(part1SampleData));


        System.out.println("--- Part 2 Sample ---");
        System.out.println("max joltage: " + calculateTotalMaxJoltageForTwelve(sampleData));

        System.out.println("--- Part 2 ---");
        System.out.println("max joltage: " + calculateTotalMaxJoltageForTwelve(part1SampleData));
    }

    public static ArrayList<String> getSampleData(){
        ArrayList<String> data = new ArrayList<>();
        String l1 = "987654321111111";
        data.add(l1);
        String l2 = "811111111111119";
        data.add(l2);
        String l3 = "234234234234278";
        data.add(l3);
        String l4 = "818181911112111";
        data.add(l4);
        return data;
    }

    public static ArrayList<String> getData(){
        ArrayList<String> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\y2025\\day3\\data.txt"))) {
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

    public static long calculateTotalMaxJoltage(ArrayList<String> data){
        long maxJoltage = 0;
        for(String bank: data){
            maxJoltage +=calculateMaxJoltageOfBank(bank);
        }
        return maxJoltage;
    }

    public static int calculateMaxJoltageOfBank(String bank){
        char[] batteries = bank.toCharArray();
        ArrayList<Integer> batteryValues = new ArrayList<>();
        for(char battery: batteries){
            batteryValues.add(battery - '0');
        }

        //find max value and location, not at end
        int maxValue = 0;
        int maxValueIndex = 0;
        for(int i=0; i< batteryValues.size() -1; i++){
            if(batteryValues.get(i) > maxValue){
                maxValue = batteryValues.get(i);
                maxValueIndex = i;
            }
        }

        //Get max value in remainder
        int secondValue = 0;
        for(int i=maxValueIndex + 1; i< batteryValues.size(); i++){
            if(batteryValues.get(i) > secondValue){
                secondValue = batteryValues.get(i);
            }
        }

        return (maxValue * 10) + secondValue;
    }

    public static long calculateTotalMaxJoltageForTwelve(ArrayList<String> data){
        long maxJoltage = 0;
        for(String bank: data){
            maxJoltage +=calculateMaxJoltageOfBankForTwelve(bank);
        }
        return maxJoltage;
    }

    public static long calculateMaxJoltageOfBankForTwelve(String bank){
        char[] batteries = bank.toCharArray();
        ArrayList<Integer> batteryValues = new ArrayList<>();
        for(char battery: batteries){
            batteryValues.add(battery - '0');
        }

        Battery battery1 = getMaxPossibleBatteryInRange(batteryValues, 0, batteryValues.size() - 11);
        Battery battery2 = getMaxPossibleBatteryInRange(batteryValues, battery1.getIndex() + 1, batteryValues.size() - 10);
        Battery battery3 = getMaxPossibleBatteryInRange(batteryValues, battery2.getIndex() + 1, batteryValues.size() - 9);
        Battery battery4 = getMaxPossibleBatteryInRange(batteryValues, battery3.getIndex() + 1, batteryValues.size() - 8);
        Battery battery5 = getMaxPossibleBatteryInRange(batteryValues, battery4.getIndex() + 1, batteryValues.size() - 7);
        Battery battery6 = getMaxPossibleBatteryInRange(batteryValues, battery5.getIndex() + 1, batteryValues.size() - 6);
        Battery battery7 = getMaxPossibleBatteryInRange(batteryValues, battery6.getIndex() + 1, batteryValues.size() - 5);
        Battery battery8 = getMaxPossibleBatteryInRange(batteryValues, battery7.getIndex() + 1, batteryValues.size() - 4);
        Battery battery9 = getMaxPossibleBatteryInRange(batteryValues, battery8.getIndex() + 1, batteryValues.size() - 3);
        Battery battery10 = getMaxPossibleBatteryInRange(batteryValues, battery9.getIndex() + 1, batteryValues.size() - 2);
        Battery battery11 = getMaxPossibleBatteryInRange(batteryValues, battery10.getIndex() + 1, batteryValues.size() - 1);
        Battery battery12 = getMaxPossibleBatteryInRange(batteryValues, battery11.getIndex() + 1, batteryValues.size());

        return (battery1.getVoltage() * 100000000000L)
                + (battery2.getVoltage() * 10000000000L)
                + (battery3.getVoltage() * 1000000000L)
                + (battery4.getVoltage() * 100000000L)
                + (battery5.getVoltage() * 10000000L)
                + (battery6.getVoltage() * 1000000L)
                + (battery7.getVoltage() * 100000L)
                + (battery8.getVoltage() * 10000L)
                + (battery9.getVoltage() * 1000L)
                + (battery10.getVoltage() * 100L)
                + (battery11.getVoltage() * 10L)
                + battery12.getVoltage();
    }

    private static Battery getMaxPossibleBatteryInRange(ArrayList<Integer> batteryValues, int start, int end){
        int maxValue = 0;
        int maxValueIndex = 0;
        for(int i=start; i< end; i++){
            if(batteryValues.get(i) > maxValue){
                maxValue = batteryValues.get(i);
                maxValueIndex = i;
            }
        }

        return new Battery(maxValue, maxValueIndex);
    }

}
