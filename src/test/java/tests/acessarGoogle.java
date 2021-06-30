package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class acessarGoogle {


    @Before
    public void Setup(){
        System.setProperty("webdriver.chrome.driver", "C:\\temp\\Drivers\\chromedriver.exe");
    }

    @Test
    public void testAccessGoogle(){

        WebDriver navegator = new ChromeDriver();
        navegator.get("https://www.google.com/");
        navegator.quit();
    }

    @Test
    public void searchGoogle(){

        WebDriver navegator = new ChromeDriver();
        navegator.get("https://www.google.com/");
        navegator.manage().window().maximize();
        WebElement text = navegator.findElement(By.cssSelector("[name='q']"));
        text.sendKeys("Amazon");
        text.submit();
        WebElement searchResults = navegator.findElement(By.cssSelector("#search"));
        assertTrue(searchResults.isDisplayed());
        navegator.quit();
    }

}
