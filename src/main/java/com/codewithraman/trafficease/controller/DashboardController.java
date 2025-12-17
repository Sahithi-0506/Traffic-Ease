package com.codewithraman.trafficease.controller;

import com.codewithraman.trafficease.service.TrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private TrafficService trafficService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("trafficData", trafficService.getTrafficStats());
        return "dashboard";  // dashboard.html
    }
}
