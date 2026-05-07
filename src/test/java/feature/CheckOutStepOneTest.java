package feature;

import action.*;
import org.testng.annotations.BeforeClass;
import ui.CheckOutStepTwoPageUI;
import utils.Hook;
import utils.ExtentReportManager;
import utils.WaitUtils;
import ui.CheckOutStepOnePageUI;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckOutStepOneTest extends Hook {
    CheckOutStepOneAction checkOutStepOneAction;
    InventoryAction inventoryAction;

    @BeforeMethod
    public void goToCheckout() {
        driver.get("https://www.saucedemo.com");
        new LoginAction(driver).performLogin("standard_user", "secret_sauce");
        inventoryAction = new InventoryAction(driver);
        inventoryAction.resetAppState();
        inventoryAction.addToCartByProductName("Sauce Labs Backpack");
        inventoryAction.addToCartByProductName("Sauce Labs Bike Light");
        WaitUtils.sleep(1);
        inventoryAction.goToCart();
        new CartAction(driver).clickCheckout();
        checkOutStepOneAction = new CheckOutStepOneAction(driver);
    }

    @Test
    public void Checkout01_VerifyCheckoutPageDisplayed() {
        ExtentReportManager.createTest("Checkout01_VerifyCheckoutPageDisplayed");

        // Kiểm tra URL
        Assert.assertTrue(checkOutStepOneAction.getCurrentUrl().contains("checkout-step-one.html"),"Should navigate to checkout step one page");

        // Kiểm tra tiêu đề trang
        Assert.assertEquals(checkOutStepOneAction.getTitle(), "Checkout: Your Information","Page title should be 'Checkout: Your Information'");

        // Kiểm tra badge giỏ hàng vẫn hiển thị đúng số lượng
        Assert.assertEquals(checkOutStepOneAction.getCartItemCount(), "2","Cart badge should still show 2");

        // Kiểm tra các field nhập liệu hiển thị
        Assert.assertTrue(WaitUtils.isElementPresent(driver, CheckOutStepOnePageUI.FIRST_NAME),"First Name field should be displayed");
        Assert.assertTrue(WaitUtils.isElementPresent(driver, CheckOutStepOnePageUI.LAST_NAME),"Last Name field should be displayed");
        Assert.assertTrue(WaitUtils.isElementPresent(driver, CheckOutStepOnePageUI.POSTAL_CODE),"Postal Code field should be displayed");

        // Kiểm tra các button hiển thị
        Assert.assertTrue(WaitUtils.isElementPresent(driver, CheckOutStepOnePageUI.CONTINUE_BUTTON),"Continue button should be displayed");
        Assert.assertTrue(WaitUtils.isElementPresent(driver, CheckOutStepOnePageUI.CANCEL_BUTTON),"Cancel button should be displayed");
    }

    @Test
    public void Checkout02_CompleteCheckoutWithValidInfo() {
        ExtentReportManager.createTest("Checkout02_CompleteCheckoutWithValidInfo");
        CheckOutStepTwoAction checkOutStepTwoAction = new CheckOutStepTwoAction(driver);
        // Nhập thông tin hợp lệ
        checkOutStepOneAction.completeCheckoutInfo("Nguyen", "Tra", "12345");

        // Kiểm tra chuyển sang trang checkout step two
        Assert.assertTrue(checkOutStepOneAction.getCurrentUrl().contains("checkout-step-two.html"),"Should navigate to checkout step two page");
        // Kiểm tra title
        String pageTitle = WaitUtils.getText(driver, CheckOutStepTwoPageUI.TITLE);
        Assert.assertEquals(pageTitle, "Checkout: Overview","Page title should be 'Checkout: Overview'");
        // Kiểm tra badge vẫn hiển thị đúng số lượng
        Assert.assertEquals(checkOutStepTwoAction.getCartItemCount(), "2","Cart badge should still show 2");

    }

    @Test
    public void Checkout03_CheckoutWithEmptyFirstName() {
        ExtentReportManager.createTest("Checkout03_CheckoutWithEmptyFirstName");

        // Nhập thiếu First Name
        checkOutStepOneAction.completeCheckoutInfo("", "Tra", "12345");
        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(checkOutStepOneAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        // Kiểm tra thông báo lỗi
        Assert.assertTrue(checkOutStepOneAction.getErrorMessage().contains("First Name is required"),"Error message should indicate First Name is required");

        // Kiểm tra vẫn ở trang checkout step one
        Assert.assertTrue(checkOutStepOneAction.getCurrentUrl().contains("checkout-step-one.html"),"Should stay on checkout step one page");

    }

    @Test
    public void Checkout04_CheckoutWithEmptyLastName() {
        ExtentReportManager.createTest("Checkout04_CheckoutWithEmptyLastName");

        // Nhập thiếu Last Name
        checkOutStepOneAction.completeCheckoutInfo("Nguyen", "", "12345");

        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(checkOutStepOneAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        // Kiểm tra thông báo lỗi
        Assert.assertTrue(checkOutStepOneAction.getErrorMessage().contains("Last Name is required"),"Error message should indicate Last Name is required");

        // Kiểm tra vẫn ở trang checkout step one
        Assert.assertTrue(checkOutStepOneAction.getCurrentUrl().contains("checkout-step-one.html"),"Should stay on checkout step one page");
    }

    @Test
    public void Checkout05_CheckoutWithEmptyPostalCode() {
        ExtentReportManager.createTest("Checkout05_CheckoutWithEmptyPostalCode");

        // Nhập thiếu Postal Code
        checkOutStepOneAction.completeCheckoutInfo("Nguyen", "Tra", "");

        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(checkOutStepOneAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        // Kiểm tra thông báo lỗi
        Assert.assertTrue(checkOutStepOneAction.getErrorMessage().contains("Postal Code is required"),"Error message should indicate Postal Code is required");

        // Kiểm tra vẫn ở trang checkout step one
        Assert.assertTrue(checkOutStepOneAction.getCurrentUrl().contains("checkout-step-one.html"),"Should stay on checkout step one page");
    }

    @Test
    public void Checkout06_CheckoutWithAllEmptyFields() {
        ExtentReportManager.createTest("Checkout06_CheckoutWithAllEmptyFields");

        // Nhập thiếu tất cả
        checkOutStepOneAction.completeCheckoutInfo("", "", "");

        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(checkOutStepOneAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        // Kiểm tra thông báo lỗi
        Assert.assertTrue(checkOutStepOneAction.getErrorMessage().contains("First Name is required"),"Error message should indicate First Name is required");

        // Kiểm tra vẫn ở trang checkout step one
        Assert.assertTrue(checkOutStepOneAction.getCurrentUrl().contains("checkout-step-one.html"),"Should stay on checkout step one page");

    }

    @Test
    public void Checkout07_ClickCancelButton() {
        ExtentReportManager.createTest("Checkout07_ClickCancelButton");

        // Click Cancel button
        checkOutStepOneAction.clickCancel();

        // Kiểm tra quay lại trang giỏ hàng
        Assert.assertTrue(checkOutStepOneAction.getCurrentUrl().contains("cart.html"),"Should navigate back to cart page");

        // Kiểm tra giỏ hàng vẫn còn sản phẩm
        Assert.assertEquals(inventoryAction.getCartItemCount(), "2","Cart should still have 2 items");
    }


}