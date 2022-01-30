package com.dala.taboo;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;

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
    Button resultsButton = new Button();

    HorizontalLayout scoresLayout = new HorizontalLayout();
    HorizontalLayout buttonLayout = new HorizontalLayout();

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

        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Attention!");
        dialog.setText("Do you really want to go to results and end the game?");

        dialog.setCancelable(true);

        dialog.setConfirmText("End Game");
        dialog.addConfirmListener(event -> startButton.getUI().ifPresent(ui -> ui.navigate("results")));

        resultsButton.setText("Go to results!");
        resultsButton.addClickListener(event -> {
            dialog.open();
        });

        buttonLayout.add(startButton, resultsButton);
        add(buttonLayout);

        for (int i = 0; i < ConfigurationService.teams.size(); i++) {
            H3 totalScore = new H3(ConfigurationService.teams.get(i).getScore() + " points");

            totalScore.getStyle().set("font-weight", "bold");
            totalScore.getStyle().set("font-size", "1em");

            scoresLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);

            ArrayList<String> scores = getScores(ConfigurationService.teams.get(i));

            VerticalLayout teamScoresLayout = new VerticalLayout();
            teamScoresLayout.getStyle().set("padding", "0");

            teamScoresLayout.add(new H3(ConfigurationService.teams.get(i).getTeamName()));

            VerticalLayout scoreList = new VerticalLayout();
            scoreList.getStyle().set("text-align", "left");
            scoreList.getStyle().set("padding", "0");

            for (String score : scores) {
                HtmlComponent br = new HtmlComponent("br");
                scoreList.add(new Text(score), br);
            }

            teamScoresLayout.add(scoreList);
            teamScoresLayout.add(totalScore);

            teamScoresLayout.getStyle().set("width", "100%");

            scoresLayout.add(teamScoresLayout);
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

    private ArrayList<String> getScores(Team team) {
        ArrayList<String> scores = new ArrayList<>();

        for (int i = 0; i < team.getListOfUsers().size(); i++) {
            scores.add(team.getListOfUsers().get(i).getUsername() + ": " + team.getListOfUsers().get(i).getScore());
        }
        return scores;
    }
}
