package com.java2nb.novel.mapper;

import com.java2nb.novel.entity.BookRank;

import java.util.List;

/**
 * @author Administrator
 */
public interface FrontBookRankMapper {


    /**
     * 查询最大日期
     *
     * @param catId    分类类型
     * @param rankType 排行榜类型
     * @return 最大统计日期
     */
    String selectMaxRankDate(Integer catId, String rankType);


    /**
     * 排行书籍列表
     *
     * @param catId    分类类型
     * @param rankType 排行榜类型
     * @return 排行书籍列表
     */
    List<BookRank> selectRankListOrder(Integer catId, String rankType, String rankDate);


}
