package com.dala.taboo;

import lombok.Data;
import lombok.ToString;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/

@ToString
@Data
public class Person {
    private String username;
    private int score;
    private int rounds;

    public Person(String username) {
        this.username = username;
        this.score = 0;
        this.rounds = 0;
    }

    /**
     * This method is used to add points to the person.
     */
    public void addScore() {
        this.score++;
    }

    /**
     * This method is used to add rounds to the person.
     */
    public void addRound() {
        this.rounds++;
    }


    /**
     * This method is used to get the the average points per round.
     * @return the average points per round.
     */
    public double getAveragePointsPerRound() {
        return (double) this.score / (double) this.rounds;
    }
}
