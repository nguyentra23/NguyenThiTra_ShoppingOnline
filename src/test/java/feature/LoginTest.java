package feature;

import action.LoginAction;
import action.InventoryAction;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import ui.InventoryPageUI;
import ui.LoginPageUI;
import utils.Hook;
import utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.WaitUtils;

public class LoginTest extends Hook {

    @BeforeMethod
    public void navigateToLoginPage() {
        driver.get("https://www.saucedemo.com");
    }

    @Test
    public void Login01_LoginWithValidCredentials() {
        ExtentReportManager.createTest("Login01_LoginWithValidCredentials");
        LoginAction loginAction = new LoginAction(driver);

        // Đăng nhập
        loginAction.performLogin("standard_user", "secret_sauce");

        // Kiểm tra URL sau khi login
        Assert.assertEquals(loginAction.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed - URL incorrect!");

        // Kiểm tra trang sau login có sản phẩm
        InventoryAction inventoryAction = new InventoryAction(driver);
        Assert.assertTrue(inventoryAction.getProductCount() > 0, "Login failed - No products displayed!");

        // Kiểm tra logo sau khi login
        WebElement appLogo = WaitUtils.waitForVisible(driver, InventoryPageUI.APP_LOGO);
        Assert.assertTrue(appLogo.isDisplayed(), "App logo is not displayed!");
    }

    @Test
    public void Login02_LoginSuccessWithProblem_user() {
        ExtentReportManager.createTest("Login02_LoginSuccessWithProblem_user");
        LoginAction loginAction = new LoginAction(driver);

        // Đăng nhập
        loginAction.performLogin("problem_user", "secret_sauce");

        // Kiểm tra URL sau khi login
        Assert.assertEquals(loginAction.getCurrentUrl(), "https://www.saucedemo.com/inventory.html", "Login failed - URL incorrect!");

        // Kiểm tra trang sau login có sản phẩm
        InventoryAction inventoryAction = new InventoryAction(driver);
        Assert.assertTrue(inventoryAction.getProductCount() > 0, "Login failed - No products displayed!");

        // Kiểm tra logo sau khi login
        WebElement appLogo = WaitUtils.waitForVisible(driver, InventoryPageUI.APP_LOGO);
        Assert.assertTrue(appLogo.isDisplayed(), "App logo is not displayed!");
    }

    @Test
    public void Login03_LoginFailWithWrongPassword() {
        ExtentReportManager.createTest("Login03_LoginFailWithWrongPassword");
        LoginAction loginAction = new LoginAction(driver);

        // Đăng nhập
        loginAction.performLogin("problem_user", "wrong_password");

        // Kiểm tra URL sau khi login thất bại
        Assert.assertEquals(loginAction.getCurrentUrl(), "https://www.saucedemo.com/", "Test failed - URL incorrect!");

        // Kiểm tra logo sau khi login thất bại
        WebElement loginLogo = WaitUtils.waitForVisible(driver, LoginPageUI.LOGIN_LOGO);
        Assert.assertTrue(loginLogo.isDisplayed(), "Login logo is not displayed!");

        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(loginAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        //Kiểm tra message lỗi có hiển thị đúng không
        Assert.assertTrue(loginAction.getErrorMessage().contains("Username and password do not match"),"Error message is not match");
    }

    @Test
    public void Login04_LoginFailWithInvalidUsername() {
        ExtentReportManager.createTest("Login04_LoginFailWithInvalidUsername");
        LoginAction loginAction = new LoginAction(driver);

        // Đăng nhập
        loginAction.performLogin("problem_ur", "secret_sauce");

        // Kiểm tra URL sau khi login thất bại
        Assert.assertEquals(loginAction.getCurrentUrl(), "https://www.saucedemo.com/", "Test failed - URL incorrect!");

        // Kiểm tra logo sau khi login thất bại
        WebElement loginLogo = WaitUtils.waitForVisible(driver, LoginPageUI.LOGIN_LOGO);
        Assert.assertTrue(loginLogo.isDisplayed(), "Login logo is not displayed!");

        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(loginAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        //Kiểm tra message lỗi có hiển thị đúng không
        Assert.assertTrue(loginAction.getErrorMessage().contains("Username and password do not match"),"Error message is not match");
    }

    @Test
    public void Login05_LoginFailWithEmptyUsername() {
        ExtentReportManager.createTest("Login05_LoginFailWithEmptyUsername");
        LoginAction loginAction = new LoginAction(driver);

        // Đăng nhập
        loginAction.performLogin("", "secret_sauce");

        // Kiểm tra URL sau khi login thất bại
        Assert.assertEquals(loginAction.getCurrentUrl(), "https://www.saucedemo.com/", "Test failed - URL incorrect!");

        // Kiểm tra logo sau khi login thất bại
        WebElement loginLogo = WaitUtils.waitForVisible(driver, LoginPageUI.LOGIN_LOGO);
        Assert.assertTrue(loginLogo.isDisplayed(), "Login logo is not displayed!");

        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(loginAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        //Kiểm tra message lỗi có hiển thị đúng không
        Assert.assertTrue(loginAction.getErrorMessage().contains("Username is required"), "Error message is not match");
    }

    @Test
    public void Login06_LoginFailWithEmptyPassword() {
        ExtentReportManager.createTest("Login06_LoginFailWithEmptyPassword");
        LoginAction loginAction = new LoginAction(driver);

        // Đăng nhập
        loginAction.performLogin("problem_user", "");

        // Kiểm tra URL sau khi login thất bại
        Assert.assertEquals(loginAction.getCurrentUrl(), "https://www.saucedemo.com/", "Test failed - URL incorrect!");

        // Kiểm tra logo sau khi login thất bại
        WebElement loginLogo = WaitUtils.waitForVisible(driver, LoginPageUI.LOGIN_LOGO);
        Assert.assertTrue(loginLogo.isDisplayed(), "Login logo is not displayed!");

        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(loginAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        //Kiểm tra message lỗi có hiển thị đúng không
        Assert.assertTrue(loginAction.getErrorMessage().contains("Password is required"), "Error message is not match");
    }

    @Test
    public void Login07_LoginFailWithEmptyBothUsernameAndPassword() {
        ExtentReportManager.createTest("Login07_LoginFailWithEmptyBothUsernameAndPassword");
        LoginAction loginAction = new LoginAction(driver);

        // Đăng nhập
        loginAction.performLogin("", "");

        // Kiểm tra URL sau khi login thất bại
        Assert.assertEquals(loginAction.getCurrentUrl(), "https://www.saucedemo.com/", "Test failed - URL incorrect!");

        // Kiểm tra logo sau khi login thất bại
        WebElement loginLogo = WaitUtils.waitForVisible(driver, LoginPageUI.LOGIN_LOGO);
        Assert.assertTrue(loginLogo.isDisplayed(), "Login logo is not displayed!");

        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(loginAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        //Kiểm tra message lỗi có hiển thị đúng không
        Assert.assertTrue(loginAction.getErrorMessage().contains("Username is required"), "Error message is not match");
    }

    @Test
    public void Login08_LoginFailWithLockedOutUser() {
        ExtentReportManager.createTest("Login08_LoginFailWithLockedOutUser");
        LoginAction loginAction = new LoginAction(driver);

        // Đăng nhập
        loginAction.performLogin("locked_out_user", "secret_sauce");

        // Kiểm tra URL sau khi login thất bại
        Assert.assertEquals(loginAction.getCurrentUrl(), "https://www.saucedemo.com/", "Test failed - URL incorrect!");

        // Kiểm tra logo sau khi login thất bại
        WebElement loginLogo = WaitUtils.waitForVisible(driver, LoginPageUI.LOGIN_LOGO);
        Assert.assertTrue(loginLogo.isDisplayed(), "Login logo is not displayed!");

        // Kiểm tra có hiển thị message lỗi
        Assert.assertTrue(loginAction.isErrorMessageDisplayed(),"Error message is NOT displayed");

        //Kiểm tra message lỗi có hiển thị đúng không
        Assert.assertTrue(loginAction.getErrorMessage().contains("Epic sadface: Sorry, this user has been locked out."), "Error message is not match");
    }

}