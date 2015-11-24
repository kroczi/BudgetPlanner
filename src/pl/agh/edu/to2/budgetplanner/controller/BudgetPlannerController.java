package pl.agh.edu.to2.budgetplanner.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pl.agh.edu.to2.budgetplanner.model.Category;
import pl.agh.edu.to2.budgetplanner.pl.agh.edu.to2.budgetplaner.presenter.BudgetPresenter;

public class BudgetPlannerController {

    public BudgetPresenter presenter;

    @FXML
    public ProgressBar overAllProgressBar;

    @FXML
    public Button addCategoryButton;

    @FXML
    public TreeTableView incomeTableTreeView;

    @FXML
    public TreeTableView outcomeTableTreeView;

    @FXML
    public TreeTableColumn<Category, String> categoryColumn;

    @FXML
    public TreeTableColumn<Category, String> transactionsColumn;

    @FXML
    public TreeTableColumn<Category, Number> planColumn;

    @FXML
    public TreeTableColumn<Category, Number> saldoColumn;


    @FXML
    private void handleAddCategoryAction(ActionEvent event) {
        presenter.showAddCategoryDialog();
    }


    @FXML
    private void initialize() {

        categoryColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getNameProperty());
        transactionsColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getTransactionsProperty());
        saldoColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getSaldoProperty());
        planColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getPlanProperty());
    }

    public void setData() {

        Category root = new Category("Transport","transactions",100,1000);

        root.addChildren(new Category("Rower","transactions",100,1000));
        root.addChildren(new Category("MPK","transactions",100,1000));
        Category auto = new Category("Auto","transactions",100,1000);
        auto.addChildren(new Category("GUMIAK","transactions",100,240));
        root.addChildren(auto);

        incomeTableTreeView.setRoot(root.buildTree());

    }

    public void setPresenter(BudgetPresenter presenter) {
        this.presenter = presenter;
    }


}
