package action;

import ui.LoginPageUI;
import utils.WaitUtils;
import org.openqa.selenium.WebDriver;

public class LoginAction {
    private WebDriver driver;

    public LoginAction(WebDriver driver) {
        this.driver = driver;
    }

    public void performLogin(String username, String password) {
        WaitUtils.sendKeys(driver, LoginPageUI.USERNAME, username);
        WaitUtils.sendKeys(driver, LoginPageUI.PASSWORD, password);
        WaitUtils.click(driver, LoginPageUI.LOGIN_BUTTON);
    }

    public String getErrorMessage() {
        return WaitUtils.getText(driver, LoginPageUI.ERROR_MESSAGE);
    }

    public boolean isErrorMessageDisplayed() {
        return WaitUtils.isElementPresent(driver, LoginPageUI.ERROR_MESSAGE);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}