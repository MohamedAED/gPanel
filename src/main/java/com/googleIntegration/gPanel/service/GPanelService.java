package com.googleIntegration.gPanel.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.googleIntegration.gPanel.dto.LabelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GPanelService {

    private final Gmail gmailService;
    private static final String USER = "me";

    @Autowired
    public GPanelService(Gmail gmailService) {
        this.gmailService = gmailService;
    }

    public List<LabelDTO> getLabels() throws IOException {
        // Print the labels in the user's account.
        ListLabelsResponse listResponse = gmailService.users().labels().list(USER).execute();
        List<Label> labels = listResponse.getLabels();

        if (labels.isEmpty()) {
            return Collections.emptyList();
        }
        return labels.stream().map(label -> new LabelDTO(label.getId(), label.getName())).collect(Collectors.toList());
    }

}
