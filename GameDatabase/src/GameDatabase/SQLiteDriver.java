/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameDatabase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jeff Paquette
 */
public class SQLiteDriver {

    /**
     * Connect to a database
     *
     * @return a connection to the database
     * @throws java.sql.SQLException
     */

    public Connection connect() throws SQLException {
        Connection conn = null;
        // db parameters

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String AbsolutePath = new File(".").getAbsolutePath();
        AbsolutePath = AbsolutePath.substring(0, AbsolutePath.length() - 1);
        String url = "jdbc:sqlite:" + AbsolutePath + "GameDatabase.db";

        // create a connection to the database
        System.out.println("Getting connection");
        conn = DriverManager.getConnection(url);
        System.out.println("Got connection");
        return conn;
    }

    public String insert(String sql) {
        try {
            execute(sql);
        } catch (SQLException e) {
            return e.getMessage();
        }

        return "Successful entry";
    }

    public void delete(String sql) {
        try {
            execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String removeRecord(String sql) {
        try {
            execute(sql);
        } catch (SQLException e) {
            return e.getMessage();
        }

        return "Record removed";
    }

    public DefaultTableModel query(String sql) throws SQLException {
        DefaultTableModel model;
        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(sql); 
                ResultSet rs = statement.executeQuery()) {
            model = buildTableModel(rs);
        }
        return model;
    }
    
    public Record getRecord(String sql){
        Record record;
        try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(sql); 
                ResultSet rs = statement.executeQuery()) {
            record = new Record(rs.getString("title"), rs.getString("platform"), rs.getString("developer"), 
                rs.getString("publisher"), Integer.toString(rs.getInt("yearofrelease")), rs.getString("genre"));
        } catch (SQLException e){
            return null;
        }
        
        return record;
    }
    
    public String update(String sql) {
        try {
            execute(sql);
        } catch (SQLException e) {
            return e.getMessage();
        }

        return "Successful update";
    }
    
    private void execute(String sql) throws SQLException {
        try (Connection conn = connect()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
            statement.close();
        }
    }
    /**
     * Create JTable from result set as found at 
     * http://stackoverflow.com/questions/10620448/most-simple-code-to-populate-jtable-from-resultset
     * by Paul Vargas 5-16-12
     * @param rs The result set to create the table from
     * @return DefaultTableModel
     * @throws SQLException 
     */
    private static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        return model;
    }
}
