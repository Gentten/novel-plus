package com.java2nb.novel.entity;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.Date;

public class BookRank implements Serializable {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long bookId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer catId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Integer rankOrder;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String rankType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String rankValue;


    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String bookName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String author;


    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String extInfo;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String rankDate;


    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getId() {
        return id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getBookId() {
        return bookId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getCatId() {
        return catId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Integer getRankOrder() {
        return rankOrder;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRankOrder(Integer rankOrder) {
        this.rankOrder = rankOrder;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRankType() {
        return rankType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRankValue() {
        return rankValue;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRankValue(String rankValue) {
        this.rankValue = rankValue;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getExtInfo() {
        return extInfo;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getRankDate() {
        return rankDate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setRankDate(String rankDate) {
        this.rankDate = rankDate;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}