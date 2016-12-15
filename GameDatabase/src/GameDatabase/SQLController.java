/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameDatabase;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jeffp_000
 */
public class SQLController {

    SQLiteDriver driver;

    public SQLController() {
        driver = new SQLiteDriver();
    }

    public String addCompany(String companyName, String companyLocation) {
        String sql = "insert into company values('"
                + companyName + "', '" + companyLocation + "')";

        return driver.insert(sql);
    }

    public String addPlatform(String platformName, String yearOfRelease) {
        String sql = "insert into platform values('"
                + platformName + "', '" + yearOfRelease + "')";

        return driver.insert(sql);
    }

    public String addGame(String title, String platform, String developer,
            String publisher, String yearOfRelease, String genre) {

        String sqlGame = "insert into game values ('"
                + title + "', '" + platform + "', " + yearOfRelease + ", '" + genre + "')";
        String sqlDeveloper = "insert into developedby values ('"
                + title + "', '" + platform + "', '" + developer + "')";
        String sqlPublisher = "insert into publishedby values ('"
                + title + "', '" + platform + "', '" + publisher + "')";

        String message = driver.insert(sqlGame);
        if (!message.equals("Successful entry")) {
            return message;
        }

        message = driver.insert(sqlDeveloper);
        if (!message.equals("Successful entry")) {
            // delete game entry
            String del = "delete from game where title = '" + title + "' and platform = '" + platform + "'";
            driver.delete(del);
            return message;
        }

        message = driver.insert(sqlPublisher);
        if (!message.equals("Successful entry")) {
            // delete game entry
            String del = "delete from game where title = '" + title + "' and platform = '" + platform + "'";
            driver.delete(del);
            // delete developer entry
            del = "delete from developedby where title = '" + title + "' and platform = '" + platform + "'";
            driver.delete(del);
            return message;
        }

        return message;
    }

    public DefaultTableModel searchForGame(String title, String platform, String developer,
            String publisher, String yearOfRelease, String genre) {
        boolean hasTitle, hasPlatform, hasDeveloper, hasPublisher, hasYOR, hasGenre;
        boolean gameTable;
        hasTitle = hasPlatform = hasDeveloper = hasPublisher = hasYOR = hasGenre = false;
        gameTable = false;

        if (!title.equals("")) {
            hasTitle = true;
        }
        if (!platform.equals("")) {
            hasPlatform = true;
        }
        if (!developer.equals("")) {
            hasDeveloper = true;
        }
        if (!publisher.equals("")) {
            hasPublisher = true;
        }
        if (!yearOfRelease.equals("")) {
            hasYOR = true;
        }
        if (!genre.equals("")) {
            hasGenre = true;
        }

        if (hasYOR || hasGenre) {
            gameTable = true;
        }

        if (!hasDeveloper && !hasPublisher) {
            gameTable = true;
        }

        if (!hasTitle && !hasPlatform && !hasDeveloper && !hasPublisher && !hasYOR && !hasGenre)
        {
            try {
                return getAll();
            } catch (SQLException e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        
        String sql = "select title, platform ";
        
        if (!gameTable && !hasPublisher && hasDeveloper){
            sql += "from developedby where company like '%" + developer + "%'";
        } else if (!gameTable && !hasDeveloper && hasPublisher){
            sql += "from publishedby where company like '%" + publisher + "%'";
        } else if (!gameTable && hasDeveloper && hasPublisher) {
            sql += "from developedby d join publishedby p where d.company like '%" +
                    developer + "%' and p.company like '%" + publisher + "%'";
        } else if (gameTable) {
            boolean addAnd = false;
            sql += "from game where ";
            if (hasTitle){
                sql += "title like '%" + title + "%' ";
                addAnd = true;
            }
            if (hasPlatform){
                if (addAnd)
                    sql += "and ";
                sql += "platform like '%" + platform + "%' ";
                addAnd = true;
            }
            if (hasYOR) {
                if (addAnd)
                    sql += "and ";
                sql += "yearofrelease = " + yearOfRelease + " ";
                addAnd = true;
            }
            if (hasGenre){
                if (addAnd)
                    sql += "and ";
                sql += "genre like '%" + genre + "%' ";
            }
            if (hasDeveloper)
                sql += "intersect select title, platform from developer where company like '%" + developer + "%' ";
            if (hasPublisher)
                sql += "intersect select title, platform from publisher where company like '%" + publisher + "%' ";
        }
        
        try {
            ResultSet rs = driver.query(sql);
            return buildTableModel(rs);
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    public String UpdateGame(String title, String platform, String developer,
            String publisher, String yearOfRelease, String genre) {
        
        String sqlGame = "update game set yearofrelease = '" + yearOfRelease + 
                "', genre = '" + genre + "' where title = '" + title + "' and platform = '" + 
                platform + "'";
        String sqlDeveloper = "update developedby set company = '" + developer + "' where title = '" + title + "' and platform = '" + 
                platform + "'";
        String sqlPublisher = "update publishedby set company = '" + publisher + "' where title = '" + title + "' and platform = '" + 
                platform + "'";
        
        String message = driver.update(sqlGame);
        if (!message.equals("Successful update")) {
            return message;
        }

        message = driver.update(sqlDeveloper);
        if (!message.equals("Successful update")) {
            // delete game entry
            String del = "delete from game where title = '" + title + "' and platform = '" + platform + "'";
            driver.delete(del);
            return message;
        }

        message = driver.update(sqlPublisher);
        if (!message.equals("Successful update")) {
            // delete game entry
            String del = "delete from game where title = '" + title + "' and platform = '" + platform + "'";
            driver.delete(del);
            // delete developer entry
            del = "delete from developedby where title = '" + title + "' and platform = '" + platform + "'";
            driver.delete(del);
            return message;
        }

        return message;
    }
    
    public Record getRecord(String title, String platform)
    {
        ResultSet rs;
        String sql = "select game.title as title, game.platform as platform, d.company as developer, p.company as publisher, yearofrelease, genre " + 
                "from game join developedby d on game.title = d.title and game.platform = d.platform " +
                "join publishedby p on game.title = p.title and game.platform = p.platform " +
                "where game.title = '" + title + "' and game.platform = '" + platform + "'";
        try {
            rs = driver.query(sql);
            rs.next();
            return new Record(rs.getString("title"), rs.getString("platform"), rs.getString("developer"), 
                rs.getString("publisher"), Integer.toString(rs.getInt("yearofrelease")), rs.getString("genre"));
        } catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public DefaultTableModel getAll() throws SQLException{
        ResultSet rs = driver.query("select title, platform from game");
        
        return buildTableModel(rs);
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
