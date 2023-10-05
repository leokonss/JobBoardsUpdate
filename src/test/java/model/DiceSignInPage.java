package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DiceSignInPage extends BasePage {
    @FindBy(css = "#email")
    private WebElement loginField;

    @FindBy(css = "#password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signInButton;

    public DiceSignInPage(WebDriver driver) {
        super(driver);
    }

    public DiceSignInPage enterLogin(String login) {
        getWait5().until(ExpectedConditions.visibilityOf(loginField)).clear();
        loginField.sendKeys(login);
        return this;
    }

    public DiceSignInPage enterPassword(String password) {
        getWait5().until(ExpectedConditions.visibilityOf(passwordField)).clear();
        passwordField.sendKeys(password);
        return this;
    }

    public DiceProfilePage signInToDiceAccount() {
        signInButton.click();
        return new DiceProfilePage(getDriver());
    }
}
