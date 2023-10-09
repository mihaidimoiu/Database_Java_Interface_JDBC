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
import java.sql.*;

public class Delete {
    @FXML
    public Button btn1 = new Button();
    @FXML
    public Button btn2 = new Button();

    @FXML
    public TextField field1 = new TextField();

    @FXML
    public Label label1 = new Label();

    @FXML
    private TableView tableview = new TableView();


    private ObservableList<ObservableList> data;

    @FXML
    public ComboBox cb = new ComboBox();

    //Initializarea obiectelor din interfata
    @FXML
    public void initialize() {
        cb.getItems().addAll(
                "Categorii",
                "Producatori"
        );
        field1.setVisible(false);
        label1.setVisible(false);
    }

    //Inchiderea secnei curente
    public void close() throws IOException {
        Main.setSelectCanceled(true);
        Stage stage = (Stage) btn1.getScene().getWindow();
        stage.setTitle("Optiuni");
        AnchorPane anchorPane = null;
        anchorPane = FXMLLoader.load(getClass().getResource("optiuni.fxml"));
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();

    }

    //Functia pentru actualizarea tabelei din interfata
    private void table(String SQL) {
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


    public void display() {
        String str = cb.getValue().toString();
        if (str == "Categorii") {
            field1.setVisible(true);

            label1.setVisible(true);
            label1.setText("Nume");

            table("SELECT a.Nume\n" +
                    "  FROM [Proiect].[dbo].[Categorii] a");
        } else if (str == "Producatori") {
            field1.setVisible(true);
            label1.setVisible(true);
            label1.setText("Nume Producator");


            table("SELECT a.Nume_Producator\n" +
                    "  FROM [Proiect].[dbo].[Producatori] a");
        }
    }

    //Functia pentru stergerea elementelor din baza de date
    public void delete() {
        Connection con = null;
        Statement stmt = null;
        if (cb.getValue() == "Producatori") {

            try {
                // Establish the connection.
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection(Main.getLoginJDBC().getFinal_conn());
                stmt = con.createStatement();
                String SQL = "DELETE FROM [Proiect].[dbo].[Producatori]\n" +
                        "      WHERE [Proiect].[dbo].[Producatori].Nume_Producator = '" + field1.getText() + "'";
                stmt.executeUpdate(SQL);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                field1.setText("");

                //status.setText("Done inserting");
                System.out.println("DONE");
                if (stmt != null)
                    try {
                        stmt.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                table("SELECT a.Nume_Producator\n" +
                        "  FROM [Proiect].[dbo].[Producatori] a");
            }
        } else if (cb.getValue() == "Categorii") {
            try {
                // Establish the connection.
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                con = DriverManager.getConnection(Main.getLoginJDBC().getFinal_conn());

                //Create and execute an SQL statement that returns some data.
                stmt = con.createStatement();
                String SQL = "DELETE FROM [Proiect].[dbo].[Categorii]\n" +
                        "      WHERE [Proiect].[dbo].[Categorii].Nume = '" + field1.getText() + "'";
                stmt.executeUpdate(SQL);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                field1.setText("");

                //status.setText("Done inserting");
                System.out.println("DONE");
                if (stmt != null)
                    try {
                        stmt.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                if (con != null) {
                    try {
                        con.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                table("SELECT a.Nume\n" +
                        "  FROM [Proiect].[dbo].[Categorii] a");
            }
        }
    }
}
