package com.dala.taboo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by lazar on 1/29/2022.
 * Project name: taboo-vaadin
 **/
@Route(value = "queue")
public class QueueView extends VerticalLayout {
    H1 title = new H1();
    H3 player = new H3();
    H3 playerDescription = new H3();
    Button startButton = new Button();
    HorizontalLayout scoresLayout = new HorizontalLayout();
    Random rand = new Random();

    public QueueView() {
        title.setText("Taboo");
        add(title);

        playerDescription.setText("It's now the turn of");
        player.getStyle().set("color", "orange");
        player.getStyle().set("margin-left", "5px");

        getRandomPlayer();
        add(new HorizontalLayout(playerDescription, player));

        startButton.setText("Start Round!");
        startButton.addClickListener(event -> startButton.getUI().ifPresent(ui -> ui.navigate("game")));
        add(startButton);

        for (int i = 0; i < ConfigurationService.teams.size(); i++) {
            H3 totalScore = new H3(ConfigurationService.teams.get(i).getScore() + " points");

            totalScore.getStyle().set("font-weight", "bold");
            totalScore.getStyle().set("font-size", "1em");

            scoresLayout.getStyle().set("width", "250px");
            scoresLayout.add(new VerticalLayout(new H3(ConfigurationService.teams.get(i).getTeamName()),
                    new VerticalLayout(getScores(ConfigurationService.teams.get(i)).toArray(new ListItem[0])),
                    totalScore));
        }
        add(scoresLayout);
        style();
    }

    /**
     * Get random player and change from each team every round
     */
    public void getRandomPlayer() {
        ConfigurationService.setNextRound();
        int playerIndex = ConfigurationService.teams.get(ConfigurationService.currentTeamIndex).getPersonIndex();
        player.setText(ConfigurationService.teams.get(ConfigurationService.currentTeamIndex).getListOfUsers().get(playerIndex).getUsername());
    }

    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private ArrayList<ListItem> getScores(Team team) {
        ArrayList<ListItem> scores = new ArrayList<>();

        for (int i = 0; i < team.getListOfUsers().size(); i++) {
            scores.add(new ListItem(team.getListOfUsers().get(i).getUsername() + ": " + team.getListOfUsers().get(i).getScore()));
        }
        return scores;
    }
}
