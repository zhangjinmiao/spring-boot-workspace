package com.zjm.springbootsquirrelmachine.buy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.annotation.State;
import org.squirrelframework.foundation.fsm.annotation.StateMachineParameters;
import org.squirrelframework.foundation.fsm.annotation.States;
import org.squirrelframework.foundation.fsm.annotation.Transit;
import org.squirrelframework.foundation.fsm.annotation.Transitions;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * 〈一句话功能简述〉<br> 〈订单状态机〉
 *
 * @author zhangjinmiao
 * @create 2019/5/29 17:22
 */
// 将event 或 state 和 action 做一次映射

@Transitions({
    @Transit(from = "UNPAID", to = "WAITING_FOR_RECEIVE", on = "PAY", callMethod = "pay"),
    @Transit(from = "WAITING_FOR_RECEIVE", to = "DONE", on = "RECEIVE", callMethod = "receive")
})

@States({
    @State(name = "UNPAID", entryCallMethod = "create"),
    @State(name = "WAITING_FOR_RECEIVE", entryCallMethod = "pay"),
    @State(name = "DONE", entryCallMethod = "receive")
})
@StateMachineParameters(stateType = OrderStatus.class, eventType = OrderEvent.class, contextType = OrderContext.class)
public class OrderStateMachine extends AbstractUntypedStateMachine {

  private Logger logger = LoggerFactory.getLogger(getClass());

  // 执行的方法
  protected void create(OrderStatus from, OrderStatus to, OrderEvent event, OrderContext context) {
    logger.info("订单创建，待支付");
  }

  protected void pay(OrderStatus from, OrderStatus to, OrderEvent event, OrderContext context) {
    logger.info("用户完成支付，待收货");
  }

  protected void receive(OrderStatus from, OrderStatus to, OrderEvent event, OrderContext context) {
    logger.info("用户已收货，订单完成");
  }
}
