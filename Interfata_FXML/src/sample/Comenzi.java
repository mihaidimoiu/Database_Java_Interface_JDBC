package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Comenzi {
    @FXML
    Button btn1 = new Button();
    @FXML
    Button btn2 = new Button();

    @FXML
    TextField field1 = new TextField();
    @FXML
    TextField field2 = new TextField();


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

            //Extragerea datei curente din PC si formatarea acesteia pentru a se potrivi cu cea din SQL
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateobj = new Date();
            System.out.println(df.format(dateobj));

            String SQL = " INSERT INTO [Proiect].[dbo].[Comenzi]\n" +
                    " ([ID_Client]\n" +
                    ",[Data_Comanda]\n" +
                    ",[Status])\n" +
                    "VALUES\n" +
                    "(" + field1.getText() + "\n" +
                    ",'" + df.format(dateobj) + "'\n" +
                    ",'" + field2.getText() + "');\n";
            stmt.executeUpdate(SQL);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            field1.setText("");
            field2.setText("");


            System.out.println("Ok");
            if (stmt != null)
                try {
                    stmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    public void close() {
        Main.setSelectCanceled(true);
        Stage stage = (Stage) btn2.getScene().getWindow();
        stage.close();
    }
}
