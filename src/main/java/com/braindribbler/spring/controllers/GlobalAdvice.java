package com.braindribbler.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.GitProperties;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalAdvice {

	@Autowired(required = false)
    private GitProperties gitProperties;

    @ModelAttribute
    public void addGitInfo(Model model) {
        if (gitProperties != null) {
            String tag = gitProperties.get("closest.tag.name");
            String branch = gitProperties.getBranch();
            String fullHash = gitProperties.get("commit.id");
            String shortHash = gitProperties.getShortCommitId();

            String displayVersion;

            // 1. If we are in 'Detached HEAD' (common in Prod), branch will be the full hash
            if (branch != null && branch.equals(fullHash)) {
                // Use tag if available, otherwise use short hash
                displayVersion = (tag != null && !tag.isEmpty()) ? tag : shortHash;
            } else {
                // 2. We are on a real branch (Local Dev)
                displayVersion = (branch != null) ? branch : "unknown";
            }

            model.addAttribute("gitBranch", displayVersion);
            model.addAttribute("gitHash", shortHash);
            model.addAttribute("showGitInfo", true);
        } else {
            model.addAttribute("showGitInfo", false);
        }
    }
}
