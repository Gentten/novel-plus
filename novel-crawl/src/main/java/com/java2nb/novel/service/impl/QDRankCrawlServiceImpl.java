package com.java2nb.novel.service.impl;

import cn.hutool.core.date.DateUtil;
import com.java2nb.novel.core.crawl.QDRook;
import com.java2nb.novel.core.crawl.RankValue;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.entity.BookRank;
import com.java2nb.novel.mapper.BookDynamicSqlSupport;
import com.java2nb.novel.mapper.BookRankMapper;
import com.java2nb.novel.mapper.CrawlBookMapper;
import com.java2nb.novel.service.QDRankCrawlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Service
@Slf4j
public class QDRankCrawlServiceImpl implements QDRankCrawlService {

    @Resource
    private BookRankMapper bookRankMapper;

    @Resource
    private CrawlBookMapper crawlBookMapper;


    @Override
    public List<QDRook> crawlRankBooks(String channel, String rankType) {

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

        String baseUrl = "https://www.qdmm.com/rank";

        // Creating New Object driver Of Webdriver
        WebDriver driver = new ChromeDriver(chromeOptions);

        // Calling the Home Page By Using Get() Method
        List<QDRook> ordered = new LinkedList<>();
        String rankUrl = baseUrl + "/" + rankType;

        String categoryUrl = rankUrl + "/" + channel;
        try {
            AtomicInteger order = new AtomicInteger(1);
            //遍历所有的页面
            for (String page : pageList) {
                String url = categoryUrl + "/" + page;
                log.info("正在爬取：{}", url);
                driver.get(url);
                List<QDRook> bookList = readBooKList(driver, order, rankType, channel);
                if (CollectionUtils.isEmpty(bookList)) {
                    //空的就不需要往后查询了
                    break;
                }
                ordered.addAll(bookList);
            }
        } finally {
            driver.quit();
        }


        return ordered;
    }

    @Override
    public void saveQDBook(List<QDRook> qdRookList) {
        for (QDRook qdRook : qdRookList) {
            //1、先查询
            Optional<Book> book = crawlBookMapper.selectOne(s -> s.where(BookDynamicSqlSupport.authorName, isEqualTo(qdRook.getAuthor())).and(BookDynamicSqlSupport.bookName, isEqualTo(qdRook.getName())));

            Book newData = toBook(qdRook);
            //不存在插入
            if (!book.isPresent()) {
                crawlBookMapper.insertSelective(newData);
            } else {
                //存在就更新
                Book old = book.get();
                //<img class="lazyload" src="/images/default.gif" data-src="/images/default.gif" alt="名门喜事"> 当图片连接不是/images/default.gif 就不更新了
                if (!"/images/default.gif".equals(old.getPicUrl())) {
                    newData.setPicUrl(null);
                }
                newData.setId(old.getId());
                crawlBookMapper.updateByPrimaryKeySelective(newData);
            }
            book = crawlBookMapper.selectOne(s -> s.where(BookDynamicSqlSupport.authorName, isEqualTo(qdRook.getAuthor())).and(BookDynamicSqlSupport.bookName, isEqualTo(qdRook.getName())));

            //插入排名
            BookRank bookRank = new BookRank();
            RankValue rankValue = qdRook.getRankValue();
            bookRank.setBookId(book.map(Book::getId).orElse(null));
            bookRank.setRankDate(DateUtil.formatDate(new Date()));
            bookRank.setRankOrder(rankValue.getOrder());
            bookRank.setRankType(rankValue.getRankType());
            bookRank.setExtInfo(rankValue.getFront());
            bookRank.setRankValue(rankValue.getValue());
            bookRank.setCatId(map2CatId(rankValue.getCat()));
            bookRank.setBookName(qdRook.getName());
            bookRank.setAuthor(qdRook.getAuthor());
            bookRankMapper.insert(bookRank);
        }
    }

    private Book toBook(QDRook qdRook) {
        Book book = new Book();
        book.setBookName(qdRook.getName());
        book.setWorkDirection((byte) 1);
        book.setCatId(map2CatId(qdRook.getCategory1()));
        book.setCat2Id(map2CatId(qdRook.getCategory2()));
        book.setCatName(qdRook.getCategory1());
        book.setPicUrl(qdRook.getImage());
        book.setAuthorName(qdRook.getAuthor());
        book.setScore(9.9F);
        book.setCrawlSourceId(5);
        book.setBookDesc(qdRook.getDescription());
        book.setBookStatus("完本".equals(qdRook.getStatus()) ? (byte) 1 : (byte) 0);
        book.setLastIndexUpdateTime(new Date());
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        return book;
    }

