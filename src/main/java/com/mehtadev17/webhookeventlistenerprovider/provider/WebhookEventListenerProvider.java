package com.mehtadev17.webhookeventlistenerprovider.provider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class WebhookEventListenerProvider implements EventListenerProvider {
    private CloseableHttpClient httpclient = HttpClients.createDefault();
    private HttpPost request = new HttpPost(System.getenv("EVENT_WEBHOOK_URL"));

    public WebhookEventListenerProvider() {
    }

    @Override
    public void onEvent(Event event) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(event);
        } catch (JsonProcessingException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        this.sendEvent(jsonString);
        
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(adminEvent);
        } catch (JsonProcessingException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        this.sendEvent(jsonString);
    }

    @Override
    public void close() {

    }

    private void sendEvent(String jsonString) {
        request.addHeader("content-type", "application/json");
        try {
            request.setEntity(new StringEntity(jsonString));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        try {
            HttpResponse httpresponse = httpclient.execute(request);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}