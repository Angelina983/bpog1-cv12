package com.mycompany.mavenproject1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class App extends Application {

    private Pane drawingPane;

    private Ellipse ellipse;
    private Circle point;
    private Line line;

    private double centerX;
    private double centerY;

    @Override
    public void start(final Stage stage) {
        drawingPane = new Pane();
        drawingPane.setPrefSize(500, 400);
        drawingPane.setStyle("-fx-background-color: #eee;");

        ellipse = new Ellipse();
        ellipse.setRadiusX(90);
        ellipse.setRadiusY(55);
        ellipse.setFill(Color.GOLD);
        ellipse.setStroke(Color.RED);
        ellipse.setStrokeWidth(3);

        point = new Circle();
        point.setRadius(8);
        point.setFill(Color.BLACK);

        line = new Line();
        line.setStroke(Color.BLACK);

        drawingPane.getChildren().add(ellipse);
        drawingPane.getChildren().add(line);
        drawingPane.getChildren().add(point);

        Button buttonEnd = new Button("Konec");
        Button buttonReset = new Button("Reset");

        HBox bottomPanel = new HBox();
        bottomPanel.setSpacing(10);
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.getChildren().add(buttonEnd);
        bottomPanel.getChildren().add(buttonReset);

        BorderPane root = new BorderPane();
        root.setCenter(drawingPane);
        root.setBottom(bottomPanel);

        buttonEnd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

        buttonReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reset();
            }
        });

        point.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                point.setCenterX(event.getX());
                point.setCenterY(event.getY());

                updateLine();
                updateRotation();
            }
        });

        drawingPane.widthProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(javafx.beans.value.ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                reset();
            }
        });

        drawingPane.heightProperty().addListener(new javafx.beans.value.ChangeListener<Number>() {
            @Override
            public void changed(javafx.beans.value.ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                reset();
            }
        });

        Scene scene = new Scene(root);
        stage.setTitle("Ellipse");
        stage.setScene(scene);
        stage.show();

        reset();
    }

    private void reset() {
        centerX = drawingPane.getWidth() / 2;
        centerY = drawingPane.getHeight() / 2;

        ellipse.setCenterX(centerX);
        ellipse.setCenterY(centerY);
        ellipse.setRotate(0);

        point.setCenterX(centerX + 120);
        point.setCenterY(centerY);

        updateLine();
    }

    private void updateLine() {
        line.setStartX(centerX);
        line.setStartY(centerY);
        line.setEndX(point.getCenterX());
        line.setEndY(point.getCenterY());
    }

    private void updateRotation() {
        double dx = point.getCenterX() - centerX;
        double dy = point.getCenterY() - centerY;

        double angle = Math.atan2(dy, dx);
        double degrees = Math.toDegrees(angle);

        ellipse.setRotate(degrees);
    }

    public static void main(String[] args) {
        launch(args);
    }
}