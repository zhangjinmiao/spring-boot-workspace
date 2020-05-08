package com.zjm.springbootsquirrelmachine.buy;

/**
 * 〈一句话功能简述〉<br> 〈订单状态驱动事件〉
 *
 * @author zhangjinmiao
 * @create 2019/5/29 17:26
 */
public enum  OrderEvent {

  PAY,        // 支付 触发状态从待支付UNPAID状态到待收货WAITING_FOR_RECEIVE状态的迁移
  RECEIVE     // 收货 触发状态从待收货WAITING_FOR_RECEIVE状态到结束DONE状态的迁移
}
