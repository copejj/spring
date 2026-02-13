package com.braindribbler.spring.advice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.GitProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.braindribbler.spring.enums.admin.ConfigEnvironment;
import com.braindribbler.spring.models.admin.Config;
import com.braindribbler.spring.repositories.admin.ConfigRepository;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalAdvice {

	@Autowired(required = false)
    private GitProperties gitProperties;

    @Value("${app.external-url}")
    private String externalUrl;

    private final ConfigRepository configRepository;

    @Value("${app.config.envronment:DEV}")
    private ConfigEnvironment currentEnv;

    public GlobalAdvice(ConfigRepository configRepository)
    {
        this.configRepository = configRepository;
    }

    @ModelAttribute("appSettings")
    public Map<String, String> addConfigToModel() {
        // 1. Fetch records for BOTH the current env (PROD/DEV) and 'ANY'
        List<ConfigEnvironment> targetEnvs = List.of(currentEnv, ConfigEnvironment.ANY);
        List<Config> configs = configRepository.findByEnvironmentIn(targetEnvs);

        // 2. Build the map with override logic
        Map<String, String> settings = new HashMap<>();

        // 1. Defaults (ANY)
        configs.stream()
            .filter(c -> c.getEnvironment() == ConfigEnvironment.ANY)
            .forEach(c -> settings.put(c.getName(), c.getValue()));

        // 2. Overrides (Specific Env)
        configs.stream()
            .filter(c -> c.getEnvironment() == currentEnv)
            .forEach(c -> settings.put(c.getName(), c.getValue()));

        return settings;
    }

    @ModelAttribute
    public void addSiteInfo(Model model) {
        model.addAttribute("siteName", "The Brain Dribbler");

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

            model.addAttribute("externalUrl", externalUrl);
            model.addAttribute("gitBranch", displayVersion);
            model.addAttribute("gitHash", shortHash);
            model.addAttribute("showGitInfo", true);
        } else {
            model.addAttribute("showGitInfo", false);
        }
    }
}
