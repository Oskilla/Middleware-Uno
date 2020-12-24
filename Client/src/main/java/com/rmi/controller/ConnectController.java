/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture Client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.controller;

import com.rmi.intf.RMIServerInterface;
import java.rmi.Naming;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

// classe permettant de se connecter avec le serveur
public class ConnectController {
  @FXML
  private ImageView background;
  @FXML
  private Button buttonConnect;
  private String url;

  public void init(){
    background.setImage(new Image("/images/uno.png"));
    background.setVisible(true);
  }

  // connexion au serveur
  @FXML
  private void handleButtonConnect (ActionEvent event) throws Exception {
    RMIServerInterface mInterface = (RMIServerInterface) Naming.lookup("rmi://localhost:1099/Server");
    Stage stage = null;
    Parent root = null;
    stage = (Stage) buttonConnect.getScene().getWindow();
    // on transmet l adresse du serveur a l ecran suivant
    stage.setUserData(mInterface);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/chooselobby.fxml"));
    root = (Parent)loader.load();
    ChooseLobbyController controller = (ChooseLobbyController) loader.getController();
    controller.init(event);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
