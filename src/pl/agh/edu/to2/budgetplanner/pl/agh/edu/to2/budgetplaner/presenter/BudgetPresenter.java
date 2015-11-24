package pl.agh.edu.to2.budgetplanner.pl.agh.edu.to2.budgetplaner.presenter;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.agh.edu.to2.budgetplanner.Main;
import pl.agh.edu.to2.budgetplanner.controller.AddCategoryDialogCOntroller;
import pl.agh.edu.to2.budgetplanner.controller.BudgetPlannerController;

import java.io.IOException;

/**
 * Created by root on 22/11/15.
 */
public class BudgetPresenter {

    private Stage primaryStage;

    public BudgetPresenter(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("Budget Planner");

            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/BudgetPlanner.fxml"));
            TitledPane rootLayout = (TitledPane) loader.load();

            // set initial data into controller
            BudgetPlannerController controller = loader.getController();
            controller.setPresenter(this);
            controller.setData();

            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            // don't do this in common apps
            e.printStackTrace();
        }

    }

    public void showAddCategoryDialog() {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AddCategory.fxml"));
            BorderPane page = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add category");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddCategoryDialogCOntroller controller = loader.getController();

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}