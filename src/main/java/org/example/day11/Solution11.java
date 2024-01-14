package org.example.day11;

import org.example.day10.GridLocation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution11 {



    public static void main(String[] args){
        System.out.println("Day 11");

        System.out.println("--- Part 1 Sample ---");
        List<List<Location>> sampleUniverse = getData("dataSample1.txt", 1);
        List<Location> galaxies = getGalaxies(sampleUniverse);
        System.out.println("distance between 5 and 9 " + calculateDistanceBetweenTwoGalaxies(galaxies.get(4), galaxies.get(8)));
        System.out.println("distance between 1 and 7 " + calculateDistanceBetweenTwoGalaxies(galaxies.get(0), galaxies.get(6)));
        System.out.println("distance between 3 and 6 " + calculateDistanceBetweenTwoGalaxies(galaxies.get(2), galaxies.get(5)));
        System.out.println("distance between 8 and 9 " + calculateDistanceBetweenTwoGalaxies(galaxies.get(7), galaxies.get(8)));
        System.out.println("Sum of distances: " + calculateSumOfDistancesBetweenGalaxies(galaxies));

        System.out.println("--- Part 1 ---");
        List<List<Location>> universe = getData("data.txt", 1);
        List<Location> galaxiesPart1 = getGalaxies(universe);
        System.out.println("Sum of distances: " + calculateSumOfDistancesBetweenGalaxies(galaxiesPart1));

        System.out.println("--- Part 2 Sample ---");
        System.out.println("Sum of distances: " + process("dataSample1.txt",1));
        System.out.println("Sum of distances: " + process("dataSample1.txt",9));
        System.out.println("Sum of distances: " + process("dataSample1.txt",99));

        System.out.println("--- Part 2 ---");
        System.out.println("Sum of distances: " + process("data.txt",999999));
    }

    public static long process(String fileName, int expansionFactor){
        List<List<Location>> universe = getUnexpandedUniverse(fileName);
        List<Location> galaxies = getGalaxies(universe);
        List<Integer> emptyColumns = findEmptyColumns(universe);
        List<Integer> emptyRows = findEmptyRows(universe);
        return calculateSumOfDistancesBetweenGalaxies(galaxies,emptyColumns,emptyRows,expansionFactor);

    }

    public static long calculateSumOfDistancesBetweenGalaxies(List<Location> galaxies){
        long sum = 0L;
        for(int i=0;i<galaxies.size() - 1;i++){
            for(int j=i+1;j< galaxies.size();j++){
                sum += calculateDistanceBetweenTwoGalaxies(galaxies.get(i), galaxies.get(j));
            }
        }
        return sum;
    }

    public static long calculateSumOfDistancesBetweenGalaxies(List<Location> galaxies, List<Integer> emptyColumns, List<Integer> emptyRows, int expansionFactor){
        long sum = 0L;
        for(int i=0;i<galaxies.size() - 1;i++){
            for(int j=i+1;j< galaxies.size();j++){
                sum += calculateDistanceBetweenTwoGalaxies(galaxies.get(i), galaxies.get(j), emptyColumns, emptyRows, expansionFactor);
            }
        }
        return sum;
    }

    public static long calculateDistanceBetweenTwoGalaxies(Location loc1, Location loc2){
        return Math.abs(loc1.getLocation().getX() - loc2.getLocation().getX()) + Math.abs(loc1.getLocation().getY() - loc2.getLocation().getY());
    }

    public static long calculateDistanceBetweenTwoGalaxies(Location loc1, Location loc2, List<Integer> emptyColumns, List<Integer> emptyRows, int expansionFactor){
        int numEmptyColumnsBetween = countNumGaps(emptyColumns,loc1.getLocation().getX(), loc2.getLocation().getX()) * expansionFactor;
        int numEmptyRowsBetween = countNumGaps(emptyRows,loc1.getLocation().getY(), loc2.getLocation().getY()) * expansionFactor;

        int x = Math.abs(loc1.getLocation().getX() - loc2.getLocation().getX()) + numEmptyColumnsBetween;
        int y = Math.abs(loc1.getLocation().getY() - loc2.getLocation().getY()) + numEmptyRowsBetween;

        return x + y;
    }

    public static int countNumGaps(List<Integer> gapLocations, int start, int end){
        int gaps = 0;
        if(start == end){
            return gaps;
        }else if(start < end) {
            for (int i = start; i < end; i++) {
                if (gapLocations.contains(i)) {
                    gaps++;
                }
            }
        }else {
            for (int i = end; i < start; i++) {
                if (gapLocations.contains(i)) {
                    gaps++;
                }
            }
        }
        return gaps;
    }

    public static List<Location> getGalaxies(List<List<Location>> data){
        List<Location> galaxyLocations = new ArrayList<>();
        for(int i=0;i<data.size();i++){
            List<Location> row = data.get(i);
            for(int j=0;j<row.size();j++){
                if(row.get(j).getType() == '#'){
                    row.get(j).setLocation(new Coord(j,i));
                    galaxyLocations.add(row.get(j));
                }
            }
        }
        return galaxyLocations;
    }

    public static List<List<Location>> getData(String fileName, int expansionFactor){
        List<List<Location>> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\day11\\" + fileName))) {
            String line = reader.readLine();
            int idCount = 1;
            while(line != null){
                List<Location> row = new ArrayList<>();
                for(int i=0;i<line.length();i++){
                    char type = line.charAt(i);
                    if(type == '#'){
                        row.add(new Location(idCount,type));
                        idCount++;
                    }else{
                        row.add(new Location(0,type));
                    }
                }
                data.add(row);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
        return expandUniverse(data, expansionFactor);
    }

    public static List<List<Location>> getUnexpandedUniverse(String fileName){
        List<List<Location>> data = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\day11\\" + fileName))) {
            String line = reader.readLine();
            int idCount = 1;
            while(line != null){
                List<Location> row = new ArrayList<>();
                for(int i=0;i<line.length();i++){
                    char type = line.charAt(i);
                    if(type == '#'){
                        row.add(new Location(idCount,type));
                        idCount++;
                    }else{
                        row.add(new Location(0,type));
                    }
                }
                data.add(row);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
        return data;
    }

    public static List<List<Location>> expandUniverse(List<List<Location>> data, int expansionFactor){
        List<List<Location>> rowExpandedData = new ArrayList<>();

        //Expand Rows
        for(List<Location> row: data){
            if(onlySpaceInRow(row)){
                for(int i=0;i<expansionFactor;i++){
                    rowExpandedData.add(new ArrayList<>(row));
                }
            }
            rowExpandedData.add(row);
        }

        return expandColumns(rowExpandedData, expansionFactor);
    }

    private static boolean onlySpaceInRow(List<Location> row){
        for (Location loc: row) {
            if(loc.getType() == '#'){
                return false;
            }
        }
        return true;
    }

    private static List<Integer> findEmptyColumns(List<List<Location>> data){
        List<Integer> emptyColumns = new ArrayList<>();
        int height = data.size();
        int width = data.get(0).size();

        for(int i=0;i<width;i++){
            boolean emptyColumn = true;
            for(int j=0;j<height;j++){
                if(data.get(j).get(i).getType() == '#'){
                    emptyColumn = false;
                    break;
                }
            }
            if(emptyColumn){
                emptyColumns.add(i);
            }
        }
        return emptyColumns;
    }

    private static List<Integer> findEmptyRows(List<List<Location>> data){
        List<Integer> emptyRows = new ArrayList<>();
        int height = data.size();
        int width = data.get(0).size();

        for(int i=0;i<height;i++){
            if(onlySpaceInRow(data.get(i))){
                emptyRows.add(i);
            }
        }
        return emptyRows;
    }

    public static List<List<Location>> expandColumns(List<List<Location>> data, int expansionFactor){
        List<Integer> emptyColumns = findEmptyColumns(data);

        List<List<Location>> expandedData = new ArrayList<>();
        int height = data.size();
        int width = data.get(0).size();
        for(int j=0;j<height;j++){
            List<Location> row = new ArrayList<>();
            for(int i=0;i<width;i++) {
                if(emptyColumns.contains(i)){
                    for(int k=0;k<expansionFactor;k++) {
                        row.add(new Location(0, '.'));
                    }
                }
                row.add(data.get(j).get(i));
            }
            expandedData.add(row);
        }
        return expandedData;
    }

}
