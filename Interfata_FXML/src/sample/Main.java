package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static LoginJDBC loginJDBC = new LoginJDBC();
    private static boolean selectCanceled = true;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("LoginJDBC");
        primaryStage.setScene(new Scene(root, 700, 600));
        //primaryStage.setScene(new Scene(root, 1920, 1080));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static LoginJDBC getLoginJDBC() {
        return loginJDBC;
    }

    public static void setSelectCanceled(boolean selectCanceled) {
        Main.selectCanceled = selectCanceled;
    }

    public static boolean isSelectCanceled() {
        return selectCanceled;
    }
}
