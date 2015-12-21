package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

/**
 * Created by root on 9/12/15.
 */
public class Data {
    private DisplayedItem spendingDisplayedItemRoot;
    private DisplayedItem earningDisplayedItemRoot;
    private DoubleProperty availaibleResources;

    public Data(DisplayedItem spendingDisplayedItemRoot, double availaibleResources, DisplayedItem earningDisplayedItemRoot) {
        this.spendingDisplayedItemRoot = spendingDisplayedItemRoot;
        this.availaibleResources = new SimpleDoubleProperty(availaibleResources);
        this.earningDisplayedItemRoot = earningDisplayedItemRoot;
    }

    public TreeItem getSpendingDisplayedItemRoot() {
        return spendingDisplayedItemRoot;
    }

    public void setSpendingDisplayedItemRoot(DisplayedItem spendingDisplayedItemRoot) {
        this.spendingDisplayedItemRoot = spendingDisplayedItemRoot;
    }

    public TreeItem getEarningDisplayedItemRoot() {
        return earningDisplayedItemRoot;
    }

    public void setEarningDisplayedItemRoot(DisplayedItem earningDisplayedItemRoot) {
        this.earningDisplayedItemRoot = earningDisplayedItemRoot;
    }

    public double getAvailaibleResources() {
        return availaibleResources.get();
    }

    public ObservableValue getavailaibleResourcesProperty() {
        return availaibleResources;
    }

    public void setAvailaibleResources(int availaibleResources) {
        this.availaibleResources.set(availaibleResources);
    }

    public ObservableValue getSummaryEarningsPlanColumn()
    {
        return earningDisplayedItemRoot.getPlanSumBinding();
    }

    public ObservableValue getSummaryEarininsRealColumn() {return earningDisplayedItemRoot.getTransactionsSumBinding();}

    public ObservableValue getSummarySpendingsPlanColumn() {
        return spendingDisplayedItemRoot.getPlanSumBinding();
    }


    public ObservableValue getSummarySpendingsRealColumn() {
        return spendingDisplayedItemRoot.getTransactionsSumBinding();
    }

    public ObservableValue getSummarySpendingsBalanceColumn() {
        return earningDisplayedItemRoot.getTransactionsSumBinding().subtract(spendingDisplayedItemRoot.getTransactionsSumBinding());
    }





}
