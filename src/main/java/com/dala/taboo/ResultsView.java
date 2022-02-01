package com.dala.taboo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lazar on 1/30/2022.
 * Project name: taboo-vaadin
 **/
@Route(value = "results")
public class ResultsView extends VerticalLayout {
    H1 title = new H1();
    H1 winnerTeam = new H1();
    Button returnToHomeBtn = new Button();

    ArrayList<Person> mvps = getMVPs();
//    ArrayList<Person> mvps = new ArrayList<>();
    HorizontalLayout gridLayout = new HorizontalLayout();

    public ResultsView() {
        title.setText("Taboo");

        ArrayList<Team> winnerTeams = getWinnerTeams();

        StringBuilder winnerTeamName = new StringBuilder();

        for (int i = 0; i < winnerTeams.size(); i++) {
            if (i < winnerTeams.size() - 1) {
                winnerTeamName.append(winnerTeams.get(i).getTeamName()).append(", ");
            } else {
                winnerTeamName.append(winnerTeams.get(i).getTeamName());
            }
        }

        winnerTeam.setText(winnerTeamName + " won the game!");
        returnToHomeBtn.setText("Return to home");
        returnToHomeBtn.addClickListener(event -> {
            returnToHomeBtn.getUI().ifPresent(ui -> ui.navigate("/"));
        });

        add(title, winnerTeam, returnToHomeBtn);


        for (Team team : ConfigurationService.teams) {
            System.out.println(team.toString());

            Grid<Person> grid = new Grid<>(Person.class, false);
            grid.addColumn(Person::getUsername).setHeader("Username")
                    .setFooter(String.format("%s total members", team.getListOfUsers().size()))
                    .setAutoWidth(true).setFlexGrow(0);
            grid.addColumn(Person::getAveragePointsPerRound).setHeader("Average Score")
                    .setFooter(String.format("%s points", team.getScore()))
                    .setAutoWidth(true);
            grid.addColumn(Person::getRounds).setHeader("Rounds")
                    .setAutoWidth(true);
            grid.addColumn(createStatusComponentRenderer()).setHeader("MVP")
                    .setAutoWidth(true);

            grid.setItems(List.of(team.getListOfUsers().toArray(new Person[0])));

            H3 teamName = new H3();
            teamName.setText(team.getTeamName());

            gridLayout.add(new VerticalLayout(teamName, grid));
        }

        gridLayout.setSizeFull();
        add(gridLayout);

        style();
    }

//    private String getWinnerTeam() {
//        Team winnerTeam = ConfigurationService.teams.get(0);
//
//        for (int i = 1; i < ConfigurationService.teams.size(); i++) {
//            if (winnerTeam.getScore() > ConfigurationService.teams.get(i).getScore()) {
//                winnerTeam = ConfigurationService.teams.get(i);
//            }
//        }
//
//        return winnerTeam.getTeamName();
//    }

    /**
     * Generates MVP-Label the specified person
     */
    private final SerializableBiConsumer<Span, Person> statusComponentUpdater = (span, person) -> {
        boolean isMVP = mvps.contains(person);
        String theme = String.format("badge %s", isMVP ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(isMVP ? "MVP" : "");
    };

    /**
     * Creates a renderer for the MVP-Label
     * @return the renderer
     */
    private ComponentRenderer<Span, Person> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

    /**
     * Gets the winner teams based on the score
     * @return the winner teams
     */
    private ArrayList<Team> getWinnerTeams() {
        ArrayList<Team> winnerTeams = new ArrayList<>();
        double highestAverageScore = 0;


        for (Team team : ConfigurationService.teams) {
            if (winnerTeams.size() == 0) {
                winnerTeams.add(team);
            }

            boolean added = false;

            if ((double) team.getScore() / (double) team.getRounds() > highestAverageScore) {
                winnerTeams = new ArrayList<>();
                highestAverageScore = team.getScore();

                if (!added) {
                    winnerTeams.add(team);
                }

            } else if ((double) team.getScore() / (double) team.getRounds() == highestAverageScore) {
                if (!added) {
                    winnerTeams.add(team);
                }
            }
        }

        return winnerTeams;
    }

    /**
     * Gets the MVPs based on the average score per round played
     * @return the MVPs of the game
     */
    private ArrayList<Person> getMVPs() {
        ArrayList<Person> people = new ArrayList<>();

        for (Team t : ConfigurationService.teams) {
            people.addAll(t.getListOfUsers());
        }

        ArrayList<Person> mvps = new ArrayList<>();
        double highestAverageScore = 0;


        for (Person p : people) {
            if (p.getRounds() == 0) {
                continue;
            }

            if (mvps.size() == 0) {
                mvps.add(p);
            }

            boolean added = false;

            if ((double) p.getScore() / (double) p.getRounds() > highestAverageScore) {
                mvps = new ArrayList<>();
                highestAverageScore = (double) p.getScore() / (double) p.getRounds();

                if (!added) {
                    mvps.add(p);
                }

            } else if ((double) p.getScore() / (double) p.getRounds() == highestAverageScore) {
                if (!added) {
                    mvps.add(p);
                }
            }
        }

        return mvps;
    }

    /**
     * Styles the page
     */
    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
