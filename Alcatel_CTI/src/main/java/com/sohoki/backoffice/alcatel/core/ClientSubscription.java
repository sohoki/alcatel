package com.sohoki.backoffice.alcatel.core;

import org.codehaus.jackson.JsonNode;
import org.glassfish.jersey.client.ChunkedInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.subscription.Subscription;
import com.sohoki.backoffice.alcatel.rest.resources.EventSubscription;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.util.concurrent.*;


public class ClientSubscription {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientSubscription.class);
    private static final String MESSAGE_EVENT_TYPE = "Message";
    private static final String ON_CALL_CREATED_EVENT_TYPE = "OnCallCreated";
    private static final String EVENT_NAME_FIELD = "eventName";
    private static final String CALL_REF_FIELD = "callRef";
    private static final String TEXT_FIELD = "text";
    private static final String ON_COM_RECORD_CREATED_EVENT_TYPE = "OnComRecordCreated";
    private static final String RECORD_FIELD = "record";
    private static final String PARTICIPANTS_FIELD = "participants";
    private static final String ANSWERED_FIELD = "answered";
    private static final String REASON_FIELD = "reason";
    private static final Integer SUBSCRIPTION_TIMEOUT = 10; //Server's default subscription time is 10 minutes

    private final String userLogin;
    private final EventSubscription eventSubscription;
    private Subscription subscription;
    private ExecutorService executorService = null;

    public ClientSubscription(String userLogin, Cookie cookie, ClientSession sessionUser) {
        this.userLogin = userLogin;
        this.eventSubscription = new EventSubscription(cookie, sessionUser);
    }

    /**
     * Subscribe to the given service
     *
     * @param service to subscribe to
     */
    public void subscribe(String service) throws OTRestClientException {

        subscription = eventSubscription.subscribe(service, SUBSCRIPTION_TIMEOUT, userLogin);
    }

    /**
     * Once no more needed we have to finnish the subscription
     */
    public void unsubscribe() throws OTRestClientException {

        if (subscription != null) {
            LOGGER.debug("Unsubscribe for {}", userLogin);
            Response response = eventSubscription.unsubscribe(subscription.getSubscriptionId());
            LOGGER.debug("Unsubscribe response: " + response);

            if (executorService != null) {
                executorService.shutdownNow();
            }
        } else {
            LOGGER.debug("Unsubscribe not necessary for {}", userLogin);
        }

    }

    /**
     * First the method opens (in a new thread) the chunk-receiving chanel, using the given URL.<br>
     * Then this new thread reads the chunks until it receives a callRef contained in an OnCallCreated event.
     *
     * @return the found callRef (otherwise an empty string)
     * @throws OTRestClientException when the chunk input has been closed, without having found the callRef
     */
    public Future<String> waitForCallRefInOnCallCreated() throws OTRestClientException {

        if (subscription == null) {
            LOGGER.error("Subscription is null, can not receive a call ref");
            throw new OTRestClientException("you should have called subscribe() before ");
        }

        LOGGER.debug("Url for chunks : {}", subscription.getPrivatePollingUrl());
        executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,           // so the created thread will die after the task has been executed
                new SynchronousQueue<Runnable>());

        Future<String> result = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                final ChunkedInput<JsonNode> chunkedInput = eventSubscription.getJsonNodeChunkedInput(subscription.getPrivatePollingUrl());
                JsonNode chunk;

                // Wait for callRef in onCallCreated event
                while ((chunk = chunkedInput.read()) != null) {
                    LOGGER.debug("Next chunk received: {}", chunk);

                    String eventName = chunk.get(EVENT_NAME_FIELD).getTextValue();
                    if (ON_CALL_CREATED_EVENT_TYPE.equals(eventName)) {
                        // Now get the call reference
                        String callRef = chunk.get(CALL_REF_FIELD).getTextValue();
                        if (callRef != null) {
                            LOGGER.debug("Found callRef: {}", callRef);
                            chunkedInput.close();
                            return callRef;
                        }
                    }

                }
                throw (new OTRestClientException("The chunk input has been closed, without having found the callRef."));
            }
        });

        return result;
    }
    /**
     * First the method opens (in a new thread) the chunk-receiving chanel, using the given URL.<br>
     * Then this new thread reads the chunks until it receives a OnComRecordCreated communication log.
     *
     * @return the OnComRecordCreated reason
     * @throws OTRestClientException when no reason was found in the event
     */
    public Future<String> waitForMissedCallEvent() throws OTRestClientException {

        if (subscription == null) {
            LOGGER.error("Subscription is null, can not receive a call ref");
            throw new OTRestClientException("you should have called subscribe() before ");
        }

        LOGGER.debug("Url for chunks : {}", subscription.getPrivatePollingUrl());
        executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,           // so the created thread will die after the task has been executed
                new SynchronousQueue<Runnable>());

        Future<String> result = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                final ChunkedInput<JsonNode> chunkedInput = eventSubscription.getJsonNodeChunkedInput(subscription.getPrivatePollingUrl());
                JsonNode chunk;

                // Wait for callRef in onCallCreated event
                while ((chunk = chunkedInput.read()) != null) {
                    LOGGER.debug("Next com log chunk received: {}", chunk);
                    String eventName = chunk.get(EVENT_NAME_FIELD).getTextValue();
                    if (ON_COM_RECORD_CREATED_EVENT_TYPE.equals(eventName)) {
                        chunkedInput.close();

                        JsonNode record = chunk.get(RECORD_FIELD);
                        if (record == null) {
                            throw (new OTRestClientException("No Record field in received Communication Log."));
                        }
                        JsonNode participants = record.get(PARTICIPANTS_FIELD);
                        if (participants == null) {
                            throw (new OTRestClientException("No Participants field in received Communication Log."));
                        }

                        // "participants" is an array
                        for (JsonNode participant : participants) {
                            JsonNode answered = participant.get(ANSWERED_FIELD);
                            if (answered != null) {
                                return answered.getTextValue();
                            }
                        }
                        throw new OTRestClientException("No Reason field in received Communication Log.");
                    }

                }
                throw new OTRestClientException("The chunk input has been closed, without having found the communication log.");
            }
        });

        return result;
    }

    /**
     * Keep alive for the subscription and event logging.<br>
     * <br>
     * First the method opens the chunk receiving chanel, using the given URL.<br>
     * Then it initiates a thread which reads the chunks. The chunk input is read and logged until it is
     * closed: it first receives error event message and then the next read will returns <code>null</code>.
     * The client restarts the chunk reader as soon as it returns <code>null</code>.<br>
     * Note that the thread will not stop by itself, it has to be interrupted.
     */
    public void keepAliveAndLogEvents() throws OTRestClientException {

        if (subscription == null) {
            LOGGER.error("Subscription is null, can not receive a call ref");
            throw new OTRestClientException("You should have called subscribe() before ");
        }

        LOGGER.debug("Url for chunks : {}", subscription.getPrivatePollingUrl());
        executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,           // so the created thread will die after the task has been executed
                new SynchronousQueue<Runnable>());

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                ChunkedInput<JsonNode> chunkedInput;
                JsonNode chunk;

                while (true) {
                    try {
                        chunkedInput = eventSubscription.getJsonNodeChunkedInput(subscription.getPrivatePollingUrl());
                    } catch (OTRestClientException e) {
                        LOGGER.error("getJsonNodeChunkedInput refused : {}", e);
                        return;
                    }

                    while ((chunk = chunkedInput.read()) != null) {
                        LOGGER.debug("Next chunk received: {}", chunk);
                        String eventName = chunk.get(EVENT_NAME_FIELD).getTextValue();
                        if (MESSAGE_EVENT_TYPE.equals(eventName)) {
                            String textValue = chunk.get(TEXT_FIELD).getTextValue();
                            if (textValue != null && textValue.startsWith("ERROR: Socket Closed")) {
                                LOGGER.debug("Socket Closed: we exit the loop at next read (which gives null back).");
                            }
                        }
                    }

                    if (Thread.currentThread().isInterrupted()) {
                        LOGGER.debug("Haha, interrupted!");
                        Thread.currentThread().interrupt(); // TODO check, I do not remember the right method
                        chunkedInput.close();
                        return;
                    }

                    LOGGER.debug(" -> Restart the chunk"); // At next loop
                }
            }
        });
    }
}
