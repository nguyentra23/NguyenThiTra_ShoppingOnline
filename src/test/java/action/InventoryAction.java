package action;

import ui.InventoryPageUI;
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryAction {
    private WebDriver driver;

    public InventoryAction(WebDriver driver) {
        this.driver = driver;
    }

    // Thêm sản phẩm vào giỏ theo tên
    public void addToCartByProductName(String productName) {
        String xpath = String.format(InventoryPageUI.ADD_TO_CART_SPECIFIC, productName);
        WaitUtils.click(driver, By.xpath(xpath));
        WaitUtils.sleep(1);
    }

    // Xóa sản phẩm khỏi giỏ theo tên
    public void removeFromCartByProductName(String productName) {
        String xpath = String.format(InventoryPageUI.REMOVE_SPECIFIC, productName);
        WaitUtils.click(driver, By.xpath(xpath));
        WaitUtils.sleep(1);
    }

    // Sắp xếp sản phẩm
    public void sortProductsBy(String value) {
        WaitUtils.selectByValue(driver, InventoryPageUI.PRODUCT_SORT_DROPDOWN, value);
        WaitUtils.sleep(1);
    }

    // Lấy số lượng sản phẩm trong giỏ (hiển thị trên badge)
    public String getCartItemCount() {
        if (WaitUtils.isElementPresent(driver, InventoryPageUI.SHOPPING_CART_BADGE)) {
            return WaitUtils.getText(driver, InventoryPageUI.SHOPPING_CART_BADGE);
        }
        return "0";
    }


    // Chuyển đến trang giỏ hàng
    public void goToCart() {
        WaitUtils.click(driver, InventoryPageUI.SHOPPING_CART_LINK);
    }

    // Chuyển đến trang chi tiết sản phẩm theo tên
    public void goToProductDetailByName(String productName) {
        String xpath = String.format(InventoryPageUI.PRODUCT_NAME_BY_PRODUCT, productName);
        WaitUtils.click(driver, By.xpath(xpath));
        WaitUtils.sleep(1);
    }
//    public void addToCartByProductName(String productName) {
//        String xpath = String.format(InventoryPageUI.ADD_TO_CART_SPECIFIC, productName);
//        WaitUtils.click(driver, By.xpath(xpath));
//        WaitUtils.sleep(1);
//    }

    // Lấy danh sách tất cả tên sản phẩm
    public List<String> getAllProductNames() {
        return driver.findElements(InventoryPageUI.PRODUCT_NAMES).stream().map(WebElement::getText).collect(Collectors.toList());
    }

    // Lấy danh sách tất cả giá sản phẩm
    public List<Double> getAllProductPrices() {
        return driver.findElements(InventoryPageUI.PRODUCT_PRICES)
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }

    // Kiểm tra sản phẩm có hiển thị không
    public boolean isProductDisplayed(String productName) {
        String xpath = String.format(InventoryPageUI.PRODUCT_ITEM_BY_NAME, productName);
        return WaitUtils.isElementPresent(driver, By.xpath(xpath));
    }

    // Lấy số lượng sản phẩm trên trang
    public int getProductCount() {
        return driver.findElements(InventoryPageUI.ALL_PRODUCT_ITEMS).size();
    }

    // Mở menu burger
    public void openMenu() {
        WaitUtils.click(driver, InventoryPageUI.BURGER_MENU);
        WaitUtils.sleep(1);
    }

    // Đăng xuất
    public void logout() {
        openMenu();
        WaitUtils.click(driver, InventoryPageUI.LOGOUT_LINK);
        WaitUtils.sleep(1);
    }

    // Reset app state (xóa tất cả sản phẩm trong giỏ)
    public void resetAppState() {
        openMenu();
        WaitUtils.sleep(1);
        WaitUtils.click(driver, InventoryPageUI.RESET_APP_STATE);
        WaitUtils.sleep(1);
        driver.navigate().refresh();
        WaitUtils.sleep(1);
    }

    // Lấy giá sản phẩm theo tên
    public String getProductPrice(String productName) {
        String xpath = String.format(InventoryPageUI.PRODUCT_PRICE_BY_NAME, productName);
        return WaitUtils.getText(driver, By.xpath(xpath));
    }

    // Thêm nhiều sản phẩm cùng lúc
    public void addMultipleProductsToCart(List<String> productNames) {
        for (String productName : productNames) {
            addToCartByProductName(productName);
            WaitUtils.sleep(1);
        }
    }

    // Kiểm tra nút Add to cart có hiển thị không
    public boolean isAddToCartButtonDisplayed(String productName) {
        String xpath = String.format(InventoryPageUI.ADD_TO_CART_SPECIFIC, productName);
        return WaitUtils.isElementPresent(driver, By.xpath(xpath));
    }

    // Kiểm tra nút Remove có hiển thị không
    public boolean isRemoveButtonDisplayed(String productName) {
        String xpath = String.format(InventoryPageUI.REMOVE_SPECIFIC, productName);
        return WaitUtils.isElementPresent(driver, By.xpath(xpath));
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}