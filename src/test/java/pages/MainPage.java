package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Sergey on 10/13/2017.
 */
public class MainPage {

    private WebDriverWait wait;
    private final WebDriver driver ;

    // Подготовка элементов страницы.
    By buttonMail = By.linkText("Почта");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Проверка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Белорусский портал TUT.BY. Новости Беларуси и мира"))){
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public MainPage clickButtonMail() {
        driver.findElement(buttonMail).click();
        return this;
    }

}
