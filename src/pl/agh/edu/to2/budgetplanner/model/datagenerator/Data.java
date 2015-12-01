package pl.agh.edu.to2.budgetplanner.model.datagenerator;

import pl.agh.edu.to2.budgetplanner.model.Category;

import java.sql.SQLException;
import java.time.YearMonth;

public class Data {
    public Category incomeRootCategory;
    public Category outcomeRootCategory;
    public PersistanceManager persistanceManager;

    public Data(YearMonth yearMonth){
        this.persistanceManager = new PersistanceManager();
        try {
            persistanceManager.register();
            persistanceManager.openConnection();
            this.outcomeRootCategory = persistanceManager.getOutComeRootForYearMonth(YearMonth.of(yearMonth.getYear(), yearMonth.getMonth().getValue()));
            this.incomeRootCategory = persistanceManager.getIncomeRoootForYearMonth(YearMonth.of(yearMonth.getYear(), yearMonth.getMonth().getValue()));
            persistanceManager.closeConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateDataForMonth(YearMonth yearMonth)
    {
        try {
            persistanceManager.openConnection();
            this.outcomeRootCategory = persistanceManager.getOutComeRootForYearMonth(YearMonth.of(yearMonth.getYear(), yearMonth.getMonth().getValue()));
            this.incomeRootCategory = persistanceManager.getIncomeRoootForYearMonth(YearMonth.of(yearMonth.getYear(), yearMonth.getMonth().getValue()));
            persistanceManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category getIncomeRootCategory() {
        return incomeRootCategory;
    }

    public void setIncomeRootCategory(Category incomeRootCategory) {
        this.incomeRootCategory = incomeRootCategory;
    }

    public Category getOutcomeRootCategory() {
        return outcomeRootCategory;
    }

    public void setOutcomeRootCategory(Category outcomeRootCategory) {
        this.outcomeRootCategory = outcomeRootCategory;
    }


}
