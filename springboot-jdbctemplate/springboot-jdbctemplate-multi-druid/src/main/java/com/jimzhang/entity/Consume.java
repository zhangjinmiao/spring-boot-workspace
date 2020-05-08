package com.jimzhang.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jimzhang
 * <>https://segmentfault.com/u/itzhangjm</>
 * @version V1.0.0
 * @description 消费表
 * @date 2018-03-01 16:45
 */
public class Consume implements Serializable {

    private Long id;

    private String userID;
    private double amount;
    private String cardType;
    private Date dateTime;
    private String orderID;
    private String status;
    private String returnRes;
    private Date logDate;
    private Date finalDate;
    private String swapType;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnRes() {
        return returnRes;
    }

    public void setReturnRes(String returnRes) {
        this.returnRes = returnRes;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public String getSwapType() {
        return swapType;
    }

    public void setSwapType(String swapType) {
        this.swapType = swapType;
    }
}
