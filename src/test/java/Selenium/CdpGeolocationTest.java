package Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;

public class CdpGeolocationTest {

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); //https://github.com/SeleniumHQ/selenium/issues/11750
        ChromeDriver driver = new ChromeDriver(options); //use ChromeDriver, not WebDriver to access Chrome DevTools Protocol
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        HashMap<String, Object> coordinates = new HashMap();
        coordinates.put("latitude", 40);
        coordinates.put("longitude", 3);
        coordinates.put("accuracy", 1);

        driver.executeCdpCommand("Emulation.setGeolocationOverride", coordinates);

        driver.get("https://www.google.com/");
        driver.findElement(By.xpath("//div[@class='QS5gu sy4vM']")).click();

        driver.findElement(By.name("q")).sendKeys("netflix", Keys.ENTER);
        driver.findElements(By.cssSelector(".LC20lb")).get(0).click();
        driver.findElement(By.id("cookie-disclosure-accept")).click();
        String title = driver.findElement(By.xpath("//h1[@data-uia='nmhp-card-hero-text-title']")).getText();
        System.out.println(title);
        driver.close();

    }
}
