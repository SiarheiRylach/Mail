package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Sergey on 10/13/2017.
 */
public class MailPage {
    private WebDriverWait wait;
    private final WebDriver driver ;

    // Подготовка элементов страницы.
    private By mail_list_locator = By.className("mail-MessageSnippet-Wrapper");
    private String expectedTitle = "Входящие — Яндекс.Почта";

    public MailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Проверка того факта, что мы на верной странице.
        if ((!wait.until(ExpectedConditions.titleContains(expectedTitle)))){
            throw new IllegalStateException("Wrong site page!");
        }
    }

    public int getNumberLetters() {
        return driver.findElements(mail_list_locator).size();
    }
}
