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
        return labels.stream().map(
                label -> LabelDTO.builder().
                        id(label.getId()).
                        name(label.getName()).
                        type(label.getType())
                        .build()
        ).collect(Collectors.toList());
    }

    public LabelDTO getLabelById(String labelId) throws IOException {
        Label label = gmailService.users().labels().get(USER, labelId).execute();

        if (label == null) {
            return null;
        }
        return LabelDTO.builder()
                .id(label.getId())
                .name(label.getName())
                .type(label.getType())
                .labelListVisibility(label.getLabelListVisibility())
                .messageListVisibility(label.getMessageListVisibility())
                .build();

    }

    public String create(LabelDTO labelDTO) throws IOException {
        Label label = new Label()
                .setName(labelDTO.getName())
                .setLabelListVisibility("labelShow")
                .setMessageListVisibility("show");

        Label createdLabel = gmailService.users().labels().create(USER, label).execute();
        if (createdLabel != null)
            return createdLabel.getName();
        return null;
    }

    public Boolean delete(String labelId) throws IOException {
        try  {
            gmailService.users().labels().delete(USER, labelId).execute();
        }  catch (IOException ex) {
            return false;
        }
        return true;
    }

    public String update (LabelDTO labelDTO) throws IOException {
        Label updatedLabel = new Label()
                .setId(labelDTO.getId())
                .setName(labelDTO.getName())
                .setLabelListVisibility("labelShow")
                .setMessageListVisibility("show");

        Label result = gmailService.users().labels().update(USER, labelDTO.getId(), updatedLabel).execute();

        if (result != null)
            return result.getName();
        return null;

    }
}
