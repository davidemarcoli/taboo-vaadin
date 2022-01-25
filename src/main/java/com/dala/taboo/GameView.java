package com.dala.taboo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.Timer;

@Push
@Route(value = "game")
public class GameView extends VerticalLayout {
    static H3 timeLeft = new H3();
    TabooWord currentWord;
    Button nextButton = new Button();

    H2 word = new H2();
    UnorderedList tabooWords = new UnorderedList();

    Notification notification = new Notification();

    Thread timer;

    public GameView() {
        if (DataService.data.size() < 1) {
            UI.getCurrent().navigate(UploadView.class);
            UI.getCurrent().getPage().reload();
            return;
        }

      
        style();


        nextButton.setText("Start game");

        nextButton.addClickListener(event -> {
            newWord();
            startTimer();
            nextButton.setText("Next word");
        });

        add(word, tabooWords, nextButton, timeLeft);

        System.out.println("It should now all be added!");

        //startTimer();
//        if (this.getUI().isPresent())
//            this.getUI().get().addAttachListener(event -> startTimer());
    }

    public void startTimer() {
        try {
            timer.interrupt();
        } catch (Exception ignore) { }
        timer = new Thread(new TimeManagement(12, timeLeft, getUI().orElse(null), this));
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