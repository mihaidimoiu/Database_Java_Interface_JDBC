package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class AdreseInsert {
    //Declarea variabilelor asociate fxml-ului
    @FXML
    public TextField field1 = new TextField();
    @FXML
    public TextField field2 = new TextField();
    @FXML
    public TextField field3 = new TextField();
    @FXML
    public TextField field4 = new TextField();
    @FXML
    public TextField field5 = new TextField();

    @FXML
    public Label status = new Label();

    @FXML
    public Button close = new Button();
    @FXML
    public Button insert = new Button();


    //Functia pentru inchiderea scenei
    public void close() {
        Main.setSelectCanceled(true);
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    //Functia care se ocupa pentru buton de inserarea dupa ce s-au adaugat datele
    public void insert() {

        Connection con;
        Statement stmt = null;

        try {
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(Main.getLoginJDBC().getFinal_conn());

            //Create and execute an SQL statement that returns some data
            stmt = con.createStatement();
            String SQL =
                    "INSERT INTO [Proiect].[dbo].[Adrese]\n" +
                            "           ([Linia_1]\n" +
                            "           ,[Linia_2]\n" +
                            "           ,[Oras]\n" +
                            "           ,[Judet]\n" +
                            "           ,[Cod_Postal])\n" +
                            "     VALUES\n" +
                            "           ('" + field1.getText() + "',\n" +
                            "           '" + field2.getText() + "',\n" +
                            "           '" + field3.getText() + "',\n" +
                            "           '" + field4.getText() + "',\n" +
                            "           " + field5.getText() + ");\n";
            stmt.executeUpdate(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            field1.setText("");
            field2.setText("");
            field3.setText("");
            field4.setText("");
            field5.setText("");

            status.setText("Inserare completa");

            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }


}
