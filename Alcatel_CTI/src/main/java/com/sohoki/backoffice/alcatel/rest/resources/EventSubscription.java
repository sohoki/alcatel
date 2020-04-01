package com.sohoki.backoffice.alcatel.rest.resources;

import org.codehaus.jackson.JsonNode;
import org.glassfish.jersey.client.ChunkedInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sohoki.backoffice.alcatel.config.ApiConfig;
import com.sohoki.backoffice.alcatel.core.ClientSession;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription.Filter;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription.Selectors;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription.Subscription;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription.SubscriptionRequest;
import com.sohoki.backoffice.alcatel.transport.JerseyRequests;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class EventSubscription {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventSubscription.class);

    private final Cookie cookie;
    private final String subscriptionPath;

    public EventSubscription(Cookie cookie, ClientSession sessionUser) {
        this.cookie = cookie;
        this.subscriptionPath = sessionUser.getServicePath(Services.SUBSCRIPTIONS.getService());
        //추가 수정 필요
        //this.subscriptionPath = sessionUser.getServicePath("http://otwog.hankooktech.com"+Services.SUBSCRIPTIONS.getService());
        LOGGER.debug("subscriptionPath = {}", subscriptionPath);
    }

    /**
     * Subscribe to the given service.
     *
     * @param service to subscribe to
     * @return the response to the subscription in Subscription.class format
     */
    public Subscription subscribe(String service, Integer keepAliveTimer, String userLogin) throws OTRestClientException {

        String[] arguments = new String[]{service, userLogin, keepAliveTimer.toString()};
        LOGGER.debug("Subscribing to {} events for user {} with {} minutes of keep alive timer.", arguments);

        final Selectors selector = new Selectors();
        selector.getIds().add(userLogin);
        selector.getNames().add(service);

        final Filter filter = new Filter();
        filter.getSelectors().add(selector);

        // Default format is JSON, default mode is CHUNK and default timeout is 10 minutes
        final SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
        subscriptionRequest.setFilter(filter);
        subscriptionRequest.setTimeout(keepAliveTimer); // If not set here, the default timer is 10 minutes.
        subscriptionRequest.setVersion(ApiConfig.INSTANCE.getApiVersion());
        LOGGER.debug("subscriptionRequest is {}", subscriptionRequest.toString());

        Response response = JerseyRequests.post(subscriptionPath, cookie, subscriptionRequest, Response.Status.OK);
        LOGGER.debug("subscriptionPath = {}, response = {}", subscriptionPath, response);

        return response.readEntity(Subscription.class);
    }

    public Response unsubscribe(String subscriptionId) throws OTRestClientException {
        final String unsubscriptionPath = subscriptionPath + "/" + subscriptionId;
        return JerseyRequests.delete(unsubscriptionPath, cookie, Response.Status.NO_CONTENT);
    }

    public ChunkedInput<JsonNode> getJsonNodeChunkedInput(String privateUrlForChunks) throws OTRestClientException {
        final Response response = JerseyRequests.get(privateUrlForChunks, cookie, null);
        return  response.readEntity(new GenericType<ChunkedInput<JsonNode>>() { });
    }
}
