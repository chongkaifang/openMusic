package com.open.entity;

import javax.persistence.*;

@Entity
@Table(name = "singer")
public class Singer {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "singer_id")
    private Long singerId;

    @Column(name = "singer_name")
    private String singerName;

    @Column(name = "singer_age")
    private String singerAge;

    @Column(name = "singer_works")
    private String singerWorks;

    @Column(name = "singer_come_from")
    private String singerComeFrom;

    @Column(name = "singer_img")
    private String singerImg;

    @Column(name = "singer_sex")
    private String singerSex;

    @Column(name = "singer_introduction")
    private String singerIntroduction;

    @Column(name = "singer_hot")
    private Integer singerHot;

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSingerAge() {
        return singerAge;
    }

    public void setSingerAge(String singerAge) {
        this.singerAge = singerAge;
    }

    public String getSingerWorks() {
        return singerWorks;
    }

    public void setSingerWorks(String singerWorks) {
        this.singerWorks = singerWorks;
    }

    public String getSingerComeFrom() {
        return singerComeFrom;
    }

    public void setSingerComeFrom(String singerComeFrom) {
        this.singerComeFrom = singerComeFrom;
    }

    public String getSingerImg() {
        return singerImg;
    }

    public void setSingerImg(String singerImg) {
        this.singerImg = singerImg;
    }

    public String getSingerSex() {
        return singerSex;
    }

    public void setSingerSex(String singerSex) {
        this.singerSex = singerSex;
    }

    public String getSingerIntroduction() {
        return singerIntroduction;
    }

    public void setSingerIntroduction(String singerIntroduction) {
        this.singerIntroduction = singerIntroduction;
    }

    public Integer getSingerHot() {
        return singerHot;
    }

    public void setSingerHot(Integer singerHot) {
        this.singerHot = singerHot;
    }
}
