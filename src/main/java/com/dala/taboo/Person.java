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

    public void addScore() {
        this.score++;
    }

    public void addRound() {
        this.rounds++;
    }

    public double getAveragePointsPerRound() {
        return (double) this.score / (double) this.rounds;
    }
}
