package tests;


import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

//import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FluxBuys {

    @Before
    public void Setup(){
        //Preparations
        System.setProperty("webdriver.chrome.driver", "C:\\temp\\Drivers\\chromedriver.exe");
        //WebDriver navegator = new ChromeDriver();

    }

    @Test
    public void testAccessGoogle(){
        //mapping
        WebDriver navegator = new ChromeDriver();
        navegator.get("https://www.google.com/");
        WebElement text = navegator.findElement(By.cssSelector("[name='q']"));
        text.sendKeys("Amazon");
        text.submit();
        //Acctions
        WebElement searchResults = navegator.findElement(By.cssSelector("#search"));
        assertTrue(searchResults.isDisplayed());
        assertThat(navegator.getTitle(),containsString("Amazon"));
        navegator.quit();
    }

    @Test
    public void testAccessAmazon(){
        //Preparations
        WebDriver navegator = new ChromeDriver();
        navegator.get("https://www.google.com/");

        //mapping
        WebElement text = navegator.findElement(By.cssSelector("[name='q']"));
        text.sendKeys("Amazon");
        text.submit();
        navegator.findElement(By.xpath("//span[contains(.,'www.amazon.com.br/')]")).click();

        //Acctions

        WebElement searchResults = navegator.findElement(By.cssSelector("#nav-search"));
        assertTrue(searchResults.isDisplayed());
        navegator.quit();
    }

    @Test
    public void testSearchOnAmazon(){
        //Preparations
        WebDriver navegator = new ChromeDriver();
        navegator.get("https://www.google.com/");
        //mapping
        WebElement text = navegator.findElement(By.cssSelector("[name='q']"));
        text.sendKeys("Amazon");
        text.submit();
        navegator.findElement(By.xpath("//span[contains(.,'www.amazon.com.br/')]")).click();
        WebElement goSearch = navegator.findElement(By.cssSelector("#twotabsearchtextbox"));
        goSearch.sendKeys("Iphone");
        goSearch.submit();
        WebDriverWait wait = new WebDriverWait(navegator, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), '1-48 de mais')]")));

        //Acctions
        List<WebElement> produto = navegator.findElements(By.cssSelector(".a-size-base-plus"));
        //List<WebElement> Iphone = navegator.findElements(By.xpath("//span[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'gb')]"));
        System.out.println(produto.size());
        int contador = 0;
        String textlist;
        for(int i=0; i<produto.size(); i++)
        {
            textlist = produto.get(i).getText().toUpperCase();
            if(textlist.contains("IPHONE"))
            {
                contador++;
            }
        }
        System.out.println(contador);
        float porcent = ((float) contador / produto.size())*100;
        System.out.println(porcent);
        assertThat(porcent, Matchers.greaterThan(80.0f));
        navegator.quit();
    }

}
