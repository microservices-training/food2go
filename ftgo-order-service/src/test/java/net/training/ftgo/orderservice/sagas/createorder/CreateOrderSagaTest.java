package net.training.ftgo.orderservice.sagas.createorder;

import net.training.ftgo.accountservice.api.AccountingServiceChannels;
import net.training.ftgo.accountservice.api.AuthorizeCommand;
import net.training.ftgo.common.CommonJsonMapperInitializer;
import net.training.ftgo.consumerservice.api.ConsumerServiceChannels;
import net.training.ftgo.consumerservice.api.ValidateOrderByConsumer;
import net.training.ftgo.kitchenservice.api.CancelCreateTicket;
import net.training.ftgo.kitchenservice.api.ConfirmCreateTicket;
import net.training.ftgo.kitchenservice.api.CreateTicket;
import net.training.ftgo.kitchenservice.api.KitchenServiceChannels;
import net.training.ftgo.orderservice.api.OrderServiceChannels;
import net.training.ftgo.orderservice.sagaparticipants.*;
import net.training.ftgo.orderservice.sagas.createorder.CreateOrderSaga;
import net.training.ftgo.orderservice.sagas.createorder.CreateOrderSagaState;

import org.junit.BeforeClass;
import org.junit.Test;

import static io.eventuate.tram.sagas.testing.SagaUnitTestSupport.given;
import static net.training.ftgo.orderservice.OrderDetailsMother.*;
import static net.training.ftgo.orderservice.RestaurantMother.AJANTA_ID;

public class CreateOrderSagaTest {

  private OrderServiceProxy orderServiceProxy = new OrderServiceProxy();
  private KitchenServiceProxy kitchenServiceProxy = new KitchenServiceProxy();
  private ConsumerServiceProxy consumerServiceProxy = new ConsumerServiceProxy();
  private AccountingServiceProxy accountingServiceProxy = new AccountingServiceProxy();

  @BeforeClass
  public static void initialize() {
    CommonJsonMapperInitializer.registerMoneyModule();
  }

  private CreateOrderSaga makeCreateOrderSaga() {
    return new CreateOrderSaga(orderServiceProxy, consumerServiceProxy, kitchenServiceProxy, accountingServiceProxy);
  }

  @Test
  public void shouldCreateOrder() {
    given()
        .saga(makeCreateOrderSaga(),
                new CreateOrderSagaState(ORDER_ID, CHICKEN_VINDALOO_ORDER_DETAILS)).
    expect().
        command(new ValidateOrderByConsumer(CONSUMER_ID, ORDER_ID,
                CHICKEN_VINDALOO_ORDER_TOTAL)).
        to(ConsumerServiceChannels.consumerServiceChannel).
    andGiven().
        successReply().
    expect().
      command(new CreateTicket(AJANTA_ID, ORDER_ID, null /* FIXME */)).
      to(KitchenServiceChannels.kitchenServiceChannel).
    andGiven().
        successReply().
    expect().
      command(new AuthorizeCommand(CONSUMER_ID, ORDER_ID, CHICKEN_VINDALOO_ORDER_TOTAL)).
      to(AccountingServiceChannels.accountingServiceChannel).
    andGiven().
        successReply().
    expect().
      command(new ConfirmCreateTicket(ORDER_ID)).
      to(KitchenServiceChannels.kitchenServiceChannel).
    andGiven().
        successReply().
    expect().
      command(new ApproveOrderCommand(ORDER_ID)).
      to(OrderServiceChannels.orderServiceChannel)
            ;
  }

  @Test
  public void shouldRejectOrderDueToConsumerVerificationFailed() {
    given()
        .saga(makeCreateOrderSaga(),
                new CreateOrderSagaState(ORDER_ID, CHICKEN_VINDALOO_ORDER_DETAILS)).
    expect().
        command(new ValidateOrderByConsumer(CONSUMER_ID, ORDER_ID,
                CHICKEN_VINDALOO_ORDER_TOTAL)).
        to(ConsumerServiceChannels.consumerServiceChannel).
    andGiven().
        failureReply().
    expect().
        command(new RejectOrderCommand(ORDER_ID)).
        to(OrderServiceChannels.orderServiceChannel);
  }

  @Test
  public void shouldRejectDueToFailedAuthorizxation() {
    given()
            .saga(makeCreateOrderSaga(),
                    new CreateOrderSagaState(ORDER_ID, CHICKEN_VINDALOO_ORDER_DETAILS)).
    expect().
      command(new ValidateOrderByConsumer(CONSUMER_ID, ORDER_ID,
              CHICKEN_VINDALOO_ORDER_TOTAL)).
      to(ConsumerServiceChannels.consumerServiceChannel).
    andGiven().
      successReply().
    expect().
      command(new CreateTicket(AJANTA_ID, ORDER_ID, null /* FIXME */)).
      to(KitchenServiceChannels.kitchenServiceChannel).
    andGiven().
      successReply().
    expect().
      command(new AuthorizeCommand(CONSUMER_ID, ORDER_ID, CHICKEN_VINDALOO_ORDER_TOTAL)).
      to(AccountingServiceChannels.accountingServiceChannel).
    andGiven().
      failureReply().
    expect().
      command(new CancelCreateTicket(ORDER_ID)).
      to(KitchenServiceChannels.kitchenServiceChannel).
    andGiven().
      successReply().
    expect().
      command(new RejectOrderCommand(ORDER_ID)).
      to(OrderServiceChannels.orderServiceChannel)
    ;
  }
}