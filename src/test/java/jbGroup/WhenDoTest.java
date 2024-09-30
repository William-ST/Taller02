package jbGroup;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class WhenDoTest {

    private AppiumDriver android;

    @BeforeEach
    public void setup() throws MalformedURLException {
        DesiredCapabilities config = new DesiredCapabilities();
        config.setCapability("appium:deviceName", "Redmi Note 13");
        config.setCapability("appium:platformVersion", "14");
        config.setCapability("appium:appPackage", "com.vrproductiveapps.whendo");
        config.setCapability("appium:appActivity", "com.vrproductiveapps.whendo.ui.HomeActivity");
        config.setCapability("platformName", "Android");
        config.setCapability("appium:automationName", "uiautomator2");
        android = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), config);
        android.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    public void searchExistTaskByTitle() throws InterruptedException {
        int countTask = 1;
        String taskTitle = "JBGroup ";
        String taskNote = "Desarrollar y entregar taller";
        android.findElement(By.id("com.vrproductiveapps.whendo:id/fab")).click();
        android.findElement(By.id("com.vrproductiveapps.whendo:id/noteTextTitle")).sendKeys(taskTitle+countTask);
        android.findElement(By.id("com.vrproductiveapps.whendo:id/noteTextNotes")).sendKeys(taskNote);
        android.findElement(By.id("com.vrproductiveapps.whendo:id/saveItem")).click();
        countTask++;

        android.findElement(By.id("com.vrproductiveapps.whendo:id/fab")).click();
        android.findElement(By.id("com.vrproductiveapps.whendo:id/noteTextTitle")).sendKeys(taskTitle+countTask);
        android.findElement(By.id("com.vrproductiveapps.whendo:id/noteTextNotes")).sendKeys(taskNote);
        android.findElement(By.id("com.vrproductiveapps.whendo:id/saveItem")).click();

        android.findElement(By.id("com.vrproductiveapps.whendo:id/search")).click();
        android.findElement(By.id("com.vrproductiveapps.whendo:id/search_src_text")).sendKeys(taskTitle);
        android.executeScript("mobile:performEditorAction", ImmutableMap.of("action", "done"));

        int expectedResult = 2;
        int actualResult = android.findElements(By.id("com.vrproductiveapps.whendo:id/search_list_item_text")).size();

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @AfterEach
    public void closeApp() {
        android.quit();
    }

}

