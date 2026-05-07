package ui;

import org.openqa.selenium.By;

public class CheckOutStepTwoPageUI {
    public static final By SHOPPING_CART_BADGE = By.xpath("//span[@class='shopping_cart_badge']");
    public static final By SHOPPING_CART_LINK = By.xpath("//a[@class='shopping_cart_link']");

    // Cart items in overview
    public static final By CART_ITEMS = By.xpath("//div[@class='cart_item']");
    public static final By ITEM_NAMES = By.xpath("//div[@class='inventory_item_name']");
    public static final By ITEM_PRICES = By.xpath("//div[@class='inventory_item_price']");
    public static final By ITEM_QUANTITIES = By.xpath("//div[@class='cart_quantity']");
    public static final By ITEM_DESCRIPTIONS = By.xpath("//div[@class='inventory_item_desc']");

    // Summary information
    public static final By SUBTOTAL = By.xpath("//div[@class='summary_subtotal_label']");
    public static final By TAX = By.xpath("//div[@class='summary_tax_label']");
    public static final By TOTAL = By.xpath("//div[@class='summary_total_label']");

    // Payment & Shipping info
    public static final By PAYMENT_INFO_LABEL = By.xpath("//div[@data-test='shipping-info-label']");
    public static final By PAYMENT_INFO_VALUE = By.xpath("//div[@data-test='payment-info-value']");
    public static final By SHIPPING_INFO_LABEL = By.xpath("//div[@data-test='shipping-info-label']");
    public static final By SHIPPING_INFO_VALUE = By.xpath("//div[@data-test='shipping-info-value']");
    public static final By TOTAL_LABEL = By.xpath("//div[@data-test='total-info-label']");

    // Buttons
    public static final By FINISH_BUTTON = By.id("finish");
    public static final By CANCEL_BUTTON = By.id("cancel");

    // Title
    public static final By TITLE = By.xpath("//span[@class='title']");

    // Dynamic XPath - Cart item by name
    public static final String CART_ITEM_BY_NAME = "//div[@class='cart_item' and contains(.,'%s')]";
}