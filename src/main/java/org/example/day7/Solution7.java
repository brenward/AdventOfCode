package org.example.day7;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class Solution7 {

    public static void main(String[] args){
        System.out.println("Day 7");

        System.out.println("--- Part 1 Sample ---");
        TreeSet<Hand> sampleData = getSampleData();
        /*for(Hand hand:sampleData){
            System.out.println("Cards: " + hand.getCards() + " bet: " + hand.getBet() + " Rank: " + hand.getType());
        }*/
        System.out.println("Winnings: " + calculateWinnings(sampleData));

        System.out.println("--- Part 1 ---");
        TreeSet<Hand> part1Data = getData();
        System.out.println("Winnings: " + calculateWinnings(part1Data));


        System.out.println("--- Part 2 Sample ---");
        TreeSet<Hand> sampleDataPart2 = getSampleData();
        for(Hand hand:sampleDataPart2){
            System.out.println("Cards: " + hand.getCards() + " bet: " + hand.getBet() + " Rank: " + hand.getType());
        }
        System.out.println("Winnings: " + calculateWinnings(sampleDataPart2));

        System.out.println("--- Part 2 ---");
        TreeSet<Hand> part2Data = getData();
        for(Hand hand:part2Data){
            System.out.println("Cards: " + hand.getCards() + " bet: " + hand.getBet() + " Rank: " + hand.getType());
        }
        System.out.println("Winnings: " + calculateWinnings(part2Data));
    }

    public static TreeSet<Hand> getSampleData(){
        TreeSet<Hand> data = new TreeSet<>();
        Hand h1 = new Hand("32T3K",765);
        data.add(h1);
        Hand h2 = new Hand("T55J5",684);
        data.add(h2);
        Hand h3 = new Hand("KK677",28);
        data.add(h3);
        Hand h4 = new Hand("KTJJT",220);
        data.add(h4);
        Hand h5 = new Hand("QQQJA",483);
        data.add(h5);
        return data;
    }

    public static TreeSet<Hand> getData(){
        TreeSet<Hand> data = new TreeSet<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\org\\example\\day7\\data.txt"))) {
            String line = reader.readLine();
            while(line != null){
                String[] splitLine = line.split(",");
                Hand hand = new Hand(splitLine[0],Integer.parseInt(splitLine[1]));
                data.add(hand);
                line = reader.readLine();;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Error reading from file");
        }

        return data;
    }

    public static long calculateWinnings(TreeSet<Hand> data){
        int ranking = data.size();
        long winnings = 0;
        for(Hand hand:data){
            winnings += ranking*hand.getBet();
            ranking--;
        }
        return winnings;
    }

}
