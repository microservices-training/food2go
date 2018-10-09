package net.training.ftgo.orderservice.sagaparticipants;

public class UndoBeginCancelCommand extends OrderCommand {
  public UndoBeginCancelCommand(long orderId) {
    super(orderId);
  }
}
