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
    
    public void addGame(String title, String platform, String developer, 
            String publisher, int yearOfRelease, String genre){
        
        String sqlGame = "insert into game(title, platform, yearofrelease, genre) values (" +
                title + ", " + platform + ", " + yearOfRelease + ", " + genre + ")";
        String sqlDeveloper = "insert into developedby(title, platform, company) values (" +
                title + ", " + platform + ", " + developer + ")";
        String sqlPublisher = "insert into publishedby(title, platform, company) values (" +
                title + ", " + platform + ", " + publisher + ")";
        
        driver.insert(sqlGame);
        driver.insert(sqlDeveloper);
        driver.insert(sqlPublisher);
    }
}
