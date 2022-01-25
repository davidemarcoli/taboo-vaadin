package com.dala.taboo.config;

import com.dala.taboo.ConfigurationService;
import com.dala.taboo.DataService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import org.vaadin.addon.sliders.PaperSlider;


@Route(value = "config")
public class ConfigView extends VerticalLayout {

    H1 title = new H1();
    PaperSlider paperSlider = new PaperSlider(30, 120, 60);
    Text timePerRoundText = new Text("60");

    Select<String> categorySelect = new Select<>();
//    MultiSelectListBox<String> listBox = new MultiSelectListBox<>();

    Select<String> languageSelect = new Select<>();

    Button applyButton = new Button();

    public ConfigView() {

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

        add(new HorizontalLayout(paperSlider, timePerRoundText));

        applyButton.setText("Apply Configuration");
        applyButton.addClickListener(event -> {
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
