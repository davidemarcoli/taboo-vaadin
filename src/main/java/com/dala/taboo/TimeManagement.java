package com.dala.taboo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H3;

import java.time.LocalTime;

import static java.time.LocalTime.now;

/**
 * Created by lazar on 1/25/2022.
 * Project name: taboo-vaadin
 **/
public class TimeManagement implements Runnable {

    static LocalTime start;
    static LocalTime quizStart = now();
    static LocalTime end;
    static UI ui;
    static GameView gameView;

    static int timerLength;
    static H3 text;

    @Override
    public void run() {

        System.out.println("Timer started");
        start = now();

        end = start.plusSeconds(12);

        System.out.println("Start: " + start);
        System.out.println("End: " + end);

        while (now().isBefore(end)/*&& !QuizManager.enteredAnswer*/) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
            //GameView.timeLeft.setText(String.valueOf(end.toSecondOfDay() - now().toSecondOfDay()));
            ui.access(() -> {
                gameView.timeLeft.setText(String.valueOf(end.toSecondOfDay() - now().toSecondOfDay()) + " seconds left");
                //gameView.timeLeft.setText(String.format("%1$3s", end.toSecondOfDay() - now().toSecondOfDay()) + " seconds left");
            });
        }

        System.out.println("Timer ended");

        ui.access(() -> {
            gameView.timeLeft.setText("Timer ended!");
        });

        synchronized (this) {
            System.out.println("Timer synchronized");
            this.notify();
            System.out.println("Game has been notified!");
        }
    }

    public TimeManagement(int timerLength, H3 text, UI ui, GameView gameView) {
        this.timerLength = timerLength;
        this.text = text;
        this.ui = ui;
        this.gameView = gameView;
    }

    public static long secondsLeft() {
        return end.toSecondOfDay() - now().toSecondOfDay();
    }
}
