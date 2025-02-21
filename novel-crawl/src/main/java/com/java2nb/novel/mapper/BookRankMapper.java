package com.java2nb.novel.mapper;

import com.java2nb.novel.entity.BookRank;

import java.util.List;

/**
 * @author Administrator
 */
public interface BookRankMapper {


    /**
     * 批量插入
     *
     * @param bookRank bookRank
     */
    void batchInsert(List<BookRank> bookRank);

    void insert(BookRank bookRank);

    BookRank selectById(Long id);

    List<BookRank> selectAll();


    void updateRank(BookRank bookRank);


    void deleteById(Long id);
}
