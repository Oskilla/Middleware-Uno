package com.rmi.controller;

import com.rmi.intf.RMIServerInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ChooseLobbyController {

  @FXML
  private ImageView background;
  @FXML
  private Button buttonNew;
  @FXML
  private Button buttonExist;
  private RMIServerInterface mInterface;

  public void init(ActionEvent event) {
    Node node = (Node) event.getSource();
    Stage stage = (Stage) node.getScene().getWindow();
    mInterface = (RMIServerInterface) stage.getUserData();
    background.setImage(new Image("/images/uno.png"));
    background.setVisible(true);
  }

  @FXML
  private void handleButtonExist (ActionEvent event) throws Exception {
    Stage stage = null;
    Parent root = null;
    stage = (Stage) buttonNew.getScene().getWindow();
    stage.setUserData(mInterface);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/selectlobby.fxml"));
    root = (Parent)loader.load();
    SelectLobbyController controller = (SelectLobbyController) loader.getController();
    controller.init(event);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void handleCreate (ActionEvent event) throws Exception {
    Stage stage = null;
    Parent root = null;
    stage = (Stage) buttonNew.getScene().getWindow();
    stage.setUserData(mInterface);
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/name.fxml"));
    root = (Parent)loader.load();
    NameController controller = (NameController) loader.getController();
    controller.init(event);
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
