package Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v109.network.Network;
import org.openqa.selenium.devtools.v109.network.model.ConnectionType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class NetworkSpeedTest {
    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); //https://github.com/SeleniumHQ/selenium/issues/11750
        ChromeDriver driver = new ChromeDriver(options); //use ChromeDriver, not WebDriver to access Chrome DevTools Protocol
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.send(Network.emulateNetworkConditions(false, 3000, 20000, 10000, Optional.of(ConnectionType.CELLULAR4G)));

        devTools.addListener(Network.loadingFailed(), loadingFailed -> {
            System.out.println(loadingFailed.getErrorText());
            System.out.println(loadingFailed.getTimestamp());
        });

        long startTime = System.currentTimeMillis();
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.xpath("//button[@routerlink='/library']")).click();
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        driver.close();
    }
}
