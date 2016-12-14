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
import java.sql.SQLException;
import org.sqlite.SQLiteJDBCLoader;
 
/**
 *
 * @author Jeff Paquette
 */
public class SQLiteDriver {
     /**
     * Connect to a database
     * @return a connection to the database
     * @throws java.sql.SQLException
     */
    
    public Connection connect() throws SQLException{
        Connection conn = null;
        // db parameters
        
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        String AbsolutePath = new File(".").getAbsolutePath();
        String url = "jdbc:sqlite:" + AbsolutePath.substring(0, AbsolutePath.length() - 1) + "GameDatabase.db";
            
        // create a connection to the database
        System.out.println("Getting connection");
        conn = DriverManager.getConnection(url);            
        System.out.println("Got connection");
        return conn;
    }
    
    public String insert(String sql){   
        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e){
            return e.getMessage();
        }
        
        return "Successful entry";
    }
    
    public void delete(String sql){
        try {
            Connection conn = connect();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
