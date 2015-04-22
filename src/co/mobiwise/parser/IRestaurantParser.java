package co.mobiwise.parser;

import co.mobiwise.model.Restaurant;

public interface IRestaurantParser {
	
	/**
	 * Get restaurant menus, menu products, user comments.
	 * @param restaurant
	 * @return
	 */
	Restaurant getRestaurant(String restaurant);

}
