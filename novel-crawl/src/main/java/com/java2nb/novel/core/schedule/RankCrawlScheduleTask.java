package com.java2nb.novel.core.schedule;


import com.java2nb.novel.core.crawl.QDRook;
import com.java2nb.novel.service.CrawlService;
import com.java2nb.novel.service.QDRankCrawlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 爬虫线程监控器,监控执行完成的爬虫源，并修改状态
 *
 * @author Administrator
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RankCrawlScheduleTask {


    private final CrawlService crawlService;

    private final QDRankCrawlService qdRankCrawlService;

    @Scheduled(cron = "0 0 0 ? * ? ")
    public void startCrawlRank() {

        List<String> rankTypeList = Arrays.asList("readindex", "recom", "collect", "newfans");

        List<String> categoryList = Arrays.asList("", "chanId80", "chanId81", "chanId82", "chanId83", "chanId84", "chanId85", "chanId86", "chanId88");

        for (String rankType : rankTypeList) {
            for (String category : categoryList) {
                log.info("开始爬取排行榜：{}，分类：{}", rankType, category);
                List<QDRook> qdRookList = qdRankCrawlService.crawlRankBooks(category, rankType);
                log.info("爬取排行榜：{}，分类：{}，数据量：{}", rankType, category, qdRookList.size());
                qdRankCrawlService.saveQDBook(qdRookList);
            }
        }

    }
}
