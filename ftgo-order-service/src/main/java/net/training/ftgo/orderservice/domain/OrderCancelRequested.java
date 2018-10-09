package net.training.ftgo.orderservice.domain;

import io.eventuate.tram.events.common.DomainEvent;
import net.training.ftgo.orderservice.api.events.OrderState;

public class OrderCancelRequested implements DomainEvent {
  private OrderState state;

  public OrderCancelRequested(OrderState state) {

    this.state = state;
  }

  public OrderState getState() {
    return state;
  }
}
