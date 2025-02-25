package com.java2nb.novel.service.impl;

import com.java2nb.novel.core.cache.CacheService;
import com.java2nb.novel.entity.Book;
import com.java2nb.novel.entity.BookContent;
import com.java2nb.novel.entity.BookIndex;
import com.java2nb.novel.mapper.*;
import com.java2nb.novel.service.BookContentService;
import com.java2nb.novel.service.BookService;
import com.java2nb.novel.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.java2nb.novel.mapper.BookDynamicSqlSupport.crawlBookId;
import static com.java2nb.novel.mapper.BookDynamicSqlSupport.crawlSourceId;
import static com.java2nb.novel.mapper.CrawlSourceDynamicSqlSupport.id;
import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static org.mybatis.dynamic.sql.select.SelectDSL.select;

/**
 * @author Administrator
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final CacheService cacheService;

    private final CrawlBookMapper bookMapper;

    private final BookCategoryMapper bookCategoryMapper;

    private final CrawlBookIndexMapper bookIndexMapper;

    private final Map<String, BookContentService> bookContentServiceMap;

    @Value("${content.save.storage}")
    private String storageType;


    @Override
    public boolean queryIsExistByBookNameAndAuthorName(String bookName, String authorName) {

        return bookMapper.count(countFrom(BookDynamicSqlSupport.book).where(BookDynamicSqlSupport.bookName, isEqualTo(bookName))
                .and(BookDynamicSqlSupport.authorName, isEqualTo(authorName))
                .build()
                .render(RenderingStrategies.MYBATIS3)) > 0;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCrawlProperties(Long id, Integer sourceId, String bookId) {
        bookMapper.update(update(BookDynamicSqlSupport.book)
                .set(crawlSourceId)
                .equalTo(sourceId)
                .set(crawlBookId)
                .equalTo(bookId)
                .where(BookDynamicSqlSupport.id, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3));
    }

    @Override
    public String queryCatNameByCatId(int catId) {
        return bookCategoryMapper.selectMany(select(BookCategoryDynamicSqlSupport.name)
                .from(BookCategoryDynamicSqlSupport.bookCategory)
                .where(id, isEqualTo(catId))
                .build()
                .render(RenderingStrategies.MYBATIS3)).get(0).getName();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveBookAndIndexAndContent(Book book, List<BookIndex> bookIndexList, List<BookContent> bookContentList) {
        if (!queryIsExistByBookNameAndAuthorName(book.getBookName(), book.getAuthorName())) {

            if (bookIndexList.size() > 0) {

                //保存小说主表

                book.setCreateTime(new Date());
                bookMapper.insertSelective(book);

                //批量保存目录和内容
                bookIndexList.forEach(bookIndex -> {
                    bookIndex.setStorageType(storageType);
                });
                bookIndexMapper.insertMultiple(bookIndexList);
                bookContentServiceMap.get(storageType).saveBookContent(bookContentList, book.getId());

            }
        }


    }

    @Override
    public List<Book> queryNeedUpdateBook(Date startDate, int limit) {
        List<Book> books = bookMapper.queryNeedUpdateBook(startDate, limit, getStartId());
        saveScanIdx(books);
        return books;
    }

    /**
     * 保存扫描索引
     *
     * @param bookList 查询bookList
     */
    private void saveScanIdx(List<Book> bookList) {
        if (CollectionUtils.isEmpty(bookList)) {
            return;
        }
        Optional<Long> max = bookList.stream().map(Book::getId)
                .filter(Objects::nonNull)
                .max(Long::compareTo);

        //存在则设置
        max.ifPresent(maxId -> cacheService.set("BOOK_CRAWL_SCAN_TASK_INDEX", String.valueOf(maxId), 60 * 10));
    }

    /**
     * 获取起始ID
     *
     * @return 起始搜索位置
     */
    private Long getStartId() {
        String picSaveScanTaskIdx = cacheService.get("BOOK_CRAWL_SCAN_TASK_INDEX");
        if (NumberUtils.isNumber(picSaveScanTaskIdx)) {
            log.error("BOOK_CRAWL_SCAN_TASK_INDEX:" + picSaveScanTaskIdx);
            return NumberUtils.toLong(picSaveScanTaskIdx);
        }
        return 0L;
    }

    @Override
    public Map<Integer, BookIndex> queryExistBookIndexMap(Long bookId) {
        List<BookIndex> bookIndexs = bookIndexMapper.selectMany(select(BookIndexDynamicSqlSupport.id, BookIndexDynamicSqlSupport.indexNum, BookIndexDynamicSqlSupport.indexName, BookIndexDynamicSqlSupport.wordCount, BookIndexDynamicSqlSupport.storageType)
                .from(BookIndexDynamicSqlSupport.bookIndex)
                .where(BookIndexDynamicSqlSupport.bookId, isEqualTo(bookId))
                .build()
                .render(RenderingStrategies.MYBATIS3));
        if (!bookIndexs.isEmpty()) {
            return bookIndexs.stream().collect(Collectors.toMap(BookIndex::getIndexNum, Function.identity()));
        }
        return new HashMap<>(0);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBookAndIndexAndContent(Book book, List<BookIndex> bookIndexList, List<BookContent> bookContentList, Map<Integer, BookIndex> existBookIndexMap) {
        for (int i = 0; i < bookIndexList.size(); i++) {
            BookIndex bookIndex = bookIndexList.get(i);
            BookContent bookContent = bookContentList.get(i);


            if (!existBookIndexMap.containsKey(bookIndex.getIndexNum())) {
                //插入
                bookIndex.setStorageType(storageType);
                bookIndexMapper.insertSelective(bookIndex);
                bookContentServiceMap.get(storageType).saveBookContent(bookContent, book.getId());
            } else {
                //更新
                bookIndexMapper.updateByPrimaryKeySelective(bookIndex);
                bookContentServiceMap.get(existBookIndexMap.get(bookIndex.getIndexNum()).getStorageType()).updateBookContent(bookContent, book.getId());
            }


        }

        //更新小说主表
        book.setBookName(null);
        book.setAuthorName(null);
        book.setUpdateTime(new Date());
        if (Constants.VISIT_COUNT_DEFAULT.equals(book.getVisitCount())) {
            book.setVisitCount(null);
        }
        bookMapper.updateByPrimaryKeySelective(book);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBookAndIndexAndContent(Book book, BookIndex bookIndex, BookContent bookContent, Map<Integer, BookIndex> existBookIndexMap) {
        if (!existBookIndexMap.containsKey(bookIndex.getIndexNum())) {
            //插入
            bookIndex.setStorageType(storageType);
            bookIndexMapper.insertSelective(bookIndex);
            bookContentServiceMap.get(storageType).saveBookContent(bookContent, book.getId());
        } else {
            //更新
            bookIndexMapper.updateByPrimaryKeySelective(bookIndex);
            bookContentServiceMap.get(existBookIndexMap.get(bookIndex.getIndexNum()).getStorageType()).updateBookContent(bookContent, book.getId());
        }
    }

    @Override
    public void updateCrawlLastTime(Long bookId) {
        Book book = new Book();
        book.setId(bookId);
        book.setCrawlLastTime(new Date());
        bookMapper.updateByPrimaryKeySelective(book);
    }

    @Override
    public Book queryBookByBookNameAndAuthorName(String bookName, String authorName) {
        List<Book> books = bookMapper.selectMany(select(BookDynamicSqlSupport.id).from(BookDynamicSqlSupport.book)
                .where(BookDynamicSqlSupport.bookName, isEqualTo(bookName))
                .and(BookDynamicSqlSupport.authorName, isEqualTo(authorName))
                .build()
                .render(RenderingStrategies.MYBATIS3));

        if (!books.isEmpty()) {
            return books.get(0);
        }

        return null;

    }

}
