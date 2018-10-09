package net.training.ftgo.orderservice.domain;

import net.training.ftgo.orderservice.api.events.OrderState;
import net.training.ftgo.orderservice.domain.Restaurant;
import net.training.ftgo.orderservice.domain.RestaurantRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import static net.training.ftgo.orderservice.OrderDetailsMother.CONSUMER_ID;
import static net.training.ftgo.orderservice.OrderDetailsMother.chickenVindalooLineItems;
import static net.training.ftgo.orderservice.RestaurantMother.AJANTA_ID;
import static net.training.ftgo.orderservice.RestaurantMother.AJANTA_RESTAURANT_MENU_ITEMS;
import static net.training.ftgo.orderservice.RestaurantMother.AJANTA_RESTAURANT_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderJpaTestConfiguration.class)
public class RestaurantJpaTest {

  @Autowired
  private RestaurantRepository restaurantRepository;

  @Autowired
  private TransactionTemplate transactionTemplate;

  @Test
  public void shouldSaveRestaurant() {

    transactionTemplate.execute((ts) -> {
      Restaurant restaurant = new Restaurant(AJANTA_ID, AJANTA_RESTAURANT_NAME, AJANTA_RESTAURANT_MENU_ITEMS);
      restaurantRepository.save(restaurant);
      return null;
    });
    transactionTemplate.execute((ts) -> {
      Restaurant restaurant = new Restaurant(AJANTA_ID, AJANTA_RESTAURANT_NAME, AJANTA_RESTAURANT_MENU_ITEMS);
      restaurantRepository.save(restaurant);
      return null;
    });



  }

}
