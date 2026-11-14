package com.acme.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Sobe a aplicação inteira, como no container, e confere o contrato com a plataforma:
 * os health checks que o Kubernetes usa (liveness e readiness).
 */
class ApplicationTest {

    private static final HttpClient HTTP = HttpClient.newHttpClient();
    private static ConfigurableApplicationContext context;

    @BeforeAll
    static void sobe() {
        context = SpringApplication.run(Application.class, "--server.port=0");
    }

    @AfterAll
    static void desce() {
        context.close();
    }

    @Test
    void liveness() throws Exception {
        assertThat(get("/actuator/health/liveness")).contains("\"status\":\"UP\"");
    }

    @Test
    void readiness() throws Exception {
        assertThat(get("/actuator/health/readiness")).contains("\"status\":\"UP\"");
    }

    @Test
    void mainSobeSemErro() {
        // o main é o ponto de entrada do container; aqui sem servidor web, para não disputar porta
        Application.main(new String[] {"--spring.main.web-application-type=none"});
    }

    private static String get(String path) throws Exception {
        String port = context.getEnvironment().getProperty("local.server.port");
        HttpResponse<String> response = HTTP.send(
                HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).build(),
                HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(200);
        return response.body();
    }
}
