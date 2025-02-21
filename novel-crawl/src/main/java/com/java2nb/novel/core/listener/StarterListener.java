package com.java2nb.novel.core.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java2nb.novel.core.crawl.CrawlParser;
import com.java2nb.novel.core.crawl.RuleBean;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.entity.BookIndex;
import com.java2nb.novel.entity.CrawlSingleTask;
import com.java2nb.novel.entity.CrawlSource;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.service.CrawlService;
import com.java2nb.novel.utils.Constants;
import com.java2nb.novel.utils.CrawlHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;


import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 */
@WebListener
@Slf4j
public class StarterListener implements ServletContextListener {
    @Resource
    private BookService bookService;

    @Resource
    private CrawlService crawlService;
    @Resource
    private CrawlParser crawlParser;

    @Value("${crawl.update.thread}")
    private int updateThreadCount;

    @Resource
    private CrawlHttpClient crawlHttpClient;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        for (int i = 0; i < updateThreadCount; i++) {
            new Thread(() -> {
                log.info("程序启动,开始执行自动更新线程。。。");
                while (true) {
                    try {
                        //1.查询最新目录更新时间在一个月之内的前100条需要更新的数据
                        Date currentDate = new Date();
                        Date startDate = DateUtils.addDays(currentDate, -30);
                        List<Book> bookList;
                        synchronized (this) {
                            bookList = bookService.queryNeedUpdateBook(startDate, 100);
                        }
                        for (Book needUpdateBook : bookList) {
                            try {
                                //没有找到书籍爬虫源
                                if (needUpdateBook.getCrawlSourceId() == null) {
                                    continue;
                                }

                                //查询爬虫源规则
                                CrawlSource source = crawlService.queryCrawlSource(needUpdateBook.getCrawlSourceId());
                                RuleBean ruleBean = new ObjectMapper().readValue(source.getCrawlRule(), RuleBean.class);
                                if (StringUtils.isEmpty(needUpdateBook.getCrawlBookId())) {
                                    log.info("尝试查询数据：{}", needUpdateBook.getBookName());
                                    String bookId = trySearchBookId(needUpdateBook.getBookName(), ruleBean);
                                    if (StringUtils.isEmpty(bookId)) {
                                        log.warn("没找到书籍：{}", needUpdateBook.getBookName());
                                        continue;
                                    }
                                    bookService.updateCrawlProperties(needUpdateBook.getId(), needUpdateBook.getCrawlSourceId(), bookId);
                                    needUpdateBook.setCrawlBookId(bookId);
                                }

                                //解析小说基本信息
                                crawlParser.parseBook(ruleBean, needUpdateBook.getCrawlBookId(), book -> {
                                    //这里只做老书更新
                                    book.setId(needUpdateBook.getId());
                                    book.setWordCount(needUpdateBook.getWordCount());
                                    if (needUpdateBook.getPicUrl() != null && needUpdateBook.getPicUrl()
                                            .contains(Constants.LOCAL_PIC_PREFIX)) {
                                        //本地图片则不更新
                                        book.setPicUrl(null);
                                    }
                                    //查询已存在的章节
                                    Map<Integer, BookIndex> existBookIndexMap = bookService.queryExistBookIndexMap(
                                            needUpdateBook.getId());
                                    //解析章节目录
                                    crawlParser.parseBookIndexAndContent(needUpdateBook.getCrawlBookId(), book,
                                            ruleBean, existBookIndexMap, chapter -> {
                                                bookService.updateBookAndIndexAndContent(book, chapter.getBookIndexList(),
                                                        chapter.getBookContentList(), existBookIndexMap);
                                            });
                                });
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }

                        }
                        //  休眠10分钟
                        TimeUnit.MINUTES.sleep(10);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }

                }
            }).start();


        }

        new Thread(() -> {
            log.info("程序启动,开始执行单本采集任务线程。。。");
            while (true) {
                CrawlSingleTask task = null;
                byte crawlStatus = 0;
                try {
                    //获取采集任务
                    task = crawlService.getCrawlSingleTask();

                    if (task != null) {
                        //查询爬虫规则
                        CrawlSource source = crawlService.queryCrawlSource(task.getSourceId());
                        RuleBean ruleBean = new ObjectMapper().readValue(source.getCrawlRule(), RuleBean.class);
                        String sourceBookId = task.getSourceBookId();

                        //尝试通过搜索去
                        if (StringUtils.isEmpty(sourceBookId)) {
                            sourceBookId = trySearchBookId(task.getBookName(), ruleBean);
                        }
                        if (crawlService.parseBookAndSave(task.getCatId(), ruleBean, task.getSourceId(),
                                sourceBookId)) {
                            //采集成功
                            crawlStatus = 1;
                        }

                    }

                    //休眠1分钟
                    TimeUnit.MINUTES.sleep(1);

                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
                if (task != null) {
                    crawlService.updateCrawlSingleTask(task, crawlStatus);
                }

            }
        }).start();
    }

    /**
     * 通过名称搜索
     *
     * @param bookName 书名
     * @param ruleBean 爬虫源
     * @return 搜索的书信息
     */
    public String trySearchBookId(String bookName, RuleBean ruleBean) {
        //搜索
        String query = ruleBean.getSearchUrl().replaceAll("\\{bookName}", bookName);
        String html = crawlHttpClient.get(query);
        Pattern compile = Pattern.compile(ruleBean.getSearchBookId());
        Matcher matcher = compile.matcher(html);
        if (html.contains(bookName) && matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
