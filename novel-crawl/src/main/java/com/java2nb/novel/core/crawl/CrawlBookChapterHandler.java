package com.java2nb.novel.core.crawl;

import com.java2nb.novel.entity.Book;
import com.java2nb.novel.entity.BookContent;
import com.java2nb.novel.entity.BookIndex;

/**
 * 爬虫小说章节内容处理器
 */
public interface CrawlBookChapterHandler {

    void handle(ChapterBean chapterBean);


    /**
     * @param book        书
     * @param bookIndex   index of
     * @param bookContent content of
     */
    default void handleSingle(Book book, BookIndex bookIndex, BookContent bookContent) {
    }

}
