package com.googleIntegration.gPanel.controller;

import com.googleIntegration.gPanel.dto.LabelDTO;
import com.googleIntegration.gPanel.service.GPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/gPanel")
public class GPanelController {

    private final GPanelService GPanelService;

    @Autowired
    public GPanelController(GPanelService GPanelService) {
        this.GPanelService = GPanelService;
    }

    @GetMapping("/")
    public ResponseEntity getAllLabels() {
        return ResponseEntity.ok("gPanel App");
    }

    @GetMapping("/labels")
    public ResponseEntity<List<LabelDTO>> printGmailLabels() throws IOException {
        List<LabelDTO> labelDTOList = GPanelService.getLabels();
        if (labelDTOList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(labelDTOList);
    }

}
