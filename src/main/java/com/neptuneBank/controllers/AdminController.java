/*
 * Copyright (c) 2025 Ramjee Prasad
 * Licensed under a custom Non-Commercial, Attribution, Share-Alike License.
 * See LICENSE file for details.
 *
 *
 */

package com.neptuneBank.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/login")
    public String admin() {
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("username", "Admin");
        model.addAttribute("totalUsers", 1245);
        model.addAttribute("pendingTasks", 87);
        model.addAttribute("uptime", "99.99%");

        List<Map<String, String>> activities = new ArrayList<>();
        activities.add(Map.of("user", "John", "action", "Logged in", "date", "2025-04-19"));
        activities.add(Map.of("user", "Alice", "action", "Added new task", "date", "2025-04-19"));

        model.addAttribute("activities", activities);
        return "dashboard";
    }
}
