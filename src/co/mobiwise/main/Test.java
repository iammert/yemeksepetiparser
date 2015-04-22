package co.mobiwise.main;

import java.util.List;

import co.mobiwise.model.Comment;
import co.mobiwise.parser.IRestaurantParser;
import co.mobiwise.parser.ParserConstants;
import co.mobiwise.parser.RestaurantParser;

import com.owlike.genson.Genson;

public class Test {

	public static void main(String[] args) {

		IRestaurantParser parser = new RestaurantParser();

		List<Comment> restaurant = parser.getRestaurant(ParserConstants.TEST_RESTAURANT_NAME).getCommentList();
		
		Genson genson = new Genson();
		String restaurantJson = genson.serialize(restaurant);
		System.out.println(restaurantJson);


	}

}
