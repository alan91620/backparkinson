package com.ece.parkisonditributed.back;

import okhttp3.Call;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class Caller {

    private static String targetUrl;
    @Value("${app.targetUrl}")
    public void settargetUrl(String targetUrl) { Caller.targetUrl = targetUrl; }

    public static JSONObject postAudio(String path){

        HttpResponse<String> response;

        JSONObject json = new JSONObject();
        json.put("path", path);

        JSONObject resp = null;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(targetUrl))
                .setHeader("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        try {
            response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            resp = new JSONObject(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return resp;

    }
}
