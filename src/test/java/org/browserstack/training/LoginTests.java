package org.browserstack.training;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests {
    String correctUsername = "student";
    String correctPassword = "Password123";
    String wrongUsername = "wrongstudent";
    String wrongPassword = "Password456";
    String incorrectUsernameMessage = "Your username is invalid!";
    String incorrectPasswordMessage = "Your password is invalid!";
    String incorrectUsermameAndPasswordMessage = "Your username and password are invalid!";

    // ThreadLocal keeps a separate WebDriver instance per thread
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver webDriver = new ChromeDriver(options);
        driver.set(webDriver);
    }

    @AfterMethod
    public void tearDown() {
        getDriver().quit();
        driver.remove();
    }

    @Test
    public void successfulLoginTest() {
        try {
            getDriver().get("https://training-site-2025.pages.dev/");
            getDriver().manage().window().maximize();

            getDriver().findElement(By.cssSelector("#loginButton")).click();

            Thread.sleep(2000); // Wait for 2 seconds to ensure the login form is loaded

            getDriver().findElement(By.cssSelector("#username")).sendKeys(correctUsername);
            getDriver().findElement(By.cssSelector("#password")).sendKeys(correctPassword);
            getDriver().findElement(By.cssSelector("#submitButton")).click();

            Thread.sleep(3000);

            Assert.assertTrue(getDriver().findElement(By.id("success")).isDisplayed(), "Login was not successful");
        } catch (Exception e) {
            System.out.println("There was an error: " + e);
        }
    }

    @Test
    public void failedLoginTestUsername() {
        try {
            getDriver().get("https://training-site-2025.pages.dev/");
            getDriver().manage().window().maximize();

            getDriver().findElement(By.cssSelector("#loginButton")).click();

            getDriver().findElement(By.cssSelector("#username")).sendKeys(wrongUsername);
            getDriver().findElement(By.cssSelector("#password")).sendKeys(correctPassword);
            getDriver().findElement(By.cssSelector("#submitButton")).click();


            Assert.assertEquals(getDriver().findElement(By.id("error")).getText(), incorrectUsernameMessage);
        } catch (Exception e) {
            System.out.println("There was an error: " + e);
        }
    }

    @Test
    public void failedLoginTestPassword() {
        try {
            getDriver().get("https://training-site-2025.pages.dev/");
            getDriver().manage().window().maximize();

            getDriver().findElement(By.cssSelector("#loginButton")).click();

            getDriver().findElement(By.cssSelector("#username")).sendKeys(correctUsername);
            getDriver().findElement(By.cssSelector("#password")).sendKeys(wrongPassword);
            getDriver().findElement(By.cssSelector("#submitButton")).click();

            Assert.assertEquals(getDriver().findElement(By.id("error")).getText(), incorrectPasswordMessage);
        } catch (Exception e) {
            System.out.println("There was an error: " + e);
        }
    }

    @Test
    public void failedLoginTestUsernameAndPassword() {
        try {
            getDriver().get("https://training-site-2025.pages.dev/");
            getDriver().manage().window().maximize();

            getDriver().findElement(By.cssSelector("#loginButton")).click();

            getDriver().findElement(By.cssSelector("#username")).sendKeys(wrongUsername);
            getDriver().findElement(By.cssSelector("#password")).sendKeys(wrongPassword);
            getDriver().findElement(By.cssSelector("#submitButton")).click();

            Assert.assertEquals(getDriver().findElement(By.id("error")).getText(), incorrectUsermameAndPasswordMessage);
        } catch (Exception e) {
            System.out.println("There was an error: " + e);
        }
    }
}
