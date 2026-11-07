package action;

import ui.CartPageUI;
import ui.InventoryPageUI;
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class CartAction {
    private WebDriver driver;

    public CartAction(WebDriver driver) {
        this.driver = driver;
    }

    // Lấy số lượng sản phẩm trong giỏ hàng
    public int getCartItemCount() {

        return driver.findElements(CartPageUI.CART_ITEMS).size();
    }

    // Lấy danh sách tên sản phẩm trong giỏ
    public List<String> getCartItemNames() {
        return driver.findElements(CartPageUI.CART_ITEM_NAMES)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    // Lấy danh sách giá sản phẩm trong giỏ
    public List<String> getCartItemPrices() {
        return driver.findElements(CartPageUI.CART_ITEM_PRICES)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    // Lấy danh sách số lượng sản phẩm trong giỏ
    public List<Integer> getCartQuantities() {
        return driver.findElements(CartPageUI.CART_ITEM_QUANTITY)
                .stream().map(e -> Integer.parseInt(e.getText())).collect(Collectors.toList());
    }

    // Kiểm tra sản phẩm có trong giỏ không
    public boolean isProductInCart(String productName) {
        return getCartItemNames().contains(productName);
    }

    // Xóa sản phẩm khỏi giỏ theo tên
    public void removeProductFromCart(String productName) {
        String xpath = String.format(CartPageUI.REMOVE_BUTTON_CART, productName);
        WaitUtils.click(driver, By.xpath(xpath));
        WaitUtils.sleep(1);
    }

    // Click nút Checkout
    public void clickCheckout() {
        WaitUtils.click(driver, CartPageUI.CHECKOUT_BUTTON);
    }

    // Click nút Continue Shopping
    public void clickContinueShopping() {
        WaitUtils.click(driver, CartPageUI.CONTINUE_SHOPPING_BUTTON);
    }

    // Kiểm tra nút Checkout có hiển thị không
    public boolean isCheckoutButtonDisplayed() {
        return WaitUtils.isElementPresent(driver, CartPageUI.CHECKOUT_BUTTON);
    }

    // Kiểm tra nút Continue Shopping có hiển thị không
    public boolean isContinueShoppingButtonDisplayed() {
        return WaitUtils.isElementPresent(driver, CartPageUI.CONTINUE_SHOPPING_BUTTON);
    }

    // Tính tổng tiền trong giỏ
    public double calculateCartTotal() {
        return getCartItemPrices().stream()
                .mapToDouble(p -> Double.parseDouble(p.replace("$", "")))
                .sum();
    }

    // Xóa tất cả sản phẩm trong giỏ
    public void removeAllProducts() {
        List<String> productNames = getCartItemNames();
        for (String productName : productNames) {
            removeProductFromCart(productName);
        }
    }

    // Kiểm tra giỏ hàng có trống không
    public boolean isCartEmpty() {
        return driver.findElements(CartPageUI.CART_ITEMS).isEmpty();
    }

    // Lấy tên sản phẩm trong cart
    public String getProductNameInCart(String productName) {
        String xpath = String.format(CartPageUI.CART_ITEM_BY_NAME, productName);
        WebElement cartItem = driver.findElement(By.xpath(xpath));
        return cartItem.findElement(CartPageUI.CART_ITEM_NAMES).getText();
    }
    // Lấy số lượng sản phẩm
    public int getCartItemQuantityByName(String productName) {
        String xpath = String.format(CartPageUI.CART_ITEM_BY_NAME, productName);
        WebElement cartItem = driver.findElement(By.xpath(xpath));
        return Integer.parseInt(cartItem.findElement(CartPageUI.CART_ITEM_QUANTITY).getText());
    }

    // Lấy mô tả sản phẩm trong cart
    public String getProductDescriptionInCartByName(String productName) {
        String xpath = String.format(CartPageUI.CART_ITEM_BY_NAME, productName);
        WebElement cartItem = driver.findElement(By.xpath(xpath));
        return cartItem.findElement(CartPageUI.CART_ITEM_DESCRIPTION).getText();
    }

    // Lấy giá sản phẩm trong cart
    public String getProductPriceInCartByName(String productName) {
        String xpath = String.format(CartPageUI.CART_ITEM_BY_NAME, productName);
        WebElement cartItem = driver.findElement(By.xpath(xpath));
        return cartItem.findElement(CartPageUI.CART_ITEM_PRICES).getText();
    }


    // Kiểm tra button Remove có hiển thị cho sản phẩm cụ thể không
    public boolean isRemoveButtonDisplayedInCart(String productName) {
        String xpath = String.format(CartPageUI.REMOVE_BUTTON_CART, productName);  // Đã sửa
        return WaitUtils.isElementPresent(driver, By.xpath(xpath));
    }

    // Chuyển đến trang chi tiết sản phẩm theo tên
    public void goToProductDetailByNameFromCart(String productName) {
        String xpath = String.format(CartPageUI.PRODUCT_NAME_BY_PRODUCT, productName);
        WaitUtils.click(driver, By.xpath(xpath));
        WaitUtils.sleep(1);
    }

}