package org.example.y2025.day2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args){
        System.out.println("Day 2: 2025");

        System.out.println("--- Part 1 Sample ---");
        List<Range> sampleData = getSampleData();

        System.out.println("invalid id sum: " + sumDuplicatesFromRangeListStream(sampleData));

        System.out.println("--- Part 1 ---");
        List<Range> part1SampleData = getData();
        System.out.println("invalid id sum: " + sumDuplicatesFromRangeListStream(part1SampleData));


        System.out.println("--- Part 2 Sample ---");
        System.out.println("invalid id sum: " + sumRepeatsFromRangeListStream(sampleData));

        System.out.println("--- Part 2 ---");
        System.out.println("invalid id sum: " + sumRepeatsFromRangeListStream(part1SampleData));
    }

    public static List<Range> getSampleData(){
        String sampleData = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124";
        return getRangeListFromString(sampleData);
    }

    public static List<Range> getData(){
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\y2025\\day2\\data.txt"))) {
            String line = reader.readLine();
            return getRangeListFromString(line);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
        return null;
    }

    public static List<Range> getRangeListFromString(String rawData){
        ArrayList<Range> ranges = new ArrayList();
        String[] parsedRanges = rawData.split(",");
        for(String stringRange: parsedRanges){
            String[] ids = stringRange.split("-");
            Range range = new Range(Long.parseLong(ids[0]), Long.parseLong(ids[1]));
            ranges.add(range);
        }
        return ranges;
    }

    public static long sumDuplicatesFromRangeList(List<Range> ranges){
        long sumOfDuplicates = 0l;

        for(Range range: ranges){
            sumOfDuplicates += sumDuplicateIdsFromRange(range);
        }

        return sumOfDuplicates;
        //ranges.stream().
    }

    public static long sumDuplicatesFromRangeListStream(List<Range> ranges){
        long sumOfDuplicates = 0l;

        sumOfDuplicates += ranges.stream()
                .mapToLong(Solution::sumDuplicateIdsFromRange)
                .sum();

        return sumOfDuplicates;

    }

    public static long sumDuplicateIdsFromRange(Range range){
        long sumOfDuplicates = 0l;
        for(long i = range.getRangeStart(); i<= range.getRangeEnd();i++){
            if(isValueDuplicate(i)){
                sumOfDuplicates+=i;
            }
        }

        return sumOfDuplicates;
    }

    public static boolean isValueDuplicate(long value){
        String valueAsString = String.valueOf(value);
        int numDigits = valueAsString.length();
        if(numDigits % 2 != 0){
            return false;
        }

        String firstHalfOfString = valueAsString.substring(0, numDigits/2);
        String secondHalfOfString = valueAsString.substring(numDigits/2);

        return firstHalfOfString.equals(secondHalfOfString);
    }

    public static long sumRepeatsFromRangeListStream(List<Range> ranges){
        long sumOfDuplicates = 0l;

        sumOfDuplicates += ranges.stream()
                .mapToLong(Solution::sumRepeatsIdsFromRange)
                .sum();

        return sumOfDuplicates;

    }

    public static long sumRepeatsIdsFromRange(Range range){
        long sumOfDuplicates = 0l;
        for(long i = range.getRangeStart(); i<= range.getRangeEnd();i++){
            if(isValueRepeated(i)){
                sumOfDuplicates+=i;
            }
        }

        return sumOfDuplicates;
    }

    public static boolean isValueRepeated(long value){
        String valueAsString = String.valueOf(value);
        int numDigits = valueAsString.length();

        for(int i = 1; i <= numDigits/2; i++){
            if(i != 1 && (numDigits % i != 0)){
                continue;
            }

            if(isValueRepeatedForSplit(i, valueAsString)){
                return true;
            }
        }

        return false;
    }

    public static boolean isValueRepeatedForSplit(int split, String value){
        String idToCheck = value.substring(0, split);
        for(int j=1;((j*split) +split) <= value.length();j++){
            String nextId = value.substring(split*j, (split*j) + split);
            if(!idToCheck.equals(nextId)){
                return false;
            }
        }
        return true;
    }

}
