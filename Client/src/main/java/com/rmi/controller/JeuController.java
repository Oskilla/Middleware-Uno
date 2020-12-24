/**
 * Projet Middleware-Uno
 * Une implémentation du jeu de plateau Uno avec une architecture Client / Serveur à l'aide de RMI.
 * @authors Leveille Bastien, Lecomte Soline, Lode Gael & Perez Damien
 */

package com.rmi.controller;

import com.rmi.intf.CarteInterface;
import com.rmi.intf.ClientInterface;
import com.rmi.intf.JoueurInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Optional;

// classe permettant de gérer le jeu graphiquement
public class JeuController extends UnicastRemoteObject implements ClientInterface {
  @FXML
  private ImageView background, JouerButton,talon,myCard,FirstLeftCard,FirstLastCard,FirstRightCard;
  @FXML
  private Text myName,FirstLeftName,FirstLastName,FirstRightName,Salle,Points;
  @FXML
  private Circle myCircle,FirstLeftButton,LastButton,FirstRightButton,BlueChoice,GreenChoice,RedChoice,YellowChoice,demande;
  @FXML
  private Pane pane;
  private JoueurInterface monJoueur;
  private int point = 0;
  private int myCardNbCard = 0;
  private int LeftPlayerNbCard = 0;
  private int RightPlayerNbCard = 0;
  private int LastPlayerNbCard = 0;
  private String couleurChoisie = "";
  private Node node;
  private boolean aPioche = false;

  public JeuController() throws RemoteException {}

  @FXML
  public void init(ActionEvent event, int salle) throws RemoteException {
    final Node[] node = {(Node) event.getSource()};
    Stage stage = (Stage) node[0].getScene().getWindow();
    // on recupere le joueur depuis l ecran suivant
    monJoueur = (JoueurInterface) stage.getUserData();
    background.setImage(new Image("/images/fondPlateau.png"));
    background.setVisible(true);
    JouerButton.setImage(new Image("/images/circleButtonDisable.png"));
    DropShadow ds = new DropShadow( 20, Color.AQUA );
    BlueChoice.setOnMouseClicked((MouseEvent event3) -> {
      BlueChoice.setEffect(ds);
      couleurChoisie = "Bleu";
      RedChoice.setEffect(null);
      GreenChoice.setEffect(null);
      YellowChoice.setEffect(null);
    });
    RedChoice.setOnMouseClicked((MouseEvent event4) -> {
      BlueChoice.setEffect(null);
      RedChoice.setEffect(ds);
      couleurChoisie = "Rouge";
      GreenChoice.setEffect(null);
      YellowChoice.setEffect(null);
    });
    GreenChoice.setOnMouseClicked((MouseEvent event5) -> {
      BlueChoice.setEffect(null);
      RedChoice.setEffect(null);
      GreenChoice.setEffect(ds);
      couleurChoisie = "Vert";
      YellowChoice.setEffect(null);
    });
    YellowChoice.setOnMouseClicked((MouseEvent event6) -> {
      BlueChoice.setEffect(null);
      RedChoice.setEffect(null);
      GreenChoice.setEffect(null);
      YellowChoice.setEffect(ds);
      couleurChoisie = "Jaune";
    });
    JouerButton.setDisable(true);
    myName.setText(monJoueur.getId());
    Salle.setText("Salle : " + salle);
    Points.setText("Point: "+point);
    // une carte de notre main est toujours presente pour que les prochaines se basent sur ses informations, elle est cachee si besoin
    // de meme pour les mains des autres joueurs
    myCard.setId("0");
    // creation du thread qui va indiquer que le joueur a bien recu son joueur associe, et qu il est donc pret
    Thread t = new Thread(() -> {
      try {
        while(monJoueur.getUno() == null){}
        monJoueur.getUno().joueurPret(myName.getText());
        Thread.currentThread().join();
      } catch (RemoteException | InterruptedException e) {
        e.printStackTrace();
      }
    });
    t.start();
  }

  // permet d indiquer graphiquement que le joueur peut jouer
  public void setPeutJouer() {
    this.JouerButton.setDisable(false);
    this.JouerButton.setImage(new Image("/images/circleButton.png"));
  }

