package com.googleIntegration.gPanel.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Configuration
public class GmailConfig {

    private static final String APPLICATION_NAME = "gPanelGoogleIntegration";
    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/tokens";
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    private static final String TEST_USER = "user";
    private static final int PORT = 8888;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final NetHttpTransport  HTTP_TRANSPORT = new NetHttpTransport();
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);

    private Credential getCredentials() throws IOException {

        // Load from src/main/resources/credentials.json
        ClassPathResource resource = new ClassPathResource(CREDENTIALS_FILE_PATH);
        InputStreamReader reader = new InputStreamReader(resource.getInputStream());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);


        // This is where the scope is utilized in the flow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(PORT).build();

        //returns an authorized Credential object.
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(TEST_USER);

    }

    @Bean
    public Gmail gmailService() throws GeneralSecurityException, IOException {
         return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

}
