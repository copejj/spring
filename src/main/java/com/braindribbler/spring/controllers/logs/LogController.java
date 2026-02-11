package com.braindribbler.spring.controllers.logs;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.braindribbler.spring.dto.logs.LogDTO;
import com.braindribbler.spring.security.UserDetailsImpl;
import com.braindribbler.spring.service.logs.LogService;

@Controller
@RequestMapping("/logs")
public class LogController {
    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/list")
    public String viewLogs(
            @AuthenticationPrincipal UserDetailsImpl userDetails, // User ID handled internally
            @RequestParam(required = false) Long weekId,
            @RequestParam(required = false) Long companyId,
            Model model) { // Model for passing data to the template

        List<LogDTO> logs = logService.findLogs(userDetails.getUserId());

        model.addAttribute("location", "Logs");
        model.addAttribute("title", "Applied Job Logs");
        model.addAttribute("logs", logs);
        model.addAttribute("selectedWeek", weekId);
        model.addAttribute("selectedCompany", companyId);

        return "logs/list"; 
    }
}
