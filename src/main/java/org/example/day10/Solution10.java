package org.example.day10;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Solution10 {

    private static final int UNKNOWN = 0;
    private static final int ENCLOSED = 1;
    private static final int NOT_ENCLOSED = 2;
    private static final int MAYBE = 3;

    private static final int LOOP = 4;

    public static void main(String[] args){
        System.out.println("Day 10");

        System.out.println("--- Part 1 Sample 1---");
        Map<Coord,GridLocation> data = getData("dataSample1.txt");
        walkRoutes(data);
        GridLocation location = getFurthestLocation(data);
        System.out.println("Furthest Part 1 Sample 1: " + location.getDistanceFromStart1() + ", " + location.getDistanceFromStart2());

        System.out.println("--- Part 1 Sample 2---");
        data = getData("dataSample2.txt");
        walkRoutes(data);
        location = getFurthestLocation(data);
        System.out.println("Furthest Part 1 Sample 2: " + location.getDistanceFromStart1() + ", " + location.getDistanceFromStart2());

        System.out.println("--- Part 1 ---");
        data = getData("data.txt");
        walkRoutes(data);
        location = getFurthestLocation(data);
        System.out.println("Furthest Part 1: " + location.getDistanceFromStart1() + ", " + location.getDistanceFromStart2());

        System.out.println("--- Part 2 Sample ---");
        Map<Coord,GridLocation> sample1 = getData("enclosedSample1.txt");
        walkRoutes(sample1);
        processMaybes(sample1);
        checkForOutsideLoop(sample1);
        System.out.println("Enclosed Squares Part 2 Sample1: " + countEnclosedSquares(sample1) + " of " + sample1.size());

        Map<Coord,GridLocation> sample2 = getData("enclosedSample2.txt");
        walkRoutes(sample2);
        processMaybes(sample2);
        checkForOutsideLoop(sample2);
        System.out.println("Enclosed Squares Part 2 Sample2: " + countEnclosedSquares(sample2));

        Map<Coord,GridLocation> sample3 = getData("enclosedSample3.txt");
        walkRoutes(sample3);
        processMaybes(sample3);
        checkForOutsideLoop(sample3);
        System.out.println("Enclosed Squares Part 2 Sample3: " + countEnclosedSquares(sample3));

        System.out.println("--- Part 2 ---");
        processMaybes(data);
        checkForOutsideLoop(data);
        System.out.println("Enclosed Squares Part 2: " + countEnclosedSquares(data));
    }

    public static void checkEnclosedSquares(Map<Coord,GridLocation> data){
        for(GridLocation loc: data.values()){
            //Already determined
            if(loc.getEnclosed() == ENCLOSED || loc.getEnclosed() == LOOP || loc.getEnclosed() == NOT_ENCLOSED){
                continue;
            }

            //In Loop
            if(loc.getDistanceFromStart1() > 0 || loc.getDistanceFromStart2() > 0){
                loc.setEnclosed(LOOP);
                continue;
            }

            GridLocation[] adjacentLocs = getAdjacentLocs(loc, data);

            //Edge Not Enclosed
            for(GridLocation adjacentLoc: adjacentLocs){
                if(adjacentLoc == null || adjacentLoc.getEnclosed() == NOT_ENCLOSED){
                    loc.setEnclosed(NOT_ENCLOSED);
                    break;
                }
            }
            if(loc.getEnclosed() == NOT_ENCLOSED){
                continue;
            }

            //Surrounded By Enclosed or LOOP
            if((adjacentLocs[0].getEnclosed() == ENCLOSED || adjacentLocs[0].getEnclosed() == LOOP)
                    && (adjacentLocs[1].getEnclosed() == ENCLOSED || adjacentLocs[1].getEnclosed() == LOOP)
                    && (adjacentLocs[2].getEnclosed() == ENCLOSED || adjacentLocs[2].getEnclosed() == LOOP)
                    && (adjacentLocs[3].getEnclosed() == ENCLOSED || adjacentLocs[3].getEnclosed() == LOOP)){
                loc.setEnclosed(ENCLOSED);
                continue;
            }

            loc.setEnclosed(MAYBE);
        }
    }

    public static void processMaybes(Map<Coord,GridLocation> data){
        int numMaybes = 0;
        int newNumMaybes = 0;

        do{
            numMaybes = countMaybeSquares(data);
            checkEnclosedSquares(data);
            newNumMaybes = countMaybeSquares(data);
        }while(numMaybes != newNumMaybes);

        for(GridLocation loc: data.values()) {
            if(loc.getEnclosed() == MAYBE){
                loc.setEnclosed(ENCLOSED);
            }
        }
    }

    public static int countEnclosedSquares(Map<Coord,GridLocation> data){
        int count = 0;
        for(GridLocation loc: data.values()) {
            if(loc.getEnclosed() == ENCLOSED){
                count++;
            }
        }
        return count;
    }

    public static int countNotEnclosedSquares(Map<Coord,GridLocation> data){
        int count = 0;
        for(GridLocation loc: data.values()) {
            if(loc.getEnclosed() == NOT_ENCLOSED){
                count++;
            }
        }
        return count;
    }

    public static int countMaybeSquares(Map<Coord,GridLocation> data){
        int count = 0;
        for(GridLocation loc: data.values()) {
            if(loc.getEnclosed() == MAYBE){
                count++;
            }
        }
        return count;
    }

    public static void checkForOutsideLoop(Map<Coord,GridLocation> data){
        for(GridLocation loc: data.values()) {
            if(loc.getEnclosed() == ENCLOSED){
                int numXLoops = countXLoops(loc.getLocation().getX(),loc.getLocation().getY(), data);
                if(numXLoops%2==0){
                    loc.setEnclosed(NOT_ENCLOSED);
                    continue;
                }

                int numYLoops = countYLoops(loc.getLocation().getX(),loc.getLocation().getY(), data);
                if(numYLoops%2==0){
                    loc.setEnclosed(NOT_ENCLOSED);
                }
            }
        }
    }

    public static int countXLoops(int x, int y, Map<Coord,GridLocation> data){
        int count = 0;
        List<GridLocation> locations = new ArrayList<>();
        for (int i=0;i<x;i++){
            if(data.get(new Coord(i,y)).getEnclosed() == LOOP) {
                locations.add(data.get(new Coord(i, y)));
            }
        }

        for(int i=0;i<locations.size();i++){
            GridLocation loc = locations.get(i);
            GridLocation nextLoc = null;
            switch (loc.getType()){
                case '|':
                    count++;
                    break;
                case 'F':
                    nextLoc = locations.get(++i);
                    while(nextLoc.getType() == '-'){
                        nextLoc = locations.get(++i);
                    }
                    if(nextLoc.getType() == 'J'){
                        count++;
                    }
                    break;
                case 'L':
                    nextLoc = locations.get(++i);
                    while(nextLoc.getType() == '-'){
                        nextLoc = locations.get(++i);
                    }
                    if(nextLoc.getType() == '7'){
                        count++;
                    }
                    break;
            }
        }

        return count;
    }

    public static int countYLoops(int x, int y, Map<Coord,GridLocation> data){
        int count = 0;
        List<GridLocation> locations = new ArrayList<>();
        for (int i=0;i<y;i++){
            if(data.get(new Coord(x,i)).getEnclosed() == LOOP) {
                locations.add(data.get(new Coord(x, i)));
            }
        }

        for(int i=0;i<locations.size();i++){
            GridLocation loc = locations.get(i);
            GridLocation nextLoc = null;
            switch (loc.getType()){
                case '-':
                    count++;
                    break;
                case 'F':
                    nextLoc = locations.get(++i);
                    while(nextLoc.getType() == '|'){
                        nextLoc = locations.get(++i);
                    }
                    if(nextLoc.getType() == 'J'){
                        count++;
                    }
                    break;
                case '7':
                    nextLoc = locations.get(++i);
                    while(nextLoc.getType() == '|'){
                        nextLoc = locations.get(++i);
                    }
                    if(nextLoc.getType() == 'L'){
                        count++;
                    }
                    break;
            }
        }

        return count;
    }

    public static GridLocation getFurthestLocation(Map<Coord,GridLocation> data){
        GridLocation location = null;
        int currentFurthest = 0;
        for(GridLocation loc: data.values()){
            if(loc.getType() != 'S' && loc.getDistanceFromStart1() != 0){
                int closest = loc.getDistanceFromStart1();
                if(loc.getDistanceFromStart1() > loc.getDistanceFromStart2()){
                    closest = loc.getDistanceFromStart2();
                }
                if(closest > currentFurthest){
                    location = loc;
                    currentFurthest = closest;
                }
            }
        }
        return location;
    }

    public static void walkRoutes(Map<Coord,GridLocation> data){
        GridLocation startLocation = getSLocation(data);
        GridLocation currentLocation = startLocation.clone();
        GridLocation previousLocation = null;

        do{
            GridLocation nextLocation = walkStepInDirection(1, currentLocation, previousLocation, data);
            previousLocation = currentLocation.clone();
            currentLocation = nextLocation.clone();
        }while(currentLocation.getLocation() != startLocation.getLocation());

        currentLocation = startLocation.clone();
        previousLocation = null;

        do{
            GridLocation nextLocation = walkStepInDirection(2, currentLocation, previousLocation, data);
            previousLocation = currentLocation.clone();
            currentLocation = nextLocation.clone();
        }while(currentLocation.getLocation() != startLocation.getLocation());
    }

    public static GridLocation getSLocation(Map<Coord,GridLocation> data){
        for(GridLocation loc: data.values()){
            if(loc.getType() == 'S'){
                return loc;
            }
        }
        return null;
    }

    public static GridLocation walkStepInDirection(int direction, GridLocation currentLoc, GridLocation previousLoc, Map<Coord,GridLocation> data){
        GridLocation nextLocation = null;
        if(previousLoc == null && direction == 1){
            nextLocation = data.get(currentLoc.getConnection1());
        }else if(previousLoc == null && direction == 2){
            nextLocation = data.get(currentLoc.getConnection2());
        }else{
            if(currentLoc.getConnection1().equals(previousLoc.getLocation())){
                nextLocation = data.get(currentLoc.getConnection2());
            }else{
                nextLocation = data.get(currentLoc.getConnection1());
            }
        }

        if(direction == 1){
            nextLocation.setDistanceFromStart1(currentLoc.getDistanceFromStart1() + 1);
        }else{
            nextLocation.setDistanceFromStart2(currentLoc.getDistanceFromStart2() + 1);
        }
        return nextLocation;
    }

    public static Map<Coord,GridLocation> getData(String fileName){
        Map<Coord,GridLocation> data = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\day10\\" + fileName))) {
            String line = reader.readLine();
            int row = 0;
            while(line != null){
                for(int i=0;i<line.length();i++){
                    char type = line.charAt(i);
                    GridLocation loc = new GridLocation(type,new Coord(i,row));
                    setConnections(loc);
                    data.put(loc.getLocation(),loc);
                }
                line = reader.readLine();
                row++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }
        setSLocation(data);
        return data;
    }

    public static void setConnections(GridLocation location){
        switch(location.getType()){
            case '|':
                location.setConnection1(new Coord(location.getLocation().getX(),location.getLocation().getY() - 1));
                location.setConnection2(new Coord(location.getLocation().getX(),location.getLocation().getY() + 1));
                break;
            case '-':
                location.setConnection1(new Coord(location.getLocation().getX() - 1,location.getLocation().getY()));
                location.setConnection2(new Coord(location.getLocation().getX() + 1,location.getLocation().getY()));
                break;
            case '7':
                location.setConnection1(new Coord(location.getLocation().getX() - 1,location.getLocation().getY()));
                location.setConnection2(new Coord(location.getLocation().getX(),location.getLocation().getY() + 1));
                break;
            case 'F':
                location.setConnection1(new Coord(location.getLocation().getX() + 1,location.getLocation().getY()));
                location.setConnection2(new Coord(location.getLocation().getX(),location.getLocation().getY() + 1));
                break;
            case 'L':
                location.setConnection1(new Coord(location.getLocation().getX() + 1,location.getLocation().getY()));
                location.setConnection2(new Coord(location.getLocation().getX(),location.getLocation().getY() - 1));
                break;
            case 'J':
                location.setConnection1(new Coord(location.getLocation().getX() - 1,location.getLocation().getY()));
                location.setConnection2(new Coord(location.getLocation().getX(),location.getLocation().getY() - 1));
                break;
            default:
        }
    }

    public static void setSLocation(Map<Coord,GridLocation> data){
        for(GridLocation loc: data.values()){
            if(loc.getType() == 'S'){
                GridLocation[] adjacentLocations = getSurroundingLocs(loc, data);
                for(GridLocation adjacentLoc:adjacentLocations){
                    if(adjacentLoc != null && (loc.getLocation().equals(adjacentLoc.getConnection1()) || loc.getLocation().equals(adjacentLoc.getConnection2()))){
                        if(loc.getConnection1() == null){
                            loc.setConnection1(new Coord(adjacentLoc.getLocation().getX(), adjacentLoc.getLocation().getY()));
                        }else{
                            loc.setConnection2(new Coord(adjacentLoc.getLocation().getX(), adjacentLoc.getLocation().getY()));
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    public static GridLocation[] getSurroundingLocs(GridLocation location, Map<Coord,GridLocation> data){
        GridLocation[] locations = new GridLocation[8];
        locations[0] = data.get(new Coord(location.getLocation().getX() - 1, location.getLocation().getY() + 1));
        locations[1] = data.get(new Coord(location.getLocation().getX(), location.getLocation().getY() + 1));
        locations[2] = data.get(new Coord(location.getLocation().getX() + 1, location.getLocation().getY() + 1));
        locations[3] = data.get(new Coord(location.getLocation().getX() - 1, location.getLocation().getY()));
        locations[4] = data.get(new Coord(location.getLocation().getX() + 1, location.getLocation().getY()));
        locations[5] = data.get(new Coord(location.getLocation().getX() - 1, location.getLocation().getY() - 1));
        locations[6] = data.get(new Coord(location.getLocation().getX(), location.getLocation().getY() - 1));
        locations[7] = data.get(new Coord(location.getLocation().getX() + 1, location.getLocation().getY() - 1));
        return locations;
    }

    public static GridLocation[] getAdjacentLocs(GridLocation location, Map<Coord,GridLocation> data){
        GridLocation[] locations = new GridLocation[4];
        locations[0] = data.get(new Coord(location.getLocation().getX(), location.getLocation().getY() + 1));
        locations[1] = data.get(new Coord(location.getLocation().getX() + 1, location.getLocation().getY()));
        locations[2] = data.get(new Coord(location.getLocation().getX() - 1, location.getLocation().getY()));
        locations[3] = data.get(new Coord(location.getLocation().getX(), location.getLocation().getY() - 1));
        return locations;
    }

}
