package com.tracker.CovidTracker.Controllers;

import com.tracker.CovidTracker.models.locationstat;
import com.tracker.CovidTracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model){
        List<locationstat> allstats=coronaVirusDataService.getAllstats();
        int totalReportedCases = allstats.stream().mapToInt(stat -> (int) stat.getConfirmedCases()).sum();
        model.addAttribute("locationStats",allstats);
        model.addAttribute("totalReportedCases",totalReportedCases);
        return "home";
    }
}
