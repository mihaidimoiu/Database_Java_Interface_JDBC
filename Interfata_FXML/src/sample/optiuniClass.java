package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class optiuniClass {

    @FXML
    Button btnSelect = new Button();

    @FXML
    Label status = new Label();

    @FXML
    public void initialize(){
        status.setText("Status : Logged");
    }

    private static Stage stage = null;

    public void select() throws IOException {
        //Stage
        if (Main.isSelectCanceled()) {
            Main.setSelectCanceled(false);
            Stage stage = (Stage) btnSelect.getScene().getWindow();
            stage.setTitle("Optiuni");
            AnchorPane anchorPane = null;
            anchorPane = FXMLLoader.load(getClass().getResource("Select2.fxml"));
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void disconnect() {
        if (Main.getLoginJDBC().disconnect()) {
            exit(0);
        }
    }

    public void insert() throws IOException{
        if (Main.isSelectCanceled()) {
            Main.setSelectCanceled(false);
            Stage stage = (Stage) btnSelect.getScene().getWindow();
            stage.setTitle("Select");
            VBox vbox = null;
            vbox = FXMLLoader.load(getClass().getResource("InsertOptiuni.fxml"));
            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void update() throws IOException{
        if (Main.isSelectCanceled()) {
            Main.setSelectCanceled(false);
            Stage stage = (Stage) btnSelect.getScene().getWindow();
            stage.setTitle("Update");
            AnchorPane pane = null;
            pane = FXMLLoader.load(getClass().getResource("Update.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        }
    }
    public void delete() throws IOException{
        if (Main.isSelectCanceled()) {
            Main.setSelectCanceled(false);
            Stage stage = (Stage) btnSelect.getScene().getWindow();
            stage.setTitle("Update");
            AnchorPane pane = null;
            pane = FXMLLoader.load(getClass().getResource("Delete.fxml"));
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        }
    }

}
