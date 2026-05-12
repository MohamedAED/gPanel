package com.googleIntegration.gPanel.controller;

import com.googleIntegration.gPanel.dto.LabelDTO;
import com.googleIntegration.gPanel.service.GPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/gPanel")
public class GPanelController {

    private final GPanelService gPanelService;

    @Autowired
    public GPanelController(GPanelService gPanelService) {
        this.gPanelService = gPanelService;
    }

    @GetMapping("/labels")
    public ResponseEntity<List<LabelDTO>> getLabels() throws IOException {
        List<LabelDTO> labelDTOList = gPanelService.getLabels();
        if (labelDTOList.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(labelDTOList);
    }

    @GetMapping("/labels/{id}")
    public ResponseEntity<LabelDTO> getLabelById(@PathVariable("id") String labelId) throws IOException {
        LabelDTO labelDTO = gPanelService.getLabelById(labelId);

        if (labelDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(labelDTO);
    }

    @PostMapping("/labels")
    public ResponseEntity<String> createLabel(@RequestBody LabelDTO labelDTO) throws IOException {
        String labelName = gPanelService.create(labelDTO);
        if (labelName != null)
            return ResponseEntity.ok(labelName);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/labels/{id}")
    public ResponseEntity<String> deleteLabel(@PathVariable("id") String labelId) throws IOException {
        if (gPanelService.delete(labelId))
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/labels")
    public ResponseEntity<String> updateLabel(@RequestBody LabelDTO labelDTO) throws IOException {
        String labelName = gPanelService.update(labelDTO);
        if (labelName != null)
            return ResponseEntity.ok(labelName);
        return ResponseEntity.notFound().build();
    }

}
