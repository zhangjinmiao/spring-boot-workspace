package com.zjm.springbootsquirrelmachine.buy;

import org.squirrelframework.foundation.fsm.UntypedAnonymousAction;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;

/**
 * 〈一句话功能简述〉<br> 〈决策类〉
 *
 * @author zhangjinmiao
 * @create 2019/5/29 18:40
 */
public class FSMDecisionMaker extends UntypedAnonymousAction {

  @Override
  public void execute(Object from, Object to, Object event, Object context,
      UntypedStateMachine stateMachine) {

    OrderStatus typedTo = (OrderStatus) to;

    OrderContext typedContext = (OrderContext) context;

    if (typedTo == OrderStatus.UNPAID) {
      if (typedContext.isValid()) {
        // 校验通过可以...
      }
      stateMachine.fire(OrderEvent.PAY, context);
    }
    else if (typedTo == OrderStatus.WAITING_FOR_RECEIVE) {
      stateMachine.fire(OrderEvent.RECEIVE, context);
    }

  }
}
