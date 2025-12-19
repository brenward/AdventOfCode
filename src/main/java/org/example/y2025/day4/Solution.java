package org.example.y2025.day4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Solution {

    public static void main(String[] args){
        System.out.println("Day 4: 2025");

        System.out.println("--- Part 1 Sample ---");
        int[][] sampleData = getSampleData();

        System.out.println("num rolls: " + calculateAccessibleNumRolls(sampleData));

        System.out.println("--- Part 1 ---");
        int[][] part1SampleData = getData();
        System.out.println("num rolls: " + calculateAccessibleNumRolls(part1SampleData));


        System.out.println("--- Part 2 Sample ---");
        System.out.println("num rolls: " + calculateAccessibleNumRollsWhenRemoving(sampleData));

        System.out.println("--- Part 2 ---");
        System.out.println("num rolls: " + calculateAccessibleNumRollsWhenRemoving(part1SampleData));
    }

    public static int[][] getSampleData(){
        int[][] data = new int[12][];
        data[0] = new int[12];

        String l1 = "..@@.@@@@.";
        data[1]= convertStringToIntArray(l1);
        String l2 = "@@@.@.@.@@";
        data[2]= convertStringToIntArray(l2);
        String l3 = "@@@@@.@.@@";
        data[3]= convertStringToIntArray(l3);
        String l4 = "@.@@@@..@.";
        data[4]= convertStringToIntArray(l4);
        String l5 = "@@.@@@@.@@";
        data[5]= convertStringToIntArray(l5);
        String l6 = ".@@@@@@@.@";
        data[6]= convertStringToIntArray(l6);
        String l7 = ".@.@.@.@@@";
        data[7]= convertStringToIntArray(l7);
        String l8 = "@.@@@.@@@@";
        data[8]= convertStringToIntArray(l8);
        String l9 = ".@@@@@@@@.";
        data[9]= convertStringToIntArray(l9);
        String l10 = "@.@.@@@.@.";
        data[10]= convertStringToIntArray(l10);
        data[11] = new int[12];
        return data;
    }

    public static int[] convertStringToIntArray(String input){
        int[] shelf = new int[input.length() + 2];
        for(int i = 1;i < input.length() + 1;i++){
            shelf[i] = input.charAt(i-1) == '@'? 1:0;
        }
        return shelf;
    }


    public static int[][] getData(){
        ArrayList<int[]> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\y2025\\day4\\data.txt"))) {
            String line = reader.readLine();
            while(line != null){
                data.add(convertStringToIntArray(line));
                line = reader.readLine();;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }

        int[][] dataAsArray = new int[data.size() + 2][];
        dataAsArray[0] = new int[data.get(0).length + 2];
        dataAsArray[data.size()+1] = new int[data.get(0).length + 2];;
        for(int i=0;i < data.size();i++){
            dataAsArray[i+1] = data.get(i);
        }

        return dataAsArray;
    }

    public static long calculateAccessibleNumRolls(int[][] data){
        long numAccessibleRolls = 0;
        for(int i =1; i< data.length -1;i++){
            for (int j = 1;j< data[i].length - 1;j++){
                if(data[i][j] == 0){
                    continue;
                }

                int numAdjacentRolls = data[i-1][j-1] + data[i-1][j] + data[i-1][j+1]
                        + data[i][j-1] + data[i][j+1]
                        + data[i+1][j-1] + data[i+1][j] + data[i+1][j+1];

                if(numAdjacentRolls < 4){
                    numAccessibleRolls++;
                }
            }
        }

        return numAccessibleRolls;
    }

    public static long calculateAccessibleNumRollsWhenRemoving(int[][] data){
        long numAccessibleRolls = 0;
        long iterationCount = 0;
        do{
            int[][] updatedData = data.clone();
            iterationCount = calculateAccessibleNumRollsAndRemove(data, updatedData);
            data = updatedData;
            numAccessibleRolls += iterationCount;
        }while (iterationCount > 0);

        return numAccessibleRolls;
    }

    public static long calculateAccessibleNumRollsAndRemove(int[][] data, int[][] updatedData){
        long numAccessibleRolls = 0;
        for(int i =1; i< data.length -1;i++){
            for (int j = 1;j< data[i].length - 1;j++){
                if(data[i][j] == 0){
                    continue;
                }

                int numAdjacentRolls = data[i-1][j-1] + data[i-1][j] + data[i-1][j+1]
                        + data[i][j-1] + data[i][j+1]
                        + data[i+1][j-1] + data[i+1][j] + data[i+1][j+1];

                if(numAdjacentRolls < 4){
                    numAccessibleRolls++;
                    updatedData[i][j] = 0;
                }
            }
        }

        return numAccessibleRolls;
    }


}
