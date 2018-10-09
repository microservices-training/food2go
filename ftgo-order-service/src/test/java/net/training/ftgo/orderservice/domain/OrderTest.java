package net.training.ftgo.orderservice.domain;

import io.eventuate.tram.events.aggregates.ResultWithDomainEvents;
import net.training.ftgo.orderservice.RestaurantMother;
import net.training.ftgo.orderservice.api.events.OrderCreatedEvent;
import net.training.ftgo.orderservice.api.events.OrderDomainEvent;
import net.training.ftgo.orderservice.api.events.OrderState;
import net.training.ftgo.orderservice.domain.LineItemQuantityChange;
import net.training.ftgo.orderservice.domain.Order;
import net.training.ftgo.orderservice.domain.OrderAuthorized;
import net.training.ftgo.orderservice.domain.OrderRevision;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static net.training.ftgo.orderservice.OrderDetailsMother.*;
import static net.training.ftgo.orderservice.RestaurantMother.AJANTA_ID;
import static net.training.ftgo.orderservice.RestaurantMother.AJANTA_RESTAURANT;
import static net.training.ftgo.orderservice.RestaurantMother.CHICKEN_VINDALOO_PRICE;
import static org.junit.Assert.assertEquals;

public class OrderTest {

  private ResultWithDomainEvents<Order, OrderDomainEvent> createResult;
  private Order order;

  @Before
  public void setUp() throws Exception {
    createResult = Order.createOrder(CONSUMER_ID, AJANTA_RESTAURANT, chickenVindalooLineItems());
    order = createResult.result;
  }

  @Test
  public void shouldCreateOrder() {
    assertEquals(singletonList(new OrderCreatedEvent(CHICKEN_VINDALOO_ORDER_DETAILS, RestaurantMother.AJANTA_RESTAURANT_NAME)), createResult.events);

    assertEquals(OrderState.APPROVAL_PENDING, order.getState());
    // ...
  }

  @Test
  public void shouldCalculateTotal() {
    assertEquals(CHICKEN_VINDALOO_PRICE.multiply(CHICKEN_VINDALOO_QUANTITY), order.getOrderTotal());
  }

  @Test
  public void shouldAuthorize() {
    List<OrderDomainEvent> events = order.noteApproved();
    assertEquals(singletonList(new OrderAuthorized()), events);
    assertEquals(OrderState.APPROVED, order.getState());
  }

  @Test
  public void shouldReviseOrder() {

    order.noteApproved();

    OrderRevision orderRevision = new OrderRevision(Optional.empty(), Collections.singletonMap("1", 10));

    ResultWithDomainEvents<LineItemQuantityChange, OrderDomainEvent> result = order.revise(orderRevision);

    assertEquals(CHICKEN_VINDALOO_PRICE.multiply(10), result.result.getNewOrderTotal());

    order.confirmRevision(orderRevision);

    assertEquals(CHICKEN_VINDALOO_PRICE.multiply(10), order.getOrderTotal());
  }
}