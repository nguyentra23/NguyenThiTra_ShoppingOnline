package action;

import ui.CartPageUI;
import ui.CheckOutStepTwoPageUI;
import ui.InventoryPageUI;
import utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class CheckOutStepTwoAction {
    private WebDriver driver;

    public CheckOutStepTwoAction(WebDriver driver) {
        this.driver = driver;
    }

    // Lấy danh sách tên sản phẩm
    public List<String> getItemNames() {
        return driver.findElements(CheckOutStepTwoPageUI.ITEM_NAMES)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    // Lấy danh sách giá sản phẩm
    public List<String> getItemPrices() {
        return driver.findElements(CheckOutStepTwoPageUI.ITEM_PRICES)
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }
    // Lấy số lượng sản phẩm trong giỏ (hiển thị trên badge)
    public String getCartItemCount() {
        if (WaitUtils.isElementPresent(driver, InventoryPageUI.SHOPPING_CART_BADGE)) {
            return WaitUtils.getText(driver, InventoryPageUI.SHOPPING_CART_BADGE);
        }
        return "0";
    }

    // Lấy danh sách số lượng sản phẩm
    public List<Integer> getItemQuantities() {
        return driver.findElements(CheckOutStepTwoPageUI.ITEM_QUANTITIES)
                .stream().map(e -> Integer.parseInt(e.getText())).collect(Collectors.toList());
    }
    // Lấy tên sản phẩm theo tên
    public String getProductNameByName(String productName) {
        String xpath = String.format(CheckOutStepTwoPageUI.CART_ITEM_BY_NAME, productName);
        WebElement cartItem = driver.findElement(By.xpath(xpath));
        return cartItem.findElement(CheckOutStepTwoPageUI.ITEM_NAMES).getText();
    }

    // Lấy giá sản phẩm theo tên
    public String getProductPriceByName(String productName) {
        String xpath = String.format(CheckOutStepTwoPageUI.CART_ITEM_BY_NAME, productName);
        WebElement cartItem = driver.findElement(By.xpath(xpath));
        return cartItem.findElement(CheckOutStepTwoPageUI.ITEM_PRICES).getText();
    }

    // Lấy số lượng sản phẩm theo tên
    public int getProductQuantityByName(String productName) {
        String xpath = String.format(CheckOutStepTwoPageUI.CART_ITEM_BY_NAME, productName);
        WebElement cartItem = driver.findElement(By.xpath(xpath));
        return Integer.parseInt(cartItem.findElement(CheckOutStepTwoPageUI.ITEM_QUANTITIES).getText());
    }

    // Lấy mô tả sản phẩm theo tên
    public String getProductDescriptionByName(String productName) {
        String xpath = String.format(CheckOutStepTwoPageUI.CART_ITEM_BY_NAME, productName);
        WebElement cartItem = driver.findElement(By.xpath(xpath));
        return cartItem.findElement(CheckOutStepTwoPageUI.ITEM_DESCRIPTIONS).getText();
    }

    // Lấy subtotal
    public double getSubtotal() {
        String subtotalText = WaitUtils.getText(driver, CheckOutStepTwoPageUI.SUBTOTAL);
        return Double.parseDouble(subtotalText.replace("Item total: $", ""));
    }

    // Lấy thuế
    public double getTax() {
        String taxText = WaitUtils.getText(driver, CheckOutStepTwoPageUI.TAX);
        return Double.parseDouble(taxText.replace("Tax: $", ""));
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Lấy tổng tiền
    public double getTotal() {
        String totalText = WaitUtils.getText(driver, CheckOutStepTwoPageUI.TOTAL);
        return Double.parseDouble(totalText.replace("Total: $", ""));
    }

    // Click nút Finish
    public void clickFinish() {
        WaitUtils.click(driver, CheckOutStepTwoPageUI.FINISH_BUTTON);
    }

    // Click nút Cancel
    public void clickCancel() {
        WaitUtils.click(driver, CheckOutStepTwoPageUI.CANCEL_BUTTON);
    }

    // Lấy thông tin thanh toán
    public String getPaymentInfo() {
        return WaitUtils.getText(driver, CheckOutStepTwoPageUI.PAYMENT_INFO_VALUE);
    }

    // Lấy thông tin vận chuyển
    public String getShippingInfo() {
        return WaitUtils.getText(driver, CheckOutStepTwoPageUI.SHIPPING_INFO_VALUE);
    }

    // Lấy số lượng sản phẩm
    public int getItemCount() {
        return driver.findElements(CheckOutStepTwoPageUI.CART_ITEMS).size();
    }

    // Kiểm tra tổng tiền có đúng không
    public boolean verifyTotalCalculation() {
        double calculatedTotal = getSubtotal() + getTax();
        double actualTotal = getTotal();
        return Math.abs(calculatedTotal - actualTotal) < 0.01;
    }

    // Kiểm tra sản phẩm có hiển thị không
    public boolean isItemDisplayed(String productName) {
        String xpath = String.format(CheckOutStepTwoPageUI.CART_ITEM_BY_NAME, productName);
        return WaitUtils.isElementPresent(driver, By.xpath(xpath));
    }

    // Lấy giá sản phẩm theo tên
    public String getItemPriceByName(String productName) {
        String xpath = String.format(CheckOutStepTwoPageUI.CART_ITEM_BY_NAME, productName);
        WebElement item = driver.findElement(By.xpath(xpath));
        return item.findElement(CheckOutStepTwoPageUI.ITEM_PRICES).getText();
    }

    // Kiểm tra có đang ở trang Checkout Step Two không
    public boolean isOnCheckoutStepTwoPage() {
        return driver.getCurrentUrl().contains("checkout-step-two.html");
    }
}