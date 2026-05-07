package ui;

import org.openqa.selenium.By;

public class InventoryPageUI {
    //Logo-app
    public static final By APP_LOGO = By.xpath("//div[@class='app_logo']");
    // Sort dropdown
    public static final By PRODUCT_SORT_DROPDOWN = By.xpath("//select[@class='product_sort_container']");

    // Product elements
    public static final By PRODUCT_NAMES = By.xpath("//div[@class='inventory_item_name ']");
    public static final By PRODUCT_PRICES = By.xpath("//div[@class='inventory_item_price']");
    public static final By ALL_PRODUCT_ITEMS = By.xpath("//div[@class='inventory_item']");
    public static final By PRODUCT_DESCRIPTION = By.xpath("//div[@class='inventory_item_desc']");
    public static final By PRODUCT_IMAGE = By.xpath("//img[@class='inventory_item_img']");

    // Cart elements
    public static final By SHOPPING_CART_BADGE = By.xpath("//span[@class='shopping_cart_badge']");
    public static final By SHOPPING_CART_LINK = By.xpath("//a[@class='shopping_cart_link']");

    // Menu (burger menu)
    public static final By BURGER_MENU = By.xpath("//button[@id='react-burger-menu-btn']");
    public static final By LOGOUT_LINK = By.xpath("//a[@id='logout_sidebar_link']");
    public static final By RESET_APP_STATE = By.xpath("//a[@id='reset_sidebar_link']");
    public static final By ALL_ITEMS_LINK = By.xpath("//a[@id='inventory_sidebar_link']");
    public static final By ABOUT_LINK = By.xpath("//a[@id='about_sidebar_link']");

    // Dynamic XPath - Add to cart button theo tên sản phẩm
    public static final String ADD_TO_CART_SPECIFIC = "//div[@class='inventory_item' and contains(.,'%s')]//button[contains(@id,'add-to-cart')]";

    // Dynamic XPath - Remove button theo tên sản phẩm
    public static final String REMOVE_SPECIFIC = "//div[@class='inventory_item' and contains(.,'%s')]//button[contains(@id,'remove')]";

    // Dynamic XPath - Giá sản phẩm theo tên
    public static final String PRODUCT_PRICE_BY_NAME = "//div[@class='inventory_item' and contains(.,'%s')]//div[@class='inventory_item_price']";

    // Dynamic XPath - Sản phẩm theo tên
    public static final String PRODUCT_ITEM_BY_NAME = "//div[@class='inventory_item' and contains(.,'%s')]";
}
