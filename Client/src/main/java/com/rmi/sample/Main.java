/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.sample;

import com.rmi.controller.ConnectController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// creation de la première scene pour se connecter au serveur
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
