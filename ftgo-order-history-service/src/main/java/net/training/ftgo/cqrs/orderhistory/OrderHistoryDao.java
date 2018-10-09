package net.training.ftgo.cqrs.orderhistory;


import java.util.Optional;

import net.training.ftgo.cqrs.orderhistory.dynamodb.Order;
import net.training.ftgo.cqrs.orderhistory.dynamodb.SourceEvent;

public interface OrderHistoryDao {

  boolean addOrder(Order order, Optional<SourceEvent> eventSource);

  OrderHistory findOrderHistory(String consumerId, OrderHistoryFilter filter);

  public boolean cancelOrder(String orderId, Optional<SourceEvent> eventSource);

  void noteTicketPreparationStarted(String orderId);

  void noteTicketPreparationCompleted(String orderId);

  void notePickedUp(String orderId, Optional<SourceEvent> eventSource);

  void updateLocation(String orderId, Location location);

  void noteDelivered(String orderId);

  Optional<Order> findOrder(String orderId);
}
