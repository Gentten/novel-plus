package com.java2nb.novel.core.crawl;

import lombok.Data;

@Data
public class QDRook {

    private String name;

    private String author;

    private String image;

    private String description;

    private String category1;

    private String category2;


    /**
     *
     */
    private String status;

    /**
     * 男 女  出版
     */
    private String type;
}
