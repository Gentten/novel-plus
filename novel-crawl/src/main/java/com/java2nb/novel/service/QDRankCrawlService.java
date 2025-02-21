package com.java2nb.novel.service;

import com.java2nb.novel.core.crawl.QDRook;

import java.util.List;

public interface QDRankCrawlService {


    /**
     * @param channel  渠道分类
     * @param rankType 排名类型
     * @return 起点书籍信息
     */
    List<QDRook> crawlRankBooks(String channel, String rankType);


    void saveQDBook(List<QDRook> qdRookList);
}
