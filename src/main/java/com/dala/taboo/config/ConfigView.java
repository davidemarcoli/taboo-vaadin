package com.dala.taboo.config;

import com.dala.taboo.ConfigurationService;
import com.dala.taboo.DataService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.addon.sliders.PaperSlider;


@Route(value = "config")
public class ConfigView extends VerticalLayout {

    H1 title = new H1();
    PaperSlider paperSlider = new PaperSlider(30, 120, 60);
    Text timePerRoundText = new Text("60");
    TextField teamOne = new TextField();
    TextField teamTwo = new TextField();
    Paragraph sliderText = new Paragraph();

    Select<String> categorySelect = new Select<>();

//    MultiSelectListBox<String> listBox = new MultiSelectListBox<>();

    Select<String> languageSelect = new Select<>();

    Button applyButton = new Button();

    public ConfigView() {

        categorySelect.setPlaceholder("Category");
        languageSelect.setPlaceholder("Language");

        title.setText("Taboo");
        add(title);

//        Integer.parseInt(timePerRoundText.getText());

        String[] categories = DataService.getAllCategories();
        categorySelect.setItems(categories);
//        listBox.setItems(categories);

        String[] languages = DataService.getAllLanguages();
        languageSelect.setItems(languages);

        add(categorySelect, /*listBox, */languageSelect);

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

        teamTwo.setLabel("Team 2");
        add(sliderText, new HorizontalLayout(paperSlider, timePerRoundText), teamOne, teamTwo);

        applyButton.setText("Apply Configuration");
        applyButton.addClickListener(event -> {
            ConfigurationService.addPersonsToTeam(ConfigurationService.team1, teamOne.getValue());
            ConfigurationService.addPersonsToTeam(ConfigurationService.team2, teamTwo.getValue());
            ConfigurationService.roundLength = paperSlider.getValue();
            DataService.insertWords(categorySelect.getValue(), languageSelect.getValue());
            applyButton.getUI().ifPresent(ui -> ui.navigate("game"));
        });
        add(applyButton);

        style();
    }

    private void style() {
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        getStyle().set("text-align", "center");
    }
}
