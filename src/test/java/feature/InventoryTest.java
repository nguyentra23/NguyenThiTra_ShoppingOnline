package feature;

import action.CartAction;
import action.InventoryAction;
import action.LoginAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ui.CartPageUI;
import ui.LoginPageUI;
import utils.Hook;
import utils.ExtentReportManager;
import utils.WaitUtils;
import ui.InventoryPageUI;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryTest extends Hook {
    InventoryAction inventoryAction;

    @BeforeMethod
    public void login() {
        driver.get("https://www.saucedemo.com");
        new LoginAction(driver).performLogin("standard_user", "secret_sauce");
        inventoryAction = new InventoryAction(driver);
        // Reset giỏ hàng trước mỗi test
        inventoryAction.resetAppState();
        WaitUtils.sleep(1);
    }

    @Test
    public void Inventory01_AddOneProductToCart() {
        ExtentReportManager.createTest("Inventory01_AddOneleProductToCart");

        String productName = "Sauce Labs Backpack";
        inventoryAction.addToCartByProductName(productName);

        WaitUtils.sleep(1);
        // Kiểm tra badge giỏ hàng hiển thị đúng số lượng
        Assert.assertEquals(inventoryAction.getCartItemCount(), "1", "Cart badge should show 1");

        // Kiểm tra nút đã chuyển thành "Remove"
        Assert.assertTrue(inventoryAction.isRemoveButtonDisplayed(productName), "Remove button should be displayed");

        // Kiểm tra nút add to cart không còn hiển thị
        Assert.assertFalse(inventoryAction.isAddToCartButtonDisplayed(productName), productName + " - Add to cart button should NOT be displayed");
    }

    @Test
    public void Inventory02_AddMultipleProductsToCart() {
        ExtentReportManager.createTest("Inventory02_AddMultipleProductsToCart");

        // Danh sách sản phẩm cần thêm
        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";
        String product3 = "Sauce Labs Bolt T-Shirt";

        // Thêm sản phẩm vào giỏ
        inventoryAction.addToCartByProductName(product1);
        inventoryAction.addToCartByProductName(product2);
        inventoryAction.addToCartByProductName(product3);

        // Kiểm tra giỏ hàng hiển thị đúng số lượng
        Assert.assertEquals(inventoryAction.getCartItemCount(), "3", "Cart badge should show 3");

        // Kiểm tra tất cả các nút đã chuyển thành "Remove"
        Assert.assertTrue(inventoryAction.isRemoveButtonDisplayed(product1), product1 + " - Remove button should be displayed");
        Assert.assertTrue(inventoryAction.isRemoveButtonDisplayed(product2), product2 + " - Remove button should be displayed");
        Assert.assertTrue(inventoryAction.isRemoveButtonDisplayed(product3), product3 + " - Remove button should be displayed");

        // Kiểm tra các nút "Add to cart" không còn hiển thị
        Assert.assertFalse(inventoryAction.isAddToCartButtonDisplayed(product1), product1 + " - Add to cart button should NOT be displayed");
        Assert.assertFalse(inventoryAction.isAddToCartButtonDisplayed(product2), product2 + " - Add to cart button should NOT be displayed");
        Assert.assertFalse(inventoryAction.isAddToCartButtonDisplayed(product3), product3 + " - Add to cart button should NOT be displayed");
    }

    @Test
    public void Inventory03_RemoveProductFromCart() {
        ExtentReportManager.createTest("Inventory03_RemoveProductFromCart");

        String productName = "Sauce Labs Backpack";

        // Thêm sản phẩm vào giỏ
        inventoryAction.addToCartByProductName(productName);
        WaitUtils.sleep(1);
        // Kiểm tra thêm thành công
        Assert.assertEquals(inventoryAction.getCartItemCount(), "1", "Cart badge should show 1");

        // Xóa sản phẩm khỏi giỏ
        inventoryAction.removeFromCartByProductName(productName);

        WaitUtils.sleep(1);
        // Kiểm tra giỏ hàng hiển thị đúng số lượng
        Assert.assertEquals(inventoryAction.getCartItemCount(), "0", "Cart badge should show 0");

        // Kiểm tra nút Remove không còn hiển thị
        Assert.assertFalse(inventoryAction.isRemoveButtonDisplayed(productName), productName + " - Remove button should NOT be displayed");

        // Kiểm tra nút đã chuyển lại thành "Add to cart"
        Assert.assertTrue(inventoryAction.isAddToCartButtonDisplayed(productName), "Add to cart button should be displayed");
    }

    @Test
    public void Inventory04_SortProductsByNameAToZ() {
        ExtentReportManager.createTest("Inventory04_SortProductsByNameAToZ");

        // Chọn sort A to Z
        inventoryAction.sortProductsBy("az");

        // Lấy danh sách tên sản phẩm thực tế
        List<String> actualList = inventoryAction.getAllProductNames();

        // Tạo list expected bằng cách sort lại
        List<String> expectedList = new ArrayList<>(actualList);
        Collections.sort(expectedList);

        // Kiểm tra thay đổi đã được sort chưa
        Assert.assertEquals(actualList, expectedList, "Products are not sorted A to Z");

       // Kiểm tra kích thước list không thay đổi sau sort
       Assert.assertEquals(actualList.size(), 6, "Product count should remain 6 after sorting");

       // Kiểm tra giá trị đầu của danh sách
       String firstProduct = actualList.get(0);
       Assert.assertEquals(firstProduct, "Sauce Labs Backpack","First product should be Sauce Labs Backpack but found: " + firstProduct);

    }

    @Test
    public void Inventory05_SortProductsByNameZToA() {
        ExtentReportManager.createTest("Inventory05_SortProductsByNameZToA");

        // Chọn sort A to Z
        inventoryAction.sortProductsBy("za");

        // Lấy danh sách tên sản phẩm thực tế
        List<String> actualList = inventoryAction.getAllProductNames();

        // Tạo list expected bằng cách sort lại
        List<String> expectedList = new ArrayList<>(actualList);
        Collections.sort(expectedList, Collections.reverseOrder());

        // Kiểm tra thay đổi đã được sort chưa
        Assert.assertEquals(actualList, expectedList, "Products are not sorted Z to A");

        // Kiểm tra kích thước list không thay đổi sau sort
        Assert.assertEquals(actualList.size(), 6, "Product count should remain 6 after sorting");

        // Kiểm tra giá trị đầu của danh sách
        String firstProduct = actualList.get(0);
        Assert.assertEquals(firstProduct, "Test.allTheThings() T-Shirt (Red)","First product should be Test.allTheThings() T-Shirt (Red) but found: " + firstProduct);

    }

    @Test
    public void Inventory06_SortProductsByPriceLowToHigh() {
        ExtentReportManager.createTest("Inventory06_SortProductsByPriceLowToHigh");

        inventoryAction.sortProductsBy("lohi");
        List<Double> actualPrices = inventoryAction.getAllProductPrices();

        // Tạo list expected
        List<Double> expectedPrices = new ArrayList<>(actualPrices);

        // Sort tăng dần
        Collections.sort(expectedPrices);

        // Kiểm tra thay đổi đã được sort chưa
        Assert.assertEquals(actualPrices, expectedPrices, "Products are not sorted from low to high");

        // Kiểm tra kích thước list không thay đổi sau sort
        Assert.assertEquals(actualPrices.size(), 6, "Product count should remain 6 after sorting");

        // Kiểm tra giá thấp nhất ở đầu
        Assert.assertTrue(actualPrices.get(0) < actualPrices.get(actualPrices.size() - 1),"First product should have lowest price");

        // Kiểm tra giá cao nhất ở cuối
        Assert.assertTrue(actualPrices.get(actualPrices.size() - 1) > actualPrices.get(0),"Last product should have highest price");
    }

    @Test
    public void Inventory07_SortProductsByPriceHighToLow() {
        ExtentReportManager.createTest("Inventory07_SortProductsByPriceHighToLow");

        // Chọn sort high to low
        inventoryAction.sortProductsBy("hilo");
        List<Double> actualPrices = inventoryAction.getAllProductPrices();

        // Tạo list expected
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices, Collections.reverseOrder());

        // Kiểm tra đã được sort đúng chưa
        Assert.assertEquals(actualPrices, expectedPrices, "Products are not sorted from high to low");

        // Kiểm tra kích thước list không thay đổi sau sort
        Assert.assertEquals(actualPrices.size(), 6, "Product count should remain 6 after sorting");

        // Kiểm tra giá cao nhất ở đầu
        Assert.assertTrue(actualPrices.get(0) > actualPrices.get(actualPrices.size() - 1),"First product should have highest price");

        // Kiểm tra giá thấp nhất ở cuối
        Assert.assertTrue(actualPrices.get(actualPrices.size() - 1) < actualPrices.get(0),"Last product should have lowest price");

    }

    @Test
    public void Inventory08_GoToCartFromInventory() {
        ExtentReportManager.createTest("Inventory08_GoToCartFromInventory");
        // Thêm sản phẩm vào giỏ
        inventoryAction.addToCartByProductName("Sauce Labs Backpack");

        // Kiểm tra badge hiển thị đúng số lượng trước khi vào cart
        Assert.assertEquals(inventoryAction.getCartItemCount(), "1","Cart badge should show 1 before going to cart");

        // Chuyển đến trang giỏ hàng
        inventoryAction.goToCart();

        // Kiểm tra URL của trang giỏ hàng
        Assert.assertTrue(inventoryAction.getCurrentUrl().contains("cart.html"), "Should navigate to cart page");

        // Kiểm tra page title giỏ hàng
        String pageTitle = WaitUtils.getText(driver, CartPageUI.CART_TITLE);
        Assert.assertEquals(pageTitle, "Your Cart", "Page title should be 'Your Cart'");

    }

    @Test
    public void Inventory09_ResetAppState() {
        ExtentReportManager.createTest("Inventory09_ResetAppState");

        // Thêm sản phẩm vào giỏ
        inventoryAction.addToCartByProductName("Sauce Labs Backpack");
        inventoryAction.addToCartByProductName("Sauce Labs Bike Light");
        WaitUtils.sleep(1);
        // Kiểm tra số lượng sản phẩm trên giỏ hàng
        Assert.assertEquals(inventoryAction.getCartItemCount(), "2", "Cart badge should show 2");

        // Reset app state
        inventoryAction.resetAppState();
        WaitUtils.sleep(2);
        // Kiểm tra giỏ hàng đã được xóa
        Assert.assertEquals(inventoryAction.getCartItemCount(), "0", "Cart should be empty after reset");

        // Kiểm tra nút "Add to cart" hiển thị lại
        Assert.assertTrue(inventoryAction.isAddToCartButtonDisplayed("Sauce Labs Backpack"),"Add to cart button should be displayed after reset");

        // Kiểm tra nút Remove không còn hiển thị
        Assert.assertFalse(inventoryAction.isRemoveButtonDisplayed("Sauce Labs Backpack"), "Sauce Labs Backpack - Remove button should NOT be displayed");

    }

    @Test
    public void Inventory10_VerifyProductDetails() {
        ExtentReportManager.createTest("Inventory10_VerifyProductDetails");

        String productName = "Sauce Labs Backpack";

        // Kiểm tra sản phẩm hiển thị
        Assert.assertTrue(inventoryAction.isProductDisplayed(productName),"Product should be displayed on inventory page");

        // Kiểm tra giá sản phẩm
        String price = inventoryAction.getProductPrice(productName);
        Assert.assertEquals(price, "$29.99", "Product price should be $29.99");

        // 3. Kiểm tra hiển thị mô tả sản phẩm
        String productXpath = String.format(InventoryPageUI.PRODUCT_ITEM_BY_NAME, productName);
        By productDesc = By.xpath(productXpath + "//div[@class='inventory_item_desc']");
        String description = WaitUtils.getText(driver, productDesc);
        Assert.assertNotNull(description, "Product description should not be null");
        Assert.assertTrue(description.length() > 0, "Product description should not be empty");


        // 4. Kiểm tra hiển thị button Add to cart
        By addToCartButton = By.xpath(String.format(InventoryPageUI.ADD_TO_CART_SPECIFIC, productName));
        Assert.assertTrue(WaitUtils.isElementPresent(driver, addToCartButton),"Add to cart button should be displayed");

    }

    @Test
    public void Inventory11_VerifyAllProductsHaveNameAndPrice() {
        ExtentReportManager.createTest("Inventory11_VerifyAllProductsHaveNameAndPrice");

        List<String> productNames = inventoryAction.getAllProductNames();
        List<Double> productPrices = inventoryAction.getAllProductPrices();

        // Kiểm tra không có sản phẩm nào bị thiếu tên
        for (String name : productNames) {
            Assert.assertNotNull(name, "Product"+name+" should not be null");
            Assert.assertFalse(name.isEmpty(), "Product "+name+" should not be empty");
        }

        // Kiểm tra không có sản phẩm nào bị thiếu giá
        for (Double price : productPrices) {
            Assert.assertNotNull(price, "Product "+price+" should not be null");
            Assert.assertTrue(price > 0, "Product "+price+" should be greater than 0");
        }

        // Kiểm tra số lượng tên và giá bằng nhau
        Assert.assertEquals(productNames.size(), productPrices.size(),"Number of product names should match number of prices");
    }

    @Test
    public void Inventory12_LogoutFromInventory() {
        ExtentReportManager.createTest("Inventory12_LogoutFromInventory");

        // Đăng xuất
        inventoryAction.logout();

        // Kiểm tra đã quay về trang login
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/","Should return to login page after logout");

        // Kiểm tra logo login hiển thị
        WebElement loginLogo = WaitUtils.waitForVisible(driver, LoginPageUI.LOGIN_LOGO);
        Assert.assertTrue(loginLogo.isDisplayed(), "Login logo is not displayed!");

        // Kiểm tra hiển thị ô nhập username
        WebElement usernameField = WaitUtils.waitForVisible(driver, LoginPageUI.USERNAME);
        Assert.assertTrue(usernameField.isDisplayed(), "Username field should be displayed");
        // Kiểm tra ô username đã được clear
        Assert.assertEquals(usernameField.getAttribute("value"), "", "Username field should be empty after logout");

        // Kiểm tra hiển thị ô nhập password
        WebElement passwordField = WaitUtils.waitForVisible(driver, LoginPageUI.PASSWORD);
        Assert.assertTrue(passwordField.isDisplayed(), "Password field should be displayed");
        // Kiểm tra ô password đã được clear
        Assert.assertEquals(passwordField.getAttribute("value"), "", "Password field should be empty after logout");

        // Kiểm tra nút Login hiển thị
        WebElement loginButton = WaitUtils.waitForVisible(driver, LoginPageUI.LOGIN_BUTTON);
        Assert.assertTrue(loginButton.isDisplayed(), "Login button should be displayed");
    }

    @Test
    public void Inventory13_ClickAboutFromInventory() {
        ExtentReportManager.createTest("Inventory13_ClickAboutFromInventory");

        // Mở burger menu
        inventoryAction.openMenu();
        WaitUtils.sleep(1);

        // kiểm tra URL trước khi click vào about
        String inventoryUrl = driver.getCurrentUrl();
        Assert.assertTrue(inventoryUrl.contains("inventory.html"),
                "Should be on inventory page before clicking About");

        // Click vào link About
        WaitUtils.click(driver, InventoryPageUI.ABOUT_LINK);
        WaitUtils.sleep(2);

        // Kiểm tra URL đã chuyển sang trang About của Sauce Labs
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucelabs.com"),
                "Should navigate to Sauce Labs About page. Actual URL: " + currentUrl);

    }

    @Test
    public void Inventory14_AddAndRemoveMultipleProductsSequentially() {
        ExtentReportManager.createTest("Inventory14_AddAndRemoveMultipleProductsSequentially");

        String product1 = "Sauce Labs Backpack";
        String product2 = "Sauce Labs Bike Light";

        // Thêm 2 sản phẩm
        inventoryAction.addToCartByProductName(product1);
        inventoryAction.addToCartByProductName(product2);
        WaitUtils.sleep(1);
        //Kiểm tra số lượng sản phẩm trong giỏ hàng (hiển thị trên bagde)
        Assert.assertEquals(inventoryAction.getCartItemCount(), "2", "Should have 2 items");

        // Xóa 1 sản phẩm
        inventoryAction.removeFromCartByProductName(product1);
        WaitUtils.sleep(1);
        //Kiểm tra số lượng sản phẩm trong giỏ hàng (hiển thị trên bagde)
        Assert.assertEquals(inventoryAction.getCartItemCount(), "1", "Should have 1 item");

        // Thêm lại
        inventoryAction.addToCartByProductName(product1);
        WaitUtils.sleep(1);
        //Kiểm tra số lượng sản phẩm trong giỏ hàng (hiển thị trên bagde)
        Assert.assertEquals(inventoryAction.getCartItemCount(), "2", "Should have 2 items again");

        // Xóa hết
        inventoryAction.removeFromCartByProductName(product1);
        inventoryAction.removeFromCartByProductName(product2);
        WaitUtils.sleep(1);
        //Kiểm tra số lượng sản phẩm trong giỏ hàng (hiển thị trên bagde)
        Assert.assertEquals(inventoryAction.getCartItemCount(), "0", "Should be empty");
    }

    @Test
    public void Inventory15_VerifyBurgerMenuOpens() {
        ExtentReportManager.createTest("Inventory15_VerifyBurgerMenuOpens");

        // Mở menu
        inventoryAction.openMenu();

        // Kiểm tra các link trong menu hiển thị
        Assert.assertTrue(WaitUtils.isElementPresent(driver, InventoryPageUI.ALL_ITEMS_LINK),
                "All Items link should be displayed");
        Assert.assertTrue(WaitUtils.isElementPresent(driver, InventoryPageUI.ABOUT_LINK),
                "About link should be displayed");
        Assert.assertTrue(WaitUtils.isElementPresent(driver, InventoryPageUI.LOGOUT_LINK),
                "Logout link should be displayed");
        Assert.assertTrue(WaitUtils.isElementPresent(driver, InventoryPageUI.RESET_APP_STATE),
                "Reset App State link should be displayed");
    }

    @Test
    public void Inventory16_VerifySortPersistence() {
        ExtentReportManager.createTest("Inventory16_VerifySortPersistence");

        // Sort Z to A
        inventoryAction.sortProductsBy("za");
        List<String> firstSort = inventoryAction.getAllProductNames();

        // Vào cart rồi quay lại
        inventoryAction.goToCart();
        WaitUtils.sleep(1);
        driver.navigate().back();
        WaitUtils.sleep(1);

        // Sort vẫn giữ nguyên Z to A
        List<String> afterBack = inventoryAction.getAllProductNames();
        Assert.assertEquals(afterBack, firstSort, "Sort should persist after navigation");
    }

    @Test
    public void Inventory17_AddSameProductTwice() {
        ExtentReportManager.createTest("Inventory17_AddSameProductTwice");

        String product = "Sauce Labs Backpack";

        inventoryAction.addToCartByProductName(product);
        WaitUtils.sleep(1);
        Assert.assertEquals(inventoryAction.getCartItemCount(), "1", "Badge = 1");

        // Nút Add đã chuyển thành Remove -> không thể add lần 2
        // Kiểm tra nút Add không còn
        Assert.assertFalse(inventoryAction.isAddToCartButtonDisplayed(product),
                "Add button should be hidden after adding");
    }

    @Test
    public void Inventory18_VerifySortPersistsAfterAddToCart() {
        ExtentReportManager.createTest("Inventory18_VerifySortPersistsAfterAddToCart");

        inventoryAction.sortProductsBy("hilo");
        WaitUtils.sleep(1);
        List<Double> before = inventoryAction.getAllProductPrices();

        // Thêm sản phẩm
        inventoryAction.addToCartByProductName("Sauce Labs Backpack");
        WaitUtils.sleep(1);

        List<Double> after = inventoryAction.getAllProductPrices();
        Assert.assertEquals(after, before, "Sort should persist after adding product");
    }

    @Test
    public void Inventory19_ClickProductNameOpensDetail() {
        ExtentReportManager.createTest("Inventory19_ClickProductNameOpensDetail");

        String product = "Sauce Labs Backpack";
        inventoryAction.goToProductDetailByName(product);
        WaitUtils.sleep(1);

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item.html"),
                "Should navigate to product detail page");
    }

    @Test
    public void Inventory20_CannotAccessWithoutLogin() {
        ExtentReportManager.createTest("Inventory20_CannotAccessWithoutLogin");

        // Logout trước
        inventoryAction.logout();
        WaitUtils.sleep(1);

        // Thử truy cập thẳng vào inventory
        driver.get("https://www.saucedemo.com/inventory.html");
        WaitUtils.sleep(1);

        // Bị redirect về trang login
        Assert.assertTrue(driver.getCurrentUrl().equals("https://www.saucedemo.com/"),
                "Should redirect to login page");
    }

}