package pl.agh.edu.to2.budgetplanner;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.agh.edu.to2.budgetplanner.pl.agh.edu.to2.budgetplaner.presenter.BudgetPresenter;

public class Main extends Application {

    private Stage primaryStage;

    private BudgetPresenter presenter;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("My first JavaFX app");

        this.presenter = new BudgetPresenter(primaryStage);
        this.presenter.initRootLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }


}