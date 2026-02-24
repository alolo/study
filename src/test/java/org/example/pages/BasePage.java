package org.example.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        waitClickable(locator).click();
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected void type(By locator, String text) {
        WebElement el = waitVisible(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getAttr(By locator, String attrName) {
        return waitVisible(locator).getAttribute(attrName);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    protected void switchToFrame(By frameLocator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    protected void clickIfPresent(By locator, int seconds) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
            WebElement el = shortWait.until(ExpectedConditions.elementToBeClickable(locator));
            el.click();
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException ignored) {
        }
    }
}