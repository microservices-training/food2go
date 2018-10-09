package net.training.ftgo.orderservice.sagaparticipants;

public class ConfirmCancelOrderCommand extends OrderCommand {

  private ConfirmCancelOrderCommand() {
  }

  public ConfirmCancelOrderCommand(long orderId) {
    super(orderId);
  }
}
