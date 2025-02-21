package com.java2nb.novel.core.crawl;

import lombok.Data;

@Data
public class QDRook {

    /**
     * 名称
     */
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 图片
     */
    private String image;

    /**
     * 描述
     */
    private String description;

    /**
     * 类别1
     */
    private String category1;

    /**
     * 类别2
     */
    private String category2;


    /**
     * 状态 连载 or 完本
     */
    private String status;

    /**
     * 男 女  出版
     */
    private String type;

    /**
     * 排名信息
     */
    private RankValue rankValue;

}
