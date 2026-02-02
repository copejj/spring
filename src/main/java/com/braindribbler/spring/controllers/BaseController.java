package com.braindribbler.spring.controllers;

import java.util.List;

import com.braindribbler.spring.models.menus.MenuItem;

public class BaseController {
	protected List<MenuItem> getDefaultMenus(String activeMenu) {
		return List.of(
			new MenuItem("/", "Home", activeMenu.equals("home") ? "link-selected" : ""),
			new MenuItem("/users", "Users", activeMenu.equals("users") ? "link-selected" : ""),
			new MenuItem("/dashboard", "Dash", activeMenu.equals("dashboard") ? "link-selected" : ""),
			new MenuItem("/about", "About", "align-right " + (activeMenu.equals("about") ? "link-selected" : "")),
			new MenuItem("/login", "Login", "align-right " + (activeMenu.equals("login") ? "link-selected" : ""))
		);
	}
}
