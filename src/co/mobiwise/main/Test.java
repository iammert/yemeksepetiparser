package co.mobiwise.main;

import com.owlike.genson.Genson;

import co.mobiwise.model.Restaurant;
import co.mobiwise.parser.IRestaurantParser;
import co.mobiwise.parser.ParserConstants;
import co.mobiwise.parser.RestaurantParser;

public class Test {

	public static void main(String[] args) {

		IRestaurantParser parser = new RestaurantParser();

		Restaurant restaurant = parser.getRestaurant(ParserConstants.TEST_RESTAURANT_NAME);
		
		Genson genson = new Genson();
		String restaurantJson = genson.serialize(restaurant);
		System.out.println(restaurantJson);


	}

}
