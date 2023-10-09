package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class LoginJDBC {

    private String url = "jdbc:sqlserver://localhost:1433;";
    private String db_name = "databaseName=;";
    private String user = "user=";
    private String pass = "password=";
    private Connection con = null;

    private String final_conn = null;

    public boolean connect(String s1, String s2, String s3) throws Exception {
        String user_temp = "user=";
        String pass_temp = "password=";
        String db_name_temp = "databaseName=";

        user_temp = user_temp + s1 + ";";
        pass_temp = pass_temp + s2;
        db_name_temp = db_name_temp + s3 + ";";


        try {
            System.out.println("-------------------------");
            System.out.println("Username: " + s1);
            System.out.println("Password: " + s2);
            System.out.println("Database: " + s3);
            System.out.println("-------------------------");
            // Establish the connection.
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(url + db_name_temp + user_temp + pass_temp);
        }
        // Handle any errors that may have occurred.
        catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
        }
        final_conn = url + db_name_temp + user_temp + pass_temp;
        return true;
    }

    public String getFinal_conn() {
        return final_conn;
    }

    public boolean disconnect() {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Disconnected!");
            return true;
        } else {
            System.out.println("You haven't been connected!");
        }
        return false;
    }

}
