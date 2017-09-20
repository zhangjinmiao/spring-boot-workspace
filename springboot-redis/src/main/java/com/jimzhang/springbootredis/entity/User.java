package com.jimzhang.springbootredis.entity;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 晋苗 on 2017/8/27.
 */
@Entity
@Table(name = "userInfo")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String passWord;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, unique = true)
    private String nickName;
    @Column(nullable = false)
    private Date regTime;


    public User() {
    }

    public User(String userName, String passWord, String email, String nickName, Date regTime) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.nickName = nickName;
        this.regTime = regTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }
}
