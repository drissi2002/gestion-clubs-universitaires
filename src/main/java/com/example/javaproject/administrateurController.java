package com.example.javaproject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.*;
import javafx.scene.control.TextField;
// @author: DRISSI Houcem eddine & BOURAOUI manel
//Controlleur de l'interface graphique  "login administrateur"
public class administrateurController {
    //--------- les "textfield" pour l'authentification ------------------
    @FXML
    private TextField IDadmin;
    @FXML
    private TextField passwordAdmin;
    //--------- les boutons de login & retour ----------------------------
    @FXML
    private Button backbtn;
    @FXML
    private Button btnlogin;
    //--------- variables de connexion-------------------------------------
    Connection conn;
    ResultSet rs;
    //aprés avoir connecter avec la base du données , on verifie si le mot de passe et l'Id
    // correspondent aux valeurs correctes stokées dans la base de données ou non
    @FXML
    void handleOnAction(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        String ID = IDadmin.getText();
        String Password = passwordAdmin.getText();
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionclub?useUnicode=true\n" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&\n" +
                    "serverTimezone=UTC","root","");
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM loginadmin WHERE idadmin=? AND passwordadmin=? ");
            pst.setString(1,ID);
            pst.setString(2,Password);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(ID.equals("")&&Password.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ID & Password sont demandés ! ");
            alert.showAndWait();
        }
        else if(rs.next()){
            Stage stage =(Stage) btnlogin.getScene().getWindow();
            stage.close();
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("espaceClub.fxml"));
            primaryStage.setScene(new Scene(root , 843,483));
            primaryStage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Verifiez votre ID & Password ! ");
            alert.showAndWait();
        }
    }
    //le retour à la page précendente
    @FXML
    void gobackAction(ActionEvent event) throws IOException {

        Stage stage =(Stage) backbtn.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        primaryStage.setScene(new Scene(root , 843,483));
        primaryStage.show();
    }
}