import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LogInMailPage;
import pages.LoginMailYandexPage;
import pages.MailPage;
import pages.MainPage;
import workers.PropertiesWorker;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergey on 8/12/2017.
 */
public class tstCheckLetters {

    private final String pathConfig = System.getProperty("user.dir") + "/src/test/resources/config.properties";
    private WebDriver driver = null;
    private PropertiesWorker propertiesWorker;
    private FirefoxProfile profile = new FirefoxProfile();
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass
    public void beforeClass() throws Exception{
        propertiesWorker = new PropertiesWorker();
        propertiesWorker.createProperties(pathConfig);
        String pathGeckodriver = propertiesWorker.getProperty("path_geckodriver");

        System.setProperty("webdriver.gecko.driver", pathGeckodriver);
        profile.setPreference("browser.startup.homepage", "about:blank");
        driver = new FirefoxDriver(profile);
        //driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            Assert.fail(verificationErrorString);
        }
    }

    @Test
    public void buttonMail_LogInMainPage(){
        String baseUrl = propertiesWorker.getProperty("base_url");
        if (baseUrl != null){
            driver.get(baseUrl);
        }else Assert.fail();

        MainPage page = new MainPage(driver);

        page.clickButtonMail();
        Assert.assertTrue(driver.getTitle().equals("TUT.BY | ВАША ПОЧТА ТУТ | Вход"));
    }

    @Test(dependsOnMethods = "buttonMail_LogInMainPage", alwaysRun = true)
    public void logIn_LogInYandexMailPage(){
        LogInMailPage page = new LogInMailPage(driver);

        Assert.assertTrue(page.isFormPresent(), "No forms found!");
        verificationErrors.append(page.getErrorOnTextAbsence("пароль"));

        String login = propertiesWorker.getProperty("login");
        String password = propertiesWorker.getProperty("password");

        page.setLogin(login);
        page.setPassword(password);
        page.submitForm();
        Assert.assertTrue(driver.getTitle().equals("Яндекс.Почта — бесплатная электронная почта"));
    }

    @Test(dependsOnMethods = "logIn_LogInYandexMailPage", alwaysRun = true)
    public void logIn_MailYandexPage(){
        LoginMailYandexPage page = new LoginMailYandexPage(driver);

        Assert.assertTrue(page.isFormPresent(), "No forms found!");

        String login = propertiesWorker.getProperty("login") + "@tut.by";
        String password = propertiesWorker.getProperty("password");

        page.setLogin(login);
        page.setPassword(password);
        page.submitForm();
        page.setLogin(login);
        page.setPassword(password);
        page.submitForm();
    }

    @Test(dependsOnMethods = "logIn_MailYandexPage", alwaysRun = true)
    public void getNumberIncomingLetters_NumberIncomingLetters(){
        MailPage page = new MailPage(driver);
        int expectedNumberLetters = Integer.parseInt(propertiesWorker.getProperty("expected_number_letters"));

        // in will only work with number of letters < 30
        Assert.assertEquals(page.getNumberLetters(), expectedNumberLetters);
    }


}
