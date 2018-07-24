package librayproblem;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class hold {

    Statement stmt;
    Connection connection;
    private String insertHoldSQL = "insert into hold (item,patron,entered,overdue) values (?,?,CURDATE(),CURDATE()+ INTERVAl 7 DAY)";
    PreparedStatement insertHoldStmt;

    public hold() {
        connect();
    }

    public int IDPatron(String inputname) {
        
        String sqlStr = "SELECT idpatron FROM patron where name = \'" + inputname + "\'";
        int IDpatron = -1;
        try {
            ResultSet rset = stmt.executeQuery(sqlStr);
            if (rset.next()) {
                IDpatron = rset.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error!");
            
            ex.printStackTrace();
        }

        return IDpatron;

    }

    public int IDItem(int inputcallnumber) {
        
        String sqlStr1 = "SELECT iditem FROM item where callnumber = " + inputcallnumber;
        int IDitem = -1;
        try {
            ResultSet rset = stmt.executeQuery(sqlStr1);
            if (rset.next()) {
                IDitem = rset.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error!");
            
            ex.printStackTrace();
        }
        return IDitem;
    }

    public void addHold(int IDitem, int IDpatron) {
        try {
            insertHoldStmt.setInt(1, IDitem);
            insertHoldStmt.setInt(2, IDpatron);
            
            insertHoldStmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void terminatingHold(int iditem, int idpatron) {
        try {
            String whereClause = "where item=" + iditem;
            String sqlStr = "delete from hold " + whereClause
                    + " and patron=" + idpatron;
            stmt.execute(sqlStr);

        } catch (SQLException ex) {
            System.out.println("An SQL error took place." + ex.getMessage());
        }
    }

    private void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("Unable to load JDBC driver. Application will exit.");

        }

        try {
           
            String url = "jdbc:mysql://localhost:3306/library?user=root&password=cmsc250";
            
            connection = (Connection) DriverManager.getConnection(url);
            stmt = (Statement) connection.createStatement();

            insertHoldStmt = connection.prepareStatement(insertHoldSQL);

        } catch (SQLException ex) {
            System.out.println("Unable to connect to database. Application will exit.");
        }
    }

   
    public int getHoldCount(int patronid) {
        
        String counting = "SELECT COUNT(idhold) FROM hold where patron=" + patronid + " group by patron";
        int count = -1;
        try {
            ResultSet rset = stmt.executeQuery(counting);
            if (rset.next()) {
                count = rset.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error!");
            
            ex.printStackTrace();
        }
        return count;

    }

    public String checkinholdinfo(int iditeminput) {
       
        try {
            ResultSet rset;
            rset = insertHoldStmt.executeQuery();
            if (rset.next()) {
                int FinalIDitem = rset.getInt(1);
                int date = rset.getInt(3);
                if (iditeminput == FinalIDitem) {
                    String oldestHold = "SELECT idhold, MIN(entered) as entered FROM hold ";
                    String oldestHoldinfo = "";

                    try {
                        rset = insertHoldStmt.executeQuery(oldestHold);
                        if (rset.next()) {
                            oldestHoldinfo = rset.getString(1) + rset.getString(2) + rset.getString(3) + rset.getString(4) + rset.getString(5);
                            return oldestHoldinfo;
                        }
                    } catch (SQLException ex) {
                        System.out.println("Error!");
                        
                       ex.printStackTrace();
                    }
                    return oldestHoldinfo;
                } else {
                    return "No holds exist for the item";
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error!");
            
            ex.printStackTrace();
        }
        return "";
    }

    public void deleteOldestHold() {
        String oldestHold = "SELECT idhold, MIN(entered) as entered FROM hold ";
        try {
            ResultSet rset = insertHoldStmt.executeQuery(oldestHold);

        } catch (SQLException ex) {
            System.out.println("Error!");
            
            ex.printStackTrace();
        }
    }
}


