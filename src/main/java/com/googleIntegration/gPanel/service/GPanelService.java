package com.googleIntegration.gPanel.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GPanelService {

    private final Gmail gmailService;
    private static final String USER = "me";

    @Autowired
    public GPanelService(Gmail gmailService) {
        this.gmailService = gmailService;
    }

    public void printGmailLabels() throws IOException {
        // Print the labels in the user's account.
        ListLabelsResponse listResponse = gmailService.users().labels().list(USER).execute();
        List<Label> labels = listResponse.getLabels();

        if (labels.isEmpty()) {
            System.out.println("No labels found.");
        } else {
            System.out.println("Labels:");
            for (Label label : labels) {
                System.out.printf("- %s\n", label.getName());
            }
        }
    }

}
