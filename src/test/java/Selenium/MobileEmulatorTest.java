package Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v110.emulation.Emulation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class MobileEmulatorTest {

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); //https://github.com/SeleniumHQ/selenium/issues/11750
        ChromeDriver driver = new ChromeDriver(options); //use ChromeDriver, not WebDriver to access Chrome DevTools Protocol
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        //using Selenium wrapper for CDP
        devTools.send(Emulation.setDeviceMetricsOverride(600,
                1000,
                50,
                true,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()));

        driver.get("https://rahulshettyacademy.com/");

        driver.findElement(By.cssSelector(".navbar-toggle")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='nav-outer clearfix']//a[normalize-space()='Courses']")));
        driver.findElement(By.xpath("//div[@class='nav-outer clearfix']//a[normalize-space()='Courses']")).click();

        driver.close();

    }
}
