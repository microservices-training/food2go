package net.training.ftgo.cqrs.orderhistory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import net.training.ftgo.cqrs.orderhistory.dynamodb.Order;

public class OrderHistory {
  private List<Order> orders;
  private Optional<String> startKey;

  public OrderHistory(List<Order> orders, Optional<String> startKey) {
    this.orders = orders;
    this.startKey = startKey;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public Optional<String> getStartKey() {
    return startKey;
  }
}
