package model;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class DiceProfilePage extends BasePage {
    @FindBy(xpath = "//dhi-seds-nav-header[@class='hydrated']")
    private WebElement navHeader;

    @FindBy(className = "hydrated")
    private WebElement navHeaderDisplay;

    @FindBy(css = ".links > ul:nth-child(2) .dropdown-button")
    private WebElement menu;

    @FindBy(css = "a[href$='dashboard/profiles']")
    private WebElement profileButton;

    @FindBy(xpath = "//dhi-candidates-wired-candidate-profile[@class='theme-candidates hydrated']")
    private WebElement skillsShadowHost;

    @FindBy(css = "seds-button[class='skills-edit-action sc-dhi-candidates-candidate-profile-skills-view seds seds-mb-100 hydrated']")
    private WebElement editSkillsButton;

    public DiceProfilePage(WebDriver driver) {
        super(driver);
    }

    private SearchContext getHeaderShadowRoot() {
        return getWait5().until(ExpectedConditions.visibilityOf(navHeader))
                .getShadowRoot()
                .findElement(By.className("hydrated"))
                .getShadowRoot();
    }

    public DiceProfilePage navigateToProfileInMenu() {
        WebElement profileMenu = getHeaderShadowRoot().findElement(By.cssSelector(".links > ul:nth-child(2) .dropdown-button"));
        new Actions(getDriver()).pause(Duration.ofSeconds(1))
                .moveToElement(profileMenu)
                .pause(Duration.ofSeconds(1))
                .perform();
        return this;
    }

    public DiceProfilePage goToProfile() {
        getHeaderShadowRoot().findElement(By.cssSelector("a[href$='dashboard/profiles']")).click();
        return this;
    }

    private SearchContext getSkillsShadowRoot() {
        return getWait5().until(ExpectedConditions.visibilityOf(skillsShadowHost))
                .getShadowRoot();
    }

    public String getSkill() {
        return getSkillsShadowRoot().findElement(By
                .cssSelector("td[class='sc-dhi-candidates-meter sc-dhi-candidates-meter-s'] > span")).getText();
    }

    public DiceProfilePage clickEditSkillsButton() {
        getSkillsShadowRoot().findElement(By
                .cssSelector("seds-button[class='skills-edit-action sc-dhi-candidates-candidate-profile-skills-view seds seds-mb-100 hydrated']"))
                .getShadowRoot()
                .findElement(By.cssSelector("button[class='seds-button seds-button-secondary']"))
                .click();
        return this;
    }

    public DiceProfilePage enterNewSkill(String skill) {
        WebElement skillField = getSkillsShadowRoot()
                .findElement(By.cssSelector("div[aria-label='Skill #1 Details']"))
                .findElement(By.cssSelector("input[aria-label='Skill']"));
        skillField.clear();
        skillField.sendKeys(skill);
        return this;
    }

    public DiceProfilePage saveNewSkill() throws InterruptedException {
        getSkillsShadowRoot()
                .findElement(By.cssSelector("#skills-edit-save-action")).click();
        Thread.sleep(3000);
        return this;
    }
}
