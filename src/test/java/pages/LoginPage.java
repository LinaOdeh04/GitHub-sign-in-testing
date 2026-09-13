package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    // Locators
    private By usernameField = By.id("login_field");
    private By passwordField = By.id("password");
    private By signInButton = By.name("commit");
    private By errorMessage = By.className("js-flash-alert");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    // Actions
    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickSignIn() {
        driver.findElement(signInButton).click();
    }

    // Combined action (helper method)
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSignIn();
    }


    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
        return driver.findElement(errorMessage).getText().trim();
    }

    public String getUsernameFieldValue() {
        return driver.findElement(usernameField).getAttribute("value");
    }
}
