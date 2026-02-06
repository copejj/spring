package com.braindribbler.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.braindribbler.spring.models.menus.MenuItem;
import com.braindribbler.spring.security.UserDetailsImpl;

@Service
public class BaseController {
	protected List<MenuItem> getDefaultMenus(String activeMenu) {
		boolean isAdmin = false;
		boolean isLoggedIn = false;
		String profileName = "Me/Myself";

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			if (auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
				isLoggedIn = true;
				UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
				profileName = details.getUser().getFirstName();
			}

			for (GrantedAuthority authority : auth.getAuthorities()) {
				if (authority.getAuthority().equals("ROLE_ADMIN")) {
					isAdmin = true;
					break;
				}
			}
		}

		List<MenuItem> menus = new ArrayList<>();
		menus.add(new MenuItem("/", "Home"));

		if (isLoggedIn) {
			if (isAdmin) {
				menus.add(new MenuItem("/users", "Users"));
				menus.add(new MenuItem("/admin/migrations", "Admin"));
			}
			menus.add(new MenuItem("/companies", "Companies"));
			menus.add(new MenuItem("/logout", "Logout", "align-right"));
			menus.add(new MenuItem("/users/current", profileName, "align-right"));
		} else {
			menus.add(new MenuItem("/login", "Login", "align-right"));
			menus.add(new MenuItem("/about", "About", "align-right"));
		}


		return menus;
	}
}
