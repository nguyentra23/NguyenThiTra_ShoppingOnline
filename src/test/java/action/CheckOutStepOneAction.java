package action;

import ui.CheckOutStepOnePageUI;
import ui.InventoryPageUI;
import utils.WaitUtils;
import org.openqa.selenium.WebDriver;

public class CheckOutStepOneAction {
    private WebDriver driver;

    public CheckOutStepOneAction(WebDriver driver) {
        this.driver = driver;
    }

    // Nhập thông tin checkout
    public void enterCheckoutInfo(String firstName, String lastName, String postalCode) {
        if (firstName != null) {
            WaitUtils.sendKeys(driver, CheckOutStepOnePageUI.FIRST_NAME, firstName);
        }
        if (lastName != null) {
            WaitUtils.sendKeys(driver, CheckOutStepOnePageUI.LAST_NAME, lastName);
        }
        if (postalCode != null) {
            WaitUtils.sendKeys(driver, CheckOutStepOnePageUI.POSTAL_CODE, postalCode);
        }
    }
    // Lấy số lượng sản phẩm trong giỏ (hiển thị trên badge)
    public String getCartItemCount() {
        if (WaitUtils.isElementPresent(driver, InventoryPageUI.SHOPPING_CART_BADGE)) {
            return WaitUtils.getText(driver, InventoryPageUI.SHOPPING_CART_BADGE);
        }
        return "0";
    }

    // Click nút Continue
    public void clickContinue() {
        WaitUtils.click(driver, CheckOutStepOnePageUI.CONTINUE_BUTTON);
    }

    // Click nút Cancel
    public void clickCancel() {

        WaitUtils.click(driver, CheckOutStepOnePageUI.CANCEL_BUTTON);
    }

    // Lấy thông báo lỗi
    public String getErrorMessage() {

        return WaitUtils.getText(driver, CheckOutStepOnePageUI.ERROR_MESSAGE);
    }

    // Kiểm tra có thông báo lỗi không
    public boolean isErrorMessageDisplayed() {
        return WaitUtils.isElementPresent(driver, CheckOutStepOnePageUI.ERROR_MESSAGE);
    }

    // Lấy title của trang
    public String getTitle() {

        return WaitUtils.getText(driver, CheckOutStepOnePageUI.TITLE);
    }
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // Hoàn thành nhập thông tin và continue
    public void completeCheckoutInfo(String firstName, String lastName, String postalCode) {
        enterCheckoutInfo(firstName, lastName, postalCode);
        clickContinue();
    }

    // Kiểm tra có đang ở trang Checkout Step One không
    public boolean isOnCheckoutStepOnePage() {

        return driver.getCurrentUrl().contains("checkout-step-one.html");
    }
}