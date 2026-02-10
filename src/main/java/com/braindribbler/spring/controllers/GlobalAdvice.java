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
    public void addGitInfo2(Model model) {
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

    @ModelAttribute
    public void addGitInfo(Model model) {
        if (gitProperties != null) {
            // 1. Get the tag (Populated when on a tag/detached HEAD in prod)
            String tag = gitProperties.get("closest.tag.name");
            // 2. Get the branch (Returns hash in detached HEAD, or "main" in dev)
            String branch = gitProperties.getBranch();

            // If we have a valid tag, use it; otherwise use the branch name
            String displayVersion = (tag != null && !tag.isEmpty()) ? tag : branch;

            model.addAttribute("gitBranch", displayVersion);
            model.addAttribute("gitHash", gitProperties.getShortCommitId());
        } else {
            model.addAttribute("gitBranch", "unknown");
            model.addAttribute("gitHash", "0000");
        }
    }
}
