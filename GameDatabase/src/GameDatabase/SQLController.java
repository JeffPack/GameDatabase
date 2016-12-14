/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameDatabase;

/**
 *
 * @author jeffp_000
 */
public class SQLController {
    
    SQLiteDriver driver;
    
    public SQLController(){
        driver = new SQLiteDriver();
    }
    
    public String addCompany(String companyName, String companyLocation){
        String sql = "insert into company(name, location) values(" + 
                companyName + ", " + companyLocation + ")";
        
        return driver.insert(sql);
    }
    
    public String addPlatform(String platformName, int yearOfRelease) {
        String sql = "insert into platform(name, yearofrelease) values(" +
                platformName + ", " + yearOfRelease + ")";
        
        return driver.insert(sql);
    }
    
    public String addGame(String title, String platform, String developer, 
            String publisher, int yearOfRelease, String genre){
        
        String sqlGame = "insert into game(title, platform, yearofrelease, genre) values (" +
                title + ", " + platform + ", " + yearOfRelease + ", " + genre + ")";
        String sqlDeveloper = "insert into developedby(title, platform, company) values (" +
                title + ", " + platform + ", " + developer + ")";
        String sqlPublisher = "insert into publishedby(title, platform, company) values (" +
                title + ", " + platform + ", " + publisher + ")";
        
        String message = driver.insert(sqlGame);
        if (!message.equals("Successful entry"))
            return message;
        
        message = driver.insert(sqlDeveloper);
        if (!message.equals("Successful entry"))
        {
            // delete game entry
            String del = "delete from game where title = " + title + " and platform = " + platform + "";
            driver.delete(del);
            return message;
        }
        
        message = driver.insert(sqlPublisher);
        if (!message.equals("Successful entry"))
        {
            // delete game entry
            String del = "delete from game where title = " + title + " and platform = " + platform + "";
            driver.delete(del);
            // delete developer entry
            del = "delete from developedby where title = " + title + " and platform = " + platform + "";
            driver.delete(del);
            return message;
        }
        
        return message;
    }
}
