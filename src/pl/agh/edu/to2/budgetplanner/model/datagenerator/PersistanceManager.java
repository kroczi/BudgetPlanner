package pl.agh.edu.to2.budgetplanner.model.datagenerator;

import pl.agh.edu.to2.budgetplanner.model.Category;

import java.sql.*;
import java.time.YearMonth;

/**
 * Created by root on 27/11/15.
 */
public class PersistanceManager {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/mydb";
    Connection conn = null;
    Statement stmt = null;
    private int monthId;

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";


    public void register() throws ClassNotFoundException
    {
        //STEP 2: Register JDBC driver
        Class.forName("com.mysql.jdbc.Driver");
    }
    public void openConnection() throws SQLException
    {
        //STEP 3: Open a connection
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
    }
    public void createStatement() throws SQLException
    {
        stmt =  conn.createStatement();
    }

    public void closeConnection() throws SQLException
    {
        if(conn!=null)
            conn.close();
    }

    public Category getOutComeRootForYearMonth(YearMonth yearMonth)
    {

        Category root = null;
        setMonth(yearMonth);
        try {
            createStatement();
            ResultSet rs = stmt.executeQuery("select oc.OutcomeCategoryID, oc.CategoryName from OutcomeCategory oc where oc.ParentCategory is null;");
            //STEP 5: Extract data from result set
            if (rs.next()) {
                //Retrieve by column name
                int rootId = rs.getInt("OutcomeCategoryID");
                String rootName = rs.getString("CategoryName");
                root = new Category(rootName, rootId);
                buildTree(root);
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return root;
    }

    public Category getIncomeRoootForYearMonth(YearMonth yearMonth)
    {
        return null;
    }

    public void setMonth(YearMonth yearMonth)
    {
        try {
            int year = yearMonth.getYear();
            int month = yearMonth.getMonth().getValue();
            createStatement();
            ResultSet rs = stmt.executeQuery("select MonthId from Month where year = " + year + " and month = " + month + ";");
            //STEP 5: Extract data from result set
            if (rs.next()) {
                //Retrieve by column name
                int montId = rs.getInt("MonthId");
                this.monthId = montId;

            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void buildTree(Category parent)
    {
        Category child = null;
        try {
            createStatement();
            ResultSet rs = stmt.executeQuery("select oc.OutcomeCategoryId, oc.CategoryName, ocm.PlannedOutcome  from OutcomeCategory oc left outer join OutCatMonth ocm on oc.OutcomeCategoryId = ocm.CategoryId where (ocm.MonthId = " + monthId + " or ocm.MonthId is null)  and oc.ParentCategory = " + parent.getId() + ";");
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int childId = rs.getInt("OutcomeCategoryId");
                Integer plannedOutcome = (Integer)rs.getObject("PlannedOutcome");
                String childName = rs.getString("CategoryName");

                // Modul BG WP
                //int transactionsValues = getTransactionsSumforCategoryMonth();

                int transactionValue = 100;
                if (plannedOutcome != null)
                {
                    child = new Category(childName, transactionValue, plannedOutcome, childId);
                } else
                {
                    child = new Category(childName, childId);
                    buildTree(child);
                }
                parent.addChildren(child);
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
