package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import com.gitlab.rmarzec.model.YTTile;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Task4Test {
    private WebDriver webDriver;

    @Test
    public void Task4Test() {
        // Initialize WebDriver
        DriverFactory driverFactory = new DriverFactory();
        webDriver = driverFactory.initDriver();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        // Step 1: Open YouTube
        webDriver.get("https://www.youtube.com/");

        // Step 2: Accept cookies
        WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Zaakceptuj wykorzystywanie plików cookie i innych danych do opisanych celów']")));
        acceptCookiesButton.click();

        // Step 3: Navigate to Shorts
        WebElement shortsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='endpoint']//yt-formatted-string[normalize-space()='Shorts']")));
        shortsTab.click();

        // Step 4: Print channel name of the first Short
        WebElement shortChannel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, '/shorts') and starts-with(normalize-space(text()), '@')]")));
        System.out.println("Shorts Channel: " + shortChannel.getText());

        // Step 5: Return to homepage
        WebElement homeTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Główna']")));
        homeTab.click();

        // Step 6: Search for "Live"
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_query")));
        searchBox.sendKeys("Live");
        searchBox.sendKeys(Keys.ENTER);

        // Step 7: Collect first 12 non-Shorts videos
        List<YTTile> ytTileList = new ArrayList<>();
        List<WebElement> videoElements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//ytd-video-renderer")));
        int count = 0;

        for (WebElement video : videoElements) {
            if (count >= 12) break;

            // Skip Shorts
            if (video.findElements(By.xpath(".//span[contains(text(), 'SHORTS')]")).isEmpty()) {
                YTTile tile = new YTTile();

                // Get title
                WebElement titleElement = video.findElement(By.id("video-title"));
                tile.setTitle(titleElement.getText());

                // Get channel
                WebElement channelElement = video.findElement(By.xpath(".//ytd-channel-name//a"));
                tile.setChannel(channelElement.getText());

                // Get length or set to "live" if live stream
                WebElement liveBadge = video.findElement(By.xpath("//div[contains(@class, 'badge-style-type-live')]"));
                if (liveBadge.isDisplayed()) {
                    tile.setLength("live");
                } else {
                    WebElement lengthElement = video.findElement(By.xpath(".//span[@class='style-scope ytd-thumbnail-overlay-time-status-renderer']"));
                    tile.setLength(lengthElement.getText().trim());
                }

                ytTileList.add(tile);
                count++;
            }
        }

        // Step 8: Print title and duration for non-live videos
        for (YTTile tile : ytTileList) {
            if (!tile.getLength().equals("live")) {
                System.out.println("Title: " + tile.getTitle() + ", Duration: " + tile.getLength());
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