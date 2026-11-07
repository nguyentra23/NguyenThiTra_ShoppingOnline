package feature;

import action.CheckOutStepOneAction;
import action.CheckOutStepTwoAction;
import action.CartAction;
import action.InventoryAction;
import action.LoginAction;
import utils.Hook;
import utils.ExtentReportManager;
import utils.WaitUtils;
import ui.CheckOutStepTwoPageUI;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CheckOutStepTwoTest extends Hook {
    CheckOutStepTwoAction checkOutStepTwoAction;
    InventoryAction inventoryAction;

    @BeforeMethod
    public void goToOverview() {
        driver.get("https://www.saucedemo.com");
        new LoginAction(driver).performLogin("standard_user", "secret_sauce");
        inventoryAction = new InventoryAction(driver);
        inventoryAction.resetAppState();
        inventoryAction.addToCartByProductName("Sauce Labs Backpack");
        inventoryAction.addToCartByProductName("Sauce Labs Bike Light");
        WaitUtils.sleep(1);
        inventoryAction.goToCart();
        new CartAction(driver).clickCheckout();
        new CheckOutStepOneAction(driver).completeCheckoutInfo("Nguyen", "Tra", "12345");
        checkOutStepTwoAction = new CheckOutStepTwoAction(driver);
    }

    @Test
    public void Overview01_VerifyOverviewPageDisplayed() {
        ExtentReportManager.createTest("Overview01_VerifyOverviewPageDisplayed");

        // Kiểm tra URL
        Assert.assertTrue(checkOutStepTwoAction.getCurrentUrl().contains("checkout-step-two.html"), "Should be on checkout step two page");

        // Kiểm tra tiêu đề trang
        String pageTitle = WaitUtils.getText(driver, CheckOutStepTwoPageUI.TITLE);
        Assert.assertEquals(pageTitle, "Checkout: Overview", "Page title should be 'Checkout: Overview'");

        // Kiểm tra badge giỏ hàng
        Assert.assertEquals(checkOutStepTwoAction.getCartItemCount(), "2", "Cart badge should still show 2");

        // Kiểm tra số lượng sản phẩm
        Assert.assertEquals(checkOutStepTwoAction.getItemCount(), 2, "Should have 2 items in overview");

        // Kiểm tra tên sản phẩm
        Assert.assertTrue(checkOutStepTwoAction.getItemNames().contains("Sauce Labs Backpack"), "Backpack should be in overview");
        Assert.assertTrue(checkOutStepTwoAction.getItemNames().contains("Sauce Labs Bike Light"), "Bike Light should be in overview");

        // Kiểm tra giá sản phẩm
        Assert.assertTrue(checkOutStepTwoAction.getItemPrices().contains("$29.99"), "Backpack price should be $29.99");
        Assert.assertTrue(checkOutStepTwoAction.getItemPrices().contains("$9.99"), "Bike Light price should be $9.99");

        // Kiểm tra số lượng từng sản phẩm
        for (Integer quantity : checkOutStepTwoAction.getItemQuantities()) {
            Assert.assertEquals(quantity, Integer.valueOf(1), "Each product should have quantity 1");
        }
    }

    @Test
    public void Overview02_VerifySpecificProductDetails() {
        ExtentReportManager.createTest("Overview02_VerifySpecificProductDetails");

        String productName = "Sauce Labs Backpack";

        // Kiểm tra tên sản phẩm
        String actualName = checkOutStepTwoAction.getProductNameByName(productName);
        Assert.assertEquals(actualName, productName, "Product name should be '" + productName + "' but found '" + actualName + "'");

        //  Kiểm tra giá sản phẩm
        String actualPrice = checkOutStepTwoAction.getItemPriceByName(productName);
        Assert.assertEquals(actualPrice, "$29.99", "Product price should be $29.99 but found '" + actualPrice + "'");

        // Kiểm tra mô tả sản phẩm
        String actualDescription = checkOutStepTwoAction.getProductDescriptionByName(productName);
        Assert.assertNotNull(actualDescription, "Product description should not be null");
        Assert.assertFalse(actualDescription.isEmpty(), "Product description should not be empty");
        Assert.assertTrue(actualDescription.contains("carry.allTheThings()"), "Description should contain expected text");

        //Kiểm tra số lượng sản phẩm
        int actualQuantity = checkOutStepTwoAction.getProductQuantityByName(productName);
        Assert.assertEquals(actualQuantity, 1, "Product quantity should be 1 but found " + actualQuantity);

    }

    @Test
    public void Overview03_VerifyPaymentAndShippingInfo() {
        ExtentReportManager.createTest("Overview03_VerifyPaymentAndShippingInfo");

        // 1. Kiểm tra thông tin thanh toán
        String paymentInfo = WaitUtils.getText(driver, CheckOutStepTwoPageUI.PAYMENT_INFO_VALUE);
        Assert.assertEquals(paymentInfo, "SauceCard #31337", "Payment info should be SauceCard #31337");

        // 2. Kiểm tra thông tin vận chuyển
        String shippingInfo = WaitUtils.getText(driver, CheckOutStepTwoPageUI.SHIPPING_INFO_VALUE);
        Assert.assertEquals(shippingInfo, "Free Pony Express Delivery!", "Shipping info should be Free Pony Express Delivery!");
    }

    @Test
    public void Overview04_VerifyTotalCalculation() {
        ExtentReportManager.createTest("Overview04_VerifyTotalCalculation");

        // Lấy subtotal, tax, total (dùng Action)
        double subtotal = checkOutStepTwoAction.getSubtotal();
        double tax = checkOutStepTwoAction.getTax();
        double total = checkOutStepTwoAction.getTotal();

        // Kiểm tra các giá trị lớn hơn 0
        Assert.assertTrue(subtotal > 0, "Subtotal should be greater than 0");
        Assert.assertTrue(tax > 0, "Tax should be greater than 0");
        Assert.assertTrue(total > 0, "Total should be greater than 0");

        // Kiểm tra subtotal đúng
        Assert.assertEquals(subtotal, 39.98, 0.01, "Subtotal should be 39.98");

        // Kiểm tra tổng tiền tính đúng (Subtotal + Tax = Total)
        Assert.assertTrue(checkOutStepTwoAction.verifyTotalCalculation(), "Total calculation should be correct: Subtotal + Tax = Total");
    }

    @Test
    public void Overview05_ClickFinishButton() {
        ExtentReportManager.createTest("Overview05_ClickFinishButton");

        // Click Finish button
        checkOutStepTwoAction.clickFinish();

        // Kiểm tra chuyển sang trang complete
        Assert.assertTrue(checkOutStepTwoAction.getCurrentUrl().contains("checkout-complete.html"), "Should navigate to checkout complete page");

        // Kiểm tra badge giỏ hàng vẫn hiển thị đúng số lượng
        Assert.assertEquals(checkOutStepTwoAction.getCartItemCount(), "0", "Cart badge should  disable");


    }

    @Test
    public void Overview06_ClickCancelButton() {
        ExtentReportManager.createTest("Overview06_ClickCancelButton");

        // Click Cancel button
        checkOutStepTwoAction.clickCancel();

        // Kiểm tra quay lại trang inventory
        Assert.assertTrue(checkOutStepTwoAction.getCurrentUrl().contains("inventory.html"), "Should navigate back to inventory page");
        // Kiểm tra badge giỏ hàng vẫn hiển thị đúng số lượng
        Assert.assertEquals(checkOutStepTwoAction.getCartItemCount(), "2", "Cart badge should still show 2");
    }
}


