package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Sergey on 10/13/2017.
 */
public class LogInMailPage {

    private WebDriverWait wait;
    private final WebDriver driver ;

    // Подготовка элементов страницы.
    private By body_locator = By.xpath("//body");
    private By form_locator = By.id("form");
    private By login_locator = By.id("Username");
    private By password_locator = By.id("Password");
    private By submit_button_locator = By.xpath("//input[@type='submit']");

    public LogInMailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("TUT.BY | ВАША ПОЧТА ТУТ | Вход"))){
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public LogInMailPage setLogin(String name) {
        driver.findElement(login_locator).clear();
        driver.findElement(login_locator).sendKeys(name);
        return this;
    }


    public LogInMailPage setPassword(String password) {
        driver.findElement(password_locator).clear();
        driver.findElement(password_locator).sendKeys(password);
        return this;
    }

    public LogInMailPage submitForm() {
        driver.findElement(submit_button_locator).click();
        return this;
    }

    public boolean isFormPresent() {
        if (driver.findElement(form_locator) != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getErrorOnTextAbsence(String search_string) {
        if (!pageTextContains(search_string)) {
            return "No '" + search_string + "' is found inside page text!\n";
        } else {
            return "";
        }
    }

    // Проверка вхождения подстроки в текст страницы.
    public boolean pageTextContains(String search_string) {
        return driver.findElement(body_locator).getText().contains(search_string);
    }

}
