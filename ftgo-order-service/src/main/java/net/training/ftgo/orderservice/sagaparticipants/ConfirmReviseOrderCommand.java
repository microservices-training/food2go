package net.training.ftgo.orderservice.sagaparticipants;

import net.training.ftgo.orderservice.domain.OrderRevision;

public class ConfirmReviseOrderCommand extends OrderCommand {

  private ConfirmReviseOrderCommand() {
  }

  public ConfirmReviseOrderCommand(long orderId, OrderRevision revision) {
    super(orderId);
    this.revision = revision;
  }

  private OrderRevision revision;

  public OrderRevision getRevision() {
    return revision;
  }
}
