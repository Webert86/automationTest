package tests;
import io.restassured.RestAssured;
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

public class compareValueIphoneSearched {

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
        navegator.manage().window().maximize();
        WebElement text = navegator.findElement(By.cssSelector("[name='q']"));
        text.sendKeys("Amazon");
        text.submit();
        navegator.findElement(By.xpath("//span[contains(.,'www.amazon.com.br/')]")).click();
        WebElement goSearch = navegator.findElement(By.cssSelector("#twotabsearchtextbox"));
        goSearch.sendKeys("Iphone");
        goSearch.submit();
        WebDriverWait wait = new WebDriverWait(navegator, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), '1-48 de mais')]")));

        List<WebElement> produto = navegator.findElements(By.cssSelector("span[class='a-price'][data-a-color='base']"));
        float dollarConvert;
        float contValida = 0;
        String textlist;
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
            textlist = produto.get(i).getText();
            float cont = convertMoedaEmFloat(textlist);

           if(contValida < cont)
            {
                contValida = cont;
            }
        }

        System.out.println("Produto mais caro da loja é : " + " " + contValida);
        String apiDollar = RestAssured.given().get("https://economia.awesomeapi.com.br/json/last/USD-BRL")
                                             .then().extract().path("USDBRL.bid");
        System.out.println(apiDollar);
        dollarConvert = Float.parseFloat(apiDollar) * 2000;
        System.out.println(dollarConvert);
        assertThat(dollarConvert, Matchers.greaterThan(contValida));
    }

    public float convertMoedaEmFloat(String moeda){
        moeda =  moeda.split("R\\$")[1]
                .trim()
                .replace(".","")
                .replace(",",".").split("\\n")[0]
        ;
        return Float.parseFloat(moeda);
    }
}
