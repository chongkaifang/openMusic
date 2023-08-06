package com.open.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "net_cookie")
public class NetCookie {
    /**
     * 主键
     */
    @Id
    @Column(name = "type")
    private String type;

    @Column(name = "cookie")
    private String cookie;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
