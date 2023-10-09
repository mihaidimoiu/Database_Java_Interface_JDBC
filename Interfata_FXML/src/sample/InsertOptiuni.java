package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InsertOptiuni {

    @FXML
    public Button btn8 = new Button();

    @FXML
    public Button adrese = new Button();

    public void close() throws IOException{
        Main.setSelectCanceled(true);
        Stage stage = (Stage) btn8.getScene().getWindow();
        stage.setTitle("Optiuni");
        AnchorPane anchorPane = null;
        anchorPane = FXMLLoader.load(getClass().getResource("optiuni.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    public void adrese() throws IOException {
          //  Main.setSelectCanceled(false);
            Stage stage = new Stage();
            stage.setTitle("Adaugare Adrese");
            AnchorPane anchorPane = null;
            anchorPane = FXMLLoader.load(getClass().getResource("AdreseInsert.fxml"));
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
    }

    public void produse() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Adaugare Produse");
        AnchorPane anchorPane = null;
        anchorPane = FXMLLoader.load(getClass().getResource("Produse.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
    public void categorii()throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Adaugare Categori");
        AnchorPane anchorPane = null;
        anchorPane = FXMLLoader.load(getClass().getResource("Categori.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
    public void comenzi() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Adaugare Categori");
        AnchorPane anchorPane = null;
        anchorPane = FXMLLoader.load(getClass().getResource("Comenzi.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
    public void client() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Adaugare Clienti");
        AnchorPane anchorPane = null;
        anchorPane = FXMLLoader.load(getClass().getResource("Client.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
    public void producatori() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Adaugare Producatori");
        AnchorPane anchorPane = null;
        anchorPane = FXMLLoader.load(getClass().getResource("Producatori.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }
}
