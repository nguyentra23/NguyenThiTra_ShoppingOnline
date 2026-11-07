package feature;

import action.CartAction;
import action.CheckOutStepOneAction;
import action.InventoryAction;
import action.LoginAction;
import ui.CheckOutStepOnePageUI;
import utils.Hook;
import utils.ExtentReportManager;
import utils.WaitUtils;
import ui.CartPageUI;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.List;

public class CartTest extends Hook {
    CartAction cartAction;
    InventoryAction inventoryAction;

    @BeforeMethod
    public void setupCart() {
        driver.get("https://www.saucedemo.com");
        new LoginAction(driver).performLogin("standard_user", "secret_sauce");
        inventoryAction = new InventoryAction(driver);
        inventoryAction.resetAppState();
        WaitUtils.sleep(1);
        inventoryAction.addToCartByProductName("Sauce Labs Backpack");
        inventoryAction.addToCartByProductName("Sauce Labs Bike Light");
        WaitUtils.sleep(1);
        inventoryAction.goToCart();
        cartAction = new CartAction(driver);

    }

    @Test
    public void Cart01_VerifyBasicCartInformation() {
        ExtentReportManager.createTest("Cart01_VerifyBasicCartInformation");

        // Kiểm tra đúng số lượng sản phẩm
        int itemCount = cartAction.getCartItemCount();
        Assert.assertEquals(itemCount, 2, "Cart should have 2 items");
        Assert.assertTrue(itemCount > 0, "Cart should not be empty");

        // Kiểm tra tên sản phẩm không rỗng
        List<String> productNames = cartAction.getCartItemNames();
        Assert.assertNotNull(productNames, "Product names list should not be null");
        Assert.assertFalse(productNames.isEmpty(), "Product names list should not be empty");
        for (String name : productNames) {
            Assert.assertNotNull(name, "Product name should not be null");
            Assert.assertFalse(name.isEmpty(), "Product name should not be empty");
        }

        // Kiểm tra giá
        List<String> productPrices = cartAction.getCartItemPrices();
        Assert.assertNotNull(productPrices, "Product prices list should not be null");
        Assert.assertFalse(productPrices.isEmpty(), "Product prices list should not be empty");
        for (String price : productPrices) {
            Assert.assertNotNull(price, "Product price should not be null");
            Assert.assertFalse(price.isEmpty(), "Product price should not be empty");
            Assert.assertTrue(price.startsWith("$"), "Price should start with $");
        }

        // Kiểm tra số lượng
        List<Integer> quantities = cartAction.getCartQuantities();
        Assert.assertNotNull(quantities, "Quantities list should not be null");
        Assert.assertFalse(quantities.isEmpty(), "Quantities list should not be empty");
        for (Integer quantity : quantities) {
            Assert.assertNotNull(quantity, "Quantity should not be null");
            Assert.assertEquals(quantity, Integer.valueOf(1), "Each product should have quantity = 1");
        }

        // Kiểm tra hiển thị button remove cho từng sản phẩm
        for (String productName : productNames) {
            Assert.assertTrue(cartAction.isRemoveButtonDisplayedInCart(productName),
                    "Remove button should be displayed for product: " + productName);
        }

        // Kiểm tra hiển thị các button

        Assert.assertTrue(cartAction.isCheckoutButtonDisplayed(), "Checkout button should be displayed");
        Assert.assertTrue(cartAction.isContinueShoppingButtonDisplayed(), "Continue Shopping button should be displayed");


    }

    @Test
    public void Cart02_VerifySpecificProductDetails() {
        ExtentReportManager.createTest("Cart02_VerifySpecificProductDetails");

        String productName = "Sauce Labs Backpack";

        // Kiểm tra tên sản phẩm
        String actualName = cartAction.getProductNameInCart(productName);
        Assert.assertEquals(actualName, productName,"Product name should be '" + productName + "' but found '" + actualName + "'");

        //  Kiểm tra giá sản phẩm
        String actualPrice = cartAction.getProductPriceInCartByName(productName);
        Assert.assertEquals(actualPrice, "$29.99","Product price should be $29.99 but found '" + actualPrice + "'");

        // Kiểm tra mô tả sản phẩm
        String actualDescription = cartAction.getProductDescriptionInCartByName(productName);
        Assert.assertNotNull(actualDescription, "Product description should not be null");
        Assert.assertFalse(actualDescription.isEmpty(), "Product description should not be empty");
        Assert.assertTrue(actualDescription.contains("carry.allTheThings()"),"Description should contain expected text");

        //Kiểm tra số lượng sản phẩm
        int actualQuantity = cartAction.getCartItemQuantityByName(productName);
        Assert.assertEquals(actualQuantity, 1, "Product quantity should be 1 but found " + actualQuantity);

        //Kiểm tra hiển thị button remove
        Assert.assertTrue(cartAction.isRemoveButtonDisplayedInCart(productName),"Remove button should be displayed for product: " + productName);

    }

