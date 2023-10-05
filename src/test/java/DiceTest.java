import model.DiceProfilePage;
import model.DiceSignInPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DiceTest extends BaseTest {
    public static final String EMAIL = System.getenv("DICE_EMAIL");
    public static final String PSWD = System.getenv("DICE_PSWD");

    @Test
    public void testUpdateDice() throws InterruptedException {
        getDriver().get("https://www.dice.com/dashboard/login");
        DiceProfilePage profilePage = new DiceSignInPage(getDriver())
                .enterLogin(EMAIL)
                .enterPassword(PSWD)
                .signInToDiceAccount()
                .navigateToProfileInMenu()
                .goToProfile();

        String previousSkill = profilePage.getSkill();
        Utils.log("Initial skill: " + previousSkill);

        String expectedSkill = profilePage.clickEditSkillsButton()
                .enterNewSkill(Utils.getSkillToEnter(previousSkill))
                .saveNewSkill()
                .getSkill();
        Utils.log("Updated skill: " + expectedSkill);

        Assert.assertTrue(expectedSkill.contains("Mobile"));
        Assert.assertNotEquals(previousSkill, expectedSkill, String.format("Previous skill - %s, new skill - %s",
                previousSkill, expectedSkill));
    }
}
