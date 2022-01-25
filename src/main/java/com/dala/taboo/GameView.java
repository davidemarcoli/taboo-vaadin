package com.dala.taboo;

import com.dala.taboo.config.ConfigView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;

@Push
@Route(value = "game")
public class GameView extends VerticalLayout {
    static H3 timeLeft = new H3();
    TabooWord currentWord;
    Button skipButton = new Button();
    Button correctButton = new Button();

    Paragraph correctWords = new Paragraph("0");
    Paragraph skippedWords = new Paragraph("0");

    H2 word = new H2();
    UnorderedList tabooWords = new UnorderedList();

    Notification notification = new Notification();

    Thread timer;

    public GameView() {
        if (DataService.data.size() < 1) {
            UI.getCurrent().navigate(ConfigView.class);
            UI.getCurrent().getPage().reload();
            return;
        }

        GameState.skippedWords = 0;
        GameState.correctWords = 0;

        style();

        skipButton.setText("Start game");

        skipButton.addClickListener(event -> {
            newWord();
            if (skipButton.getText().contains("Start")) {
                startTimer();
                correctWords.setText("Correct Words: " + GameState.correctWords);
                correctWords.setVisible(true);
                skippedWords.setVisible(true);
                skipButton.setText("Next word");
                correctButton.setVisible(true);
            } else {
                GameState.skippedWords++;
            }
            skippedWords.setText("Skipped Words: " + GameState.skippedWords);
        });

        correctWords.setVisible(false);
        skippedWords.setVisible(false);

        correctButton.setText("Correct");
        correctButton.setVisible(false);
        correctButton.getStyle().set("background-color", "green");
        correctButton.getStyle().set("color", "black");
        correctButton.getStyle().set("font-weight", "700");

        correctButton.addClickListener(event -> {
            newWord();
            GameState.correctWords++;
            correctWords.setText("Correct Words: " + GameState.correctWords);
        });

        add(word, tabooWords, new HorizontalLayout(skipButton, correctButton), timeLeft, correctWords, skippedWords);

        System.out.println("It should now all be added!");

        //startTimer();
//        if (this.getUI().isPresent())
//            this.getUI().get().addAttachListener(event -> startTimer());
    }

    public void startTimer() {
        try {
            timer.interrupt();
        } catch (Exception ignore) { }
        timer = new Thread(new TimeManagement(20, timeLeft, getUI().get(), this));
        timer.start();

//        synchronized (timer) {
//            System.out.println("Timer finished");
//            try {
//                timer.wait();
//                System.out.println("The Timer wait has finished!");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

       // System.out.println("Wait finished!");

    //    timeLeft.setText("Time is up!!!");
    }

    public void newWord() {

        currentWord = DataService.getRandomWord();

        System.out.println(currentWord);

        if (currentWord == null) {
            getUI().ifPresent(event -> {
                notification = Notification.show("All words shown!", 5000, Notification.Position.BOTTOM_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_PRIMARY);
                getUI().get().navigate("config");
            });
            return;
        }

        word.setText(currentWord.getWord());

        tabooWords.removeAll();
        for (int i = 0; i < currentWord.getTabooWords().length; i++) {
            tabooWords.add(new ListItem(currentWord.getTabooWords()[i]));
        }
    }

    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);

        getStyle().set("text-align", "center");

        tabooWords.getStyle().set("text-align", "left");
    }
}