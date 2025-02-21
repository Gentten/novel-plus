package com.java2nb.novel.service;

import com.java2nb.novel.service.impl.QDRankCrawlServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class QDRankCrawlServiceTest {
    private QDRankCrawlService qdRankCrawlService = new QDRankCrawlServiceImpl();

    @Test
    public void crawlRankBooks() {
        qdRankCrawlService.crawlRankBooks("chanId80", "collect");
    }
}