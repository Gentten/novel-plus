package com.java2nb.novel.core.listener;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.java2nb.novel.core.crawl.QDRook;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.*;

public class QDRankTop100Cat8Test {

    @Test
    public void test() {
        // String Where Home Page URL Is Stored
        String baseUrl = "https://www.qdmm.com/rank";
        // Try-Catch Block For Implementing Sleep Method
        List<String> rankTypeList = Arrays.asList("readindex", "recom", "collect", "newfans");

        List<String> categoryList = Arrays.asList("", "chanId80", "chanId81", "chanId82", "chanId83", "chanId84", "chanId85", "chanId86", "chanId88");

        List<String> pageList = Arrays.asList("", "page2", "page3", "page4", "page5");


        // Implementation of SetProperty Method
        String path = System.getProperty("webdriver.chrome.driver");
        if (StringUtils.isEmpty(path)) {
            System.setProperty("webdriver.chrome.driver", "F:\\code\\opensourcecodes\\novel-plus\\novel-crawl\\src\\main\\build\\driver\\chromedriver-win64\\chromedriver.exe");
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

        Map<String, Map<String, List<QDRook>>> data = new HashMap<>();

        // Calling the Home Page By Using Get() Method
        for (String rankType : rankTypeList) {
            String rankUrl = baseUrl + "/" + rankType;
            Map<String, List<QDRook>> rankData = data.computeIfAbsent(rankType, k -> new HashMap<>());
            for (String category : categoryList) {
                try {
                    List<QDRook> ordered = rankData.computeIfAbsent(category, k -> new LinkedList<>());
                    String categoryUrl = rankUrl + "/" + category;
                    //遍历所有的页面
                    for (String page : pageList) {
                        String url = categoryUrl + "/" + page;
                        driver.get(url);
                        List<QDRook> qdRookList = readBooKList(driver);
                        if (CollectionUtils.isEmpty(qdRookList)) {
                            //空的就不需要往后查询了
                            break;
                        }
                        ordered.addAll(qdRookList);
                    }
                } catch (Exception e) {
                    // 停止此类别
                    break;
                }

            }
            FileUtil.appendString(JSONUtil.toJsonPrettyStr(rankData), rankType + ".json", Charset.defaultCharset());
        }
        FileUtil.appendString(JSONUtil.toJsonPrettyStr(data), "rank.json", Charset.defaultCharset());
        // Closing The Opened Window
        driver.quit();
    }

    /**
     * 读取包含的书籍信息
     *
     * @param driver the driver
     * @return 读取的数据列表
     */
    private List<QDRook> readBooKList(WebDriver driver) {
        WebElement element = driver.findElement(By.id("book-img-text"));
        List<QDRook> bookList = new LinkedList<>();
        List<WebElement> list = element.findElements(By.cssSelector("li"));
        for (WebElement li : list) {
            QDRook qdRook = new QDRook();
            WebElement name = li.findElement(By.cssSelector("div.book-mid-info > h2 > a"));
            qdRook.setName(name.getText());

            WebElement author = li.findElement(By.className("name"));
            qdRook.setAuthor(author.getText());

            //#book-img-text > ul > li:nth-child(1) > div.book-mid-info > p.author > a:nth-child(4)
            WebElement type1 = li.findElement(By.cssSelector("div.book-mid-info > p.author > a:nth-child(4)"));
            qdRook.setCategory1(type1.getText());

            //#book-img-text > ul > li:nth-child(1) > div.book-mid-info > p.author > a.go-sub-type
            WebElement type2 = li.findElement(By.cssSelector("div.book-mid-info > p.author > a.go-sub-type"));
            qdRook.setCategory2(type2.getText());

            WebElement intro = li.findElement(By.className("intro"));
            qdRook.setDescription(intro.getText());

            // div.book-img-box > a > img
            WebElement image = li.findElement(By.cssSelector("div.book-img-box > a > img"));
            qdRook.setImage(image.getAttribute("src"));


            WebElement status = li.findElement(By.cssSelector("div.book-mid-info > p.author > span"));
            qdRook.setStatus(status.getText());

            qdRook.setType("女生");

            bookList.add(qdRook);
        }
        return bookList;
    }

}
