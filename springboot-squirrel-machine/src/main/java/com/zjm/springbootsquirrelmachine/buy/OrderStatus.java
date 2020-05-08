package com.zjm.springbootsquirrelmachine.buy;

/**
 * 〈一句话功能简述〉<br> 〈订单状态枚举〉
 *
 * @author zhangjinmiao
 * @create 2019/5/29 17:25
 */
public enum OrderStatus {

  UNPAID,                 // 待支付
  WAITING_FOR_RECEIVE,    // 待收货
  DONE                    // 结束
}
