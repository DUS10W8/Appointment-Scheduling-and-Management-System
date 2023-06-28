package MAIN;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

    /**
    runs application that facilitates a customer and appointment management.
     */
public class Main extends Application {

    static Stage stage;

    /**
    *displays the log in screen and loads language bundles.
     *
     */
    @Override
    public void start(Stage stage) {

        this.stage = stage;

        ResourceBundle rb = ResourceBundle.getBundle("language_files/rb", Locale.getDefault());

        Parent main = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/VIEW/LogInScreen.fxml"));
            loader.setResources(rb);
            main = loader.load();

            Scene scene = new Scene(main);

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
    *makes connection with database and launches program.
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        JDBC.makeConnection();

        launch(args);

    }

    /**
    *gets stage
    * @return stage
     */
public static Stage getStage() {
        return stage;
}


}
