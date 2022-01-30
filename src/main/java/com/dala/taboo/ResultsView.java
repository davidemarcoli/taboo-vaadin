package com.dala.taboo;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Created by lazar on 1/30/2022.
 * Project name: taboo-vaadin
 **/
@Route(value = "results")
public class ResultsView extends VerticalLayout {
    H1 title = new H1();
    H1 winnerTeam = new H1();

    public ResultsView() {
        title.setText("Taboo");
        winnerTeam.setText(getWinnerTeam() + " won the game!");
        style();
        add(title, winnerTeam);
    }

    private String getWinnerTeam() {
        for (int i = 0; i < ConfigurationService.teams.size(); i++) {
            if (ConfigurationService.teams.get(i).getScore() > ConfigurationService.teams.get(i+1).getScore()) {
                return ConfigurationService.teams.get(i).getTeamName();
            } else {
                return ConfigurationService.teams.get(i+1).getTeamName();
            }
        }
        return null;
    }

    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
