package net.training.ftgo.restaurantservice.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.eventuate.javaclient.commonimpl.JSonMapper;
import net.training.ftgo.restaurantservice.aws.ApiGatewayResponse;
import net.training.ftgo.restaurantservice.domain.Restaurant;
import net.training.ftgo.restaurantservice.domain.RestaurantService;
import net.training.ftgo.restaurantservice.events.CreateRestaurantRequest;
import net.training.ftgo.restaurantservice.web.CreateRestaurantResponse;

import static net.training.ftgo.restaurantservice.aws.ApiGatewayResponse.applicationJsonHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RestaurantServiceLambdaConfiguration.class)
public class CreateRestaurantRequestHandler extends AbstractAutowiringHttpRequestHandler {

  @Autowired
  private RestaurantService restaurantService;

  @Override
  protected Class<?> getApplicationContextClass() {
    return CreateRestaurantRequestHandler.class;
  }

  @Override
  protected APIGatewayProxyResponseEvent handleHttpRequest(APIGatewayProxyRequestEvent request, Context context) {

    CreateRestaurantRequest crr = JSonMapper.fromJson(request.getBody(), CreateRestaurantRequest.class);

    Restaurant rest = restaurantService.create(crr);

    return ApiGatewayResponse.builder()
            .setStatusCode(200)
            .setObjectBody(new CreateRestaurantResponse(rest.getId()))
            .setHeaders(applicationJsonHeaders())
            .build();

  }
}
