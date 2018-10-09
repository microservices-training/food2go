package net.training.ftgo.consumerservice.web;

import net.training.ftgo.common.PersonName;
import net.training.ftgo.consumerservice.api.web.CreateConsumerResponse;
import net.training.ftgo.consumerservice.domain.Consumer;

public class GetConsumerResponse extends CreateConsumerResponse {
  private PersonName name;

  public PersonName getName() {
    return name;
  }

  public GetConsumerResponse(PersonName name) {

    this.name = name;
  }
}
