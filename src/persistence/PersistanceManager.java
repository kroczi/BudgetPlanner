package persistence;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class PersistanceManager {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/BudgetPlanner";
    Connection conn = null;
    Statement stmt = null;

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

    public Integer getMonthId(int year,int month) throws SQLException
    {
        Integer monthId = null;
        System.out.println("Creating statement...");
        String sql;
        sql = "SELECT MonthId from Month where Year =" + year + " and Month =" + month + " ";
        ResultSet rs = ((java.sql.Statement) stmt).executeQuery(sql);
        if(rs.next()) monthId =  rs.getInt("MonthId");
        return monthId;
    }


    public Map<String,Integer> getPlannedValuesForMonth(int year,int month) throws SQLException
    {
        HashMap<String,Integer> map = new HashMap<String, Integer>();
        //STEP 4: Execute a query
        System.out.println("Creating statement...");
        String sql;
        Integer monthId = getMonthId(year, month);
        if (monthId == null)
        {
            return map;
        }

        sql = "SELECT CategoryName,PlannedValue from Plan where MonthId =" + monthId + " ";
        ResultSet rs = ((java.sql.Statement) stmt).executeQuery(sql);

        //STEP 5: Extract data from result set
        while(rs.next()){
            //Retrieve by column name
            int plannedValue  = rs.getInt("PlannedValue");
            String categoryName = rs.getString("CategoryName");
            map.put(categoryName, plannedValue);

            //Display values
            System.out.print("CategoryName: " + categoryName);
            System.out.println(", plannedValue: " + plannedValue);
        }
        //STEP 6: Clean-up environment
        rs.close();
        return map;
    }

    public int insertIntoMonth(int year, int month) throws SQLException
    {
        Integer monthId = getMonthId(year, month);
        if (monthId != null) return monthId ;
        System.out.println("Creating statement...");
        String sql;
        sql = "select MonthId from Month order by MonthId desc LIMIT 1";
        ResultSet rs = ((java.sql.Statement) stmt).executeQuery(sql);
        if(rs.next())
        {
            monthId = rs.getInt("MonthId");
        } else
        {
            monthId = 1;
        }
        monthId +=1;
        sql = "INSERT INTO Month " +
                "VALUES("+monthId+",'"+year+"','"+month+")";
        System.out.println(sql);
        stmt.executeUpdate(sql);
        System.out.println("Inserted records into the table...");
        return monthId + 1;
    }

    public void insertIntoPlan(String categoryName,int plannedValue, int year, int month) throws SQLException
    {
        Integer monthId = getMonthId(year, month);
        if (getMonthId(year, month) == null)
        {
            monthId = insertIntoMonth(year, month);
        }
        String sql = "INSERT INTO Plan " +
                "VALUES('"+categoryName+"',"+plannedValue+","+monthId +")";
        System.out.println(sql);
        stmt.executeUpdate(sql);
        System.out.println("Inserted records into the table...");
    }
    public void closeConnection() throws SQLException
    {
        //STEP 6: Clean-up environment
        if(stmt!=null)
            stmt.close();
        if(conn!=null)
            conn.close();
    }

    public static void main(String [] argv)
    {
        PersistanceManager persistanceManager = new PersistanceManager();
        try {
            persistanceManager.register();
            persistanceManager.openConnection();
            persistanceManager.createStatement();
            //persistanceManager.insertIntoPlan("MPK",200,2015,12);
            persistanceManager.getPlannedValuesForMonth(2015,12);
            persistanceManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
