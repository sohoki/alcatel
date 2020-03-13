package com.sohoki.backoffice.alcatel.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sohoki.backoffice.alcatel.config.ApiConfig;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.sessionschema.Service;
import com.sohoki.backoffice.alcatel.rest.data.v1_0.sessionschema.SessionInfo;
import com.sohoki.backoffice.alcatel.rest.resources.Session;

import javax.ws.rs.core.Cookie;
import java.util.List;

public class ClientSession {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientSession.class);

    private Boolean sessionOpened;
    private final Session sessions;
    private final String userLogin;
    private final String sessionPublicURL;
    private final Cookie cookie;
    private SessionInfo sessionInfo;

    public ClientSession(String userLogin, Authentication authenticationUser) {
        sessionOpened = false;
        sessions = new Session();
        this.userLogin = userLogin;
        this.cookie = authenticationUser.getCookie();
        this.sessionPublicURL = authenticationUser.getSessionPublicURL();
    }

    /**
     * Open session.<br>
     * Once authenticated a user has to open a session (it uses a cookie as identifier).
     */
    public void open() throws OTRestClientException {

        LOGGER.debug("Open sessions User {}", userLogin);
        sessionInfo = sessions.openSession(sessionPublicURL, cookie);
        LOGGER.debug("SessionInfo = {}", sessionInfo);
        sessionOpened = true;
    }

    /**
     * Keep session alive.<br>
     * Once open, the session will be closed by the server after the timeToLive timer without Session activity.<br>
     * Calling the keepalive will restart the timeout.<br>
     * By default, the duration of a Session is the one defined in administration.
     */
    public void keepSessionAlive() throws OTRestClientException {

        if (sessionOpened) {
            LOGGER.debug("Keep alive the opened session for User {}", userLogin);
            sessions.keepAlive(sessionPublicURL, cookie);
            LOGGER.debug("Session keepalive accepted");
        }
    }

    /**
     * Close session.<br>
     * An opened session should be closed (released) properly, however it will be automatically closed
     * after the session timer ends. It is possible to restart the session timer to maintain the session open.
     */
    public void close() throws OTRestClientException {

        if (sessionOpened) {
            LOGGER.debug("Close sessions User {}", userLogin);
            sessions.closeSession(sessionPublicURL, cookie);
            sessionOpened = false;
        }
    }

    /**
     * Get session info
     * @return session info
     */
    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    /**
     * Get service path
     * @param serviceName the service
     * @return service path (= URL + service)
     */
    public String getServicePath(String serviceName) {

        if (sessionOpened) {
            List<Service> serviceList = sessionInfo.getServices();
            for (Service service : serviceList) {
                if (service.getServiceName().equals(serviceName)) {
                    return sessionInfo.getPublicBaseUrl() + service.getRelativeUrl();
                }
            }
        }
        return ApiConfig.INSTANCE.getRootPath();
    }
}
