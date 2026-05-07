package feature;

import action.CheckOutCompleteAction;
import action.CheckOutStepOneAction;
import action.CheckOutStepTwoAction;
import action.CartAction;
import action.InventoryAction;
import action.LoginAction;
import utils.Hook;
import utils.ExtentReportManager;
import utils.WaitUtils;
import ui.CheckOutCompletePageUI;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckOutCompleteTest extends Hook {
    CheckOutCompleteAction checkoutCompleteAction;
    InventoryAction inventoryAction;

    @BeforeMethod
    public void completeOrder() {
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
        new CheckOutStepTwoAction(driver).clickFinish();
        checkoutCompleteAction = new CheckOutCompleteAction(driver);
    }

    @Test
    public void Complete01_VerifyCompletePageDisplayed() {
        ExtentReportManager.createTest("Complete01_VerifyCompletePageDisplayed");

        //Kiểm tra URL
        Assert.assertTrue(checkoutCompleteAction.getCurrentUrl().contains("checkout-complete.html"),"Should be on checkout complete page");

        // Kiểm tra tiêu đề trang
        String pageTitle = WaitUtils.getText(driver, CheckOutCompletePageUI.TITLE);
        Assert.assertEquals(pageTitle, "Checkout: Complete!","Page title should be 'Checkout: Complete!'");

        // Kiểm tra header message
        Assert.assertEquals(checkoutCompleteAction.getCompleteHeader(), "Thank you for your order!","Order success message should be displayed");

        // 2. Kiểm tra text message
        String completeText = checkoutCompleteAction.getCompleteText();
        Assert.assertTrue(completeText.contains("Your order has been dispatched"),"Complete text should contain dispatch message");

        // Kiểm tra hình ảnh pony express hiển thị
        Assert.assertTrue(checkoutCompleteAction.isPonyExpressImageDisplayed(),"Pony Express image should be displayed");

        // Kiểm tra nút Back Home hiển thị
        Assert.assertTrue(WaitUtils.isElementPresent(driver, CheckOutCompletePageUI.BACK_HOME_BUTTON),"Back Home button should be displayed");
    }

    @Test
    public void Complete02_ClickBackHomeButton() {
        ExtentReportManager.createTest("Complete02_ClickBackHomeButton");

        // Click Back Home button
        checkoutCompleteAction.clickBackHome();

        // Kiểm tra chuyển về trang inventory
        Assert.assertTrue(checkoutCompleteAction.getCurrentUrl().contains("inventory.html"),"Should navigate back to inventory page");

        // Kiểm tra badge giỏ hàng đã được reset (không còn sản phẩm)
        Assert.assertEquals(inventoryAction.getCartItemCount(), "0","Cart should be empty after completing order");
    }

}