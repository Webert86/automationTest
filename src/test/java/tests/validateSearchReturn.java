package tests;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.junit.Assert.assertThat;

public class validateSearchReturn {

    @Before
    public void Setup(){
        acessarGoogle nav;
        nav = new acessarGoogle();
        nav.Setup();
    }

    @Test
    public void testSearchOnAmazon() throws InterruptedException {

        WebDriver navegator = new ChromeDriver();
        navegator.get("https://www.google.com/");
        WebElement text = navegator.findElement(By.cssSelector("[name='q']"));
        text.sendKeys("Amazon");
        text.submit();
        navegator.findElement(By.xpath("//span[contains(.,'www.amazon.com.br/')]")).click();
        WebElement goSearch = navegator.findElement(By.cssSelector("#twotabsearchtextbox"));
        goSearch.sendKeys("Iphone");
        goSearch.submit();
        WebDriverWait wait = new WebDriverWait(navegator, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), '1-48 de mais')]")));

        List<WebElement> produto = navegator.findElements(By.cssSelector(".a-size-base-plus"));
        Thread.sleep(1000);
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
        //System.out.println(contador);
        //System.out.println(contIphone);
        float porcent = ((float) contador / produto.size())*100;
        float porcentIphone = ((float) contIphone / produto.size())*100;
        System.out.println("Quantida de produtos relacionados a Iphone : " + porcent);
        System.out.println("Quantida de Iphone retornados na pesquisa : " + porcentIphone);
        assertThat(porcent, Matchers.greaterThan(80.0f));
        //assertThat(porcentIphone, Matchers.greaterThan(80.0f));
        navegator.quit();
    }

    @Test
    public void testSearchOfDifferent() throws InterruptedException {

        WebDriver navegator = new ChromeDriver();
        navegator.get("https://www.google.com/");
        WebElement text = navegator.findElement(By.cssSelector("[name='q']"));
        text.sendKeys("Amazon");
        text.submit();
        navegator.findElement(By.xpath("//span[contains(.,'www.amazon.com.br/')]")).click();
        WebElement goSearch = navegator.findElement(By.cssSelector("#twotabsearchtextbox"));
        goSearch.sendKeys("Iphone");
        goSearch.submit();
        WebDriverWait wait = new WebDriverWait(navegator, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), '1-48 de mais')]")));

        List<WebElement> produto = navegator.findElements(By.cssSelector(".a-size-base-plus"));
        List<WebElement> listProduto = navegator.findElements(By.cssSelector("span[class='a-price'][data-a-color='base']"));
        String textlist;
        String prclist;
        int seconds=0;
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
