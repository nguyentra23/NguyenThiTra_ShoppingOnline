package ui;

import org.openqa.selenium.By;

public class CheckOutStepOnePageUI {

    public static final By SHOPPING_CART_BADGE = By.xpath("//span[@class='shopping_cart_badge']");
    public static final By SHOPPING_CART_LINK = By.xpath("//a[@class='shopping_cart_link']");

    // Input fields
    public static final By FIRST_NAME = By.id("first-name");
    public static final By LAST_NAME = By.id("last-name");
    public static final By POSTAL_CODE = By.id("postal-code");

    // Buttons
    public static final By CONTINUE_BUTTON = By.id("continue");
    public static final By CANCEL_BUTTON = By.id("cancel");

    // Error message
    public static final By ERROR_MESSAGE = By.xpath("//h3[@data-test='error']");

    // Title
    public static final By TITLE = By.xpath("//span[@class='title']");

    // Cart link
    public static final By CART_LINK = By.xpath("//a[@class='shopping_cart_link']");
}