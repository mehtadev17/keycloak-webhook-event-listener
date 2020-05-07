package com.mehtadev17.webhookeventlistenerprovider.provider;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import java.util.ArrayList;
import java.util.Arrays;
import org.keycloak.events.EventType;
import java.util.List;

public class WebhookEventListenerProviderFactory implements EventListenerProviderFactory {

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {
        String eventWebhookUrl = System.getenv("EVENT_WEBHOOK_URL");
        if(eventWebhookUrl == null) {
            eventWebhookUrl = "http://localhost:5000/webhook";
        }
        String eventTypesWebhookEnvVar = System.getenv("EVENT_WEBHOOK_TYPES");
        if (eventTypesWebhookEnvVar == null) {
            eventTypesWebhookEnvVar = "IDENTITY_PROVIDER_FIRST_LOGIN,LOGIN";
        }
        List<EventType> eventTypes = new ArrayList<EventType>();
        List<String> eventTypeRawList = new ArrayList<String>(Arrays.asList(eventTypesWebhookEnvVar.split(",")));
        for (String eventRaw : eventTypeRawList) {
            eventTypes.add(EventType.valueOf(eventRaw));
        }
        Boolean enableAdminEventWebhook = false;
        return new WebhookEventListenerProvider(eventWebhookUrl, eventTypes, enableAdminEventWebhook);
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return "keycloak-webhook-event-listener";
    }
}