package com.braindribbler.spring.controllers.logs;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.braindribbler.spring.dto.logs.LogDTO;
import com.braindribbler.spring.forms.logs.LogForm;
import com.braindribbler.spring.security.UserDetailsImpl;
import com.braindribbler.spring.service.companies.CompanyService;
import com.braindribbler.spring.service.logs.LogService;
import com.braindribbler.spring.service.logs.WeekService;

import jakarta.validation.Valid;

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
        model.addAttribute("title", "Applications List");
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

		LogDTO dto = logService.getLogDtoById(logId);

        LogForm form = new LogForm();
        form.setLogId(dto.logId());
        form.setTitle(dto.title());
        form.setJobNumber(dto.jobNumber());
        form.setNextStep(dto.nextStep());
        form.setNotes(dto.notes());
        form.setConfirmation(dto.confirmation());
        form.setContact(dto.contact());
        form.setContactNumber(dto.contactNumber());
        form.setCompanyId(dto.companyId());
        form.setActionDate(dto.actionDate());

        Long userId = userDetails.getUserId();

		model.addAttribute("location", "Edit Log");
		model.addAttribute("title", "Application Information");

		model.addAttribute("log", form);
        model.addAttribute("companies", companyService.getAll(userId));

		return "logs/edit";
	}

        @GetMapping("/create")
        public String createLogForm(
                @AuthenticationPrincipal UserDetailsImpl userDetails,
                Model model) {

        model.addAttribute("log", new LogForm());
        model.addAttribute("companies", companyService.getAll(userDetails.getUserId()));
        
        model.addAttribute("location", "New Log");
		model.addAttribute("title", "Application Information");

        return "logs/edit";
    }

    @PostMapping("/save")
    public String saveLog(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @ModelAttribute("log") LogForm logForm,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("location", logForm.getLogId() == null ? "New Log" : "Edit Log");
            model.addAttribute("companies", companyService.getAll(userDetails.getUserId()));
            return "logs/edit";
        }

        try {
            Long savedId = logService.saveFromForm(logForm, userDetails.getUserId());
            
            redirectAttributes.addFlashAttribute("saveSuccess", "Log saved successfully!");
            return "redirect:/logs/edit/" + savedId;
        } catch (Exception e) {
            model.addAttribute("saveError", "Error: " + e.getMessage());
            model.addAttribute("companies", companyService.getAll(userDetails.getUserId()));
            return "logs/edit";
        }
    }
}
