package org.example.day12;

import org.apache.commons.lang3.StringUtils;
import org.example.day11.Coord;
import org.example.day11.Location;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution12 {



    public static void main(String[] args){
        System.out.println("Day 12");

        System.out.println("--- Part 1 Sample ---");
        //List<Spring> sample = getData("dataSample1.txt");
        //System.out.println("Total num of arrangements: " + processArrangements(sample));
        //System.out.println("Total num of arrangements: " + processSprings(sample));

        System.out.println("--- Part 1 ---");
        //List<Spring> part1Data = getData("data.txt");
        //System.out.println("Total num of arrangements: " + processArrangements(part1Data));
        //System.out.println("Total num of arrangements: " + processSprings(part1Data));

        System.out.println("--- Part 2 Sample ---");
        //List<Spring> sample2 = getUnfoldedData("dataSample1.txt");
        //System.out.println("Total num of arrangements: " + processSprings(sample2));


        System.out.println("--- Part 2 ---");
        List<Spring> part2Data = getUnfoldedData("data.txt");
        System.out.println("Total num of arrangements: " + processSprings(part2Data));
    }

    public static long processArrangements(List<Spring> data){
        long sum = 0;
        Map<String, List<String>> cache = new HashMap<>();
        for(Spring spring:data){
            determineNumValidArrangementsInSpring(spring, cache);
            sum+=spring.getNumArrangements();
            System.out.println("Num of arrangements for " + spring.getNumArrangements() + ": " + spring.getNumArrangements());
        }
        return sum;
    }

    public static void determineNumValidArrangementsInSpring(Spring spring, Map<String,List<String>> cache){
        List<String> combos = getCombinations(spring.getRecord(),cache);
        int count = 0;
        for(String combo:combos){
            if(isArrangementValid(combo,spring.getArrangement())){
                count++;
            }
        }
        spring.setNumArrangements(count);
    }

    public static long processSprings(List<Spring> data){
        long sum = 0;
        int count = 0;
        for(Spring spring:data){
            Map<String, Long> cache = new HashMap<>();
            sum += getCountOfValidCombos(spring.getRecord(), spring.getArrangement(),"", cache);
            System.out.println("Sum after processing entry " + ++count + " is " + sum);
        }
        return sum;
    }

    public static long getCountOfValidCombos(String configWithUnknowns, int[] arrangement, String prefix, Map<String, Long> cache){
        /*if(cache.containsKey(prefix+configWithUnknowns)){
            System.out.println("Found match in cache " + prefix+configWithUnknowns);
            return cache.get(prefix+configWithUnknowns);
        }*/

        int numUnknowns= StringUtils.countMatches(configWithUnknowns,'?');

        if(numUnknowns > 1){
            int firstUnknown = configWithUnknowns.indexOf('?');
            String prefix1 = prefix + configWithUnknowns.substring(0,firstUnknown + 1).replace('?','#');
            String prefix2 = prefix + configWithUnknowns.substring(0,firstUnknown + 1).replace('?','.');
            long count1 = getCountOfValidCombos(configWithUnknowns.substring(firstUnknown + 1), arrangement, prefix1, cache);
            long count2 = getCountOfValidCombos(configWithUnknowns.substring(firstUnknown + 1), arrangement, prefix2, cache);
            return count1 + count2;
        }else{
            long validCount = 0;
            String end1 = prefix + configWithUnknowns.replace('?','#');
            String end2 = prefix + configWithUnknowns.replace('?','.');
            if(isArrangementValid(end1,arrangement)){
                validCount++;
                cache.put(end1,1L);
            }
            if(isArrangementValid(end2,arrangement)){
                validCount++;
                cache.put(end2,1L);
            }

            return validCount;
        }

    }

    public static List<String> getCombinations(String configWithUnknowns, Map<String,List<String>> cache){
        if(cache.get(configWithUnknowns) != null){
            System.out.println("Found match in cache");
            return cache.get(configWithUnknowns);
        }

        int count = StringUtils.countMatches(configWithUnknowns,'?');
        if(count > 1){
            int firstUnknown = configWithUnknowns.indexOf('?');
            List<String> prefixes = getCombinations(configWithUnknowns.substring(0,firstUnknown + 1), cache);
            List<String> postfixes = getCombinations(configWithUnknowns.substring(firstUnknown + 1), cache);
            List<String> combinations = new ArrayList<>();
            for(String pre:prefixes){
                for (String post:postfixes){
                    combinations.add(pre+post);
                }
            }
            cache.put(configWithUnknowns,combinations);
            return combinations;
        }else {
            String replace1 = configWithUnknowns.replace('?','#');
            String replace2 = configWithUnknowns.replace('?','.');
            List<String> combinations = new ArrayList<>();
            combinations.add(replace1);
            combinations.add(replace2);
            cache.put(configWithUnknowns,combinations);
            return combinations;
        }
    }

    public static boolean isArrangementValid(String configuration, int[] arrangement){
        List<Integer> splitConfig = getConfigAsList(configuration);

        if(splitConfig.size() != arrangement.length){
            return false;
        }else{
            for(int i=0;i<splitConfig.size();i++){
                if(splitConfig.get(i) != arrangement[i]){
                    return false;
                }
            }
        }
        return true;
    }

    public static List<Integer> getConfigAsList(String config){
        List<Integer> splitConfig = new ArrayList<>();
        for (int i=0;i<config.length();i++){
            if(config.charAt(i) == '.'){
                continue;
            }else{
                int count = 1;

                while(i+1<config.length() && config.charAt(i+1) == '#'){
                    count++;
                    i++;
                }

                splitConfig.add(count);
            }
        }
        return splitConfig;
    }

    public static List<Spring> getData(String fileName){
        List<Spring> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\day12\\" + fileName))) {
            String line = reader.readLine();
            int idCount = 1;
            while(line != null){
                String[] split = line.split(" ");
                String[] arrangementString = split[1].split(",");
                int[] arrangement = new int[arrangementString.length];
                for(int i=0;i<arrangementString.length;i++){
                    arrangement[i] = Integer.parseInt(arrangementString[i]);
                }
                Spring spring = new Spring(idCount,split[0],arrangement);
                data.add(spring);
                idCount++;
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
        return data;
    }

    public static List<Spring> getUnfoldedData(String fileName){
        List<Spring> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\day12\\" + fileName))) {
            String line = reader.readLine();
            int idCount = 1;
            while(line != null){
                String[] split = line.split(" ");
                String[] arrangementString = split[1].split(",");
                int[] arrangement = new int[arrangementString.length * 5];
                for(int i=0;i<arrangementString.length*5;i++){
                    arrangement[i] = Integer.parseInt(arrangementString[i%arrangementString.length]);
                }
                String config = split[0] + "?" + split[0] + "?" + split[0] + "?" + split[0] + "?" + split[0];
                Spring spring = new Spring(idCount,config,arrangement);
                data.add(spring);
                idCount++;
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
        return data;
    }

}
