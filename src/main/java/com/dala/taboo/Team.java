package com.dala.taboo;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/

@Data
@ToString
public class Team {
    private ArrayList<Person> listOfUsers = new ArrayList<>();
    private int score = 0;
    private int rounds = 0;
    private String teamName = "";
    private int personIndex = 0;

    public Team(ArrayList<Person> listOfUsers, int score, String teamName) {
        this.listOfUsers = listOfUsers;
        this.score = score;
        this.teamName = teamName;
    }

    /**
     * This method is used to add score to the team
     */
    public void addScore() {
        this.score++;
    }

    /**
     * This method is used to add round to the team
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

    public Team() {}
}
