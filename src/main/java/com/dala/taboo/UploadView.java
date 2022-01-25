package com.dala.taboo;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.io.InputStream;


@Route("upload")
public class UploadView extends VerticalLayout {
    Thread timer;
    static H3 timeLeft = new H3();

    public UploadView() {
        style();

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

    private void style() {
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        setHorizontalComponentAlignment(Alignment.CENTER);

        getStyle().set("text-align", "center");
    }
}