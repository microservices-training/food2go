package net.training.ftgo.orderservice.sagaparticipants;

public class BeginCancelCommand extends OrderCommand {

  private BeginCancelCommand() {
  }

  public BeginCancelCommand(long orderId) {
    super(orderId);
  }
}
