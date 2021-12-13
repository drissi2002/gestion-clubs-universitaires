package com.example.javaproject;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.*;
// @author: DRISSI Houcem eddine & BOURAOUI manel
//Controlleur de l'interface graphique  "login representant"
public class repClubController {
    //--------- les "textfield" pour l'authentification ------------------
    @FXML
    private PasswordField tfpasswordrep;
    @FXML
    private TextField tfidrep;
    //--------- les boutons de login & retour ----------------------------
    @FXML
    private Button backbtn;
    @FXML
    private Button btnlogin;
    //--------- variables de connexion-------------------------------------
    Connection conn;
    ResultSet rs;
    //le retour à la page précendente
    @FXML
    void GobackAction(ActionEvent event) throws IOException {

        Stage stage =(Stage) backbtn.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("lesClubsInterface.fxml"));
        primaryStage.setScene(new Scene(root , 843,483));
        primaryStage.show();
    }
    //aprés avoir connecter avec la base du données , on verifie si le mot de passe et l'Id
    // correspondent aux valeurs correctes stokées dans la base de données ou non
    @FXML
    void handleOnAction(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        String ID = tfidrep.getText();
        String Password = tfpasswordrep.getText();

         try{
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionclub?useUnicode=true\n" +
                    "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&\n" +
                    "serverTimezone=UTC&useSSL=false","root","");
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM loginrepresentant WHERE idrep=? AND passwordrep=?");
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
            Parent root = FXMLLoader.load(getClass().getResource("espaceMembre.fxml"));
            primaryStage.setScene(new Scene(root , 843,483));
            primaryStage.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Verifiez votre ID & Password ! ");
            alert.showAndWait();
        }
    }
}
