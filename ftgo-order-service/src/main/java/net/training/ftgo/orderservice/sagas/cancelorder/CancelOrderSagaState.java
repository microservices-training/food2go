package net.training.ftgo.orderservice.sagas.cancelorder;

public enum CancelOrderSagaState {
  state, WAITING_TO_AUTHORIZE, COMPLETED, REVERSING
}
