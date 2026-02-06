package com.braindribbler.spring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.braindribbler.spring.models.menus.MenuItem;

@ControllerAdvice
public class MenuAdvice {

    @ModelAttribute("menuItems") // This name MUST match your HTML ${menuItems}
    public List<MenuItem> menuItems() {
        List<MenuItem> list = new ArrayList<>();
        
        // Add your items here
        MenuItem home = new MenuItem("/", "Home");
        
        MenuItem products = new MenuItem("/products", "Products");
        products.getChildren().add(new MenuItem("/laptops", "Laptops"));
        products.getChildren().add(new MenuItem("/phones", "Phones"));
        
        list.add(home);
        list.add(products);
        list.add(new MenuItem("/about", "About"));
        
        return list;
    }
}
