package Selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Request;
import org.openqa.selenium.devtools.v85.network.model.Response;

import java.time.Duration;
import java.util.Optional;

public class NetworkLogActivityTest {

    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); //https://github.com/SeleniumHQ/selenium/issues/11750
        ChromeDriver driver = new ChromeDriver(options); //use ChromeDriver, not WebDriver to access Chrome DevTools Protocol

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        //add listeners to listen when events are fired

        devTools.addListener(Network.requestWillBeSent(), request -> {
            Request req = request.getRequest();
            //System.out.println(req.getUrl());
            //System.out.println(req.getHeaders());
        });

        //add listener to listen when event is fired and handle it
        devTools.addListener(Network.responseReceived(), response -> {
            Response res = response.getResponse();
            if (res.getStatus().toString().startsWith("4")) {
                System.out.println(res.getUrl() + "is failing with status code" + res.getStatus());
            }
        });



        driver.get("https://rahulshettyacademy.com/angularAppdemo/");
        driver.findElement(By.xpath("//button[@routerlink='/library']")).click();

        driver.close();

    }
}