    private Integer map2CatId(String category) {
        if ("古代言情".equals(category)) return 100;
        if ("古代情缘".equals(category)) return 101;
        if ("宫闱宅斗".equals(category)) return 102;
        if ("经商种田".equals(category)) return 103;
        if ("古典架空".equals(category)) return 104;
        if ("女尊王朝".equals(category)) return 105;
        if ("穿越奇情".equals(category)) return 106;
        if ("西方时空".equals(category)) return 107;
        if ("清穿民国".equals(category)) return 108;
        if ("上古蛮荒".equals(category)) return 109;
        if ("热血江湖".equals(category)) return 110;

        if ("仙侠奇缘".equals(category)) return 200;
        if ("武侠情缘".equals(category)) return 201;
        if ("古典仙侠".equals(category)) return 202;
        if ("现代修真".equals(category)) return 203;
        if ("远古洪荒".equals(category)) return 204;
        if ("仙侣奇缘".equals(category)) return 205;


        if ("现代言情".equals(category)) return 300;
        if ("商战职场".equals(category)) return 301;
        if ("豪门世家".equals(category)) return 302;
        if ("都市生活".equals(category)) return 303;
        if ("婚恋情缘".equals(category)) return 304;
        if ("娱乐明星".equals(category)) return 305;
        if ("都市异能".equals(category)) return 306;
        if ("极道江湖".equals(category)) return 307;
        if ("民国情缘".equals(category)) return 308;
        if ("异国情缘".equals(category)) return 309;


        if ("浪漫青春".equals(category)) return 400;
        if ("青春校园".equals(category)) return 401;
        if ("青春疼痛".equals(category)) return 402;
        if ("叛逆成长".equals(category)) return 403;
        if ("青春纯爱".equals(category)) return 404;


        if ("玄幻言情".equals(category)) return 500;
        if ("东方玄幻".equals(category)) return 501;
        if ("异世大陆".equals(category)) return 502;
        if ("西方奇幻".equals(category)) return 503;
        if ("远古神话".equals(category)) return 504;
        if ("异族恋情".equals(category)) return 505;
        if ("魔法幻情".equals(category)) return 506;
        if ("异能超术".equals(category)) return 507;


        if ("悬疑推理".equals(category)) return 600;
        if ("推理侦探".equals(category)) return 601;
        if ("诡秘惊险".equals(category)) return 602;
        if ("悬疑探险".equals(category)) return 603;
        if ("奇妙世界".equals(category)) return 604;
        if ("神秘文化".equals(category)) return 605;
        if ("幽情奇缘".equals(category)) return 606;

        if ("科幻空间".equals(category)) return 700;
        if ("星际恋歌".equals(category)) return 701;
        if ("时空穿梭".equals(category)) return 702;
        if ("未来世界".equals(category)) return 703;
        if ("古武机甲".equals(category)) return 704;
        if ("超级科技".equals(category)) return 705;
        if ("进化变异".equals(category)) return 706;
        if ("末世危机".equals(category)) return 707;


        if ("游戏竞技".equals(category)) return 800;
        if ("电子竞技".equals(category)) return 801;
        if ("网游情缘".equals(category)) return 802;
        if ("游戏异界".equals(category)) return 803;
        if ("体育竞技".equals(category)) return 804;

        // 表示全部
        return 0;
    }


    /**
     * 读取包含的书籍信息
     *
     * @param driver  the driver
     * @param order   排序
     * @param channel
     * @return 读取的数据列表
     */
    private List<QDRook> readBooKList(WebDriver driver, AtomicInteger order, String rankType, String channel) {
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


            RankValue rankValue = new RankValue();
            rankValue.setRankType(rankType);
            rankValue.setOrder(order.getAndIncrement());
            //#book-img-text > ul > li:nth-child(20) > div.book-right-info > div > p
            try {
                WebElement total = li.findElement(By.className("total"));
                WebElement p = total.findElement(By.cssSelector("p"));
                rankValue.setValue(p.getText());
                //#book-img-text > ul > li:nth-child(20) > div.book-right-info > div > p > span > style
                rankValue.setFront(total.getAttribute("outerHTML"));
            } catch (Exception e) {
                //#book-img-text > ul > li:nth-child
            }

            if (StringUtils.isEmpty(channel)) {
                rankValue.setCat("全部");
            } else {
                rankValue.setCat(qdRook.getCategory1());
            }

            qdRook.setRankValue(rankValue);
            bookList.add(qdRook);
        }
        return bookList;
    }
}
