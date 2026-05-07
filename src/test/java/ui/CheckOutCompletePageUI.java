package ui;

import org.openqa.selenium.By;

public class CheckOutCompletePageUI {
    // Success messages
    public static final By COMPLETE_HEADER = By.xpath("//h2[@class='complete-header']");
    public static final By COMPLETE_TEXT = By.xpath("//div[@class='complete-text']");

    // Image
    public static final By PONY_EXPRESS_IMAGE = By.xpath("//img[@class='pony_express']");

    // Button
    public static final By BACK_HOME_BUTTON = By.xpath("//button[@id='back-to-products']");

    // Title
    public static final By TITLE = By.xpath("//span[@class='title']");

    // Dynamic XPath - Check message by text
    public static final String COMPLETE_MESSAGE_BY_TEXT = "//h2[contains(text(),'%s')]";
}