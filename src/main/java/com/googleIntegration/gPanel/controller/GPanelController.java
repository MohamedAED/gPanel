package com.googleIntegration.gPanel.controller;

import com.googleIntegration.gPanel.service.GPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/gPanel")
public class GPanelController {

    private final GPanelService GPanelService;

    @Autowired
    public GPanelController(GPanelService GPanelService) {
        this.GPanelService = GPanelService;
    }

    @GetMapping("/printGmailLabels")
    public void printGmailLabels() throws IOException {
        GPanelService.printGmailLabels();
    }


}
