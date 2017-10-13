package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created by Sergey on 10/13/2017.
 */

public class LoginMailYandexPage {
    private WebDriverWait wait;
    private final WebDriver driver ;

    // Подготовка элементов страницы.
    private By body_locator = By.xpath("//body");
    private By form_locator = By.className("new-auth-form");
    private By login_locator = By.name("login");
    private By password_locator = By.name("passwd");
    private By submit_button_locator = By.xpath("//button[@type='submit']");

    public LoginMailYandexPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Проверка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Яндекс.Почта — бесплатная электронная почта"))){
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public LoginMailYandexPage setLogin(String name) {
        driver.findElement(login_locator).clear();
        driver.findElement(login_locator).sendKeys(name);
        return this;
    }


    public LoginMailYandexPage setPassword(String password) {
        driver.findElement(password_locator).clear();
        driver.findElement(password_locator).sendKeys(password);
        return this;
    }

    public LoginMailYandexPage submitForm() {
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
