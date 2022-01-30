package com.dala.taboo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 */
public class ConfigurationService {
    public static int roundLength = 60;
    public static ArrayList<Team> teams = new ArrayList<>(List.of(new Team[]{new Team(), new Team()}));
    public static int currentTeamIndex = -1;
    public static boolean customGame = false;
    private static final Random rand = new Random();

    /**
     * Add persons to team.
     *
     * @param teamIndex the team index
     * @param players   the players
     * @param teamName  the team name
     */
    public static void addPersonsToTeam(int teamIndex, String players, String teamName) {
        String[] splittedPlayers = players.split(",");

        for (String player: splittedPlayers) {
            teams.get(teamIndex).getListOfUsers().add(new Person(player.trim()));
            teams.get(teamIndex).setTeamName(teamName);
        }

        System.out.println(teams.get(teamIndex).getListOfUsers().toString());
        System.out.println(teams.get(teamIndex).getListOfUsers().toString());
    }

    /**
     * Sets next round.
     */
    public static void setNextRound() {
        if (currentTeamIndex == -1) {
            currentTeamIndex = rand.nextInt(teams.size());
        } else {
            if (currentTeamIndex < teams.size() - 1) {
                currentTeamIndex++;
            } else {
                currentTeamIndex = 0;
            }
        }

        if (teams.get(currentTeamIndex).getPersonIndex() < teams.get(currentTeamIndex).getListOfUsers().size() - 1) {
            teams.get(currentTeamIndex).setPersonIndex(teams.get(currentTeamIndex).getPersonIndex() + 1);
        } else {
            teams.get(currentTeamIndex).setPersonIndex(0);
        }
    }
}
