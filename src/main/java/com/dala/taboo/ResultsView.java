package com.dala.taboo;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
public class ResultsView extends HorizontalLayout {
    H1 title = new H1();

//    ArrayList<Person> mvps = getMVPs();
    ArrayList<Person> mvps = new ArrayList<>();
    HorizontalLayout gridLayout = new HorizontalLayout();

    public ResultsView() {
        title.setText("Taboo");
        style();
        add(title);


        for (Team team : ConfigurationService.teams) {
            System.out.println(team.toString());

            Grid<Person> grid = new Grid<>(Person.class, false);
            grid.addColumn(Person::getUsername).setHeader("Username")
                    .setFooter(String.format("%s total members", team.getListOfUsers().size()))
                    .setAutoWidth(true).setFlexGrow(0);
            grid.addColumn(Person::getScore).setHeader("Score")
                    .setFooter(String.format("%s points", team.getScore()))
                    .setAutoWidth(true);
            grid.addColumn(createStatusComponentRenderer()).setHeader("MVP")
                    .setAutoWidth(true);

            grid.setItems(List.of(team.getListOfUsers().toArray(new Person[0])));

            gridLayout.add(grid);
        }

        gridLayout.setSizeFull();
        add(gridLayout);

    }

    private final SerializableBiConsumer<Span, Person> statusComponentUpdater = (span, person) -> {
        boolean isMVP = mvps.contains(person);
        String theme = String.format("badge %s", isMVP ? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(isMVP ? "MVP" : "");
    };

    private ComponentRenderer<Span, Person> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private ArrayList<Person> getMVPs() {
        ArrayList<Person> people = new ArrayList<>();

        for (Team t : ConfigurationService.teams) {
            people.addAll(t.getListOfUsers());
        }

        ArrayList<Person> mvps = new ArrayList<>();


        for (Person p : people) {
            if (mvps.size() == 0) {
                mvps.add(p);
            }

            for (Person mvp: mvps) {

                boolean added = false;

                if (p.getScore() > mvp.getScore()) {

                    if (!added) {
                        mvps.add(p);
                    }
                    mvps.remove(mvp);

                } else if (p.getScore() == mvp.getScore()) {
                    if (!added) {
                        mvps.add(p);
                    }
                }
            }
        }

        return mvps;
    }
}