    @Test
    public void Cart03_RemoveProductFromCart() {
        ExtentReportManager.createTest("Cart03_RemoveProductFromCart");

        // Xóa 1 sản phẩm
        cartAction.removeProductFromCart("Sauce Labs Backpack");

        // Kiểm tra số lượng còn lại
        Assert.assertEquals(cartAction.getCartItemCount(), 1, "Cart should have 1 item after removal");
        // Kiểm tra sản phẩm còn lại
        Assert.assertTrue(cartAction.isProductInCart("Sauce Labs Bike Light"), "Bike Light should remain in cart");
        Assert.assertFalse(cartAction.isProductInCart("Sauce Labs Backpack"), "Backpack should be removed from cart");
    }

    @Test
    public void Cart04_RemoveAllProductsFromCart() {
        ExtentReportManager.createTest("Cart04_RemoveAllProductsFromCart");

        // Xóa tất cả sản phẩm
        cartAction.removeAllProducts();

        // Kiểm tra giỏ hàng trống
        Assert.assertTrue(cartAction.isCartEmpty(), "Cart should be empty after removing all products");
        Assert.assertEquals(cartAction.getCartItemCount(), 0, "Cart count should be 0");
        // Kiểm tra sản phẩm đã được xóa
        Assert.assertFalse(cartAction.isProductInCart("Sauce Labs Bike Light"), "Bike Light should be removed from cart");
        Assert.assertFalse(cartAction.isProductInCart("Sauce Labs Backpack"), "Backpack should be removed from cart");

    }

    @Test
    public void Cart05_ClickContinueShopping() {
        ExtentReportManager.createTest("Cart05_ClickContinueShopping");
        List<String> productNames = cartAction.getCartItemNames();

        cartAction.clickContinueShopping();

        // Kiểm tra quay lại trang inventory
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),"Should return to inventory page");

        // Kiểm tra badge vẫn hiển thị đúng số lượng
        Assert.assertEquals(inventoryAction.getCartItemCount(), "2","Cart badge should still show 2");

        // Kiểm tra button Remove ở trang Inventory vẫn hiển thị
        for (String productName : productNames) {
            Assert.assertTrue(inventoryAction.isRemoveButtonDisplayed(productName),"Remove button should be displayed for product: " + productName);
        }

    }

    @Test
    public void Cart06_ClickCheckout() {
        ExtentReportManager.createTest("Cart06_ClickCheckout");
        CheckOutStepOneAction checkOutStepOneAction = new CheckOutStepOneAction(driver);
        cartAction.clickCheckout();

        // Kiểm tra chuyển đến trang checkout step one
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"),"Should navigate to checkout step one page");
        // Kiểm tra badge vẫn hiển thị đúng số lượng sau checkout
        Assert.assertEquals(checkOutStepOneAction.getCartItemCount(), "2","Cart badge should still show 2");
        // Kiểm tra tiêu đề trang Checkout
        String pageTitle = checkOutStepOneAction.getTitle();
        Assert.assertEquals(pageTitle, "Checkout: Your Information","Page title should be 'Checkout: Your Information'");
    }

    @Test
    public void Cart07_AddMoreProductFromCart() {
        ExtentReportManager.createTest("Cart07_AddMoreProductFromCart");

        // Click Continue Shopping để quay lại inventory
        cartAction.clickContinueShopping();

        // Thêm sản phẩm mới
        inventoryAction.addToCartByProductName("Sauce Labs Bolt T-Shirt");

        // Kiểm tra badge tăng lên
        Assert.assertEquals(inventoryAction.getCartItemCount(), "3", "Cart badge should show 3");

        // Quay lại giỏ hàng
        inventoryAction.goToCart();

        // Kiểm tra sản phẩm mới có trong giỏ
        Assert.assertTrue(cartAction.isProductInCart("Sauce Labs Bolt T-Shirt"),"New product should appear in cart");
        Assert.assertEquals(cartAction.getCartItemCount(), 3, "Cart should have 3 items");
    }

    @Test
    public void Cart08_VerifyCartAfterLogout() {
        ExtentReportManager.createTest("Cart08_VerifyCartAfterLogout");

        // Đăng xuất
        inventoryAction.logout();

        // Đăng nhập lại
        new LoginAction(driver).performLogin("standard_user", "secret_sauce");

        // Kiểm tra giỏ hàng vẫn còn sản phẩm (sau khi đăng nhập lại)
        Assert.assertEquals(inventoryAction.getCartItemCount(), "2","Cart should still have items after re-login");

        // Vào giỏ hàng kiểm tra
        inventoryAction.goToCart();
        Assert.assertEquals(cartAction.getCartItemCount(), 2, "Cart should still have 2 items");
        Assert.assertTrue(cartAction.isProductInCart("Sauce Labs Backpack"), "Backpack should still be in cart");
        Assert.assertTrue(cartAction.isProductInCart("Sauce Labs Bike Light"), "Bike Light should still be in cart");
    }

    @Test
    public void Cart09_ClickProductNameInCartOpensDetail() {
        ExtentReportManager.createTest("Cart09_ClickProductNameInCartOpensDetail");

        String product = "Sauce Labs Backpack";
        cartAction.goToProductDetailByNameFromCart(product);
        WaitUtils.sleep(1);

        // Kiểm tra chuyển sang trang chi tiết
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item"),
                "Should navigate to product detail page");
    }


}
