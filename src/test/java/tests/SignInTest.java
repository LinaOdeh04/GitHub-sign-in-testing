package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;

public class SignInTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
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

    @AfterMethod
    public void tearDown() {

        driver.quit();
    }
}
