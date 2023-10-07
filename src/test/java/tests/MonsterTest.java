package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

import java.util.List;

public class MonsterTest extends BaseTest {
    public static final String EMAIL = System.getenv("MONSTER_EMAIL");
    public static final String PSWD = System.getenv("MONSTER_PSWD");
    public static final int MIN_SKILL_LEVEL = 50;

    @Test
    public void testUpdateMonsterProfile() {
        getDriver().get("https://www.monster.com/profile/detail");
        WebElement login = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        login.clear();
        login.sendKeys(EMAIL);

        WebElement password = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        password.clear();
        password.sendKeys(PSWD);

        getDriver().findElement(By.xpath("//div/div[contains(text(), 'Log In')]")).click();
        navigateToLastSkill();

        List<WebElement> initialSliderList = getDriver().findElements(By
                .xpath("//span[contains(@class, 'MuiSlider-track ProfileSlider_track__eB0LF')]"));
        WebElement lastSliderElement = initialSliderList.get(initialSliderList.size() - 1);
        int initialSliderValue = Integer.parseInt(lastSliderElement.getAttribute("style").substring(lastSliderElement
                .getAttribute("style").length() - 4, lastSliderElement.getAttribute("style").length() - 2));
        Utils.log("Initial skill value: " + initialSliderValue);

        moveSlider(lastSliderElement, initialSliderValue);

        getDriver().findElement(By.cssSelector("#dialog-control-profile-skills-edit-form-control-submit")).click();
        navigateToLastSkill();

        List<WebElement> updatedSliderList = getDriver().findElements(By.xpath("//span[contains(@class, 'MuiSlider-" +
                "track ProfileSlider_track__eB0LF')]"));
        WebElement updatedSlider = updatedSliderList.get(initialSliderList.size() - 1);
        int newSliderValue = Integer.parseInt(updatedSlider.getAttribute("style").substring(updatedSlider
                .getAttribute("style").length() - 4, updatedSlider.getAttribute("style").length() - 2));
        Utils.log("Updated skill value: " + newSliderValue);

        Assert.assertTrue(newSliderValue != initialSliderValue);
    }

    private void moveSlider(WebElement slider, int initialSliderValue) {
        if (initialSliderValue <= MIN_SKILL_LEVEL) {
            new Actions(getDriver())
                    .clickAndHold(slider)
                    .sendKeys(Keys.ARROW_RIGHT, Keys.ARROW_RIGHT)
                    .perform();
        } else {
            new Actions(getDriver())
                    .clickAndHold(slider)
                    .sendKeys(Keys.ARROW_LEFT, Keys.ARROW_RIGHT)
                    .perform();
        }
    }

    private void navigateToLastSkill() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@aria-label='EDIT - Skills']")))
                .click();
        WebElement saveButton =  getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//button[@id='dialog-control-profile-skills-edit-form-submit']")));
        new Actions(getDriver())
                .scrollToElement(saveButton)
                .perform();
    }
}
