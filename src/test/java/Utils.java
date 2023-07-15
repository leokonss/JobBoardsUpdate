import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {
    private static final ChromeOptions chromeOptions = new ChromeOptions();
    static WebDriver createDriver() {
        chromeOptions.addArguments("--headless", "--window-size=1920,1080");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        return new ChromeDriver(chromeOptions);
    }

    static void get(WebDriver driver) {
        driver.get("https://www.dice.com/dashboard/login");
    }
    static void takeScreenshot(WebDriver driver, String methodName, String className) {
        File file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(String.format("screenshots/%s.%s.png", className, methodName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        try {
            FileInputStream fp = new FileInputStream("src/test/resources/local.properties");
            Properties prop = new Properties();
            prop.load(fp);
            return prop;
        } catch (IOException ignored) {}
        return new Properties();
    }

    public static String getLogin() {
        return getProperties().getProperty("username");
    }

    public static String getPassword() {
        return getProperties().getProperty("password");
    }

    public static void log(String str) {
        System.out.println(str);
    }

    public static void logf(String str, Object... arr) {
        System.out.printf(str, arr);
        System.out.println();
    }

    public static String getSkillToEnter(String previousSkill) {
        if (previousSkill.equals("Mobile")) {
            return "Mobile testing";
        } else {
            return "Mobile";
        }
    }
}
