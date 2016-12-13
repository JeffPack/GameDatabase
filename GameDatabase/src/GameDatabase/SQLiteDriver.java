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
 
/**
 *
 * @author Jeff Paquette
 */
public class SQLiteDriver {
     /**
     * Connect to a database
     * @return a connection to the database
     */
    public Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String AbsolutePath = new File(".").getAbsolutePath();
            String url = "jdbc:sqlite:" + AbsolutePath + "GameData.db";
            
            // create a connection to the database
            conn = DriverManager.getConnection(url);            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
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
}
