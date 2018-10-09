package net.training.ftgo.orderservice.sagaparticipants;

import io.eventuate.tram.commands.common.Success;
import io.eventuate.tram.sagas.simpledsl.CommandEndpoint;
import io.eventuate.tram.sagas.simpledsl.CommandEndpointBuilder;
import net.training.ftgo.accountservice.api.AccountingServiceChannels;
import net.training.ftgo.accountservice.api.AuthorizeCommand;
import net.training.ftgo.consumerservice.api.ConsumerServiceChannels;
import net.training.ftgo.consumerservice.api.ValidateOrderByConsumer;

public class AccountingServiceProxy {

  public final CommandEndpoint<AuthorizeCommand> authorize= CommandEndpointBuilder
          .forCommand(AuthorizeCommand.class)
          .withChannel(AccountingServiceChannels.accountingServiceChannel)
          .withReply(Success.class)
          .build();

}
