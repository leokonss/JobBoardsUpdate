import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DiceTest extends BaseTest {
    public static final String EMAIL = System.getenv("DICE_EMAIL");
    public static final String PSWD = System.getenv("DICE_PSWD");
    @Test
    public void testUpdateDice() throws InterruptedException {
        getDriver().get("https://www.dice.com/dashboard/login");
        WebElement login = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
        login.clear();
        login.sendKeys(EMAIL);

        WebElement password = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        password.clear();
        password.sendKeys(PSWD);
        getDriver().findElement(By.xpath("//button[@type='submit']")).click();

        SearchContext shadowRoot = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//dhi-seds-nav-header[@class='hydrated']")))
                .getShadowRoot()
                .findElement(By.className("hydrated"))
                .getShadowRoot();

        WebElement profileMenu = shadowRoot.findElement(By.cssSelector(".links > ul:nth-child(2) .dropdown-button"));
        new Actions(getDriver()).pause(Duration.ofSeconds(1))
                .moveToElement(profileMenu)
                .pause(Duration.ofSeconds(1))
                .perform();

        shadowRoot.findElement(By.cssSelector("a[href$='dashboard/profiles']")).click();

        WebElement skillsShadowHost = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//dhi-candidates-wired-candidate-profile[@class='theme-candidates hydrated']")));
        SearchContext skillsShadowRoot = skillsShadowHost.getShadowRoot();

        String previousSkill = skillsShadowRoot.findElement(By
                .cssSelector("td[class='sc-dhi-candidates-meter sc-dhi-candidates-meter-s'] > span")).getText();
        Utils.log("Initial skill: " + previousSkill);
        skillsShadowRoot.findElement(By
                .cssSelector("seds-button[class='skills-edit-action sc-dhi-candidates-candidate-profile-skills-view seds seds-mb-100 hydrated']"))
                .getShadowRoot()
                .findElement(By.cssSelector("button[class='seds-button seds-button-secondary']"))
                .click();

        WebElement skillField = skillsShadowRoot
                .findElement(By.cssSelector("div[aria-label='Skill #1 Details']"))
                .findElement(By.cssSelector("input[aria-label='Skill']"));
        skillField.clear();
        skillField.sendKeys(Utils.getSkillToEnter(previousSkill));

        WebElement saveButton = skillsShadowRoot.findElement(By.cssSelector("#skills-edit-save-action"));
        saveButton.click();
        Thread.sleep(3000);

        String expectedSkill = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By
                .xpath("//dhi-candidates-wired-candidate-profile[@class='theme-candidates hydrated']")))
                .getShadowRoot()
                .findElement(By
                .cssSelector("td[class='sc-dhi-candidates-meter sc-dhi-candidates-meter-s'] > span")).getText();
        Utils.log("Updated skill: " + expectedSkill);

        Assert.assertTrue(expectedSkill.contains("Mobile"));
        Assert.assertNotEquals(previousSkill, expectedSkill, String.format("Previous skill - %s, new skill - %s", previousSkill, expectedSkill));
    }
}
