package net.training.ftgo.orderservice.sagaparticipants;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import net.training.ftgo.consumerservice.api.ConsumerServiceChannels;
import net.training.ftgo.consumerservice.api.ValidateOrderByConsumer;
import net.training.ftgo.orderservice.api.OrderServiceChannels;

public class ConsumerServiceProxy {


  public final CommandEndpoint<ValidateOrderByConsumer> validateOrder= CommandEndpointBuilder
          .forCommand(ValidateOrderByConsumer.class)
          .withChannel(ConsumerServiceChannels.consumerServiceChannel)
          .withReply(Success.class)
          .build();

}
