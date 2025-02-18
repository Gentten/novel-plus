package com.java2nb.novel.core.listener;

import com.java2nb.novel.core.utils.HttpUtil;
import com.java2nb.novel.core.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class QDRankTest {

    @Test
    public void test() {
        // Try-Catch Block For Implementing Sleep Method
        try {
            // String Where Home Page URL Is Stored
            String baseUrl = "https://www.qidian.com/finish/";

            // Implementation of SetProperty Method
            String path = System.getProperty("webdriver.chrome.driver");
            if (StringUtils.isEmpty(path)) {
                System.setProperty("webdriver.chrome.driver", "F:\\code\\opensourcecodes\\novel-plus\\driver\\chromedriver-win64\\chromedriver.exe");
            }
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--no-sandbox");
            chromeOptions.addArguments("--incognito");

            String user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
            chromeOptions.addArguments("user-agent=" + user_agent);
            chromeOptions.setHeadless(true);

            // Creating New Object driver Of Webdriver
            WebDriver driver = new ChromeDriver(chromeOptions);

            // Calling the Home Page By Using Get() Method
            driver.get(baseUrl);

            //driver.get
            WebElement element = driver.findElement(By.cssSelector("body > div.wrap > div.all-pro-wrap.box-center.cf > div.main-content-wrap.fl > div.all-book-list"));
            List<WebElement> list = element.findElements(By.cssSelector("li"));

            for (WebElement li : list) {
                WebElement name = li.findElement(By.cssSelector("div.book-mid-info > h2 > a"));
                System.out.println("name:" + name.getText());

                WebElement author = li.findElement(By.className("name"));
                System.out.println("author:" + author.getText());

                WebElement type = li.findElement(By.cssSelector("div.book-mid-info > p.author > a:nth-child(4)"));
                System.out.println("type:" + type.getText());

                WebElement intro = li.findElement(By.className("intro"));
                System.out.println("intro:" + intro.getText());

                WebElement num = li.findElement(By.cssSelector("div.book-mid-info > p.update > span > span"));
                System.out.println("num:" + num.getText());

            }


            // Closing The Opened Window
            driver.quit();
        } catch (Exception e) {
            // Catching The Exception
            System.out.println(e);
        }
    }

}
