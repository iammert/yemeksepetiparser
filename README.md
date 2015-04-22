# yemeksepetiparser

#Features

*Fetch restaurant menus and menu products.
*Fetch user comments about restaurant.

#Usage
```java
		IRestaurantParser parser = new RestaurantParser();

		//Fetch restaurant menus, menu products, user comments about restaurant
		Restaurant restaurant = parser.getRestaurant("restaurant_url_href");
		
		//Menu
		List<Menu> menu = restaurant.getMenuList();
		
		//Comments
		List<Comment> comments = restaurant.getCommentList();
```
