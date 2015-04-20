package co.mobiwise.parser;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.mobiwise.model.Menu;
import co.mobiwise.model.MenuItem;
import co.mobiwise.model.Restaurant;

public class RestaurantParser implements IRestaurantParser{

	@Override
	public Restaurant getRestaurant(String restaurantName) {
		
		Restaurant restaurant = new Restaurant();
		ArrayList<Menu> restaurantMenuList = new ArrayList<>();
		
		try {
			//Fetch document
			Document doc = Jsoup.connect(ParserConstants.BASE_URL + restaurantName)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(10000)
					  .post();
			
			//All details about restaurant
			Element restaurantDetail = doc.getElementById(ParserConstants.ID_RESTAURANT_DETAIL_CONTENT);
			
			//Point detail about restaurant
			Elements restaurantPoints = restaurantDetail
										.getElementsByClass(ParserConstants.CLASS_POINTS)
										.get(0)
										.select(ParserConstants.SPAN_POINT_SEPERATE);
			
			NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
			
			restaurant.setRestauratSpeedPoint(nf.parse(restaurantPoints.get(0).text().toString()).doubleValue());
			restaurant.setRestaurantServicePoint(nf.parse(restaurantPoints.get(1).text().toString()).doubleValue());
			restaurant.setRestaurantFlavorPoint(nf.parse(restaurantPoints.get(2).text().toString()).doubleValue());
			
			Elements restaurantMenus = restaurantDetail
										.getElementsByClass("restaurantDetailBox")
										.select(".None");

			
			for (int i = 0; i < restaurantMenus.size(); i++) {
				
				Element menuElement = restaurantMenus.get(i);
				
				//initialize menu category object
				Menu tempMenu = new Menu();
				//sets name
				tempMenu.setMenuName(menuElement
						.select(ParserConstants.MENU_NAME_CLASS_1)
						.select(ParserConstants.MENU_NAME_CLASS_2).text().toString());
				
				//fill menu items to menu
				ArrayList<MenuItem> restaurantMenuItemList = new ArrayList<>();
				Elements menuItems = menuElement
						.select(ParserConstants.MENU_ITEMS_LIST_CLASS)
						.select("ul")
						.select("li");
				for(Element e : menuItems){
					MenuItem menuItem = new MenuItem();
					menuItem.setMenuItemName(e.getElementsByClass(ParserConstants.MENU_ITEM_NAME_CLASS).select(ParserConstants.MENU_ITEM_NAME_CLASS_2).text().toString());
					menuItem.setMenuItemInfo(e.getElementsByClass(ParserConstants.MENU_ITEM_INFO_CLASS).select("p").text().toString());
					menuItem.setMenuItemPrice(e.getElementsByClass(ParserConstants.MENU_ITEM_PRICE_CLASS).text().toString());
					if(!menuItem.getMenuItemName().equals(""))
						restaurantMenuItemList.add(menuItem);
				}
				tempMenu.setMenuItemList(restaurantMenuItemList);

				if(tempMenu.getMenuItemList().size()>0)
					restaurantMenuList.add(tempMenu);
			}
			
			restaurant.setMenuList(restaurantMenuList);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restaurant;
	}

}
