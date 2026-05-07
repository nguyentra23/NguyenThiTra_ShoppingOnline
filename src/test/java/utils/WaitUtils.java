package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtils {
    private static final int TIMEOUT = 10;

    //tạo WebDriverWait
    private static WebDriverWait getWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    // chờ element hiển thị lên màn hình
    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    //chờ element click được
    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Click
    public static void click(WebDriver driver, By locator) {
        waitForClickable(driver, locator).click();
    }

    //sendKeys
    public static void sendKeys(WebDriver driver, By locator, String text) {
        WebElement element = waitForVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    //Select by visible text
    public static void selectByText(WebDriver driver, By locator, String text) {
        WebElement element = waitForVisible(driver, locator);
        new Select(element).selectByVisibleText(text);
    }

    //Select by value
    public static void selectByValue(WebDriver driver, By locator, String value) {
        WebElement element = waitForVisible(driver, locator);
        new Select(element).selectByValue(value);
    }

    //Get text
    public static String getText(WebDriver driver, By locator) {
        return waitForVisible(driver, locator).getText();
    }

    //Check element displayed
    public static boolean isElementDisplayed(WebDriver driver, By locator) {
        try {
            return waitForVisible(driver, locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    //Check element present
    public static boolean isElementPresent(WebDriver driver, By locator) {
        try {
            getWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    //Wait for element invisible
    public static boolean waitForInvisible(WebDriver driver, By locator) {
        return getWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    //Sleep
    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}