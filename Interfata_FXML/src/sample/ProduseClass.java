package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ProduseClass {

    @FXML
    public TextField field1 = new TextField();
    @FXML
    public TextField field2 = new TextField();
    @FXML
    public TextField field3 = new TextField();

    @FXML
    public Button btn1 = new Button();
    @FXML
    public Button btn2 = new Button();

    @FXML
    public Label label1 = new Label("");

    public void cancel() {
        Main.setSelectCanceled(true);
        Stage stage = (Stage) btn1.getScene().getWindow();
        stage.close();
    }

    public void insert() {


        Connection con;
        Statement stmt = null;

        try {
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(Main.getLoginJDBC().getFinal_conn());

            //Create and execute an SQL statement that returns some data.
            stmt = con.createStatement();
            String SQL =
                    "INSERT INTO [Proiect].[dbo].[Produse]\n" +
                            "           ([Nume]\n" +
                            "           ,[Cantitate]\n" +
                            "           ,[Pret])\n" +
                            "      VALUES\n" +
                            "           ('" + field1.getText() + "',\n" +
                            "           " + field2.getText() + ",\n" +
                            "           " + field3.getText() + ");\n";

            stmt.executeUpdate(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            field1.setText("");
            field2.setText("");
            field3.setText("");
            label1.setText("Done inserting");

            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }


    }
}
