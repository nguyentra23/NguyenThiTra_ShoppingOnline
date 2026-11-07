package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.Status;

public class ExtentReportManager implements org.testng.ITestListener{
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // Khởi tạo report
    public static void initReport() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("SauceDemo Automation Report");
            sparkReporter.config().setReportName("SauceDemo Test Execution Report");
            sparkReporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // Thêm thông tin hệ thống
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User Name", System.getProperty("user.name"));
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("URL", "https://www.saucedemo.com");
        }
    }

    // Tạo test case mới
    public static ExtentTest createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    // Lấy test hiện tại
    public static ExtentTest getTest() {
        return test.get();
    }

    // Log thông tin
    public static void logInfo(String message) {
        if (getTest() != null) {
            getTest().log(Status.INFO, message);
        }
    }

    // Log pass
    public static void logPass(String message) {
        if (getTest() != null) {
            getTest().log(Status.PASS, message);
        }
    }

    // Log fail
    public static void logFail(String message) {
        if (getTest() != null) {
            getTest().log(Status.FAIL, message);
        }
    }

    // Log skip
    public static void logSkip(String message) {
        if (getTest() != null) {
            getTest().log(Status.SKIP, message);
        }
    }

    // Log warning
    public static void logWarning(String message) {
        if (getTest() != null) {
            getTest().log(Status.WARNING, message);
        }
    }

    // Log với ảnh chụp màn hình (nếu có)
    public static void logWithScreenshot(String message, String base64Screenshot) {
        if (getTest() != null) {
            getTest().log(Status.INFO, message).addScreenCaptureFromBase64String(base64Screenshot);
        }
    }


    // Ghi report ra file
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // Xóa test hiện tại
    public static void removeTest() {
        test.remove();
    }
}