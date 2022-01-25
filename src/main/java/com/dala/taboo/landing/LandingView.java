package com.dala.taboo.landing;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/
@Route(value = "")
public class LandingView extends VerticalLayout {
    H1 word = new H1("Taboo");
    Button configBtn = new Button();

    public LandingView() {
        style();
        add(word, configBtn);


        configBtn.setText("CONFIGURE GAME");

        configBtn.addClickListener(event -> {
            configBtn.getUI().ifPresent(ui -> {
                ui.navigate("config");
            });
        });
    }

    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);

        getStyle().set("text-align", "center");

        word.getStyle().set("text-align", "left");
    }
}
