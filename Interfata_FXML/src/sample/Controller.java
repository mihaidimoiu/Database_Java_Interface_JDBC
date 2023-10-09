package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;


public class Controller {

    //Declararea variabilelor
    @FXML
    TextField field1 = new TextField();
    @FXML
    PasswordField field2 = new PasswordField();
    @FXML
    TextField field3 = new TextField();

    @FXML
    Label label1 = new Label();
    @FXML
    Label label2 = new Label();
    @FXML
    Label label3 = new Label();
    @FXML
    Label label4 = new Label();

    @FXML
    Button btn1 = new Button();
    @FXML
    Button btn2 = new Button();
    @FXML
    Button btn3 = new Button();


    //Functie pentru initializarea obiectelor din interfata cu o anumita valoare
    @FXML
    public void initialize() {
        btn1.setDisable(true);
        btn2.setDisable(true);
        label4.setText("Status:");
    }

    private boolean logged = false; //Arata starea aplicatiei cand se va loga in baza de date


    //Conectarea la baza de date si mentinerea conexiunii
    @FXML
    public void connect() throws IOException {
        try {
            if (Main.getLoginJDBC().connect(field1.getText(), field2.getText(), field3.getText())) {
                System.out.println("Logged in!");


                field1.setDisable(true);
                field2.setDisable(true);
                field3.setDisable(true);

                field1.setText("");
                field2.setText("");
                field3.setText("");
                btn1.setDisable(true);

                label4.setText("Status: Logat");

                logged = true;
                btn3.setDisable(true);
            } else {
                System.out.println("User/Pass incorecta!\nSau numele bazei de date incorect!");
                label4.setText("Status: Error");
                logged = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (logged == true) {
            Stage stage = (Stage) btn1.getScene().getWindow();
            stage.setTitle("Optiuni");
            AnchorPane anchorPane = null;
            anchorPane = FXMLLoader.load(getClass().getResource("optiuni.fxml"));
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            stage.show();
        }
    }

    //Functia pentru cand se va apasa minim o tasta sa faca butoanele de control active
    @FXML
    public void handleKeyRelease() {
        String text1 = field1.getText();
        String text2 = field2.getText();
        String text3 = field3.getText();

        boolean disableButtons = (text1.isEmpty() && text2.isEmpty() && text3.isEmpty());

        btn1.setDisable(disableButtons);
        btn2.setDisable(disableButtons);
    }

    //Deconectarea de la baza de date
    public void disconnect() {

        //LoginJDBC login = new LoginJDBC();
        if (Main.getLoginJDBC().disconnect()) {

            field1.setText("");
            field2.setText("");
            field3.setText("");

            field1.setDisable(false);
            field2.setDisable(false);
            field3.setDisable(false);
            label4.setText("Status: Deconectat");

            btn1.setDisable(false);
            btn2.setDisable(true);
            btn3.setDisable(false);
        }
    }

    //Funcita pentru inchiderea aplicatiei
    public void clicked3() throws IOException {
        if (Main.getLoginJDBC().disconnect()) {

            exit(0);
        } else {
            System.out.println("Inchidere . . .");;
            Stage stage = (Stage) btn2.getScene().getWindow();
            stage.close();
        }
    }
}