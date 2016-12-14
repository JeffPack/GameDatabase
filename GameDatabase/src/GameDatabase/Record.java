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
public class Record {
    public String title, platform, developer, publisher, yearOfRelease, genre;
    
    public Record(String title, String platform, String developer,
            String publisher, String yearOfRelease, String genre){
        this.title = title;
        this.platform = platform;
        this.developer = developer;
        this.publisher = publisher;
        this.yearOfRelease = yearOfRelease;
        this.genre = genre;
    }
}
