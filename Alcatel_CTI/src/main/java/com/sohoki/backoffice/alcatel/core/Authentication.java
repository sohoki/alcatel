package com.sohoki.backoffice.alcatel.core;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonNode;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohoki.backoffice.alcatel.config.ApiConfig;
import com.sohoki.backoffice.alcatel.config.UserConfig;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.versionschema.RestApiDescriptor;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.versionschema.Version;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.versionschema.Versions;
import com.sohoki.backoffice.alcatel.transport.JerseyClient;
import com.sohoki.backoffice.alcatel.transport.JerseyRequests;

public class Authentication {

	private static final Logger LOGGER = LoggerFactory.getLogger(Authentication.class);
    private static final String USER_COOKIE_NAME = "AlcUserId";

    private Cookie cookie;
    private String sessionPublicUrl;

    /**
     * Main method the user has to call to do it's authentication.
     * @throws sample.core.exception.OTRestClientException each authentication step can throw this exception
     */
    public final void authenticate(UserConfig userConfig) throws OTRestClientException {

        Versions versions = getAvailableApiVersions(ApiConfig.INSTANCE.getRootPath());

 //       Response response = initiateAuthentication(getAuthenticatePublicURL(versions));

        Response response = performBasicAuthentication(getAuthenticatePublicURL(versions), userConfig.getLogin(), userConfig.getPassword());
        Map<String,NewCookie> cookies = response.getCookies();
        this.cookie = cookies.get(USER_COOKIE_NAME);
        LOGGER.debug("cookies = {}", cookies);

 //       response = authenticateWithAlcUserId(response.getLocation());
        JsonNode jsonNode = response.readEntity(JsonNode.class);
        this.sessionPublicUrl = jsonNode.get("publicUrl").getTextValue();
        LOGGER.debug("sessionPublicUrl = {}", sessionPublicUrl);
    }

    public Cookie getCookie() {
        return cookie;
    }

    private Versions getAvailableApiVersions(String location) throws OTRestClientException {
        Response response = JerseyRequests.get(location, Status.OK);
        RestApiDescriptor desc = response.readEntity(RestApiDescriptor.class);
        Versions versions = new Versions();
        versions.setVersions(desc.getVersions());
        LOGGER.debug("versions = {}", versions);
        return versions;
    }

    private Response initiateAuthentication(String location) throws OTRestClientException {
        return JerseyRequests.get(location, Status.UNAUTHORIZED);
    }

    private Response performBasicAuthentication(String location, String login, String password) throws OTRestClientException {

        WebTarget webTarget = JerseyClient.INSTANCE.getClient().target(location);
        Response response = webTarget.request().get();
        LOGGER.debug("performBasicAuthentication response = {}", response);

        if (Status.UNAUTHORIZED.getStatusCode() != response.getStatus())  {
            throw new OTRestClientException(getErrorMessage(response, "not expected"));
        }

        webTarget = JerseyClient.INSTANCE.getClient().target(location);
        HttpAuthenticationFeature httpBasicAuthFilter = HttpAuthenticationFeature.basic(login, password);
        response = webTarget.register(httpBasicAuthFilter).request().accept(MediaType.APPLICATION_JSON_TYPE).get();
        LOGGER.debug("performBasicAuthentication response = {}", response);

        if (Status.UNAUTHORIZED.getStatusCode() == response.getStatus())  {
            throw new OTRestClientException(getErrorMessage(response, "wrong user login or password"));
        }
        return response;
    }

    private Response authenticateWithAlcUserId(URI location) throws OTRestClientException {
        return JerseyRequests.get(location.toString(), cookie, Status.OK);
    }

    private String getAuthenticatePublicURL(Versions versions) throws OTRestClientException {

        String apiVersion = ApiConfig.INSTANCE.getApiVersion();
        List<Version> versionsList = versions.getVersions();
        for (Version version : versionsList) {
            if (version.getId().equals(apiVersion)) {
                return version.getPublicUrl();
            }
        }
        throw new OTRestClientException("API " + apiVersion + " not found in " + versionsList);
    }

    /**
     * Get the public url of the session resource. This url is given at the end of the authentication.
     * @return Public URL for session handling
     */
    public String getSessionPublicURL() {
        if (sessionPublicUrl== null) {
            LOGGER.error("getSessionPublicURL - Need to be authenticated before getting session public url");
            return "";
        } else {
            return sessionPublicUrl;
        }
    }

    private String getErrorMessage(Response response, String message) {
        return "Status " + response.getStatus() + " " + response.getStatusInfo() + " : " + message;
    }
}
