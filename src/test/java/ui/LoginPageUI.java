package ui;

import org.openqa.selenium.By;

public class LoginPageUI {
    public static final By USERNAME = By.id("user-name");
    public static final By PASSWORD  = By.id("password");
    public static final By LOGIN_BUTTON   = By.id("login-button");
    public static final By ERROR_MESSAGE  = By.xpath("//h3[@data-test='error']");
    public static final By LOGIN_LOGO = By.xpath("//div[@class='login_logo']");
}
