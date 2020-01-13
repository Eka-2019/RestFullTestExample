import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class APODTodayData {
    private WebDriver driver;

    public APODTodayData(WebDriver driver) {
        this.driver = driver;
    }

    private By copyright = By.xpath(".//body/center[2]/a");
    private By title = By.xpath(".//body/center[2]/b[1]");
    private By date = By.xpath(".//body/center[1]/p[2]");
    private By explanation = By.xpath(".//body/p[1]");
    private By mediaType = By.xpath(".//body/center[2]/b[2]");
    private By url = By.xpath(".//body/center[1]/p[2]/a/img");


    public String getCopyright() {
        return driver.findElement(copyright).getText();
    }

    public String getTitle() {
        return driver.findElement(title).getText();
    }

    public String getExplanation1() {
        String[] str =  driver.findElement(explanation).getText().split(" ");
        String word1 = str[10].toLowerCase();
        return word1;
    }

    public String getExplanation2() {
        String str =  driver.findElement(explanation).getText().substring(13);
       // String str1 = str.substring(13);
        return str;
    }

    public String getUrl(){
        return driver.findElement(url).getAttribute("src").toString();
    }


    public String getDate() throws ParseException {
        String date1 = driver.findElement(date).getText();
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy MMMM dd");
        SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = originalFormat.parse(date1);
        return targetFormat.format(date);
    }

    public String getMediaType() {
        String[] str =  driver.findElement(mediaType).getText().split(" ");
        String word1 = str[0].toLowerCase();
        return word1;
    }

}
