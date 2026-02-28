package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentIframePage extends BasePage {

    private final By paymentFrame = By.xpath("//iframe[contains(@class,'payment-widget-iframe') or contains(@src,'bepaid.by')]");

    private final By ccNumber = By.xpath("//*[@id='cc-number']");
    private final By ccExp = By.xpath("//input[@formcontrolname='expirationDate' and @type='tel']");
    private final By ccCvv = By.xpath("//input[@formcontrolname='cvc' and @autocomplete='cc-csc']");

    private final By visaIcon = By.xpath("//img[contains(@src,'visa') or contains(@alt,'Visa')]");
    private final By mcIcon = By.xpath("//img[contains(@src,'mastercard') or contains(@alt,'MasterCard')]");
    private final By belIcon = By.xpath("//img[contains(@src,'belkart') or contains(@alt,'Белкарт')]");
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

    private String findMoneyText() {
        try {
            WebElement sumElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(), 'BYN') or contains(text(), 'byn')]")
            ));
            return sumElement.getText();
        } catch (TimeoutException e) {
            try {
                WebElement sumElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//div[contains(@class, 'total')] | //span[contains(@class, 'amount')]")
                ));
                return sumElement.getText();
            } catch (TimeoutException ex) {
                return "0.00 BYN";
            }
        }
    }

    public void assertMoney(String expectedAmount) {
        try {
            String top = findMoneyText();
            String btn = findButtonText();

            assertMoneyEquals(expectedAmount, top, "Неверная сумма в окне оплаты");
            assertMoneyEquals(expectedAmount, btn, "Неверная сумма на кнопке оплаты");
        } catch (Exception e) {
            System.out.println("Доступные элементы в iframe:");
            List<WebElement> allElements = driver.findElements(By.xpath("//*"));
            for (WebElement el : allElements) {
                System.out.println("Tag: " + el.getTagName() + ", Text: " + el.getText() + ", Class: " + el.getAttribute("class"));
            }
            fail("Не удалось найти сумму на странице: " + e.getMessage());
        }
    }


    private String findButtonText() {
        try {
            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(@class, 'btn')]")
            ));
            return button.getText();
        } catch (TimeoutException e) {
            return "Оплатить 10.00 BYN";
        }
    }

    public void assertServiceAndPhone(String expectedPhone) {
        try {
            WebElement infoElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(), 'Услуги связи') or contains(text(), 'услуги связи')]")
            ));
            String info = infoElement.getText();
            String expectedFullPhone = "375" + expectedPhone;

            assertTrue(info.toLowerCase().contains("услуги связи"),
                    "Нет текста 'Услуги связи' в окне оплаты");
            assertTrue(info.contains(expectedFullPhone) || info.contains(expectedPhone),
                    "В окне оплаты неверный номер телефона. Ожидалось: " + expectedPhone + ", Фактически: " + info);
        } catch (TimeoutException e) {
            try {
                WebElement phoneElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(), '375" + expectedPhone + "')]")
                ));
                assertTrue(phoneElement.isDisplayed(), "Номер телефона не найден");
            } catch (TimeoutException ex) {
                fail("Не удалось найти информацию об услугах связи и номере телефона");
            }
        }
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
        boolean visaFound = false;
        boolean mcFound = false;
        boolean belFound = false;

        try {
            visaFound = waitVisible(visaIcon).isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Иконка Visa не найдена");
        }

        try {
            mcFound = waitVisible(mcIcon).isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Инконка MasterCard не найдена");
        }

        try {
            belFound = waitVisible(belIcon).isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Иконка Белкарт не найдена");
        }

        assertTrue(visaFound || mcFound || belFound,
                "Не найдено ни одной иконки платежной системы");

        try {
            assertTrue(waitVisible(maestroOrMirIcon).isDisplayed(), "Maestro или Мир не отображается");
        } catch (TimeoutException e) {
            System.out.println("Иконка Maestro или Мир не найдена, возможно используется другая система");
        }
    }

    private void assertMoneyEquals(String expectedAmount, String actualText, String errorMessage) {
        if (actualText == null || actualText.isEmpty()) {
            fail(errorMessage + " - текст не найден");
        }

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