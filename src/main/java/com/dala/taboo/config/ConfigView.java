package com.dala.taboo.config;

import com.dala.taboo.ConfigurationService;
import com.dala.taboo.DataService;
import com.dala.taboo.Team;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.addon.sliders.PaperSlider;

import java.util.ArrayList;
import java.util.List;

@Route(value = "config")
public class ConfigView extends VerticalLayout {
    H1 title = new H1();
    PaperSlider paperSlider = new PaperSlider(5, 120, 60);
    Text timePerRoundText = new Text("60");
    TextField teamOne = new TextField();
    TextField teamTwo = new TextField();
    Paragraph sliderText = new Paragraph();
    Select<String> categorySelect = new Select<>();
    Select<String> languageSelect = new Select<>();
    Button applyButton = new Button();
    Notification notification = new Notification();

    /**
     * Instantiates a new Config view.
     */
    public ConfigView() {
        categorySelect.setPlaceholder("Category");
        languageSelect.setPlaceholder("Language");
        title.setText("Taboo");
        add(title);

        String[] categories = DataService.getAllCategories();
        categorySelect.setItems(categories);
        categorySelect.setEmptySelectionAllowed(false);
        teamOne.setRequiredIndicatorVisible(true);
        teamOne.setErrorMessage("This field is required");

        String[] languages = DataService.getAllLanguages();
        languageSelect.setItems(languages);
        languageSelect.setEmptySelectionAllowed(false);
        teamOne.setRequiredIndicatorVisible(true);
        teamOne.setErrorMessage("This field is required");

        if (!ConfigurationService.customGame)
            add(categorySelect, languageSelect);

        paperSlider.addValueChangeListener(event -> {
            timePerRoundText.setText(paperSlider.getValue().toString());
        });

        paperSlider.getStyle().set("padding-top", "0");

        sliderText.setText("Round duration in seconds");
        sliderText.getStyle().set("margin-bottom", "0px");
        sliderText.getStyle().set("margin-top", "30px");
        sliderText.getStyle().set("color", "grey");

        teamOne.setLabel("Team 1");
        teamOne.setPlaceholder("User1, user2, USER3");
        teamOne.setMinLength(1);
        teamOne.setRequiredIndicatorVisible(true);
        teamOne.setErrorMessage("This field is required");

        teamTwo.setLabel("Team 2");
        teamTwo.setMinLength(1);
        teamTwo.setRequiredIndicatorVisible(true);
        teamTwo.setErrorMessage("This field is required");

        add(sliderText, new HorizontalLayout(paperSlider, timePerRoundText), teamOne, teamTwo);

        applyButton.setText("Apply Configuration");
        applyButton.addClickListener(event -> {
            ConfigurationService.teams = new ArrayList<>(List.of(new Team[]{new Team(), new Team()}));
            if (teamTwo.isEmpty() || teamOne.isEmpty() || ((categorySelect.isEmpty() || languageSelect.isEmpty()) && !ConfigurationService.customGame)) {
                notification = Notification.show("Please fill out all Fields!", 5000, Notification.Position.BOTTOM_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            ConfigurationService.addPersonsToTeam(0, teamOne.getValue(), "Team 1");
            ConfigurationService.addPersonsToTeam(1, teamTwo.getValue(), "Team 2");
            ConfigurationService.roundLength = paperSlider.getValue();

            if (!ConfigurationService.customGame)
                DataService.insertWords(categorySelect.getValue(), languageSelect.getValue());

            applyButton.getUI().ifPresent(ui -> ui.navigate("queue"));
        });
        add(applyButton);
        style();
    }

    /**
     * Styling of the components.
     */
    private void style() {
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        getStyle().set("text-align", "center");
    }
}
