package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Client {

    //Declararea variabilelor
    @FXML
    Button btn1 = new Button();
    @FXML
    Button btn2 = new Button();

    @FXML
    TextField field1 = new TextField();
    @FXML
    TextField field2 = new TextField();
    @FXML
    TextField field3 = new TextField();
    @FXML
    TextField field4 = new TextField();


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

            String SQL = " INSERT INTO [Proiect].[dbo].[Client]\n" +
                    " ([Nume]\n" +
                    ",[CNP]\n" +
                    ",[ID_Adresa]\n" +
                    ",[Sex])\n" +
                    "VALUES\n" +
                    "('" + field1.getText() + "'\n" +
                    "," + field2.getText() + "\n" +
                    "," + field3.getText() + "\n" +
                    ",'" + field4.getText() + "');\n";
            stmt.executeUpdate(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            field1.setText("");
            field2.setText("");
            field3.setText("");
            field4.setText("");


            //status.setText("Done inserting");
            System.out.println("DONE");
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }
    //Inchiderea ferestrei
    public void close() {
        Main.setSelectCanceled(true);
        Stage stage = (Stage) btn2.getScene().getWindow();
        stage.close();
    }
}
