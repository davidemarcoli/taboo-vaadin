package com.dala.taboo;

import java.util.ArrayList;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/
public class Team {
    private ArrayList<Person> listOfUsers = new ArrayList<>();
    private int score = 0;

    public Team(ArrayList<Person> listOfUsers, int score) {
        this.listOfUsers = listOfUsers;
        this.score = score;
    }

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
}
