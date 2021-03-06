package net.training.ftgo.consumerservice.web;

import io.eventuate.tram.events.ResultWithEvents;
import net.training.ftgo.consumerservice.api.web.CreateConsumerRequest;
import net.training.ftgo.consumerservice.api.web.CreateConsumerResponse;
import net.training.ftgo.consumerservice.domain.Consumer;
import net.training.ftgo.consumerservice.domain.ConsumerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/consumers")
public class ConsumerController {

  @Autowired
  private ConsumerService consumerService;

  @RequestMapping(method= RequestMethod.POST)
  public CreateConsumerResponse create(@RequestBody CreateConsumerRequest request) {
    ResultWithEvents<Consumer> result = consumerService.create(request.getName());
    return new CreateConsumerResponse(result.result.getId());
  }

  @RequestMapping(method= RequestMethod.GET,  path="/{consumerId}")
  public ResponseEntity<GetConsumerResponse> get(@PathVariable long consumerId) {
    return consumerService.findById(consumerId)
            .map(consumer -> new ResponseEntity<>(new GetConsumerResponse(consumer.getName()), HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }
}
