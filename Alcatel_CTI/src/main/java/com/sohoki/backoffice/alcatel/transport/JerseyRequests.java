package com.sohoki.backoffice.alcatel.transport;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;

public class JerseyRequests {

	private static final Logger LOGGER = LoggerFactory.getLogger(JerseyRequests.class);

    public static Response post(String path, Cookie cookie, Object request, Response.Status expectedStatus)
        throws OTRestClientException {

        WebTarget webTarget = JerseyClient.INSTANCE.getClient().target(path);
        Response response = webTarget.request().cookie(cookie).post(Entity.json(request));
        LOGGER.debug(response.toString());

        if ((expectedStatus != null) && (expectedStatus.getStatusCode() != response.getStatus())) {
            throw new OTRestClientException(getErrorMessage(response));
        }
        return response;
    }

    public static Response post(String path, Cookie cookie, Response.Status expectedStatus)
        throws OTRestClientException {
        return post(path, cookie, "{ }", expectedStatus);
    }

    public static Response get(String path, Cookie cookie, Response.Status expectedStatus) throws OTRestClientException {

        WebTarget webTarget = JerseyClient.INSTANCE.getClient().target(path);
        Response response = webTarget.request().cookie(cookie).get();
        LOGGER.debug(response.toString());

        if ((expectedStatus != null) && (expectedStatus.getStatusCode() != response.getStatus())) {
            throw new OTRestClientException(getErrorMessage(response));
        }
        return response;
    }

    public static Response get(String path, Response.Status expectedStatus) throws OTRestClientException {
        return get(path, null, expectedStatus);
    }

    public static Response delete(String path, Cookie cookie, Response.Status expectedStatus)
        throws OTRestClientException {

        WebTarget webTarget = JerseyClient.INSTANCE.getClient().target(path);
        Response response = webTarget.request().cookie(cookie).delete();
        LOGGER.debug(response.toString());

        if ((expectedStatus != null) && (expectedStatus.getStatusCode() != response.getStatus())) {
            throw new OTRestClientException(getErrorMessage(response));
        }
        return response;
    }

    private static String getErrorMessage(Response response) {
        StringBuilder result = new StringBuilder();
        result.append("Status ").append(response.getStatus()).append(' ').append(response.getStatusInfo());
        if (response.hasEntity()) {
            if (MediaType.APPLICATION_JSON_TYPE.equals(response.getMediaType())) {
                JsonNode jsonNode = response.readEntity(JsonNode.class);
                JsonNode message = jsonNode.get("message"); // Required
                JsonNode comment = jsonNode.get("comment"); // Optional
                result.append(" : ").append(message.getTextValue());
                if (comment != null) {
                    result.append(" (").append(comment.getTextValue()).append(')');
                }
            } else {
                result.append(" : ").append(response.readEntity(String.class));
            }
        }
        return result.toString();
    }
}
