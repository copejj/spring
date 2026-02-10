package com.braindribbler.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalAdvice {

	@Autowired
	private Environment environment;

	@Autowired(required = false)
    private GitProperties gitProperties;

    @ModelAttribute
    public void addGitInfo(Model model) {
        if (gitProperties != null) {
            // 1. Try to get the tag first (best for Production)
            String version = gitProperties.get("closest.tag.name");
            
            // 2. If no tag exists or it's empty, use the branch (best for Dev)
            if (version == null || version.isEmpty() || version.equals(gitProperties.getShortCommitId())) {
                version = gitProperties.getBranch();
            }

            model.addAttribute("gitBranch", version);
            model.addAttribute("gitHash", gitProperties.getShortCommitId());
        } else {
            model.addAttribute("gitBranch", "unknown");
            model.addAttribute("gitHash", "0000");
        }
    }
}
