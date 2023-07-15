package com.open.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_singer")
public class Singer {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @Column(name = "works")
    private String works;

    @Column(name = "come_from")
    private String comeFrom;

    @Column(name = "img")
    private String img;

    @Column(name = "sex")
    private String sex;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "hot")
    private Integer hot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
         this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
         this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
         this.age = age;
    }

    public String getWorks() {
        return works;
    }

    public void setWorks(String works) {
         this.works = works;
    }

    public String getComeFrom() {
        return comeFrom;
    }

    public void setComeFrom(String comeFrom) {
        this.comeFrom = comeFrom;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }
}
