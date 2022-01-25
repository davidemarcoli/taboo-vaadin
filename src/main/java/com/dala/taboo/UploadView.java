package com.dala.taboo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.io.InputStream;


@Route("")
public class UploadView extends VerticalLayout {
    Thread timer;
    static H3 timeLeft = new H3();

    public UploadView() {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("application/zip", "application/json");

        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);

            if (fileName.endsWith(".zip")) {
                System.out.println("ZIP");
                try {
                    DataService.addZip(buffer.getFileData(fileName).getFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("JSON");
                DataService.addStream(inputStream);
            }


            // Do something with the file data
            // processFile(inputStream, fileName);
        });

        Button toGame = new Button();
        toGame.setText("Apply configuration");


        toGame.addClickListener(event -> {
//            UI.getCurrent().navigate(GameView.class);

            DataService.printAll();
            toGame.getUI().ifPresent(ui -> ui.navigate("game"));
        });

        add(new VerticalLayout(
                upload,
                toGame
        ));

    }

    /*public void startTimer() {
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
    }*/
}