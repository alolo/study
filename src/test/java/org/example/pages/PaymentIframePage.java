package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentIframePage extends BasePage {

    private final By paymentFrame = By.xpath("//iframe[contains(@class,'payment-widget-iframe') or contains(@src,'bepaid.by')]");

    private final By sumInFrame = By.xpath("//span[contains(text(), 'BYN')]");
    private final By sumOnButton = By.xpath("//button[.//span[contains(text(), 'Оплатить')]]");

    private final By serviceAndPhone = By.xpath("//span[contains(text(), 'Оплата: Услуги связи') and contains(text(), '375297777777')]");

    private final By ccNumber = By.xpath("//*[@id='cc-number']");

    private final By visaIcon = By.xpath("//img[contains(@src,'visa-system.svg')]");
    private final By mcIcon = By.xpath("//img[contains(@src,'mastercard-system.svg')]");
    private final By belIcon = By.xpath("//img[contains(@src,'belkart-system.svg')]");
    private final By maestroOrMirIcon = By.xpath("//img[contains(@src,'maestro-system.svg') or contains(@src,'mir-system-ru.svg')]");

    public PaymentIframePage(WebDriver driver) {
        super(driver);
    }

    public PaymentIframePage switchTo() {
        switchToFrame(paymentFrame);
        return this;
    }

    public void switchBack() {
        switchToDefaultContent();
    }

    public void assertOpened() {
        assertTrue(waitVisible(ccNumber).isDisplayed(),
                "Окно оплаты не открылось (нет поля номера карты)");
    }

    public void assertMoney(String expectedAmount) {
        String top = getText(sumInFrame);
        String btn = getText(sumOnButton);

        assertMoneyEquals(expectedAmount, top, "Неверная сумма в окне оплаты (верх)");
        assertMoneyEquals(expectedAmount, btn, "Неверная сумма на кнопке оплаты");
    }

    public void assertServiceAndPhone(String expectedPhone) {
        String info = getText(serviceAndPhone);

        assertTrue(info.toLowerCase().contains("услуги связи"),
                "Нет текста 'Услуги связи' в окне оплаты");
        assertTrue(info.contains(expectedPhone),
                "В окне оплаты неверный номер телефона");
    }

    public void assertCardFieldLabels() {
        waitVisible(ccNumber);

        assertLabelVisible("Номер карты");
        assertLabelVisible("Срок действия");
        assertLabelVisible("CVC");
        assertLabelVisible("Имя и фамилия на карте");
    }

    private void assertLabelVisible(String text) {
        By label = By.xpath("//*[normalize-space(text())='" + text + "']");
        WebElement el = waitVisible(label);
        assertTrue(el.isDisplayed(), "Лейбл '" + text + "' не отображается");
    }

    public void assertPaymentIconsPresent() {
        assertTrue(waitVisible(visaIcon).isDisplayed(), "Visa не отображается");
        assertTrue(waitVisible(mcIcon).isDisplayed(), "MasterCard не отображается");
        assertTrue(waitVisible(belIcon).isDisplayed(), "Belkart не отображается");

        List<WebElement> icons = findAll(maestroOrMirIcon);
        assertFalse(icons.isEmpty(), "Не найдена иконка Maestro или MIR");
    }

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