package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MtsTopUpBlockPage extends BasePage {

    private final By blockRoot = By.xpath("//*[@id='pay-section']/div/div/div[2]/section/div");

    private final By cookieAgree = By.xpath("//*[@id='cookie-agree']");

    private final By continueButtonConnection = By.xpath("//*[@id='pay-connection']/button");

    public MtsTopUpBlockPage(WebDriver driver) {
        super(driver);
    }

    public MtsTopUpBlockPage open() {
        driver.get("https://www.mts.by/");
        closeCookieIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(blockRoot));
        return this;
    }

    public MtsTopUpBlockPage selectPaymentType(String typeText) {

        // Кнопка "шапка" селекта
        By dropdownHeader = By.xpath(
                "//*[@id='pay-section']//div[contains(@class,'select__wrapper')]//button[contains(@class,'select__header')]"
        );

        click(dropdownHeader);

        // Сам список
        By dropdownList = By.xpath(
                "//*[@id='pay-section']//div[contains(@class,'select__wrapper')]//ul[contains(@class,'select__list')]"
        );

        waitVisible(dropdownList);

        // ВАЖНО: выбираем li по тексту внутри p.select__option
        By optionLi = By.xpath(
                "//*[@id='pay-section']//div[contains(@class,'select__wrapper')]//ul[contains(@class,'select__list')]"
                        + "//li[.//p[contains(@class,'select__option') and normalize-space(.)='" + typeText + "']]"
        );

        WebElement li = waitVisible(optionLi);

        // Скроллим, чтобы элемент был в зоне клика
        scrollIntoView(li);

        // Пытаемся обычный click, если перехват — JS click
        try {
            wait.until(ExpectedConditions.elementToBeClickable(li)).click();
        } catch (ElementClickInterceptedException e) {
            jsClick(li);
        }

        // Ждём, что выбранное значение реально поменялось
        By selectedNow = By.xpath(
                "//*[@id='pay-section']//div[contains(@class,'select__wrapper')]//span[contains(@class,'select__now')]"
        );
        wait.until(ExpectedConditions.textToBePresentInElementLocated(selectedNow, typeText));

        return this;
    }

    public MtsTopUpBlockPage fillConnectionForm(String phone, String sum, String email) {
        type(By.id("connection-phone"), phone);
        type(By.id("connection-sum"), sum);

        // email в "Услуги связи" необязательное, но в задании про плейсхолдер — мы проверяем его тоже
        type(By.id("connection-email"), email);

        return this;
    }

    public MtsTopUpBlockPage clickContinueForConnection() {
        click(continueButtonConnection);
        return this;
    }

    /**
     * Проверка плейсхолдеров для каждого типа оплаты.
     * Здесь всё в одном месте (не надо 4 отдельных PageObject).
     */
    public void assertPlaceholdersFor(String paymentType) {
        selectPaymentType(paymentType);

        switch (paymentType) {
            case "Услуги связи":
                assertEquals("Номер телефона", getPlaceholder(By.id("connection-phone")),
                        "Неверный placeholder (Услуги связи) - номер телефона");
                assertEquals("Сумма", getPlaceholder(By.id("connection-sum")),
                        "Неверный placeholder (Услуги связи) - сумма");
                assertEquals("E-mail для отправки чека", getPlaceholder(By.id("connection-email")),
                        "Неверный placeholder (Услуги связи) - email");
                break;

            case "Домашний интернет":
                assertEquals("Номер абонента", getPlaceholder(By.id("internet-phone")),
                        "Неверный placeholder (Домашний интернет) - номер абонента");
                assertEquals("Сумма", getPlaceholder(By.id("internet-sum")),
                        "Неверный placeholder (Домашний интернет) - сумма");
                assertEquals("E-mail для отправки чека", getPlaceholder(By.id("internet-email")),
                        "Неверный placeholder (Домашний интернет) - email");
                break;

            case "Рассрочка":
                assertEquals("Номер счета на 44", getPlaceholder(By.id("score-instalment")),
                        "Неверный placeholder (Рассрочка) - номер счета");
                assertEquals("Сумма", getPlaceholder(By.id("instalment-sum")),
                        "Неверный placeholder (Рассрочка) - сумма");
                assertEquals("E-mail для отправки чека", getPlaceholder(By.id("instalment-email")),
                        "Неверный placeholder (Рассрочка) - email");
                break;

            case "Задолженность":
                assertEquals("Номер счета на 2073", getPlaceholder(By.id("score-arrears")),
                        "Неверный placeholder (Задолженность) - номер счета");
                assertEquals("Сумма", getPlaceholder(By.id("arrears-sum")),
                        "Неверный placeholder (Задолженность) - сумма");
                assertEquals("E-mail для отправки чека", getPlaceholder(By.id("arrears-email")),
                        "Неверный placeholder (Задолженность) - email");
                break;

            default:
                throw new IllegalArgumentException("Неизвестный тип оплаты: " + paymentType);
        }
    }

    private String getPlaceholder(By locator) {
        return getAttr(locator, "placeholder");
    }

    private void closeCookieIfPresent() {
        // НИЧЕГО не ломаем: просто используем общий метод
        clickIfPresent(cookieAgree, 3);
    }
}