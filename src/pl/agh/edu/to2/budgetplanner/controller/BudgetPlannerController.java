package pl.agh.edu.to2.budgetplanner.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import pl.agh.edu.to2.budgetplanner.model.Category;
import pl.agh.edu.to2.budgetplanner.pl.agh.edu.to2.budgetplaner.presenter.BudgetPresenter;

public class BudgetPlannerController {

    public BudgetPresenter presenter;

    public Category incomeCategoryRoot;

    public Category outcomeCategoryRoot;

    @FXML
    public ProgressBar budgetProgressBar;

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
    public TreeTableColumn<Category, Number> transactionsColumn;

    @FXML
    public TreeTableColumn<Category, String> planColumn;

    @FXML
    public TreeTableColumn<Category, Number> saldoColumn;


    @FXML
    private void handleAddCategoryAction(ActionEvent event) {
        presenter.showAddCategoryDialog();
    }

    @FXML
    private void initialize() {
        incomeTableTreeView.setEditable(true);
        outcomeTableTreeView.setEditable((true));

        categoryColumn.setCellFactory(TextFieldTreeTableCell.<Category>forTreeTableColumn());
        categoryColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Category, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Category, String> event) {
                event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().setName(event.getNewValue());
            }
        });

        planColumn.setCellFactory(TextFieldTreeTableCell.<Category>forTreeTableColumn());
        planColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Category, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Category, String> event) {
                event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().setPlan(Integer.parseInt(event.getNewValue()));
            }
        });
        categoryColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getNameProperty());
        transactionsColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getTransactionsValue());
        planColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getPlanValue());
        saldoColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getSaldoValue());
    }

    public void setData() {

        Category root = new Category("Transport");

        root.addChildren(new Category("Rower",10,4));
        root.addChildren(new Category("MPK",10,50));
        Category auto = new Category("Auto");
        auto.addChildren(new Category("PaliWO",500,1000));
        auto.addChildren(new Category("GUMIAK",100,200));
        root.addChildren(auto);

        overAllProgressBar.setProgress(0.4);
        overAllProgressBar.setStyle("-fx-accent: green;");
        budgetProgressBar.setProgress(0.2);
        budgetProgressBar.setStyle("-fx-accent: red;");
        incomeTableTreeView.setRoot(root.buildTree());

    }

    public void setPresenter(BudgetPresenter presenter) {
        this.presenter = presenter;
    }


}
