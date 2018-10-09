package net.training.ftgo.kitchenservice.domain;


import net.training.ftgo.kitchenservice.api.TicketDetails;

public class TicketCreatedEvent implements TicketDomainEvent {
  public TicketCreatedEvent(Long id, TicketDetails details) {

  }
}
