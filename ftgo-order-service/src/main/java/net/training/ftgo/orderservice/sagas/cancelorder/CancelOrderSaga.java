package net.training.ftgo.orderservice.sagas.cancelorder;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.orchestration.SagaDefinition;
import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
import net.training.ftgo.accountservice.api.AccountingServiceChannels;
import net.training.ftgo.accountservice.api.ReverseAuthorizationCommand;
import net.training.ftgo.kitchenservice.api.BeginCancelTicketCommand;
import net.training.ftgo.kitchenservice.api.ConfirmCancelTicketCommand;
import net.training.ftgo.kitchenservice.api.KitchenServiceChannels;
import net.training.ftgo.kitchenservice.api.UndoBeginCancelTicketCommand;
import net.training.ftgo.orderservice.api.OrderServiceChannels;
import net.training.ftgo.orderservice.sagaparticipants.BeginCancelCommand;
import net.training.ftgo.orderservice.sagaparticipants.ConfirmCancelOrderCommand;
import net.training.ftgo.orderservice.sagaparticipants.UndoBeginCancelCommand;

import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

import static io.eventuate.tram.commands.consumer.CommandWithDestinationBuilder.send;

public class CancelOrderSaga implements SimpleSaga<CancelOrderSagaData> {



  private SagaDefinition<CancelOrderSagaData> sagaDefinition;


  @PostConstruct
  public void initializeSagaDefinition() {
    sagaDefinition = step()
            .invokeParticipant(this::beginCancel)
            .withCompensation(this::undoBeginCancel)
            .step()
            .invokeParticipant(this::beginCancelTicket)
            .withCompensation(this::undoBeginCancelTicket)
            .step()
            .invokeParticipant(this::reverseAuthorization)
            .step()
            .invokeParticipant(this::confirmTicketCancel)
            .step()
            .invokeParticipant(this::confirmOrderCancel)
            .build();

  }

  private CommandWithDestination confirmOrderCancel(CancelOrderSagaData data) {
    return send(new ConfirmCancelOrderCommand(data.getOrderId()))
            .to(OrderServiceChannels.orderServiceChannel)
            .build();

  }

  private CommandWithDestination confirmTicketCancel(CancelOrderSagaData data) {
    return send(new ConfirmCancelTicketCommand(data.getRestaurantId(), data.getOrderId()))
            .to(KitchenServiceChannels.kitchenServiceChannel)
            .build();

  }

  private CommandWithDestination reverseAuthorization(CancelOrderSagaData data) {
    return send(new ReverseAuthorizationCommand(data.getConsumerId(), data.getOrderId(), data.getOrderTotal()))
            .to(AccountingServiceChannels.accountingServiceChannel)
            .build();

  }

  private CommandWithDestination undoBeginCancelTicket(CancelOrderSagaData data) {
    return send(new UndoBeginCancelTicketCommand(data.getRestaurantId(), data.getOrderId()))
            .to(KitchenServiceChannels.kitchenServiceChannel)
            .build();

  }

  private CommandWithDestination beginCancelTicket(CancelOrderSagaData data) {
    return send(new BeginCancelTicketCommand(data.getRestaurantId(), (long) data.getOrderId()))
            .to(KitchenServiceChannels.kitchenServiceChannel)
            .build();

  }

  private CommandWithDestination undoBeginCancel(CancelOrderSagaData data) {
    return send(new UndoBeginCancelCommand(data.getOrderId()))
            .to(OrderServiceChannels.orderServiceChannel)
            .build();
  }

  private CommandWithDestination beginCancel(CancelOrderSagaData data) {
    return send(new BeginCancelCommand(data.getOrderId()))
            .to(OrderServiceChannels.orderServiceChannel)
            .build();
  }


  @Override
  public SagaDefinition<CancelOrderSagaData> getSagaDefinition() {
    Assert.notNull(sagaDefinition);
    return sagaDefinition;
  }



}
