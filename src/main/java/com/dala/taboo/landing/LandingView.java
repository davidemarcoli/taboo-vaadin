package com.dala.taboo.landing;

import com.dala.taboo.ConfigurationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/
@Route(value = "")
public class LandingView extends VerticalLayout {
    H1 word = new H1("Taboo");
    Button configBtn = new Button();
    Button customBtn = new Button();

    public LandingView() {
        style();
        add(word, configBtn, customBtn);


        configBtn.setText("CONFIGURE GAME");

        configBtn.addClickListener(event -> {
            configBtn.getUI().ifPresent(ui -> {
                ConfigurationService.customGame = false;
                ui.navigate("config");
            });
        });


        customBtn.setText("CUSTOM GAME");

        customBtn.addClickListener(event -> {
            customBtn.getUI().ifPresent(ui -> {
                ConfigurationService.customGame = true;
                ui.navigate("upload");
            });
        });
    }

    /**
     * Styling of the components
     */
    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);

        getStyle().set("text-align", "center");

        word.getStyle().set("text-align", "left");
    }
}
