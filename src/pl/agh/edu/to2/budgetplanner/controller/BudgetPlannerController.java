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
import pl.agh.edu.to2.budgetplanner.model.datagenerator.Data;
import pl.agh.edu.to2.budgetplanner.pl.agh.edu.to2.budgetplaner.presenter.BudgetPresenter;

import java.time.YearMonth;

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
    public TreeTableColumn<Category, String> incomeCategoryColumn;

    @FXML
    public TreeTableColumn<Category, Number> incomeTransactionsColumn;

    @FXML
    public TreeTableColumn<Category, String> incomePlanColumn;

    @FXML
    public TreeTableColumn<Category, Number> incomeSaldoColumn;

    @FXML
    public TreeTableColumn<Category, String> outcomeCategoryColumn;

    @FXML
    public TreeTableColumn<Category, Number> outcomeTransactionsColumn;

    @FXML
    public TreeTableColumn<Category, String> outcomePlanColumn;

    @FXML
    public TreeTableColumn<Category, Number> outcomeSaldoColumn;


    @FXML
    private void handleAddCategoryAction(ActionEvent event) {presenter.showAddCategoryDialog();}

    @FXML
    private void initialize() {
        incomeTableTreeView.setEditable(true);
        outcomeTableTreeView.setEditable((true));

        outcomeCategoryColumn.setCellFactory(TextFieldTreeTableCell.<Category>forTreeTableColumn());
        outcomeCategoryColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Category, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Category, String> event) {
                event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().setName(event.getNewValue());
            }
        });

        outcomePlanColumn.setCellFactory(TextFieldTreeTableCell.<Category>forTreeTableColumn());
        outcomePlanColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Category, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Category, String> event) {
                event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().setPlan(Integer.parseInt(event.getNewValue()));
            }
        });
        outcomeCategoryColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getNameProperty());
        outcomeTransactionsColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getTransactionsValue());
        outcomePlanColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getPlanValue());
        outcomeSaldoColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getSaldoValue());

        incomeCategoryColumn.setCellFactory(TextFieldTreeTableCell.<Category>forTreeTableColumn());
        incomeCategoryColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Category, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Category, String> event) {
                event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().setName(event.getNewValue());
            }
        });

        incomePlanColumn.setCellFactory(TextFieldTreeTableCell.<Category>forTreeTableColumn());
        incomePlanColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Category, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Category, String> event) {
                event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow()).getValue().setPlan(Integer.parseInt(event.getNewValue()));
            }
        });
        incomeCategoryColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getNameProperty());
        incomeTransactionsColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getTransactionsValue());
        incomePlanColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getPlanValue());
        incomeSaldoColumn.setCellValueFactory(dataValue -> dataValue.getValue().getValue().getSaldoValue());
    }

    public void setData() {

        Data data = new Data(YearMonth.of(2015,12));
        //incomeTableTreeView.setRoot(data.getIncomeRootCategory().buildTree());
        outcomeTableTreeView.setRoot(data.getOutcomeRootCategory().buildTree());
        overAllProgressBar.setProgress(0.4);
        overAllProgressBar.setStyle("-fx-accent: green;");
        budgetProgressBar.setProgress(0.2);
        budgetProgressBar.setStyle("-fx-accent: red;");

    }

    public void setPresenter(BudgetPresenter presenter) {
        this.presenter = presenter;
    }


}
