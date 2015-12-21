package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

/**
 * Created by root on 22/11/15.
 */
public class DisplayedItem extends TreeItem<DisplayedItem> {
    private ObjectProperty<Category> category;
    private DoubleBinding planSum;
    private DoubleBinding transactionsSum;
    private DoubleBinding balance;

    private DoubleBinding progressBarBinding;


    public DisplayedItem(Category category)
    {
        this.category = new SimpleObjectProperty<Category>(category);
        this.planSum = Bindings.add(0.0, new SimpleDoubleProperty(0.0));
        this.transactionsSum = Bindings.add(0.0,new SimpleDoubleProperty(0.0));
        if (!category.hasChildren())
        {
            if (!category.isSpending())
            {
                this.balance = new DoubleBinding() {
                    {
                        super.bind(category.planValueProperty(), category.transactionsValueProperty());
                    }
                    @Override
                    protected double computeValue() {
                        return (category.transactionsValueProperty().get() - category.planValueProperty().get());
                    }
                };
            } else
            {
                this.balance = new DoubleBinding() {
                    {
                        super.bind(category.planValueProperty(), category.transactionsValueProperty());
                    }
                    @Override
                    protected double computeValue() {
                        return (category.planValueProperty().get() - category.transactionsValueProperty().get());
                    }
                };
            }
            this.progressBarBinding = new DoubleBinding() {
                {
                    super.bind(getTransactionsValueProperty(), getPlanValueProperty());
                }

                @Override
                protected double computeValue() {
                    return (getTransactionsValueProperty().getValue() / getPlanValueProperty().getValue());
                }
            };

        } else
        {
            if(!category.isSpending())
            {
                this.balance = new DoubleBinding() {
                    {
                        super.bind(planSum, transactionsSum);
                    }
                    @Override
                    protected double computeValue() {
                        return (transactionsSum.get() - planSum.get() );
                    }
                };
            } else
            {
                this.balance = new DoubleBinding() {
                    {
                        super.bind(planSum, transactionsSum);
                    }
                    @Override
                    protected double computeValue() {
                        return (planSum.get() - transactionsSum.get());
                    }
                };
            }
            this.progressBarBinding = new DoubleBinding() {
                {
                    super.bind(getTransactionsSumBinding(), getPlanSumBinding());
                }

                @Override
                protected double computeValue() {
                    return (getTransactionsSumBinding().getValue() / getPlanSumBinding().getValue());
                }
            };

            this.planSum = this.planSum.add(this.getPlanValueProperty());
            this.transactionsSum = this.transactionsSum.add(this.getTransactionsValueProperty());
        }



    }

    public void addChildren(DisplayedItem child)
    {
        if(child.isLeaf())
        {
            this.planSum = this.planSum.add(child.getPlanValueProperty());
            this.transactionsSum = this.transactionsSum.add(child.getTransactionsValueProperty());

        } else
        {
            this.planSum = this.planSum.add(child.getPlanSumBinding());
            this.transactionsSum = this.transactionsSum.add(child.getTransactionsSumBinding());
        }
        this.progressBarBinding = new DoubleBinding() {
            {
                super.bind(getTransactionsSumBinding(), getPlanSumBinding());
            }

            @Override
            protected double computeValue() {
                return (getTransactionsSumBinding().getValue() / getPlanSumBinding().getValue());
            }
        };
        if(!category.get().isSpending())
        {
            this.balance = new DoubleBinding() {
                {
                    super.bind(planSum, transactionsSum);
                }
                @Override
                protected double computeValue() {
                    return (transactionsSum.get() - planSum.get() );
                }
            };
        } else
        {
            this.balance = new DoubleBinding() {
                {
                    super.bind(planSum, transactionsSum);
                }
                @Override
                protected double computeValue() {
                    return (planSum.get() - transactionsSum.get());
                }
            };
        }
        super.getChildren().add(child);

    }

    public Double getProgressBarBindingValue() {
        return progressBarBinding.get();
    }

    public DoubleBinding progressBarBindingProperty() {
        return progressBarBinding;
    }

    public String getName() {
        return category.get().nameProperty().get();
    }

    public StringProperty getNameProperty() {
        return category.get().nameProperty();
    }

    public void setName(String name) {
        this.getNameProperty().set(name);
    }

    public double getPlanValue() {
        return category.get().planValueProperty().get();
    }

    public DoubleProperty getPlanValueProperty() {
        return category.get().planValueProperty();
    }

    public ObservableStringValue showPlanValueAsString()
    {
        return category.get().planValueProperty().asString();
    }

    public ObservableValue showBalance()
    {
        return (ObservableValue) this.balance;
    }

    public ObservableStringValue showPlanSumAsString()
    {
        if (isLeaf())
        {
            return new SimpleStringProperty("-");
        } else
        {
            return planSum.asString();
        }
    }

    public ObservableStringValue showTransactionsSumAsString()
    {
        if (isLeaf())
        {
            return new SimpleStringProperty("-");
        } else
        {
            return transactionsSum.asString();
        }
    }

    public boolean isSpending() {
        return category.get().isSpending();
    }

    public void setIsSpending(boolean isSpending) {
        this.category.get().setSpending(isSpending);
    }

    public void setPlanValue(double planValue) {
        this.category.get().setPlanValue(planValue);
    }

    public Double getPlanSum() {
        return planSum.getValue();
    }

    public DoubleBinding getPlanSumBinding() {
        return planSum;
    }

    public double getTransactionsValue() {
        return category.get().transactionsValueProperty().get();
    }

    public DoubleProperty getTransactionsValueProperty() {
        return category.get().transactionsValueProperty();
    }

    public void setTransactionsValue(double transactionsValue) {
        this.category.get().transactionsValueProperty().set(transactionsValue);
    }

    public Double getTransactionsSum() {
        return transactionsSum.getValue();
    }

    public DoubleBinding getTransactionsSumBinding() {
        return transactionsSum;
    }

    public Double getBalance() {
        return balance.getValue();
    }

    public DoubleBinding getBalanceBinding() {
        return balance;
    }


    public boolean isLeaf()
    {
        return super.getChildren().isEmpty();
    }

    public boolean hasParent(){
        return super.getParent() != null;
    }
}