  // permet de changer graphiquement le talon
  public void setTalon(CarteInterface c) throws RemoteException {
    if(c.getClassName().equals("CarteAction")){
      this.talon.setImage(new Image("/images/"+c.getCouleur()+"/"+c.getSymbole()+".png"));
    }else{
      this.talon.setImage(new Image("/images/"+c.getCouleur()+"/"+c.getNumero()+".png"));
    }
  }

  // permet d indiquer le nom du joueur de gauche
  public void setLeftJoueur(String name) {
    this.FirstLeftName.setText(name);
  }

  // permet d indiquer le nom du joueur de droite
  public void setRightJoueur(String name) {
    this.FirstRightName.setText(name);
  }

  // permet d indiquer le nom du joueur en face de nous même
  public void setLastJoueur(String name) {
    this.FirstLastName.setText(name);
  }

  // permet de changer graphiquement le cercle indiquant le tour du joueur
  public void setPlayerTurn(String name) {
    if(myName.getText().equals(name)){
      myCircle.setFill(Paint.valueOf("#0db202"));
      FirstLeftButton.setFill(Paint.valueOf("#ff1f1f"));
      FirstRightButton.setFill(Paint.valueOf("#ff1f1f"));
      LastButton.setFill(Paint.valueOf("#ff1f1f"));
    }
    if(FirstLeftName.getText().equals(name)){
      myCircle.setFill(Paint.valueOf("#ff1f1f"));
      FirstLeftButton.setFill(Paint.valueOf("#0db202"));
      FirstRightButton.setFill(Paint.valueOf("#ff1f1f"));
      LastButton.setFill(Paint.valueOf("#ff1f1f"));
    }
    if(FirstRightName.getText().equals(name)){
      myCircle.setFill(Paint.valueOf("#ff1f1f"));
      FirstLeftButton.setFill(Paint.valueOf("#ff1f1f"));
      FirstRightButton.setFill(Paint.valueOf("#0db202"));
      LastButton.setFill(Paint.valueOf("#ff1f1f"));
    }
    if(FirstLastName.getText().equals(name)){
      myCircle.setFill(Paint.valueOf("#ff1f1f"));
      FirstLeftButton.setFill(Paint.valueOf("#ff1f1f"));
      FirstRightButton.setFill(Paint.valueOf("#ff1f1f"));
      LastButton.setFill(Paint.valueOf("#0db202"));
    }
  }

  // permet d indiquer graphiquement la couleur demandee
  public void setCouleurChoisie(String col){
    if(col.equals("Bleu")){
      demande.setFill(Paint.valueOf("DODGERBLUE"));
    }
    if(col.equals("Vert")){
      demande.setFill(Paint.valueOf("#0db202"));
    }
    if(col.equals("Jaune")){
      demande.setFill(Paint.valueOf("#f8ff32"));
    }
    if(col.equals("Rouge")){
      demande.setFill(Paint.valueOf("#ff1f1f"));
    }
  }

