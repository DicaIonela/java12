package com.example.demo;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OBradFrumos extends Application {
    private static TextArea rezultatCautare;
    private static Label nrInregistrari;
    private ListaMosului listaMosului;
    private Slider slider;

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("O, brad frumos!");

        // Initialize data (test or file)
        listaMosului = new ListaMosului();

        // Layout setup
        BorderPane root = new BorderPane();

        // Image section (Top)
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("brad.png")));
        imageView.setFitHeight(400); // Set height for the image
        imageView.setPreserveRatio(true); // Maintain aspect ratio
        root.setRight(imageView);

        // Controls section (Left)
        VBox controls = getControlsPane();
        root.setTop(controls);


        // Text display section (Center)
        rezultatCautare = new TextArea();
        rezultatCautare.setWrapText(true);
        rezultatCautare.setMaxHeight(400); // Limit the height of the TextArea
        rezultatCautare.setMaxWidth(300);

        root.setCenter(rezultatCautare);
        VBox bottombox=new VBox(10);

        nrInregistrari = new Label("Inregistrari: 0");
        bottombox.getChildren().add(nrInregistrari);
        slider = getSlider();
        bottombox.getChildren().add(slider);
        root.setBottom(bottombox);




        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox getControlsPane() {
        VBox vbox = new VBox(10);
        Label nume = new Label("Nume:");
        Label localitate = new Label("Localitate:");

        // ChoiceBoxes for filtering
        ChoiceBox<String> nameBox = getChoiceBox(listaMosului.getMapNume());
        ChoiceBox<String> localityBox = getChoiceBox(listaMosului.getMapLocalitate());

        // Slider for grades


        // Button for file loading
        Button loadFileButton = getLoadFileButton();
        vbox.getChildren().addAll(nume,nameBox,localitate, localityBox, loadFileButton);

        // Label for record count

        return vbox;
    }

    private ChoiceBox<String> getChoiceBox(Map<String, List<Student>> map) {
        ObservableList<String> items = FXCollections.observableArrayList(map.keySet());
        ChoiceBox<String> choiceBox = new ChoiceBox<>(items);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(new SelectareItem(map));
        return choiceBox;
    }

    private Slider getSlider() {
        Slider slider = new Slider(5, 10, 7);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double minGrade = newValue.doubleValue();
            nrInregistrari.setText(String.format("Stud. cu media >= %.2f", minGrade));
            OBradFrumos.afisareRezultat(listaMosului.getMapMedie()
                    .entrySet().stream()
                    .filter(e -> e.getKey() >= minGrade)
                    .flatMap(e -> e.getValue().stream())
                    .toList());
        });
        return slider;
    }

    private Button getLoadFileButton() {
        Button button = new Button("Citeste fisier");
        button.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                try {
                    listaMosului = new ListaMosului(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return button;
    }

    public static void afisareRezultat(List<Student> lstud) {
        rezultatCautare.clear();
        if (lstud != null && !lstud.isEmpty()) {
            lstud.forEach(s -> rezultatCautare.appendText(s.toString() + "\n"));
        }
        nrInregistrari.setText("Inregistrari: " + (lstud != null ? lstud.size() : 0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
