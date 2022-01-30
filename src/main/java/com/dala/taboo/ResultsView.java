package com.dala.taboo;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;

/**
 * Created by lazar on 1/30/2022.
 * Project name: taboo-vaadin
 **/
@Route(value = "results")
public class ResultsView extends HorizontalLayout {
    H1 title = new H1();

    public ResultsView() {
        title.setText("Taboo");
        style();
        add(title);
    }

    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
