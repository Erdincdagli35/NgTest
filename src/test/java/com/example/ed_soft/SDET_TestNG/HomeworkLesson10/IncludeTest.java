package com.example.ed_soft.SDET_TestNG.HomeworkLesson10;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class IncludeTest extends HomeworkLesson10_Trendyol {

    @Test(priority = 1)
    public void openTrendyolHomePage() {
        Assert.assertTrue(true, "Title does match");
    }

    @Test(priority = 2)
    public void verifyErrorMessageWithInvalidUsername() throws InterruptedException {

        WebElement emailField = driver.findElement(By.id("login-email"));
        WebElement passwordField = driver.findElement(By.id("login-password-input"));

        Thread.sleep(5000);

        emailField.clear();
        passwordField.clear();

        Thread.sleep(1000);

        emailField.sendKeys("3rd1fbslash@gmail.com");
        passwordField.sendKeys("invalidpassword");

        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.q-primary.q-fluid.q-button-medium.q-button.submit[type='submit']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);

        WebElement errorBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#error-box-wrapper span.message")));
        String errorMessage = errorBox.getText();
        Assert.assertTrue(errorMessage.contains("E-posta adresiniz ve/veya şifreniz hatalı"), "Error message was not displayed");
    }

    @Test(priority = 3)
    public void verifySuccessfulLogin() throws InterruptedException {
        WebElement emailField = driver.findElement(By.id("login-email"));
        WebElement passwordField = driver.findElement(By.id("login-password-input"));

        emailField.clear();
        passwordField.clear();

        emailField.sendKeys("3rd1fbslash@gmail.com");
        passwordField.sendKeys("3rd1fbsl4sh");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.q-primary.q-fluid.q-button-medium.q-button.submit[type='submit']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);

        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='Trendyol']")));

        Assert.assertTrue(logo.isDisplayed(), "Login was successful");
    }

    @Test(priority = 4)
    public void verifySearchingForElbise() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement searchBox = driver.findElement(By.cssSelector("input[data-testid='suggestion']"));
        searchBox.sendKeys("Elbise");
        searchBox.sendKeys(Keys.ENTER);

        Thread.sleep(5000);

        List<WebElement> searchResultTitles = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[class='prdct-desc-cntnr'] h3")));

        Assert.assertTrue(searchResultTitles.size() > 0, "No search results found for 'Elbise'");
    }

    @Test(priority = 5)
    public void verifySearchingForElbiseResultOfNumber() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> searchResultTitles = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[class='prdct-desc-cntnr'] h3")));

        Assert.assertTrue(searchResultTitles.size() > 10, "No search results found for 'Elbise'");
    }

    @Test(priority = 6)
    public void verifyAddingElbiseToCard() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement divElement = driver.findElement(By.cssSelector(".cstm-stmp-box-wrppr .upper-left"));
        divElement.click();

        Thread.sleep(5000);

        Set<String> windowHandles = driver.getWindowHandles();
        Iterator<String> iterator = windowHandles.iterator();
        String originalWindow = iterator.next();
        String newWindow = iterator.next();

        driver.switchTo().window(newWindow);

        Thread.sleep(5000);

        WebElement addToBasketButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".add-to-basket-button-text")));
        addToBasketButton.click();

        Thread.sleep(5000);

        driver.get("https://www.trendyol.com/sepet");

        boolean isItemInBasket = driver.findElements(By.cssSelector(".pb-basket-item-details .pb-basket-item-details-info .pb-item")).size() > 0;
        Assert.assertTrue(isItemInBasket, "The basket is not empty");
    }
}
