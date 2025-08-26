package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Task2Test {
    private WebDriver webDriver;

    @Test
    public void Task2Test() {
        // Initialize WebDriver
        DriverFactory driverFactory = new DriverFactory();
        webDriver = driverFactory.initDriver();

        // Step 1: Open Wikipedia page
        webDriver.get("https://pl.wikipedia.org/wiki/Wiki");

        // Step 2: Click the language selection button
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement languageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='p-lang-btn' and @class='vector-dropdown mw-portlet mw-portlet-lang']")));
        languageButton.click();

        // Step 3: Get all language link elements
        List<WebElement> languageLinks = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='row uls-language-list uls-lcd']//a[@class='autonym']")));

        // Step 4: Iterate through language links and print names; for "English", print URL
        for (WebElement link : languageLinks) {
            String languageName = link.getText().trim();
            System.out.println("Language: " + languageName);
            if (languageName.equals("English")) {
                String url = link.getAttribute("href");
                System.out.println("English URL: " + url);
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
            DriverFactory.tlDriver.remove();
        }
    }
}