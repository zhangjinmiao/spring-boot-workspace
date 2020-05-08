package com.zjm.springbootsquirrelmachine.buy;

import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.UntypedStateMachine;
import org.squirrelframework.foundation.fsm.UntypedStateMachineBuilder;

/**
 * 〈一句话功能简述〉<br> 〈〉
 *
 * @author zhangjinmiao
 * @create 2019/5/29 20:07
 */
public class Test {


  public static void main(String[] args) {

    OrderContext context = new OrderContext();

    FSMDecisionMaker decisionMaker = new FSMDecisionMaker();

    //Build State Transitions
    UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(OrderStateMachine.class);

    // UNPAID -> WAITING_FOR_RECEIVE
    builder.onEntry(OrderStatus.UNPAID).perform(decisionMaker);
    builder.transitions().from(OrderStatus.UNPAID).toAmong(OrderStatus.WAITING_FOR_RECEIVE)
        .onEach(OrderEvent.PAY);

    // WAITING_FOR_RECEIVE -> DONE
    builder.onEntry(OrderStatus.WAITING_FOR_RECEIVE).perform(decisionMaker);
    builder.transitions().from(OrderStatus.WAITING_FOR_RECEIVE).toAmong(OrderStatus.DONE)
        .onEach(OrderEvent.RECEIVE);

    //Use State Machine
    UntypedStateMachine fsm = builder.newStateMachine(OrderStatus.UNPAID);

    //
    fsm.fire(OrderEvent.PAY, context);

    fsm.terminate();


  }
}
