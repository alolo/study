package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.example.pages.MtsTopUpBlockPage;
import org.example.pages.PaymentIframePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.SeverityLevel.*;

@DisplayName("Тестирование функции 'оплата без комиссии'")
@Link(name = "MTS", url = "https://mts.by/")
@Owner("Алексей Санин")
@Severity(CRITICAL)
public class MtsReplenishmentPageObjectTests {

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void teardown() {
        if (driver != null) driver.quit();
    }


    @Test
    @Step("Проверка переключений назначения платежа")
    void shouldCheckPlaceholdersForAllPaymentTypes() {
        MtsTopUpBlockPage page = new MtsTopUpBlockPage(driver).open();

        page.assertPlaceholdersFor("Услуги связи");
        page.assertPlaceholdersFor("Домашний интернет");
        page.assertPlaceholdersFor("Рассрочка");
        page.assertPlaceholdersFor("Задолженность");
    }


    @Test
    @Step("Проверка оплаты 'Услуги связи'")
    void shouldCheckConnectionPaymentFlowAndIframeData() {
        String phone = "297777777";
        String sum = "10";
        String email = "what@gmail.com";

        MtsTopUpBlockPage page = new MtsTopUpBlockPage(driver).open();

        page.selectPaymentType("Услуги связи")
                .fillConnectionForm(phone, sum, email)
                .clickContinueForConnection();

        PaymentIframePage iframe = new PaymentIframePage(driver);

        iframe.switchTo();
        try {
            iframe.assertOpened();
            iframe.assertMoney(sum);
            iframe.assertServiceAndPhone(phone);
            iframe.assertCardFieldLabels();
            iframe.assertPaymentIconsPresent();
        } finally {
            iframe.switchBack();
        }
    }
}