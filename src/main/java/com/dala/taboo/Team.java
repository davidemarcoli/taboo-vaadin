package com.dala.taboo;

import java.util.ArrayList;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/
public class Team {
    private ArrayList<Person> listOfUsers = new ArrayList<>();
    private int score = 0;
    private String teamName = "";

    public Team(ArrayList<Person> listOfUsers, int score, String teamName) {
        this.listOfUsers = listOfUsers;
        this.score = score;
        this.teamName = teamName;
    }

    public Team() {}

    public ArrayList<Person> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(ArrayList<Person> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
