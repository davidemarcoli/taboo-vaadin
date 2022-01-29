package com.dala.taboo;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/

@Data
public class Team {
    private ArrayList<Person> listOfUsers = new ArrayList<>();
    private int score = 0;
    private String teamName = "";
    private int personIndex = 0;

    public Team(ArrayList<Person> listOfUsers, int score, String teamName) {
        this.listOfUsers = listOfUsers;
        this.score = score;
        this.teamName = teamName;
    }

    public Team() {}
}