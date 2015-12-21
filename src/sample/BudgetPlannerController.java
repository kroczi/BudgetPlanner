package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class BudgetPlannerController {

    private Stage primaryStage;

    private VBox spendingVbox = new VBox();

    private VBox earningVbox = new VBox();

    @FXML
    public calendar.DatePicker datePicker;

    @FXML
    public TreeTableView earningTreeTableView;

    @FXML
    public TreeTableView spendingTreeTableView;

    @FXML
    public TreeTableColumn<DisplayedItem, String> earningCategoryColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, String> spendingCategoryColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, String> earningPlanValueColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, String> earningPlanSumColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, Number> earningTransactionValueColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, String> earningTransactionSumColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, Number> earningBalanceColumn;

    @FXML
    public AnchorPane earningProgressBarPane;

    @FXML
    public TreeTableColumn<DisplayedItem, String> spendingPlanValueColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, String> spendingPlanSumColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, Number> spendingTransactionValueColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, String> spendingTransactionSumColumn;

    @FXML
    public TreeTableColumn<DisplayedItem, Number> spendingBalanceColumn;

    @FXML
    public AnchorPane spendingProgressBarPane;

    @FXML
    public TableColumn<Data, Double> summaryAvaiableResourcesColumn;

    @FXML
    public TableColumn<Data, ObservableValue> summaryEarningsPlanColumn;

    @FXML
    public TableColumn<Data, ObservableValue> summaryEarininsRealColumn;

    @FXML
    public TableColumn<Data, ObservableValue> summarySpendingsPlanColumn;

    @FXML
    public TableColumn<Data, ObservableValue> summarySpendingsRealColumn;

    @FXML
    public TableColumn<Data, ObservableValue>  summarySpendingsBalanceColumn;

    @FXML
    public Button addCategoryButton;

    @FXML
    public Button editCategoryButton;

    @FXML
    public TableView summaryTableView;

    @FXML
    private void handleAddCategoryAction(ActionEvent event) {
        Parent root;
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("AddCategory.fxml"));
            BorderPane page = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit transaction");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditCategoryAction(ActionEvent event) {

    }

    @FXML
    public void initialize()
    {
        earningTreeTableView.setEditable(true);
        spendingTreeTableView.setEditable(true);
        earningCategoryColumn.setCellFactory(TextFieldTreeTableCell.<DisplayedItem>forTreeTableColumn());
        earningCategoryColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<DisplayedItem, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<DisplayedItem, String> event) {
                DisplayedItem p = (DisplayedItem) event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow());
                        p.setName(event.getNewValue());

            }
        });
        earningCategoryColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.getNameProperty();
                }
        );
        spendingCategoryColumn.setCellFactory(TextFieldTreeTableCell.<DisplayedItem>forTreeTableColumn());
        spendingCategoryColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<DisplayedItem, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<DisplayedItem, String> event) {
                DisplayedItem p = (DisplayedItem) event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow());
                p.setName(event.getNewValue());
            }
        });
        spendingCategoryColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.getNameProperty();
                }
        );
        earningPlanValueColumn.setCellFactory(TextFieldTreeTableCell.<DisplayedItem>forTreeTableColumn());
        earningPlanValueColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<DisplayedItem, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<DisplayedItem, String> event) {
                DisplayedItem displayedItem = (DisplayedItem)event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow());
                displayedItem.setPlanValue(Double.parseDouble(event.getNewValue()));
                //updateEarningProgressBarValue(displayedItem);
            }
        });
        earningPlanValueColumn.setCellValueFactory(dataValue -> {
            DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showPlanValueAsString();
         }
        );
        spendingPlanValueColumn.setCellFactory(TextFieldTreeTableCell.<DisplayedItem>forTreeTableColumn());
        spendingPlanValueColumn.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<DisplayedItem, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<DisplayedItem, String> event) {
                DisplayedItem displayedItem = (DisplayedItem)event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow());
                displayedItem.setPlanValue(Double.parseDouble(event.getNewValue()));
                //updateSpendingProgressBarValue(displayedItem);
            }
        });
        spendingPlanValueColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showPlanValueAsString();
                }
        );
        spendingBalanceColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showBalance();
                }
        );
        earningBalanceColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showBalance();
                }
        );
        earningBalanceColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showBalance();
                }
        );
        earningPlanSumColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showPlanSumAsString();
                }
        );
        spendingPlanSumColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showPlanSumAsString();
                }
        );
        spendingPlanSumColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showPlanSumAsString();
                }
        );
        earningTransactionValueColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.getTransactionsValueProperty();
                }
        );
        spendingTransactionValueColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.getTransactionsValueProperty();
                }
        );

        earningTransactionSumColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showTransactionsSumAsString();
                }
        );

        spendingTransactionSumColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showTransactionsSumAsString();
                }
        );
        summaryAvaiableResourcesColumn.setCellValueFactory(dataValue -> dataValue.getValue().getavailaibleResourcesProperty());
        summaryEarningsPlanColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSummaryEarningsPlanColumn());
        summaryEarininsRealColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSummaryEarininsRealColumn());
        summarySpendingsPlanColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSummarySpendingsPlanColumn());
        summarySpendingsRealColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSummarySpendingsRealColumn());
        summarySpendingsBalanceColumn.setCellValueFactory(dataValue -> dataValue.getValue().getSummarySpendingsBalanceColumn());
        earningTransactionSumColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showTransactionsSumAsString();
                }
        );
        spendingTransactionSumColumn.setCellValueFactory(dataValue -> {
                    DisplayedItem p = (DisplayedItem) dataValue.getValue();
                    return p.showTransactionsSumAsString();
                }
        );

        Data data = new Data(buildTree(DataGenerator.generateSpendings(), spendingVbox), 5000.0, buildTree(DataGenerator.generateEarnings(), earningVbox));

        spendingTreeTableView.setRoot(data.getSpendingDisplayedItemRoot());
        earningTreeTableView.setRoot(data.getEarningDisplayedItemRoot());
        final ObservableList<Data> datalist =
                FXCollections.observableArrayList(data
                );
        summaryTableView.setItems(datalist);
        spendingProgressBarPane.getChildren().addAll(spendingVbox);
        earningProgressBarPane.getChildren().addAll(earningVbox);

    }
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    private void addTextField(DisplayedItem item, VBox vbox)
    {
        TextField textField = new TextField(item.getName());
        textField.setEditable(false);
        vbox.getChildren().add(textField);
        item.getNameProperty().addListener(new ChangeListener(){
                                               @Override public void changed(ObservableValue o,Object oldVal,
                                                                             Object newVal) {
                                                   textField.setText((String)newVal);
                                               }}
        );
    }
    private ProgressBar addProgressBar(DisplayedItem item, VBox vbox)
    {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setStyle("-fx-accent: deepskyblue;");
        if (item.isSpending())
        {
            progressBar.setPrefWidth(spendingProgressBarPane.getPrefWidth());
        } else
        {
            progressBar.setPrefWidth(earningProgressBarPane.getPrefWidth());
        }
        vbox.getChildren().add(progressBar);
        return  progressBar;
    }

    private void setProgressBarProgress(DisplayedItem item, ProgressBar progressBar)
    {
        progressBar.setProgress(item.getProgressBarBindingValue());
        if (item.isSpending())
        {
            if(item.getProgressBarBindingValue() > 1.0)
                progressBar.setStyle("-fx-accent: red;");

        } else
        {
            if(item.getProgressBarBindingValue() < 0.5)
                progressBar.setStyle("-fx-accent: red;");
        }

        item.progressBarBindingProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue o, Object oldVal,
                                Object newVal) {
                Double value = item.progressBarBindingProperty().getValue();
                progressBar.setProgress(value);
                if (item.isSpending())
                {
                    if(value > 1.0)
                        progressBar.setStyle("-fx-accent: red;");
                    else
                        progressBar.setStyle("-fx-accent: deepskyblue;");

                } else
                {
                    if(value < 0.5)
                        progressBar.setStyle("-fx-accent: red;");
                    else
                        progressBar.setStyle("-fx-accent: deepskyblue;");
                }
            }
        });
    }




    public DisplayedItem buildTree(Category category,VBox vbox){
        DisplayedItem root = new DisplayedItem(category);
        addTextField(root, vbox);
        ProgressBar progressBar = addProgressBar(root, vbox);
        if (category.hasChildren()) {

            for (Category child : category.getSubcategories())
            {
                DisplayedItem newChild = (DisplayedItem) buildTree(child, vbox);
                root.addChildren(newChild);
            }
        }
        setProgressBarProgress(root, progressBar);
        return root;
    }
    /*
    public void registerProgressBarListener(boolean isSpending, double val, DisplayedItem displayedItem)
    {


    }
    */



}
