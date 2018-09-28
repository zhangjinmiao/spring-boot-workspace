package com.jimzhang.entity;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2018/7/2
 */
public class StudentEntity implements Serializable{

  private Long id;
  private Long serialId;
  private String name;
  private int age;

  public StudentEntity() {
  }

  public StudentEntity(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public StudentEntity(Long serialId, String name, int age) {
    this.serialId = serialId;
    this.name = name;
    this.age = age;
  }

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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Long getSerialId() {
    return serialId;
  }

  public void setSerialId(Long serialId) {
    this.serialId = serialId;
  }
}
