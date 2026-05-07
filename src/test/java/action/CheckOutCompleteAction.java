package action;

import ui.CheckOutCompletePageUI;
import utils.WaitUtils;
import org.openqa.selenium.WebDriver;

public class CheckOutCompleteAction {
    private WebDriver driver;

    public CheckOutCompleteAction(WebDriver driver) {
        this.driver = driver;
    }

    // Lấy thông báo thành công (header)
    public String getCompleteHeader() {
        return WaitUtils.getText(driver, CheckOutCompletePageUI.COMPLETE_HEADER);
    }

    // Lấy text thông báo thành công
    public String getCompleteText() {
        return WaitUtils.getText(driver, CheckOutCompletePageUI.COMPLETE_TEXT);
    }

    // Click nút Back Home
    public void clickBackHome() {
        WaitUtils.click(driver, CheckOutCompletePageUI.BACK_HOME_BUTTON);
    }

    // Lấy URL hiện tại
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }


    // Kiểm tra đặt hàng thành công
    public boolean verifyOrderSuccess() {
        return getCompleteHeader().equals("Thank you for your order!");
    }

    // Kiểm tra hình pony express có hiển thị không
    public boolean isPonyExpressImageDisplayed() {
        return WaitUtils.isElementPresent(driver, CheckOutCompletePageUI.PONY_EXPRESS_IMAGE);
    }
}