package com.sohoki.backoffice.alcatel.transport;

import com.sohoki.backoffice.alcatel.config.ApiConfig;
import com.sohoki.backoffice.alcatel.core.exception.OTRestClientException;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public enum JerseyClient {

	INSTANCE; // This is a singleton.

    Client client = null;

    public Client getClient() throws OTRestClientException {
        if (client == null) {
            try {
                client = createClient();
            } catch (KeyManagementException | IOException e) {
                throw new OTRestClientException("Error to create Jersey Client ",  e);
            }
        }
        return client;
    }

    public void close() {
        if (client != null) {
            client.close();
            client = null;
        }
    }

    private Client createClient() throws KeyManagementException, IOException {

        // log traces in a configured file
        ClientConfig clientConfig = new ClientConfig(JacksonJsonProvider.class);
        FileHandler fileHandler = new FileHandler(ApiConfig.INSTANCE.getApiLogFile());
        Logger logger = Logger.getLogger("sample.transport.JerseyClient");
        logger.addHandler(fileHandler);
        LoggingFilter loggingFilter = new LoggingFilter(logger, true);
        clientConfig.register(loggingFilter);

        SSLContext sslContext = SslConfigurator.newInstance().createSSLContext();
        sslContext.init(null, new TrustManager[ ] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType)
                            throws CertificateException {
                    }
                }
        }, new SecureRandom());

        Client client = ClientBuilder.newBuilder().withConfig(clientConfig).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        }).sslContext(sslContext).build();

        client.property(ClientProperties.FOLLOW_REDIRECTS, Boolean.FALSE);

        return client;
    }
}
