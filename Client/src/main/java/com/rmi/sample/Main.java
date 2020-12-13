package com.rmi.sample;

import com.rmi.controller.ConnectController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/connect.fxml"));
        Parent root = (Parent)loader.load();

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Middleware : UNO");
        primaryStage.setScene(scene);

        ConnectController controller = (ConnectController) loader.getController();
        controller.init();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
