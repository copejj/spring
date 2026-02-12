package com.braindribbler.spring.controllers.logs;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.braindribbler.spring.dto.logs.LogDTO;
import com.braindribbler.spring.models.companies.Company;
import com.braindribbler.spring.models.logs.Week;
import com.braindribbler.spring.security.UserDetailsImpl;
import com.braindribbler.spring.service.companies.CompanyService;
import com.braindribbler.spring.service.logs.LogService;
import com.braindribbler.spring.service.logs.WeekService;

@Controller
@RequestMapping("/logs")
public class LogController {
    private final LogService logService;
    private final CompanyService companyService;
    private final WeekService weekService;

    public LogController(LogService logService, CompanyService companyService, WeekService weekService) {
        this.logService = logService;
        this.companyService = companyService;
        this.weekService = weekService;
    }

    @GetMapping("/list")
    public String viewLogs(
            @AuthenticationPrincipal UserDetailsImpl userDetails, // User ID handled internally
            @RequestParam(required = false) Long weekId,
            @RequestParam(required = false) Long companyId,
            Model model) { // Model for passing data to the template

        List<LogDTO> logs = logService.findLogs(userDetails.getUserId(), weekId, companyId);

        model.addAttribute("location", "Logs");
        model.addAttribute("title", "Applied Job Logs");
        model.addAttribute("logs", logs);
        model.addAttribute("selectedWeek", weekId);
        model.addAttribute("selectedCompany", companyId);

        return "logs/list"; 
    }

	@GetMapping("/edit/{logId}")
	public String editLog(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long logId,
        Model model) {

		LogDTO logDto = logService.getLogDtoById(logId);
        
        Long userId = userDetails.getUserId();
        List<Company> companies = companyService.getAll(userId);
        List<Week> weeks = weekService.getAll();

		model.addAttribute("location", "Edit Log");
		model.addAttribute("title", "Log Information");

		model.addAttribute("log", logDto);
        model.addAttribute("companies", companies);
        model.addAttribute("weeks", weeks);

		return "logs/edit";
	}
}
