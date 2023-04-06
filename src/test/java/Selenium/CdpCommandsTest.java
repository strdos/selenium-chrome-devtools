package Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;

public class CdpCommandsTest {

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); //https://github.com/SeleniumHQ/selenium/issues/11750
        ChromeDriver driver = new ChromeDriver(options); //use ChromeDriver, not WebDriver to access Chrome DevTools Protocol
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        HashMap deviceParams = new HashMap();
        deviceParams.put("width", 600);
        deviceParams.put("height", 1000);
        deviceParams.put("deviceScaleFactor", 50);
        deviceParams.put("mobile", true);

        //calling CDP directly, without using Selenium wrapper for CDP
        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceParams);

        driver.get("https://rahulshettyacademy.com/");

        driver.findElement(By.cssSelector(".navbar-toggle")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='nav-outer clearfix']//a[normalize-space()='Courses']")));
        driver.findElement(By.xpath("//div[@class='nav-outer clearfix']//a[normalize-space()='Courses']")).click();

        driver.close();

    }
}
