package com.braindribbler.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.braindribbler.spring.models.menus.MenuItem;

@Service
public class BaseController {
	protected List<MenuItem> getDefaultMenus(String activeMenu) {
		boolean isAdmin = false;
		boolean isLoggedIn = false;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			isLoggedIn = auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser");

			for (GrantedAuthority authority : auth.getAuthorities()) {
				if (authority.getAuthority().equals("ROLE_ADMIN")) {
					isAdmin = true;
					break;
				}
			}
		}

		List<MenuItem> menus = new ArrayList<>();
		menus.add(new MenuItem("/", "Home", activeMenu.equals("home") ? "link-selected" : ""));

		if (isLoggedIn) {
			if (isAdmin) {
				menus.add(new MenuItem("/users", "Users", activeMenu.equals("users") ? "link-selected" : ""));
			}
			menus.add(new MenuItem("/logout", "Logout", "align-right " + (activeMenu.equals("logout") ? "link-selected" : "")));
			menus.add(new MenuItem("/user/id", "My Profile", "align-right " + (activeMenu.equals("user/id") ? "link-selected" : "")));
		} else {
			menus.add(new MenuItem("/login", "Login", "align-right " + (activeMenu.equals("login") ? "link-selected" : "")));
			menus.add(new MenuItem("/about", "About", "align-right " + (activeMenu.equals("about") ? "link-selected" : "")));
		}


		return menus;
	}
}
