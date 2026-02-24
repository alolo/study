package org.example.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pages.MtsTopUpBlockPage;
import org.example.pages.PaymentIframePage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
    void shouldCheckPlaceholdersForAllPaymentTypes() {
        MtsTopUpBlockPage page = new MtsTopUpBlockPage(driver).open();

        page.assertPlaceholdersFor("Услуги связи");
        page.assertPlaceholdersFor("Домашний интернет");
        page.assertPlaceholdersFor("Рассрочка");
        page.assertPlaceholdersFor("Задолженность");
    }

    @Test
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