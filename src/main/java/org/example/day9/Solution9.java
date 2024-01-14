package org.example.day9;

import org.example.day8.Location;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution9 {

    public static void main(String[] args){
        System.out.println("Day 9");

        System.out.println("--- Part 1 Sample ---");
        List<int[]> sampleData =  getSampleData();
        long test = calculateSumOfAddedElements(sampleData);
        //long test = getNextElementInSequence(sampleData.get(1));
        System.out.println("Part 1 Sample result: " + test);

        System.out.println("--- Part 1 ---");
        List<int[]> data =  getData();
        long part1Result = calculateSumOfAddedElements(data);
        System.out.println("Part 1 result: " + part1Result);


        System.out.println("--- Part 2 Sample ---");
        List<int[]> part2SampleData =  getSampleDataPart2();
        long part2SampleResult = calculateSumOfNewStartElements(part2SampleData);
        System.out.println("Part 2 Sample result: " + part2SampleResult);

        System.out.println("--- Part 2 ---");
        data =  getData();
        long part2Result = calculateSumOfNewStartElements(data);
        System.out.println("Part 2 result: " + part2Result);
    }

    public static List<int[]> getSampleData(){
        List<int[]> data = new ArrayList<>();
        int[] l1 = {0,3,6,9,12,15};
        data.add(l1);
        int[] l2 = {1,3,6,10,15,21};
        data.add(l2);
        int[] l3 = {10,13,16,21,30,45};
        data.add(l3);
        return data;
    }

    public static List<int[]> getSampleDataPart2(){
        List<int[]> data = new ArrayList<>();
        int[] l1 = {10,13,16,21,30,45};
        data.add(l1);
        return data;
    }

    public static List<int[]> getData(){
        List<int[]> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\day9\\data.txt"))) {
            String line = reader.readLine();
            while(line != null){
                String[] splitLine = line.split(" ");
                int[] dataLine = new int[splitLine.length];
                for(int i=0;i<splitLine.length;i++){
                    dataLine[i] = Integer.parseInt(splitLine[i]);
                }
                data.add(dataLine);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }

        return data;
    }

    public static long calculateSumOfNewStartElements(List<int[]> data){
        long sum = 0l;
        for (int[] array:data){
            sum += getNewStartElementInSequence(array);
        }
        return sum;
    }

    public static long getNewStartElementInSequence(int[] sequence){
        List<int[]> requiredSequences = getAllRequiredSequences(sequence);
        List<int[]> expandedSequences = addNewStartElementToSequences(requiredSequences);
        int[] expandedSequence = expandedSequences.get(0);
        return expandedSequence[0];
    }

    public static long getNextElementInSequence(int[] sequence){
        List<int[]> requiredSequences = getAllRequiredSequences(sequence);
        List<int[]> expandedSequences = addNewStartElementToSequences(requiredSequences);
        int[] expandedSequence = expandedSequences.get(0);
        return expandedSequence[expandedSequence.length -1];
    }

    public static long calculateSumOfAddedElements(List<int[]> data){
        long sum = 0l;
        for (int[] array:data){
            sum += getNextElementInSequence(array);
        }
        return sum;
    }
    public static List<int[]> getAllRequiredSequences(int[] line){
        List<int[]> sequences = new ArrayList<>();
        sequences.add(line);

        boolean isBottomReached = false;
        while(!isBottomReached){
            int[] nextSequence = getNextSequence(sequences.get(sequences.size() - 1));
            sequences.add(nextSequence);
            isBottomReached = isArrayAllZeroes(nextSequence);
        }
        return sequences;
    }

    public static List<int[]> addNextElementsToSequences(List<int[]> sequences){
        Stack<int[]> processedSequences = new Stack<>();
        int count = 0;
        for(int i=sequences.size()-1;i>0;i--){
            if(processedSequences.isEmpty()) {
                int[] increasedSeqeunce = addNextElementToSequence(sequences.get(i - 1), sequences.get(i));
                processedSequences.push(increasedSeqeunce);
            }else{
                int[] increasedSeqeunce = addNextElementToSequence(sequences.get(i - 1), processedSequences.peek());
                processedSequences.push(increasedSeqeunce);
            }
        }

        List<int[]> listOfExpandedSequences = new ArrayList<>();
        while(!processedSequences.isEmpty()){
            listOfExpandedSequences.add(processedSequences.pop());
        }
        return listOfExpandedSequences;
    }

    public static int[] getNextSequence(int[] previousSequence){
        int[] nextSequence = new int[previousSequence.length -1];
        for(int i=0;i<nextSequence.length;i++){
            nextSequence[i] = previousSequence[i+1] - previousSequence[i];
        }
        return nextSequence;
    }

    public static int[] addNextElementToSequence(int[] currentSequence, int[] previousSequence){
        int[] expandedSequence = new int[currentSequence.length + 1];
        for(int i=0;i<currentSequence.length;i++){
            expandedSequence[i] = currentSequence[i];
        }
        expandedSequence[expandedSequence.length -1] = currentSequence[currentSequence.length - 1] + previousSequence[previousSequence.length - 1];
        return expandedSequence;
    }

    public static List<int[]> addNewStartElementToSequences(List<int[]> sequences){
        Stack<int[]> processedSequences = new Stack<>();
        int count = 0;
        for(int i=sequences.size()-1;i>0;i--){
            if(processedSequences.isEmpty()) {
                int[] increasedSeqeunce = addNewStartElementToSequence(sequences.get(i - 1), sequences.get(i));
                processedSequences.push(increasedSeqeunce);
            }else{
                int[] increasedSeqeunce = addNewStartElementToSequence(sequences.get(i - 1), processedSequences.peek());
                processedSequences.push(increasedSeqeunce);
            }
        }

        List<int[]> listOfExpandedSequences = new ArrayList<>();
        while(!processedSequences.isEmpty()){
            listOfExpandedSequences.add(processedSequences.pop());
        }
        return listOfExpandedSequences;
    }

    public static int[] addNewStartElementToSequence(int[] currentSequence, int[] previousSequence){
        int[] expandedSequence = new int[currentSequence.length + 1];
        for(int i=1;i<currentSequence.length;i++){
            expandedSequence[i] = currentSequence[i-1];
        }
        expandedSequence[0] = currentSequence[0] - previousSequence[0];
        return expandedSequence;
    }

    public static boolean isArrayAllZeroes(int[] arrayToTest){
        for(int i:arrayToTest){
            if(i != 0){
                return false;
            }
        }
        return true;
    }

}
