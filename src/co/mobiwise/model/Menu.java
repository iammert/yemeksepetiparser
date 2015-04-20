package co.mobiwise.model;

import java.util.List;

public class Menu {
	
	String menuName;
	
	List<MenuItem> menuItemList;
	
	public Menu() {
		// TODO Auto-generated constructor stub
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<MenuItem> getMenuItemList() {
		return menuItemList;
	}

	public void setMenuItemList(List<MenuItem> menuItemList) {
		this.menuItemList = menuItemList;
	}

}
