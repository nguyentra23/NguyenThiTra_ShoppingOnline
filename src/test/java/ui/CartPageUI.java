package ui;

import org.openqa.selenium.By;

public class CartPageUI {
    // Cart title
    public static final By CART_TITLE = By.xpath("//span[@class='title']");

    // Cart container
    public static final By CART_LIST = By.xpath("//div[@class='cart_list']");
    public static final By CART_ITEMS = By.xpath("//div[@class='cart_item']");

    // Cart item details
    public static final By CART_ITEM_NAMES = By.xpath("//div[@class='inventory_item_name']");
    public static final By CART_ITEM_PRICES = By.xpath("//div[@class='inventory_item_price']");
    public static final By CART_ITEM_QUANTITY = By.xpath("//div[@class='cart_quantity']");
    public static final By CART_ITEM_DESCRIPTION = By.xpath("//div[@class='inventory_item_desc']");

    // Buttons
    public static final By CHECKOUT_BUTTON = By.xpath("//button[@id='checkout']");
    public static final By CONTINUE_SHOPPING_BUTTON = By.xpath("//button[@id='continue-shopping']");

    // Dynamic XPath - Cart item theo tên sản phẩm
    public static final String CART_ITEM_BY_NAME = "//div[@class='cart_item' and contains(.,'%s')]";
    // Dynamic XPath - Remove button trong cart (đúng)
    public static final String REMOVE_BUTTON_CART = "//div[@class='cart_item' and contains(.,'%s')]//button[contains(@id,'remove')]";


}