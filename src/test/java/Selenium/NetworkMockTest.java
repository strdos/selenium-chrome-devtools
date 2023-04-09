package Selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.fetch.Fetch;
import org.openqa.selenium.devtools.v85.network.model.Request;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

public class NetworkMockTest {
    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); //https://github.com/SeleniumHQ/selenium/issues/11750
        ChromeDriver driver = new ChromeDriver(options); //use ChromeDriver, not WebDriver to access Chrome DevTools Protocol
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));

        devTools.addListener(Fetch.requestPaused(), request -> {
            Request req = request.getRequest();
            //modify request url
            if (req.getUrl().contains("shetty")) {
                String mockUrl = req.getUrl().replace("=shetty", "=BadGuy");
                System.out.println(mockUrl);
                //resume the request with the mock url
                devTools.send(Fetch.continueRequest(request.getRequestId(),
                        Optional.of(mockUrl),
                        Optional.of(req.getMethod()),
                        Optional.empty(),
                        Optional.empty()));
            }
            else {
                //resume the request with the original url
                devTools.send(Fetch.continueRequest(request.getRequestId(),
                        Optional.of(request.getRequest().getUrl()),
                        Optional.of(req.getMethod()),
                        Optional.empty(),
                        Optional.empty()));
            }
        });
        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.xpath("//button[@routerlink='/library']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("p")));
        System.out.println(driver.findElement(By.tagName("p")).getText());
        //driver.close();
    }
}
