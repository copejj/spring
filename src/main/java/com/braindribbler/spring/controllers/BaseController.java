package com.braindribbler.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.braindribbler.spring.models.menus.MenuItem;

@Service
public class BaseController {
	protected List<MenuItem> getDefaultMenus(String activeMenu) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isLoggedIn = auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");

		List<MenuItem> menus = new ArrayList<>();
		menus.add(new MenuItem("/", "Home", activeMenu.equals("home") ? "link-selected" : ""));

		if (isLoggedIn) {
			menus.add(new MenuItem("/users", "Users", activeMenu.equals("users") ? "link-selected" : ""));
			menus.add(new MenuItem("/logout", "Logout", "align-right " + (activeMenu.equals("logout") ? "link-selected" : "")));
		} else {
			menus.add(new MenuItem("/login", "Login", "align-right " + (activeMenu.equals("login") ? "link-selected" : "")));
		}

		menus.add(new MenuItem("/about", "About", "align-right " + (activeMenu.equals("about") ? "link-selected" : "")));


		return menus;
		/*
		return List.of(
			new MenuItem("/", "Home", activeMenu.equals("home") ? "link-selected" : ""),
			new MenuItem("/users", "Users", activeMenu.equals("users") ? "link-selected" : ""),

			new MenuItem("/logout", "Logout", "align-right " + (activeMenu.equals("logout") ? "link-selected" : "")),
			new LoginMenuItem(activeMenu),
			new MenuItem("/about", "About", "align-right" + (activeMenu.equals("about") ? "link-selected" : ""))
		);
		*/
	}
}
