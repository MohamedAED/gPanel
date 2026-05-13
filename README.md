# gPanel Core Integration Service (Backend)

An enterprise-grade, stateless **Spring Boot 3.x REST API** designed to act as a secure middleware bridge between modern web clients and the official Google Workspace REST engine. This service exposes transactional endpoints to perform secure CRUD operations on Gmail user labels.

---

## 🏗️ Architecture & Technology Stack
*   **Core Engine:** Java 17 / Spring Boot 3.x
*   **Security Context:** Programmatic Spring Security managing Stateless HTTP Basic Authentication.
*   **Cross-Origin Isolation:** Specialized top-tier CORS Preflight Interception Filter processing configurations ahead of the standard web filter chain.
*   **Integration Framework:** Official Google API Client Library for Java (Gmail v1).

---

## 🔐 Google Cloud Platform Core Setup

This service requires a valid Google Cloud Platform (GCP) project wrapper to securely communicate with the Gmail API via OAuth 2.0.

1. Navigate to the [Google Cloud Console](https://google.com).
2. Initialize a new project named `gpanel-service-core`.
3. Locate the **API Library** portal, search for the **Gmail API**, and click **Enable**.
4. Configure the **OAuth Consent Screen**:
    * Set user visibility constraints to **External**.
    * Register your developer email account under the **Test Users** panel (Critical for sandbox testing).
5. Generate Credentials:
    * Select **Create Credentials** → **OAuth Client ID**.
    * Define the application architecture profile explicitly as a **Desktop Application**.
6. Download the resulting credentials payload JSON.
7. Rename the file exactly to `credentials.json` and save it directly in the root directory of this `src/main/resources` folder.

---

## 🗂️ Application Directory Layout

```text
gPanel/
├── src/                  # Java Application Files
└── pom.xml               # Maven Dependency Specifier
```

---

## 🚀 Execution & Operational Quickstart

Ensure a valid Java 17 Development Kit (JDK) configuration is active on your machine environment.

### 1. Clean Compilation and Assembly
Build the localized binaries while ensuring test specifications pass smoothly:
```bash
./mvnw clean package
```

### 2. Launch the Application Service
Run the integrated Spring Boot instance locally:
```bash
./mvnw spring-boot:run
```
The service container will initialize and bind automatically to network port `8080`.

*Note on Runtime Initialization: Upon invoking your first protected API route from the UI, an interactive automated browser window will open locally. Authenticate using your registered GCP test account. Success parameters will write persistent access credentials into an automated system folder named `src/main/resources/tokens/` inside this project directory.*

---

## 🛡️ Enterprise Security Configuration Blueprint

To completely eliminate preflight cross-origin blocks when processing secure payloads, this service intercepts incoming browser requests prior to hitting standard authentication blockades:

```java
@Bean
public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:3000"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    config.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
}
```

---

## 🛑 Secrets and Version Isolation Policy

Sensitive runtime descriptor files must **never** be committed to version control. Ensure your local environment `.gitignore` rules strictly encompass the following:
```text
src/main/resources/credentials.json
src/main/resources/tokens/
target/
.mvn/wrapper/
*.class
```
