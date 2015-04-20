package co.mobiwise.model;

import java.util.List;

public class Restaurant {

	String restaurantName;

	String restaurantHref;
	
	double restauratSpeedPoint;
	
	double restaurantServicePoint;
	
	double restaurantFlavorPoint;
	
	List<Menu> menuList;
	
	public Restaurant() {
		// TODO Auto-generated constructor stub
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public double getRestauratSpeedPoint() {
		return restauratSpeedPoint;
	}

	public void setRestauratSpeedPoint(double restauratSpeedPoint) {
		this.restauratSpeedPoint = restauratSpeedPoint;
	}

	public double getRestaurantServicePoint() {
		return restaurantServicePoint;
	}

	public void setRestaurantServicePoint(double restaurantServicePoint) {
		this.restaurantServicePoint = restaurantServicePoint;
	}

	public double getRestaurantFlavorPoint() {
		return restaurantFlavorPoint;
	}

	public void setRestaurantFlavorPoint(double restaurantFlavorPoint) {
		this.restaurantFlavorPoint = restaurantFlavorPoint;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	
}
