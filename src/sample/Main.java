package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // load layout from FXML file
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("budget.fxml"));
        BorderPane rootLayout = (BorderPane) loader.load();

        // set initial data into controller
        BudgetPlannerController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
        Scene scene = new Scene(rootLayout);
        scene.getStylesheets().add("calendarstyle.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
