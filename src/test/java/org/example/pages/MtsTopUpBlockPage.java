package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MtsTopUpBlockPage extends BasePage {

    private final By blockRoot = By.xpath("//*[@id='pay-section']/div/div/div[2]/section/div");
    private final By blockTitle = By.xpath("//div[@class='pay__wrapper']//h2[normalize-space()='Онлайн пополнение без комиссии']");

    private final By cookieAgree = By.xpath("//*[@id='cookie-agree']");

    private final By continueButtonConnection = By.xpath("//*[@id='pay-connection']/button");

    // Логотипы платежных систем в основном блоке
    private final By visaLogo = By.xpath("//img[@alt='Visa']");
    private final By verifiedByVisaLogo = By.xpath("//img[@alt='Verified By Visa']");
    private final By mastercardLogo = By.xpath("//img[@alt='MasterCard']");
    private final By mastercardSecureLogo = By.xpath("//img[@alt='MasterCard Secure Code']");
    private final By belkartLogo = By.xpath("//img[@alt='Белкарт']");

    // Ссылка "Подробнее о сервисе"
    private final By detailsLink = By.xpath("//a[contains(@href, 'poryadok-oplaty')]");

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

        By dropdownHeader = By.xpath(
                "//*[@id='pay-section']//div[contains(@class,'select__wrapper')]//button[contains(@class,'select__header')]"
        );

        click(dropdownHeader);

        By dropdownList = By.xpath(
                "//*[@id='pay-section']//div[contains(@class,'select__wrapper')]//ul[contains(@class,'select__list')]"
        );

        waitVisible(dropdownList);

        By optionLi = By.xpath(
                "//*[@id='pay-section']//div[contains(@class,'select__wrapper')]//ul[contains(@class,'select__list')]"
                        + "//li[.//p[contains(@class,'select__option') and normalize-space(.)='" + typeText + "']]"
        );

        WebElement li = waitVisible(optionLi);

        scrollIntoView(li);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(li)).click();
        } catch (ElementClickInterceptedException e) {
            jsClick(li);
        }

        By selectedNow = By.xpath(
                "//*[@id='pay-section']//div[contains(@class,'select__wrapper')]//span[contains(@class,'select__now')]"
        );
        wait.until(ExpectedConditions.textToBePresentInElementLocated(selectedNow, typeText));

        return this;
    }

    public MtsTopUpBlockPage fillConnectionForm(String phone, String sum, String email) {
        type(By.id("connection-phone"), phone);
        type(By.id("connection-sum"), sum);
        type(By.id("connection-email"), email);
        return this;
    }

    public MtsTopUpBlockPage clickContinueForConnection() {
        click(continueButtonConnection);
        return this;
    }

    public void assertBlockTitle() {
        String actualTitle = getText(blockTitle).trim();
        String expectedTitle = "Онлайн пополнение\nбез комиссии";

        assertEquals(expectedTitle, actualTitle,
                String.format("Текст заголовка не совпадает.%nОжидалось: '%s'%nФактически: '%s'",
                        expectedTitle, actualTitle));

        WebElement block = driver.findElement(By.xpath("//div[@class='pay__wrapper']"));
        assertTrue(block.isDisplayed(), "Блок оплаты не отображается");
    }

    public void assertPaymentLogos() {
        assertTrue(waitVisible(visaLogo).isDisplayed(), "Логотип Visa не найден");
        assertTrue(waitVisible(verifiedByVisaLogo).isDisplayed(), "Логотип Verified By Visa не найден");
        assertTrue(waitVisible(mastercardLogo).isDisplayed(), "Логотип MasterCard не найден");
        assertTrue(waitVisible(mastercardSecureLogo).isDisplayed(), "Логотип MasterCard Secure Code не найден");
        assertTrue(waitVisible(belkartLogo).isDisplayed(), "Логотип Белкарт не найден");
    }

    public void assertDetailsLinkWorks() {
        String oldUrl = driver.getCurrentUrl();
        click(detailsLink);

        wait.until(driver -> !driver.getCurrentUrl().equals(oldUrl));

        assertTrue(!driver.getCurrentUrl().equals(oldUrl),
                "Ссылка 'Подробнее о сервисе' не сработала");
    }

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
        clickIfPresent(cookieAgree, 3);
    }
}