package com.dala.taboo;

import java.util.ArrayList;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/
public class ConfigurationService {
    public static int roundLength = 60;
    public static Team team1 = new Team();
    public static Team team2 = new Team();

    public static void addPersonsToTeam(Team team, String players, String teamName) {
        String[] splittedPlayers = players.split(",");

        for (String player: splittedPlayers) {
            team.getListOfUsers().add(new Person(player.trim()));
            team.setTeamName(teamName);
        }

        System.out.println(team1.getListOfUsers().toString());
        System.out.println(team2.getListOfUsers().toString());
    }
}
