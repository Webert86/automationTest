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

public class SearchReturn {

    @Before
    public void Setup(){
        //Preparations
        System.setProperty("webdriver.chrome.driver", "C:\\temp\\Drivers\\chromedriver.exe");
        //WebDriver navegator = new ChromeDriver();

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
        System.out.println(produto.size());
        int contador = 0;
        int contIphone = 0;
        String textlist;
        for(int i=0; i<produto.size(); i++)
        {
            textlist = produto.get(i).getText().toUpperCase();
            if(textlist.contains("IPHONE"))
            {
                contador++;
            }
            if(textlist.contains("GB"))
            {
                contIphone++;
            }
        }
        System.out.println(contador);
        System.out.println(contIphone);
        float porcent = ((float) contador / produto.size())*100;
        float porcentIphone = ((float) contIphone / produto.size())*100;
        System.out.println(porcent);
        System.out.println(porcentIphone);
        assertThat(porcent, Matchers.greaterThan(80.0f));
        navegator.quit();
    }

    @Test
    public void testSearchOfDifferent() throws InterruptedException {
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
        List<WebElement> listProduto = navegator.findElements(By.cssSelector("span[class='a-price'][data-a-color='base']"));
        int noIphone = 0;
        String textlist;
        String prclist;int seconds=0;
        int contador = produto.size();

        while (contador != 48)
        {
            produto = navegator.findElements(By.cssSelector("span[class='a-price'][data-a-color='base']"));
            contador = produto.size();
            Thread.sleep(1000);
            if (seconds > 10)
            {
                contador = 48;
            }seconds++;
        }

        for(int i=0; i<produto.size(); i++)
        {
            textlist = produto.get(i).getText().toUpperCase();
            prclist = listProduto.get(i).getText();
            float cont = convertsMoedaEmFloat(prclist);
            float conta = convertsMoedaEmFloat(textlist);

            while (textlist.contains("IPHONE"))
            {
                assertThat(conta, Matchers.greaterThan(cont));
            }
        }
        navegator.quit();
    }
    public float convertsMoedaEmFloat(String moeda){
        moeda =  moeda.split("R\\$")[1]
                .trim()
                .replace(".","")
                .replace(",",".").split("\\n")[0]
        ;

        return Float.parseFloat(moeda);

    }
}
