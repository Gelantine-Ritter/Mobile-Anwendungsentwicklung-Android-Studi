package com.example.travelquest.database.logic;

public class DestinationPercentage implements Comparable<DestinationPercentage> {
    String destinationName;
    double score;

    public DestinationPercentage(String destinationName, double score) {
        this.destinationName = destinationName;
        this.score = score;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public double getScore() {
        return score;
    }

    @Override
    public int compareTo(DestinationPercentage dp) {
        return Double.compare(dp.score, this.score);
    }
}