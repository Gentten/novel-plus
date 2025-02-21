package com.java2nb.novel.core.crawl;

import lombok.Data;

@Data
public class RankValue {

    private String value;

    private String front;

    /**
     * 分类
     */
    private String cat;

    private String rankType;

    private Integer order;

}
