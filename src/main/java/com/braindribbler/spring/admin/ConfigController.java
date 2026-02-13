package com.braindribbler.spring.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.braindribbler.spring.enums.admin.ConfigEnvironment;
import com.braindribbler.spring.models.admin.Config;
import com.braindribbler.spring.service.admin.ConfigService;

@Controller
@RequestMapping("/admin/configs") // All routes now start with /admin/configs
public class ConfigController {

    private final ConfigService configService;

    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping
    public String listConfigs(Model model) {
        // Fetches DEV + ANY configs as an example
        model.addAttribute("configs", configService.getActiveConfigs(ConfigEnvironment.DEV));
        return "admin/config-list"; 
    }

    @PostMapping("/save")
    public String saveConfig(@ModelAttribute Config config) {
        configService.saveConfig(config);
        return "redirect:/admin/configs"; // Redirect back to the list
    }

    @PostMapping("/deactivate/{id}")
    public String deactivate(@PathVariable Long id) {
        configService.deactivateConfig(id);
        return "redirect:/admin/configs";
    }
}
