package br.com.driver.finder.server.Connection;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpConnection {

    public String getRequest(String request) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(request))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();
        return this.sendHttpRequest(httpRequest);
    }

    public String postRequest(String request) throws IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(request))
                .timeout(Duration.ofSeconds(15))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        return this.sendHttpRequest(httpRequest);
    }
    private String sendHttpRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
            return httpClient
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .body();
    }

}
