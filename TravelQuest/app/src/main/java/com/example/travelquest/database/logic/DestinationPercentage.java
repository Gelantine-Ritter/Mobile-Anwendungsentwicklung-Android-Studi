package com.example.travelquest.database.logic;

/**
 * Klasse repräsentiert ein Objekt, welches den berechneten
 * Prozentsatz zu dem jeweiligen Zielort hällt
 */
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