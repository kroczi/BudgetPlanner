package pl.agh.edu.to2.budgetplanner.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TreeItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 22/11/15.
 */
public class Category {
    private StringProperty name;
    private StringProperty transactions;
    private IntegerProperty plan;
    private IntegerProperty saldo;
    private List<Category> subcategories;

    public Category(String name, String transactions, int plan, int saldo)
    {
        this.name = new SimpleStringProperty(name);
        this.transactions = new SimpleStringProperty(transactions);
        this.plan = new SimpleIntegerProperty(plan);
        this.saldo = new SimpleIntegerProperty(saldo);
        this.subcategories = new LinkedList<Category>();
    }

    public void addChildren(Category category)
    {
        this.subcategories.add(category);
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

    public int getSaldo() {
        return saldo.get();
    }

    public IntegerProperty getSaldoProperty() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo.set(saldo);
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

    public String getTransactions() {
        return transactions.get();
    }

    public StringProperty getTransactionsProperty() {
        return transactions;
    }

    public void setTransactions(String transactions) {
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
