package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Select {

    @FXML
    private Button close = new Button();
    @FXML
    private Button select = new Button();
    @FXML
    private Button selectAll = new Button();

    @FXML
    private TableView tableview = new TableView();

    @FXML
    private Label labelNume = new Label();

    @FXML
    private TextField field1 = new TextField();
    @FXML
    private TextField field2 = new TextField();

    private ObservableList<ObservableList> data;

    @FXML
    public ComboBox cb = new ComboBox();
    @FXML
    public ComboBox cb2 = new ComboBox();
    @FXML
    public ComboBox cb3 = new ComboBox();

    @FXML
    public void initialize() {
        cb.getItems().addAll(
                "Adresa",
                "Categorii",
                "Clienti",
                "Comenzi",
                "Producatori",
                "Produse"
        );
        cb.setValue("Alege");
        cb2.setValue("Alege");
        cb3.setValue("Alege");
        cb2.getItems().addAll(
                "Au",
                "Nu au"
        );
        cb3.getItems().addAll(
                "Au",
                "Nu au"
        );
        field1.setVisible(false);
        labelNume.setVisible(false);
    }

    private String cb1;

    public void cbsel() {
        cb1 = cb.getValue().toString();
        field1.setText("");
        if (cb1.equals("Produse")) {
            field1.setVisible(true);
            labelNume.setVisible(true);
            labelNume.setText("Nume Produs");
        } else {
            field1.setVisible(false);
            labelNume.setVisible(false);
        }
        if (cb1.equals("Producatori")) {
            field1.setVisible(true);
            labelNume.setVisible(true);
            labelNume.setText("Nume Producatori");
        }
        if (cb1.equals("Categorii")) {
            field1.setVisible(true);
            labelNume.setVisible(true);
            labelNume.setText("Categorie");
        }
        if (cb1.equals("Comenzi")) {
            field1.setVisible(true);
            labelNume.setVisible(true);
            labelNume.setText("Nume Client");
        }
        if (cb1.equals("Adresa")) {
            field1.setVisible(true);
            labelNume.setVisible(true);
            labelNume.setText("Nume Oras");
        }
        System.out.println(cb1);
        cb2.setValue("Alege");
        cb3.setValue("Alege");
    }

    public void cbsel2() {      //toti clienti care au /n-au facut cumparaturi
        if (cb2.getValue().toString().equals("Au")) {
            print("SELECT cl.Nume, adr.Linia_1 + ' ' + adr.Linia_2 AS 'Adresa' ,adr.Oras,adr.Judet,adr.Cod_Postal\n" +
                    "FROM Proiect.dbo.Client cl INNER JOIN Adrese adr ON cl.ID_Adresa = adr.ID_Adresa \n" +
                    "WHERE cl.ID_Client IN (SELECT cl1.ID_Client \n" +
                    "FROM Client cl1, Comenzi cm\n" +
                    "WHERE cl.ID_Client = cm.ID_Client) AND adr.Oras ='"+ field2.getText()+"'");
        }
        if (cb2.getValue().toString().equals("Nu au")) {
            print("SELECT cl.Nume, adr.Linia_1 + ' ' + adr.Linia_2 AS 'Adresa' ,adr.Oras,adr.Judet,adr.Cod_Postal\n" +
                    "FROM Proiect.dbo.Client cl INNER JOIN Adrese adr ON cl.ID_Adresa = adr.ID_Adresa \n" +
                    "WHERE cl.ID_Client NOT IN (SELECT cl1.ID_Client \n" +
                    "FROM Client cl1, Comenzi cm\n" +
                    "WHERE cl.ID_Client = cm.ID_Client) AND adr.Oras ='"+ field2.getText()+"'");
        }
        cb3.setValue("Alege");
        cb.setValue("Alege");
    }
    public void cbsel3() {      //toate produsele care au /n-au facut cumparaturi

        if (cb3.getValue().toString().equals("Au")) {
            print("SELECT pr.Nume,pr.Pret\n" +
                    "FROM Proiect.dbo.Produse pr \n" +
                    "WHERE pr.ID_Produs IN (SELECT pc.ID_Produs\n" +
                    "FROM Proiect.dbo.Client cl INNER JOIN Proiect.dbo.Comenzi cm ON cl.ID_Client = cm.ID_Client INNER JOIN Proiect.dbo.Produse_Comenzi pc on cm.ID_Comanda = pc.ID_Comanda)");
        }
        if (cb3.getValue().toString().equals("Nu au")) {
            print("SELECT pr.Nume,pr.Pret\n" +
                    "FROM Proiect.dbo.Produse pr \n" +
                    "WHERE pr.ID_Produs NOT IN (SELECT pc.ID_Produs\n" +
                    "FROM Proiect.dbo.Client cl INNER JOIN Proiect.dbo.Comenzi cm ON cl.ID_Client = cm.ID_Client INNER JOIN Proiect.dbo.Produse_Comenzi pc on cm.ID_Comanda = pc.ID_Comanda)");
        }
        cb2.setValue("Alege");
        cb.setValue("Alege");
    }

    public void close() throws IOException {
        Main.setSelectCanceled(true);
        Stage stage = (Stage) close.getScene().getWindow();
        stage.setTitle("Optiuni");
        AnchorPane anchorPane = null;
        anchorPane = FXMLLoader.load(getClass().getResource("optiuni.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();
    }

    private void print(String SQL) {
        tableview.getColumns().clear();     //Remove all cloumns + data
        Connection con = null;
        ResultSet rs = null;
        data = FXCollections.observableArrayList();
        try {
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(Main.getLoginJDBC().getFinal_conn());

            //Create and execute an SQL statement that returns some data.

            rs = con.createStatement().executeQuery(SQL);
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                tableview.getColumns().addAll(col);
                //System.out.println("Column [" + i + "] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                //System.out.println("Row [1] added " + row);
                data.add(row);
            }
            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("DONE");
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void select() {

        if (cb.getValue().toString().equals("Categorii")) {
            print("  SELECT [Proiect].[dbo].Categorii.Nume, [Proiect].[dbo].Produse.Nume,[Proiect].[dbo].Produse.Pret\n" +
                    "        FROM [Proiect].[dbo].[Categorii] INNER JOIN [Proiect].[dbo].[Produse] ON Categorii.ID_Categorie = [Proiect].[dbo].[Produse].ID_Categorie\n" +
                    "        WHERE (lower([Proiect].[dbo].[Categorii].Nume) LIKE '%" + field1.getText() + "%');");
        }
        if (cb.getValue().toString().equals("Comenzi")) {
            print("SELECT cl.Nume,pr.Nume, pr.Pret\n" +
                    "FROM Produse_Comenzi pc INNER JOIN Produse pr ON  pc.ID_Produs = pr.ID_Produs INNER JOIN Comenzi cm ON pc.ID_Comanda = cm.ID_Comanda INNER JOIN Client cl ON cm.ID_Client = cl.ID_Client\n" +
                    "WHERE (lower(cl.Nume) LIKE '%" + field1.getText() + "%')");
        }

        if (cb.getValue().toString().equals("Clienti")) {
            print("SELECT [Nume],[CNP],[Sex],[Linia_1] + ' ' + [Linia_2] AS 'Adresa', [Oras],[Judet],[Cod_Postal]\n" +
                    " FROM [Proiect].[dbo].[Client] INNER JOIN [Proiect].[dbo].[Adrese] ON [Proiect].[dbo].[Client].ID_Adresa = [Proiect].[dbo].[Adrese].ID_Adresa ");
        }
        if (cb.getValue().toString().equals("Produse")) {
            print("SELECT [Nume],[Cantitate],[Pret],[Producatori].Nume_Producator AS 'Nume Producator'\n" +
                    "  FROM [Proiect].[dbo].[Produse] INNER JOIN  [Proiect].[dbo].[Producatori] ON [Proiect].[dbo].[Produse].ID_Producator = [Proiect].[dbo].[Producatori].ID_Producator\n" +
                    "  WHERE (lower([Produse].Nume) LIKE '%" + field1.getText() + "%');");
        }
        if (cb.getValue().toString().equals("Producatori")) {
            print("SELECT [Nume],[Cantitate],[Pret],[Producatori].Nume_Producator AS 'Nume Producator'\n" +
                    "  FROM [Proiect].[dbo].[Produse] INNER JOIN  [Proiect].[dbo].[Producatori] ON [Proiect].[dbo].[Produse].ID_Producator = [Proiect].[dbo].[Producatori].ID_Producator\n" +
                    "  WHERE (lower([Producatori].Nume_Producator) LIKE '%" + field1.getText() + "%');");
        }
        if (cb.getValue().toString().equals("Adresa")) {
            print("SELECT [Nume],[CNP],[Sex],[Linia_1] + ' ' + [Linia_2] AS 'Adresa', [Oras],[Judet],[Cod_Postal]\n" +
                    "  FROM [Proiect].[dbo].[Client] INNER JOIN [Proiect].[dbo].[Adrese] ON [Proiect].[dbo].[Client].ID_Adresa = [Proiect].[dbo].[Adrese].ID_Adresa\n" +
                    "  WHERE lower('" + field1.getText() + "') IN (SELECT Proiect.dbo.Adrese.Oras\n" +
                    "FROM Adrese)");
        }
    }

    public void selectAll() {
        print("SELECT [Nume],[Cantitate],[Pret],[Producatori].Nume_Producator AS 'Nume Producator'\n" +
                "  FROM [Proiect].[dbo].[Produse] INNER JOIN  [Proiect].[dbo].[Producatori] ON [Proiect].[dbo].[Produse].ID_Producator = [Proiect].[dbo].[Producatori].ID_Producator;");

    }
}
