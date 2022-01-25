package com.dala.taboo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;

@Route(value = "game")
public class GameView extends VerticalLayout {

    TabooWord currentWord;
    Button nextButton = new Button();

    H2 word = new H2();
    UnorderedList tabooWords = new UnorderedList();

    public GameView() {
        if (DataService.files.size() < 1) {
            UI.getCurrent().navigate(UploadView.class);
            UI.getCurrent().getPage().reload();
            return;
        }

        newWord();

        nextButton.setText("Next Word!");

        nextButton.addClickListener(event -> {
            newWord();
        });

        add(word, tabooWords, nextButton);
    }

    public void newWord() {

        currentWord = DataService.getRandomWord();

        System.out.println(currentWord);

        word.setText(currentWord.getWord());

        tabooWords.removeAll();
        for (int i = 0; i < currentWord.getTabooWords().length; i++) {
            tabooWords.add(new ListItem(currentWord.getTabooWords()[i]));
        }

    }
}