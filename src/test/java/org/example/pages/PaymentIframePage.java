package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentIframePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By paymentFrame = By.xpath("//iframe[contains(@class,'payment-widget-iframe') or contains(@src,'bepaid.by')]");

    private final By sumInFrame = By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/div/div[1]/div[1]/span");
    private final By sumOnButton = By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/app-card-page/div/div[1]/button/span");
    private final By serviceAndPhone = By.xpath("/html/body/app-root/div/div/div/app-payment-container/section/div/div/div[2]/span");

    private final By ccNumber = By.xpath("//*[@id=\"cc-number\"]");
    private final By expDate = By.xpath("//input[@formcontrolname='expirationDate']");
    private final By cvc = By.xpath("//input[@formcontrolname='cvc']");
    private final By holder = By.xpath("//input[@formcontrolname='holder']");

    private final By visaIcon = By.xpath("//img[contains(@src,'visa-system.svg')]");
    private final By mcIcon = By.xpath("//img[contains(@src,'mastercard-system.svg')]");
    private final By belIcon = By.xpath("//img[contains(@src,'belkart-system.svg')]");
    private final By maestroOrMirIcon = By.xpath("//img[contains(@src,'maestro-system.svg') or contains(@src,'mir-system-ru.svg')]");

    public PaymentIframePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public PaymentIframePage switchTo() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(paymentFrame));
        return this;
    }

    public void switchBack() {
        driver.switchTo().defaultContent();
    }

    public void assertOpened() {
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(ccNumber)).isDisplayed(),
                "Окно оплаты не открылось (нет поля номера карты)");
    }

    public void assertMoney(String expectedAmount) {
        String top = wait.until(ExpectedConditions.visibilityOfElementLocated(sumInFrame)).getText();
        String btn = wait.until(ExpectedConditions.visibilityOfElementLocated(sumOnButton)).getText();

        assertMoneyEquals(expectedAmount, top, "Неверная сумма в окне оплаты (верх)");
        assertMoneyEquals(expectedAmount, btn, "Неверная сумма на кнопке оплаты");
    }

    public void assertServiceAndPhone(String expectedPhone) {
        String info = wait.until(ExpectedConditions.visibilityOfElementLocated(serviceAndPhone)).getText();
        assertTrue(info.toLowerCase().contains("услуги связи"),
                "Нет текста 'Услуги связи' в окне оплаты");
        assertTrue(info.contains(expectedPhone),
                "В окне оплаты неверный номер телефона");
    }

    public void assertCardFieldLabels() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ccNumber));

        assertLabelVisible("Номер карты");
        assertLabelVisible("Срок действия");
        assertLabelVisible("CVC");
        assertLabelVisible("Имя и фамилия на карте");
    }

    private void assertLabelVisible(String text) {
        By label = By.xpath("//form//*[normalize-space(text())='" + text + "']");
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(label));
        assertTrue(el.isDisplayed(), "Лейбл '" + text + "' не отображается");
    }

    public void assertPaymentIconsPresent() {
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(visaIcon)).isDisplayed(),
                "Visa не отображается");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(mcIcon)).isDisplayed(),
                "MasterCard не отображается");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(belIcon)).isDisplayed(),
                "Belkart не отображается");

        // Maestro/MIR может быть анимирован — проверяем хотя бы наличие в DOM
        List<WebElement> icons = driver.findElements(maestroOrMirIcon);
        assertFalse(icons.isEmpty(), "Не найдена иконка Maestro или MIR");
    }

    // ---------------- helpers ----------------

    private void assertMoneyEquals(String expectedAmount, String actualText, String errorMessage) {
        BigDecimal expected = new BigDecimal(expectedAmount);
        BigDecimal actual = parseMoney(actualText);
        assertEquals(0, expected.compareTo(actual),
                errorMessage + " (текст: '" + actualText + "')");
    }

    private BigDecimal parseMoney(String text) {
        String normalized = text.replace(",", ".").replaceAll("[^0-9.]", "");
        if (normalized.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(normalized);
    }
}