package co.mobiwise.parser;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import co.mobiwise.model.Comment;
import co.mobiwise.model.Menu;
import co.mobiwise.model.MenuItem;
import co.mobiwise.model.Restaurant;

public class RestaurantParser implements IRestaurantParser {

	@Override
	public Restaurant getRestaurant(String restaurantName) {

		Restaurant restaurant = new Restaurant();

		try {
			// Fetch document
			Document doc = Jsoup
					.connect(ParserConstants.BASE_URL + restaurantName)
					.data("query", "Java").userAgent("Mozilla")
					.cookie("auth", "token").timeout(10000).post();

			// All details about restaurant
			Element restaurantDetail = doc.getElementById(ParserConstants.ID_RESTAURANT_DETAIL_CONTENT);

			// Point detail about restaurant
			restaurant = getRestaurantPoints(restaurantDetail, restaurant);
			
			//set menus
			restaurant.setMenuList(getRestaurantMenu(restaurantDetail));
			
			//set comments
			restaurant.setCommentList(getRestaurantComments(restaurantName));

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return restaurant;
	}

	/**
	 * By given restaurant detail Element, this method parse for menus and menu
	 * products.
	 * @param restaurantDetail
	 * @return
	 */
	private ArrayList<Menu> getRestaurantMenu(Element restaurantDetail) {
		ArrayList<Menu> restaurantMenuList = new ArrayList<>();
		Elements restaurantMenus = restaurantDetail.getElementsByClass(
				"restaurantDetailBox").select(".None");

		for (int i = 0; i < restaurantMenus.size(); i++) {

			Element menuElement = restaurantMenus.get(i);

			// initialize menu category object
			Menu tempMenu = new Menu();
			// sets name
			tempMenu.setMenuName(menuElement
					.select(ParserConstants.MENU_NAME_CLASS_1)
					.select(ParserConstants.MENU_NAME_CLASS_2).text()
					.toString());

			// fill menu items to menu
			ArrayList<MenuItem> restaurantMenuItemList = new ArrayList<>();
			Elements menuItems = menuElement
					.select(ParserConstants.MENU_ITEMS_LIST_CLASS).select("ul")
					.select("li");
			for (Element e : menuItems) {
				MenuItem menuItem = new MenuItem();
				menuItem.setMenuItemName(e
						.getElementsByClass(
								ParserConstants.MENU_ITEM_NAME_CLASS)
						.select(ParserConstants.MENU_ITEM_NAME_CLASS_2).text()
						.toString());
				menuItem.setMenuItemInfo(e
						.getElementsByClass(
								ParserConstants.MENU_ITEM_INFO_CLASS)
						.select("p").text().toString());
				menuItem.setMenuItemPrice(e
						.getElementsByClass(
								ParserConstants.MENU_ITEM_PRICE_CLASS).text()
						.toString());
				if (!menuItem.getMenuItemName().equals(""))
					restaurantMenuItemList.add(menuItem);
			}
			tempMenu.setMenuItemList(restaurantMenuItemList);

			if (tempMenu.getMenuItemList().size() > 0)
				restaurantMenuList.add(tempMenu);
		}
		
		return restaurantMenuList;
	}

	/**
	 * Fetch Restaurant avarage points.
	 * @param restaurantDetail
	 * @param restaurant
	 * @return
	 * @throws ParseException
	 */
	private Restaurant getRestaurantPoints(Element restaurantDetail, Restaurant restaurant) throws ParseException{
		Elements restaurantPoints = restaurantDetail
				.getElementsByClass(ParserConstants.CLASS_POINTS).get(0)
				.select(ParserConstants.SPAN_POINT_SEPERATE);

		NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);

		restaurant.setRestauratSpeedPoint(nf.parse(restaurantPoints.get(0).text().toString()).doubleValue());
		restaurant.setRestaurantServicePoint(nf.parse(
				restaurantPoints.get(1).text().toString()).doubleValue());
		restaurant.setRestaurantFlavorPoint(nf.parse(
				restaurantPoints.get(2).text().toString()).doubleValue());
		
		return restaurant;
	}

	/**
	 * Fetch User comments and points about restaurant.
	 * This method fetching only first page.
	 * Pagination is not added yet.
	 * @param restaurantHref
	 * @return
	 * @throws IOException
	 */
	private List<Comment> getRestaurantComments(String restaurantHref) throws IOException{
		
		List<Comment> commentList = new ArrayList<>();
		Document doc = Jsoup
				.connect(ParserConstants.BASE_URL + restaurantHref + ParserConstants.COMMENTS_URL)
				.data("query", "Java").userAgent("Mozilla")
				.cookie("auth", "token").timeout(10000).post();
		
		Elements allComments = doc.getElementsByClass(ParserConstants.CLASS_COMMENTS)
				.select(ParserConstants.CLASS_COMMENTS_AREA)
				.select(ParserConstants.CLASS_COMMENTS_BODY);
		
		for(Element e : allComments){
			Comment comment = new Comment();
			
			//Points
			Elements commentPoints = e.getElementsByClass(ParserConstants.CLASS_COMMENTS_POINTS);
			comment.setSpeedPoint(commentPoints.select(ParserConstants.CLASS_COMMENTS_POINTS_SPEED).text().toString());
			comment.setServicePoint(commentPoints.select(ParserConstants.CLASS_COMMENTS_POINTS_SERVICE).text().toString());
			comment.setFlavorPoint(commentPoints.select(ParserConstants.CLASS_COMMENTS_POINTS_FLAVOUR).text().toString());

			//Date
			String commentDate = e.getElementsByClass(ParserConstants.CLASS_COMMENTS_COMMENT_DATE).first().text().toString();
			comment.setDate(commentDate);
			
			//Comment
			String commentText = e.getElementsByClass(ParserConstants.CLASS_COMMENTS_COMMENT).text().toString();
			comment.setComment(commentText);
			
			commentList.add(comment);
		}
		
		return commentList;
	}
}
