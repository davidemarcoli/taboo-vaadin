package com.dala.taboo;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by lazar on 1/30/2022.
 * Project name: taboo-vaadin
 **/
class ConfigurationServiceTest {
    private String players = "Davide, Lazar";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addPersonsToTeam() {
        ConfigurationService.addPersonsToTeam(1, players, "team1");
        ArrayList<Person> playerList = new ArrayList<>(Arrays.asList(new Person("Davide"), new Person("Lazar")));
        assertArrayEquals(playerList.toArray(new Person[0]), ConfigurationService.teams.get(1).getListOfUsers().toArray(new Person[0]));
    }

    @Test
    void setNextRound() {
        ConfigurationService.setNextRound();
        Team currentTeam = ConfigurationService.teams.get(ConfigurationService.currentTeamIndex);
        ConfigurationService.setNextRound();
        assertNotSame(currentTeam, ConfigurationService.teams.get(ConfigurationService.currentTeamIndex));
    }
}