package com.braindribbler.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.braindribbler.spring.models.menus.MenuItem;
import com.braindribbler.spring.security.UserDetailsImpl;

@ControllerAdvice
public class MenuAdvice {

    @ModelAttribute("menuItems") 
    public List<MenuItem> menuItems(@AuthenticationPrincipal UserDetailsImpl user) {
        List<MenuItem> list = new ArrayList<>();
        
        // Links for everyone
        list.add(new MenuItem("/", "Home"));

        if (user != null) {
            boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            list.add(new MenuItem("/companies", "Companies"));

            if (isAdmin) {
                list.add(new MenuItem("/users", "Users"));
                list.add(new MenuItem("/admin/migrations", "Admin"));
            }

            list.add(new MenuItem("/logout", "Logout", "align-right"));
        } else {
            // Links for GUESTS only
            list.add(new MenuItem("/login", "Login", "align-right"));
        }

        list.add(new MenuItem("/about", "About", "align-right"));
        
        return list;
    }
}
