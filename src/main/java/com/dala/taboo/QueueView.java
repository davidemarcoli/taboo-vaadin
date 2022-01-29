package com.dala.taboo;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;

import java.util.Random;

/**
 * Created by lazar on 1/29/2022.
 * Project name: taboo-vaadin
 **/
@Route(value = "queue")
public class QueueView extends VerticalLayout {
    H1 title = new H1();
    H3 player = new H3();

    public QueueView() {
        title.setText("Taboo");

        style();
        add(title);
    }

    /**
     * Get random player and change from each team every round
     */
    public void getRandomPlayer(Team currentTeam) {
        Random r = new Random();
        int randomNr = r.nextInt(currentTeam.getListOfUsers().size());
        if (currentTeam.getTeamName().equals("team1")) {

        } else {

        }
        ConfigurationService.team1.getListOfUsers();
        ConfigurationService.team2.getListOfUsers();
    }

    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
