package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Categori {
    //Declararea variabilelor
    @FXML
    Button btn1 = new Button();
    @FXML
    Button btn2 = new Button();

    @FXML
    TextField field1 = new TextField();

    //Functia care se ocupa pentru buton de inserarea dupa ce s-au adaugat datele
    public void add() {
        Connection con;
        Statement stmt = null;

        try {
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(Main.getLoginJDBC().getFinal_conn());

            //Create and execute an SQL statement that returns some data.
            stmt = con.createStatement();

            String SQL = "INSERT INTO [Proiect].[dbo].[Categorii]\n" +
                    "           ([Nume])\n" +
                    "     VALUES\n" +
                    "           ('"+field1.getText()+"');";
            stmt.executeUpdate(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            field1.setText("");


            //status.setText("Done inserting");
            System.out.println("Ok");
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }
    //Functia ce se ocupa pentru butonul de inchidere a ferestrei
    public void close() {
        Main.setSelectCanceled(true);
        Stage stage = (Stage) btn2.getScene().getWindow();
        stage.close();
    }
}
