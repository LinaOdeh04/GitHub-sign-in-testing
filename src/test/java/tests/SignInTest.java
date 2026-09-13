package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;

public class SignInTest {
    WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browser) {
        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                case "edge":
                    System.setProperty("webdriver.edge.driver","C:\\Users\\lenovo\\Downloads\\edgedriver_win64\\msedgedriver.exe");
                    driver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Browser not supported: " + browser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        driver.manage().window().maximize();
        driver.get("https://github.com/login");
    }

    @Test
    public void testValidSignIn() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("github.username"), ConfigReader.get("github.password"));

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isLoginSuccessful(), "Login was not successful - avatar not found");
    }
    @Test
    public void testInvalidUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("nonExistentUser12345", "anyPassword123");

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "Incorrect username or password.");
    }
    @Test
    public void testInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("github.username"), "wrongPassword123");

        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "Incorrect username or password.");
    }
    @Test
    public void testEmptyFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("login") || currentUrl.contains("session"),
                "Expected to stay on login page, but URL was: " + currentUrl);
    }
    @Test
    public void testEmptyFieldsAlternative() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        String usernameValue = loginPage.getUsernameFieldValue();
        Assert.assertEquals(usernameValue, "", "Username field should still be empty");
    }

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }
}
