package com.rmi.controller;

import com.rmi.intf.JoueurInterface;
import com.rmi.intf.RMIServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.rmi.RemoteException;

public class NameController {
    @FXML
    private ImageView background;
    @FXML
    private Button valider;
    @FXML
    private TextField pseudo;
    private RMIServerInterface mInterface;

    public void init(ActionEvent event) throws RemoteException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        mInterface = (RMIServerInterface) stage.getUserData();
        valider.setDisable(true);
        background.setImage(new Image("/images/uno.png"));
        background.setVisible(true);
        pseudo.textProperty().addListener((obj, oldVal, newVal) -> valider.setDisable(newVal.trim().isEmpty()));
    }

    @FXML
    private void handleButtonValider (ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;
        stage = (Stage) valider.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/jeu.fxml"));
        root = (Parent)loader.load();
        JeuController controller = (JeuController) loader.getController();
        JoueurInterface joueur = mInterface.joinGame(pseudo.getText(),-1,controller);
        if(joueur != null){
            Scene scene = new Scene(root);
            stage.setUserData(joueur);
            controller.init(event,mInterface.getLobbys()-1);
            stage.setScene(scene);
            stage.show();
        }else{
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setContentText("Pseudo Incorret");
            al.showAndWait();
        }
    }
}