  // cree une alerte pour indiquer que la partie est finie
  public void setFinPartie(String name) throws IOException {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fin de la partie");
        alert.setHeaderText("Le joueur  "+ name+" a gagné");
        alert.setResizable(false);
        alert.setContentText("Souhaitez-vous rejouer ?");
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);
        if (button == ButtonType.OK) {
          try {
            JouerButton.setImage(new Image("/images/circleButtonDisable.png"));
            JouerButton.setDisable(true);
            talon.setImage(new Image("/images/backcard.png"));
            // indique au uno commun que le joueur est pret et qu il souhaite rejouer une partie
            monJoueur.getUno().joueurPret(myName.getText());
            couleurChoisie = "";
            node = null;
            BlueChoice.setEffect(null);
            RedChoice.setEffect(null);
            GreenChoice.setEffect(null);
            YellowChoice.setEffect(null);
            aPioche = false;
          } catch (RemoteException e) {
            e.printStackTrace();
          }
        } else {
          System.out.println("canceled");
        }
      }
    });
  }

  // permet d afficher graphiquement les cartes composant la main du joueur
  public void setMyHand(List<CarteInterface> c) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          int indice = pane.getChildren().indexOf(myCard);
          DropShadow ds = new DropShadow( 20, Color.AQUA );
          if(myCardNbCard > 1){
            pane.getChildren().remove(indice+1,indice+myCardNbCard);
          }
          myCardNbCard = c.size();
          if(c.get(0).getClassName().equals("CarteAction")){
            myCard.setImage(new Image("/images/"+c.get(0).getCouleur()+"/"+c.get(0).getSymbole()+".png"));
          }else{
            myCard.setImage(new Image("/images/"+c.get(0).getCouleur()+"/"+c.get(0).getNumero()+".png"));
          }
          myCard.setOnMouseClicked((MouseEvent event2) -> {
            if(node == myCard){
              myCard.setEffect(null);
              node = null;
            }else{
              myCard.setEffect(ds);
              if(node != null){
                node.setEffect(null);
              }
              node = myCard;
            }
          });
          for(int i=1;i<myCardNbCard;i++){
            ImageView temp = new ImageView();
            if(c.get(i).getClassName().equals("CarteAction")){
              temp.setImage(new Image("/images/"+c.get(i).getCouleur()+"/"+c.get(i).getSymbole()+".png"));
            }else{
              temp.setImage(new Image("/images/"+c.get(i).getCouleur()+"/"+c.get(i).getNumero()+".png"));
            }
            temp.setFitHeight(myCard.getFitHeight());
            temp.setLayoutY(myCard.getLayoutY());
            temp.setLayoutX(myCard.getLayoutX() + (i*32));
            temp.setFitWidth(myCard.getFitWidth());
            temp.setPreserveRatio(true);
            temp.setPickOnBounds(true);
            temp.setRotate(myCard.getRotate());
            temp.setId(Integer.toString(i));
            temp.setOnMouseClicked((MouseEvent event2) -> {
              if(node == temp){
                temp.setEffect(null);
                node = null;
              }else{
                temp.setEffect(ds);
                if(node != null){
                  node.setEffect(null);
                }
                node = temp;
              }
            });
            pane.getChildren().add(indice+i,temp);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  // permet d afficher graphiquement les cartes composant les mains des joueurs
  public void setJoueurHand(String name, int n) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try {
          int indice;
          if(FirstLeftName.getText().equals(name)){
            indice = pane.getChildren().indexOf(FirstLeftCard);
            if(LeftPlayerNbCard > 1){
              pane.getChildren().remove(indice+1,indice+LeftPlayerNbCard);
            }
            LeftPlayerNbCard = n;
            FirstLeftCard.setImage(new Image("/images/backcard.png"));
            for(int i=1;i<n;i++){
              ImageView temp = new ImageView();
              temp.setImage(new Image("/images/backcard.png"));
              temp.setFitHeight(FirstLeftCard.getFitHeight());
              temp.setFitWidth(FirstLeftCard.getFitWidth());
              temp.setPreserveRatio(true);
              temp.setLayoutX(FirstLeftCard.getLayoutX());
              temp.setLayoutY(FirstLeftCard.getLayoutY()+(i*15));
              temp.setPickOnBounds(true);
              temp.setRotate(FirstLeftCard.getRotate());
              pane.getChildren().add(indice+i,temp);
            }
          }
          if(FirstRightName.getText().equals(name)){
            indice = pane.getChildren().indexOf(FirstRightCard);
            if(RightPlayerNbCard > 1){
              pane.getChildren().remove(indice+1,indice+RightPlayerNbCard);
            }
            RightPlayerNbCard = n;
            FirstRightCard.setImage(new Image("/images/backcard.png"));
            for(int i=1;i<n;i++){
              ImageView temp = new ImageView();
              temp.setImage(new Image("/images/backcard.png"));
              temp.setFitHeight(FirstRightCard.getFitHeight());
              temp.setFitWidth(FirstRightCard.getFitWidth());
              temp.setPreserveRatio(true);
              temp.setLayoutX(FirstRightCard.getLayoutX());
              temp.setLayoutY(FirstRightCard.getLayoutY()-(i*15));
              temp.setPickOnBounds(true);
              temp.setRotate(FirstRightCard.getRotate());
              pane.getChildren().add(indice+i,temp);
            }
          }
          if(FirstLastName.getText().equals(name)){
            indice = pane.getChildren().indexOf(FirstLastCard);
            if(LastPlayerNbCard > 1){
              pane.getChildren().remove(indice+1,indice+LastPlayerNbCard);
            }
            LastPlayerNbCard = n;
              FirstLastCard.setImage(new Image("/images/backcard.png"));
              for(int i=1;i<n;i++){
                ImageView temp = new ImageView();
                temp.setImage(new Image("/images/backcard.png"));
                temp.setFitHeight(FirstLastCard.getFitHeight());
                temp.setFitWidth(FirstLastCard.getFitWidth());
                temp.setPreserveRatio(true);
                temp.setLayoutX(FirstLastCard.getLayoutX()-(i*15));
                temp.setLayoutY(FirstLastCard.getLayoutY());
                temp.setPickOnBounds(true);
                temp.setRotate(FirstLastCard.getRotate());
                pane.getChildren().add(indice+i,temp);
              }
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  // permet de jouer une carte
  @FXML
  private void handleJouer(MouseEvent event) throws IOException {
    boolean carteJouee = false;
    if(node == null){
      // verifie que le joueur n a aucune carte a jouer
      CarteInterface c = monJoueur.getUno().peutJouer(monJoueur);
      if(c == null){
        if(!aPioche){
          // indique au serveur que le joueur n a aucune carte a jouer, donc il pioche
          monJoueur.getUno().JouerCarte(myName.getText(),null,null,false);
          aPioche = true;
        }else{
          // indique au serveur que le joueur a deja pioche mais qu il ne peut joueur aucune carte
          monJoueur.getUno().JouerCarte(myName.getText(),null,null,true);
          myCircle.setFill(Paint.valueOf("#ff1f1f"));
          JouerButton.setDisable(true);
          aPioche = false;
          JouerButton.setImage(new Image("/images/circleButtonDisable.png"));
          couleurChoisie = "";
          node = null;
          BlueChoice.setEffect(null);
          RedChoice.setEffect(null);
          GreenChoice.setEffect(null);
          YellowChoice.setEffect(null);
        }
      }else{
        Alert al = new Alert(Alert.AlertType.WARNING);
        if(c.getClassName().equals("CarteAction")){
            al.setContentText("La carte " + c.getCouleur() + " : " + c.getSymbole() + " peut être jouée");
        }else{
            al.setContentText("La carte " + c.getCouleur() + " : " + c.getNumero() + " peut être jouée");
        }
        al.showAndWait();
      }
    }else{
      try {
        CarteInterface vasJouer = monJoueur.getMain().get(Integer.parseInt(node.getId()));
        if(vasJouer.getCouleur().equals("Noire")){
          if(couleurChoisie == ""){
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setContentText("Veuillez selectionner une couleur");
            al.showAndWait();
            return;
          }else{
            // joue la carte noire avec la couleur associee
            carteJouee = monJoueur.getUno().JouerCarte(myName.getText(),monJoueur.getMain().get(Integer.parseInt(node.getId())),couleurChoisie,false);
          }
        }else{
          // joue la carte non noire
          carteJouee = monJoueur.getUno().JouerCarte(myName.getText(),monJoueur.getMain().get(Integer.parseInt(node.getId())),couleurChoisie,false);
        }
        // si la carte a pu etre jouee
        if(carteJouee){
          myCircle.setFill(Paint.valueOf("#ff1f1f"));
          JouerButton.setDisable(true);
          JouerButton.setImage(new Image("/images/circleButtonDisable.png"));
          couleurChoisie = "";
          node.setEffect(null);
          aPioche = false;
          node = null;
          BlueChoice.setEffect(null);
          RedChoice.setEffect(null);
          GreenChoice.setEffect(null);
          YellowChoice.setEffect(null);
        }else {
          Alert al = new Alert(Alert.AlertType.WARNING);
          al.setContentText("Vous ne pouvez pas jouer cette carte");
          al.showAndWait();
        }
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }

  // permet d indiquer graphiquement le nombre de points du joueur
  public void incrementPoint() {
    ++this.point;
    this.Points.setText("Point: "+this.point);
  }
}
