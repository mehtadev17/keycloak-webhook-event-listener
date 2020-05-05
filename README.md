# keycloak-event-listener-spi
A  webhook event listener SPI for keycloak

## Build
```
mvn clean package
```

## Deploy

1. Copy `target/keycloak-webhook-event-listener.jar` to `/opt/jboss/keycloak/standalone/deployments/keycloak-webhook-event-listener.jar`
2. Configure ENV VAR `EVENT_WEBHOOK_URL` to point to a valid webhook