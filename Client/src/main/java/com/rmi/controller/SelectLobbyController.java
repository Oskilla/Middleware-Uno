/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.controller;

import com.rmi.intf.JoueurInterface;
import com.rmi.intf.RMIServerInterface;

import java.rmi.RemoteException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SelectLobbyController {
  @FXML
  private ImageView background;
  @FXML
  private ListView itemList;
  @FXML
  private Button refresh;
  @FXML
  private Button valider;
  @FXML
  private TextField pseudo;
  private RMIServerInterface mInterface;
  private int nbLobbys;

  // classe permettant de choisir un lobby a rejoindre
  public void init(ActionEvent event) throws RemoteException{
    Node node = (Node) event.getSource();
    Stage stage = (Stage) node.getScene().getWindow();
    // recuperation de l adresse du serveur
    mInterface = (RMIServerInterface) stage.getUserData();
    // recuperation du nombre de lobbys present sur le serveur
    nbLobbys = mInterface.getLobbys();
    valider.setDisable(true);
    background.setImage(new Image("/images/uno.png"));
    background.setVisible(true);
    itemList.setEditable(true);
    final ObservableList names = FXCollections.observableArrayList();
    for(int i=0;i<nbLobbys;i++){
      names.add("Salon : " + i);
    }
    itemList.setItems(names);
    pseudo.textProperty().addListener((obj, oldVal, newVal) -> valider.setDisable(newVal.trim().isEmpty()));
  }

  // permet de rafraichir les lobbys
  @FXML
  private void handleButtonRefresh (ActionEvent event) throws Exception {
    nbLobbys = mInterface.getLobbys();
    final ObservableList names = FXCollections.observableArrayList();
    for(int i=0;i<nbLobbys;i++){
      names.add("Salon : " + i);
    }
    itemList.setItems(names);
  }

  // permet de confirmer le lobby que l on souhaite rejoindre
  @FXML
  private void handleButtonValider (ActionEvent event) throws Exception {
    Stage stage = null;
    Parent root = null;
    stage = (Stage) valider.getScene().getWindow();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/jeu.fxml"));
    root = (Parent)loader.load();
    JeuController controller = (JeuController) loader.getController();
    // on creer notre joueur dans le lobby selectionne
    JoueurInterface joueur = mInterface.joinGame(pseudo.getText(),itemList.getSelectionModel().getSelectedIndex(),controller);
    if(joueur != null){
      Scene scene = new Scene(root);
      stage.setUserData(joueur);
      if(itemList.getSelectionModel().getSelectedIndex() == -1){
        controller.init(event,mInterface.getLobbys()-1);
      }else{
        controller.init(event,itemList.getSelectionModel().getSelectedIndex());
      }
      stage.setScene(scene);
      stage.show();
    }else{
      Alert al = new Alert(Alert.AlertType.WARNING);
      al.setContentText("Pseudo Incorret");
      al.showAndWait();
    }
  }
}
