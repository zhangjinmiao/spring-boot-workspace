package com.zjm.springbootsquirrelmachine.buy;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2019/5/29 17:44
 */
public class OrderContext {

  private Boolean valid;// 业务逻辑校验


  public Boolean getValid() {
    return valid;
  }

  public void setValid(Boolean valid) {
    this.valid = valid;
  }

  // 业务逻辑校验
  public Boolean isValid(){


    return true;
  }
}
