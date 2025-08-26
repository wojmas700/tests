package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Task3Test {
    private WebDriver webDriver;

    @Test
    public void Task3Test() {
        // Initialize WebDriver
        DriverFactory driverFactory = new DriverFactory();
        webDriver = driverFactory.initDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        // Step 1: Open Google
        webDriver.get("https://www.google.com/");

        // Step 2: Accept cookies
        WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("L2AGLb")));
        acceptCookiesButton.click();

        // Step 3: Enter search query
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        searchBox.sendKeys("HTML select tag - W3Schools");

        // Step 4: Click "I'm Feeling Lucky"
        WebElement luckyButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("btnI")));
        luckyButton.click();

        // Step 5: Verify URL and navigate if incorrect
        String expectedUrl = "https://www.w3schools.com/tags/tag_select.asp";
        String currentUrl = webDriver.getCurrentUrl();
        if (!currentUrl.equals(expectedUrl)) {
            System.out.println("Current URL: " + currentUrl);
            webDriver.get(expectedUrl);
        }

        // Step 6: Accept cookies on W3Schools
        WebElement w3CookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("accept-choices")));
        w3CookiesButton.click();

        // Step 7: Click "Try it Yourself"
        WebElement tryItButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@target='_blank' and @href='tryit.asp?filename=tryhtml_select' and @class='w3-btn w3-margin-bottom']")));
        tryItButton.click();

        // Switch to new window
        String originalWindow = webDriver.getWindowHandle();
        for (String windowHandle : webDriver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                webDriver.switchTo().window(windowHandle);
                break;
            }
        }

        // Step 8: Switch to iframe
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("iframeResult")));
        webDriver.switchTo().frame(iframe);

        // Step 9: Get and print header text
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='The select element']")));
        System.out.println("Header: " + header.getText());

        // Step 10: Select "Opel" from dropdown
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cars")));
        Select select = new Select(dropdown);
        select.selectByVisibleText("Opel");

        // Step 11: Get selected element and print text and value
        WebElement selectedOption = select.getFirstSelectedOption();
        System.out.println("Selected Text: " + selectedOption.getText());
        System.out.println("Selected Value: " + selectedOption.getAttribute("value"));

        // Switch back to default content
        webDriver.switchTo().defaultContent();

        // Switch back to original window
        webDriver.switchTo().window(originalWindow);
    }

    @AfterMethod
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
            DriverFactory.tlDriver.remove();
        }
    }
}