package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MtsReplenishmentTests {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.mts.by/");

        closeCookieIfPresent();
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void checkBlockTitle() {

        WebElement block = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div")
                )
        );

        WebElement title = block.findElement(
                By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2")
        );

        assertTrue(title.isDisplayed(), "Заголовок блока не отображается");
    }

    @Test
    void checkPaymentLogos() {

        WebElement block = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul")
                )
        );

        checkLogo(block, "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[1]/img",
                "Логотип Visa не найден");

        checkLogo(block, "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[2]/img",
                "Логотип Verified By Visa не найден");

        checkLogo(block, "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[3]/img",
                "Логотип MasterCard не найден");

        checkLogo(block, "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[4]/img",
                "Логотип MasterCard Secure Code не найден");

        checkLogo(block, "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[5]/img",
                "Логотип Белкарт не найден");
    }

    @Test
    void checkDetailsLink() {

        WebElement link = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/a")
                )
        );

        String oldUrl = driver.getCurrentUrl();

        link.click();

        wait.until(driver -> !driver.getCurrentUrl().equals(oldUrl));

        assertNotEquals(oldUrl, driver.getCurrentUrl(),
                "Ссылка 'Подробнее о сервисе' не сработала");
    }

    @Test
    void checkContinueButton() {

        WebElement servicesTab = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[1]/div[1]/div[2]/button")
                )
        );
        servicesTab.click();

        WebElement phoneInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"connection-phone\"]")
                )
        );

        phoneInput.clear();
        phoneInput.sendKeys("297777777");

        WebElement amountInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"connection-sum\"]")
                )
        );
        amountInput.clear();
        amountInput.sendKeys("10");

        WebElement continueButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"pay-connection\"]/button")
                )
        );

        WebElement emailInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[@id=\"connection-email\"]")
                )
        );

        emailInput.clear();
        emailInput.sendKeys("what@gmail.com");

        continueButton.click();

        By paymentFrame = By.xpath("//iframe[contains(@class,'payment-widget-iframe')]");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(paymentFrame));

        By cardFieldInsideFrame = By.xpath("//*[@id=\"cc-number\"]");

        WebElement cardEl = wait.until(ExpectedConditions.visibilityOfElementLocated(cardFieldInsideFrame));
        assertTrue(cardEl.isDisplayed(), "Окно оплаты не открылось");

        driver.switchTo().defaultContent();
    }

    private void checkLogo(WebElement block, String xpath, String errorMessage) {
        List<WebElement> logos = block.findElements(By.xpath(xpath));
        assertFalse(logos.isEmpty(), errorMessage);
        assertTrue(logos.get(0).isDisplayed(), errorMessage);
    }

    private void closeCookieIfPresent() {
        try {
            By cookieButton = By.xpath("//*[@id='cookie-agree']");

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

            WebElement button = shortWait.until(
                    ExpectedConditions.elementToBeClickable(cookieButton)
            );

            button.click();

            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(cookieButton));

        } catch (TimeoutException ignored) {
        }
    }
}