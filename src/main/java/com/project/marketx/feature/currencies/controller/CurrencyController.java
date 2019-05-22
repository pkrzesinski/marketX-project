package com.project.marketx.feature.currencies.controller;

import com.project.marketx.feature.currencies.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/currencies")
public class CurrencyController {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyController.class);
    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/main")
    public String freeMarker(Model model) {
        model.addAttribute("message", "Spring Boot" + "FreeMarker");
        return "test";
    }
}
