package pl.agh.edu.to2.budgetplanner.model;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableStringValue;
import javafx.scene.control.TreeItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 22/11/15.
 */
public class Category {
    private StringProperty name;
    private IntegerProperty transactions;
    private IntegerProperty plan;
    private List<Category> subcategories;
    private NumberBinding saldoBinding;
    private NumberBinding planBinding;
    private NumberBinding transactionsBinding;



    public Category(String name, int transactions, int plan)
    {
        this.name = new SimpleStringProperty(name);
        this.transactions = new SimpleIntegerProperty(transactions);
        this.plan = new SimpleIntegerProperty(plan);
        saldoBinding = Bindings.subtract(this.plan, this.transactions);
        subcategories = null;

    }

    public Category(String name)
    {
        this.name = new SimpleStringProperty(name);
        this.subcategories = new LinkedList<Category>();
    }


    public void addChildren(Category category)
    {
        if (category.isLeaf()) {
            if (saldoBinding == null)
            {
                planBinding = Bindings.add(category.getPlanProperty(), new SimpleIntegerProperty(0));
                transactionsBinding = Bindings.add(category.getTransactionsProperty(), new SimpleIntegerProperty(0));
                saldoBinding = Bindings.subtract(planBinding, transactionsBinding);
            } else
            {
                planBinding = Bindings.add(category.getPlanProperty(), planBinding);
                transactionsBinding = Bindings.add(category.getTransactionsProperty(), transactionsBinding);
                saldoBinding = Bindings.subtract(planBinding, transactionsBinding);
            }

        } else
        {
            planBinding = planBinding.add(category.getPlanBinding());
            transactionsBinding = transactionsBinding.add(category.getTransactionsBinding());
            //saldoBinding = Bindings.subtract(planBinding, transactionsBinding);
        }

        subcategories.add(category);
    }

    public ObservableIntegerValue getSaldoValue()
    {
        return (ObservableIntegerValue) getSaldoBinding();
    }

    public ObservableIntegerValue getTransactionsValue()
    {
        if(isLeaf()){
            return getTransactionsProperty();
        } else
            return (ObservableIntegerValue) getTransactionsBinding();
    }

    public ObservableStringValue getPlanValue()
    {
        if(isLeaf()){
            return getPlanProperty().asString();
        } else
            return getPlanBinding().asString();
    }

    public NumberBinding getSaldoBinding() {
        return saldoBinding;
    }

    public NumberBinding getPlanBinding() {
        return planBinding;
    }

    public NumberBinding getTransactionsBinding() {
        return transactionsBinding;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getPlan() {
        return plan.get();
    }

    public IntegerProperty getPlanProperty() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan.set(plan);
    }

    public int getTransactions() {
        return transactions.get();
    }

    public IntegerProperty getTransactionsProperty() {
        return transactions;
    }

    public void setTransactions(int transactions) {
        this.transactions.set(transactions);
    }

    public boolean isLeaf()
    {
        return subcategories ==  null;
    }

    public TreeItem<Category> buildTree(){
        TreeItem<Category> root = new TreeItem<Category>(this);
        if (!isLeaf()) {
            for (Category child : subcategories)
            {
                root.getChildren().add(child.buildTree());
            }
        }
        return root;
    }


}
