package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {
    private WebDriver driver;

    private By avatarIcon = By.cssSelector("[data-testid='github-avatar']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoginSuccessful() {
        return driver.findElement(avatarIcon).isDisplayed();
    }
}
